package Managers;

import Exception.ManagerSaveException;

import Tasks.*;

import java.io.*;
import java.nio.file.Files;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTasksManager {
    private final File file;
    public FileBackedTasksManager (File file) {
        this.file = file;
    }
    public static void main (String[] args) throws FileNotFoundException {
        FileBackedTasksManager fileBackedTasksManager1 = new FileBackedTasksManager(new File("data\\csv.csv"));
        Task task1 = new Task("Забрать книги","Поехать в пункт выдачи", TaskType.TASK, Status.NEW);
        fileBackedTasksManager1.createTask(task1);
        Task task2 = new Task("Сходить на шашлыки", "Собрать друзей", TaskType.TASK, Status.NEW);
        fileBackedTasksManager1.createTask(task2);
        Epic epic1 = new Epic("Почистить пк", "Чистка пк", TaskType.EPIC);
        fileBackedTasksManager1.createEpic(epic1);
        Subtask subtask1 = new Subtask( "Купить термопасту", "Сходить в днс", TaskType.SUBTASK, Status.NEW, 1);
        fileBackedTasksManager1.createSubTask(subtask1, epic1.getId());
        Subtask subtask2 = new Subtask("Разобрать пк", "Разобрать и собрать пк", TaskType.SUBTASK,Status.NEW, 1);
        fileBackedTasksManager1.createSubTask(subtask2, epic1.getId());
        Epic epic2 = new Epic(6,"Сделать стол", "Сделать самому рабочий стол", TaskType.EPIC);
        fileBackedTasksManager1.createEpic(epic2);
        Subtask subtask3 = new Subtask("Закупить стройматериалы", "Найти нужные материалы и сделать стол", TaskType.SUBTASK, Status.NEW, 2);
        fileBackedTasksManager1.createSubTask(subtask3, epic2.getId());
        fileBackedTasksManager1.getTasksById(task1.getId());
        fileBackedTasksManager1.getTasksById(task2.getId());
        fileBackedTasksManager1.getEpicsById(epic1.getId());
        fileBackedTasksManager1.getEpicsById(epic2.getId());
        FileBackedTasksManager fileBackedTasksManager2 = loadFromFile(new File("data\\csv.csv"));
        System.out.println(fileBackedTasksManager2.getTasks());
        System.out.println(fileBackedTasksManager2.getEpics());
        System.out.println(fileBackedTasksManager2.getSubTasks());

    }
    public void save() throws ManagerSaveException {
        File file = new File ("data\\csv.csv");
        try (BufferedWriter write = new BufferedWriter(new FileWriter(file))) {
            write.write(CSVFormatter.getHeader());
            write.newLine();
            for (Map.Entry<Integer,Task> entry : tasks.entrySet()) {
                final Task task = entry.getValue();
                write.write(CSVFormatter.taskToString(task));
                write.newLine();
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                final Epic epic = entry.getValue();
                write.write(CSVFormatter.epicToString(epic));
                write.newLine();
            }
            for (Map.Entry<Integer, Subtask> entry : subtasks.entrySet()) {
                final Subtask subtask = entry.getValue();
                write.write(CSVFormatter.subtaskToString(subtask));
                write.newLine();
            }
            write.newLine();
            write.write(CSVFormatter.historyToString(this.inMemoryHistoryManager));
            write.newLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить файл: " + file.getName() + " .");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws FileNotFoundException {
        final FileBackedTasksManager tasksManager = new FileBackedTasksManager(file);
        try {
            String csv = Files.readString(file.toPath());
            String[] lines = csv.split(System.lineSeparator());
            List<Integer> history = null;
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    history = CSVFormatter.historyFromString(lines[i + 1]);
                    break;
                 }
                String[] type = line.split(",");
                String typeTask = type[3];
                switch (typeTask){
                    case "TASK" :
                        Task task = CSVFormatter.taskFromString(lines[i]);
                        tasksManager.tasks.put(task.getId(), task);
                        break;
                    case "EPIC" :
                        Epic epic = CSVFormatter.epicFromString(lines[i]);
                        tasksManager.epics.put(epic.getId(), epic);
                        break;
                    case "SUBTASK" :
                        Subtask subtask = CSVFormatter.subtaskFromString(lines[i]);
                        tasksManager.subtasks.put(subtask.getId(), subtask);
                        break;
                }
            }
            for (Integer taskId : history) {
                tasksManager.historyManager.add(tasksManager.findTask(taskId));
            }
        } catch(IOException e) {
            throw new FileNotFoundException("Файл: " + file.getName() + " не может быть прочитан.");
        }
        return tasksManager;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public ArrayList<Subtask> getSubTasks() {
        return super.getSubTasks();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public Task getTasksById(int id) {
        final Task task = super.getTasksById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicsById(int id) {
        final Epic epic = super.getEpicsById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubTasksById(int id) {
        final Subtask subtask = super.getSubTasksById(id);
        save();
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask createSubTask(Subtask subtask, int epicId) {
        if (subtask != null && super.checkId(epicId)) {
            super.createSubTask(subtask, epicId);
            save();
            return subtask;
        }
        return  null;
    }

    @Override
    public void updateTask(Task task, Status status) {
        super.updateTask(task, status);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask, Status status, int idEpic) {
        super.updateSubTask(subtask, status, idEpic);
        save();
    }

    @Override
    public void removeTaskById(int Id) {
        super.removeTaskById(Id);
        save();
    }

    @Override
    public void removeEpicById(int Id) {
        super.removeEpicById(Id);
        save();
    }

    @Override
    public void removeSubTaskById(int Id) {
        super.removeSubTaskById(Id);
            save();
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasksList(Epic epic) {
        ArrayList<Subtask> subtasksList = super.getEpicSubtasksList(epic);
        save();
        return subtasksList;
    }
}
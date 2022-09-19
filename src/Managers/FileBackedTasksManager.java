package Managers;

import Exception.ManagerSaveException;
import Tasks.*;

import java.io.*;
import java.nio.file.Files;

import java.util.List;
import java.util.ArrayList;

public class FileBackedTasksManager extends InMemoryTasksManager {
    public static void main (String[] args) throws FileNotFoundException {
        File file = new File("data\\csv.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        Task task1 = new Task("Забрать книги","Поехать в пункт выдачи", TaskType.TASK, Status.NEW);
        fileBackedTasksManager.createTask(task1);
        Task task2 = new Task("Сходить на шашлыки", "Собрать друзей", TaskType.TASK, Status.NEW);
        fileBackedTasksManager.createTask(task2);
        Epic epic1 = new Epic("Почистить пк", "Чистка пк", TaskType.EPIC);
        fileBackedTasksManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Купить термопасту", "Сходить в днс",1, TaskType.SUBTASK);
        fileBackedTasksManager.createSubTask(subtask1, epic1.getId());
        Subtask subtask2 = new Subtask("Разобрать пк", "Разобрать и собрать  пк",1, TaskType.SUBTASK);
        fileBackedTasksManager.createSubTask(subtask2, epic1.getId());
        Epic epic2 = new Epic("Сделать стол", "Сделать самому рабочий стол", TaskType.EPIC);
        fileBackedTasksManager.createEpic(epic2);
        Subtask subtask3 = new Subtask("Закупить стройматериалы", "Найти нужные материалы и сделать стол",2, TaskType.SUBTASK);
        fileBackedTasksManager.createSubTask(subtask3, epic2.getId());
        fileBackedTasksManager.getTasksById(task1.getId());
        fileBackedTasksManager.getTasksById(task2.getId());
        fileBackedTasksManager.getEpicsById(epic1.getId());
        fileBackedTasksManager.getEpicsById(epic2.getId());
        loadFromFile(file);
    }
    public void save() throws ManagerSaveException {
        File file = new File ("data\\csv.csv");
        try {
            String head = "id,name,description,type,status,epicId\n";
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(head);
            for (Task task : tasks.values()) {
                fileWriter.write(task.toString() + System.lineSeparator());
            }
            for (Epic epic : epics.values()) {
                fileWriter.write(epic.toString() + System.lineSeparator());
            }
            for (Subtask subtask : subtasks.values()) {
                fileWriter.write(subtask.toString() + System.lineSeparator());
            }
            fileWriter.write(System.lineSeparator());
            fileWriter.write(CSVFormatter.toString(this.inMemoryHistoryManager));
            fileWriter.close();
        } catch(IOException e) {
            throw new ManagerSaveException("Не удалось сохранить файл.");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws FileNotFoundException {
        final FileBackedTasksManager tasksManager = new FileBackedTasksManager();
        List<Integer> history = null;
        try {
            String csv = Files.readString(file.toPath());
            String[] lines = csv.split(System.lineSeparator());
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.isEmpty()) {
                    line = lines[i + 1];
                    history = CSVFormatter.historyFromString(line);
                    break;
                }
                String[] type = line.split(",");
                String typeTask = type[1];
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
                        Subtask subtask = CSVFormatter.subtaskFromString(lines[1]);
                        tasksManager.subtasks.put(subtask.getId(), subtask);
                        break;
                }
            }
        } catch(IOException e) {
            throw new FileNotFoundException("Файл не может быть прочитан.");
        }
        //Оставляю для проверки работы метода. Почему-то не видит строки с тасками:(
        System.out.println(tasksManager.inMemoryHistoryManager.getHistory());
        System.out.println(tasksManager.tasks);
        System.out.println(tasksManager.epics);
        System.out.println(tasksManager.subtasks);
        System.out.println(history);
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
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getTasksById(int id) {
        Task task = super.getTasksById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public Epic getEpicsById(int id) {
        Epic epic = super.getEpicsById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return epic;
    }

    @Override
    public Subtask getSubTasksById(int id) {
        Subtask subtask = super.getSubTasksById(id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return epic;
    }

    @Override
    public Subtask createSubTask(Subtask subtask, int epicId) {
        if (subtask != null && super.checkId(epicId)) {
            super.createSubTask(subtask, epicId);
            try {
                save();
            } catch (ManagerSaveException e) {
                e.printStackTrace();
            }
            return subtask;
        }
        return  null;
    }

    @Override
    public void updateTask(Task task, Status status) {
        super.updateTask(task, status);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSubTask(Subtask subtask, Status status, int idEpic) {
        super.updateSubTask(subtask, status, idEpic);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTaskById(int Id) {
        super.removeTaskById(Id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEpicById(int Id) {
        super.removeEpicById(Id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSubTaskById(int Id) {
        super.removeSubTaskById(Id);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasksList(Epic epic) {
        ArrayList<Subtask> subtasksList = super.getEpicSubtasksList(epic);
        try {
            save();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
        return subtasksList;
    }
}
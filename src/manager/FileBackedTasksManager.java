package manager;

import Exception.*;
import tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTasksManager {
    private File file;
    /*private String fileName;*/

    public FileBackedTasksManager(File file) {
        this.file = file;

        /*fileName = "C:\\Users\\theha\\dev\\java-kanban\\data\\data.csv";
        file = new File(fileName);
        if (!file.isFile()) {
            try {
                Path path = Files.createFile(Paths.get(fileName));
            } catch (IOException e) {
                throw new ManagerSaveException("Ошибка создания файла.");
            }
        }*/ //файл уже создан и в main указан путь.

    }

    public static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try {
            String read = Files.readString(Path.of(file.getAbsolutePath()));
            String[] lines = read.split("\n");
            List<String> epics = new ArrayList<>();
            List<String> subtasks = new ArrayList<>();
            List<String> tasks = new ArrayList<>();
            String lineOfHistory = "";
            boolean isTitle = true;
            boolean itsTask = true;
            int maxId = 0;
            int id;

            for (String line : lines) {
                if (isTitle) {
                    isTitle = false;
                    continue;
                }
                if (line.isEmpty() || line.equals("\r")) {
                    itsTask = false;
                    continue;
                }
                if (itsTask) {
                    TaskType taskType = TaskType.valueOf(line.split(",")[1]);
                    switch (taskType) {
                        case EPIC:
                            Epic epic = (Epic) fromString(line, TaskType.EPIC, fileBackedTasksManager);
                            id = epic.getId();
                            if (id > maxId) {
                                maxId = id;
                            }
                            fileBackedTasksManager.epics.put(id, epic);
                            epics.add(line);
                            break;

                        case SUBTASK:
                            Subtask subtask = (Subtask) fromString(line, TaskType.SUBTASK, fileBackedTasksManager);
                            id = subtask.getId();
                            if (id > maxId) {
                                maxId = id;
                            }
                            fileBackedTasksManager.subtasks.put(id, subtask);
                            subtasks.add(line);
                            break;

                        case TASK:
                            Task task = fromString(line, TaskType.TASK, fileBackedTasksManager);

                            id = task.getId();
                            if (id > maxId) {
                                maxId = id;
                            }
                            fileBackedTasksManager.tasks.put(id, task);
                            tasks.add(line);
                            break;

                    }
                } else {
                    lineOfHistory = line;
                }
            }
            fileBackedTasksManager.id = maxId;
            List<Integer> ids = fromString(lineOfHistory);
            for (Integer taskId : ids) {
                fileBackedTasksManager.historyManager.add(getTaskAllKind(taskId, fileBackedTasksManager));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла.");
        }
        return fileBackedTasksManager;
    }

    private static Task getTaskAllKind(int id, FileBackedTasksManager fileBackedTasksManager) { //Тут поменял InMemoryHistoryManager inMemoryHistoryManager
        Task task = fileBackedTasksManager.getTasks().get(id);
        if (!(task == null)) {
            return task;
        }
        Task epic = fileBackedTasksManager.getEpics().get(id);
        if (!(epic == null)) {
            return epic;
        }
        Task subtask = fileBackedTasksManager.getSubTasks().get(id);
        if (!(subtask == null)) {
            return subtask;
        }
        return null;
    }

    private static String toString(HistoryManager manager) {
        List<String> s = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            s.add(String.valueOf(task.getId()));
        }
        String hist = String.join(",", s);
        return hist;
    }

    private static List<Integer> fromString(String value) {
        String[] idsString = value.split(",");
        List<Integer> tasksId = new ArrayList<>();
        for (String idString : idsString) {
            tasksId.add(Integer.valueOf(idString));
        }
        return tasksId;
    }

    private static Task fromString(String value, TaskType taskType, FileBackedTasksManager fileBackedTasksManager) {
        String[] dataOfTask = value.split(",", 6);
        int id = Integer.valueOf(dataOfTask[0]);
        String name = dataOfTask[2];
        Status status = Status.valueOf(dataOfTask[3]);
        String description = dataOfTask[4];
        String epicIdString = dataOfTask[5].trim();

        switch (taskType) {
            case TASK:
                return new Task(id, name, description, status);
            case EPIC:
                return new Epic(id, name, status, description);
            case SUBTASK:
                return new Subtask(id, name, description, status, fileBackedTasksManager.epics.get(Integer.valueOf(epicIdString)));
            default:
                return null;
        }
    }

@Override
    public List<Task> history() {
    return super.history();
}

public void save() {
    try (Writer writer = new FileWriter(file)) {
        writer.write("id,type,name,status,description,epic\n");
        HashMap<Integer, String> allTasks = new HashMap<>();

        HashMap<Integer, Task> tasks = super.getTasks();
        for (Integer id : tasks.keySet()) {
            allTasks.put(id, tasks.get(id).toStringFromFile());
        }

        HashMap<Integer, Epic> epics = super.getEpics();
        for (Integer id : epics.keySet()) {
            allTasks.put(id, epics.get(id).toStringFromFile());
        }

        HashMap<Integer, Subtask> subtasks = super.getSubTasks();
        for (Integer id : subtasks.keySet()) {
            allTasks.put(id, subtasks.get(id).toStringFromFile());
        }

        for (String value : allTasks.values()) {
            writer.write(String.format("%s\n", value));
        }
        writer.write("\n");
        writer.write(toString(this.historyManager));
    }catch (IOException e) {
        throw new ManagerSaveException("Ошибка записи файла.");
    }
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
        Task task = super.getTasksById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicsById(int id) {
        Epic epic = super.getEpicsById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubTasksById(int id) {
        Subtask subtask = super.getSubTasksById(id);
        save();
        return subtask;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubTask(Subtask subtask) {
        super.createSubTask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.updateSubTask(subtask);
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
}

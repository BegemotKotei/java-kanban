package managers;

import tasks.*;

import java.util.ArrayList;
import java.util.List;

public class CSVFormatter {

    public static List<Integer> historyFromString(String value) { //Метод восстановления менеджера из csv.
        List<Integer> result = new ArrayList<>();
        String[] lines = value.split(",");
        for(String line : lines) {
            result.add(Integer.parseInt(line));
        }
        return result;
    }

    public static Task taskFromString(String value) { //Метод создания task из строк.
        String[] lines = value.split(",");
        int id = Integer.parseInt(lines[0]);
        String name = lines[1];
        String description = lines[2];
        TaskType taskType = typeFromString(lines[3]);
        Status status = statusFromString(lines[4]);

        Task task = new Task(name, description,  taskType, status);
        task.setId(id);
        task.setStatus(status);
        return task;
    }

    public static Epic epicFromString(String value) { //Метод создания epic из строк.
        String[] lines = value.split(",");
        int id = Integer.parseInt(lines[0]);
        String name = lines[1];
        String description = lines[2];
        TaskType taskType = typeFromString(lines[3]);
        Status status = statusFromString(lines[4]);

        Epic epic = new Epic(name, description, taskType);
        epic.setId(id);
        epic.setStatus(status);
        return epic;
    }

    public static Subtask subtaskFromString(String value) { //Метод создания subtask из строк.
        String[] lines = value.split(",");
        int id = Integer.parseInt(lines[0]);
        String name = lines[1];
        String description = lines[2];
        TaskType taskType = typeFromString(lines[3]);
        Status status = statusFromString(lines[4]);
        int epicId = Integer.parseInt(lines[5]);

        Subtask subtask = new Subtask(name, description, taskType, status, epicId);
        subtask.setId(id);
        subtask.setStatus(status);
        return subtask;
    }
    public static TaskType typeFromString(String string) {
        switch (string) {
            case "TASK":
                return TaskType.TASK;
            case "EPIC":
                return TaskType.EPIC;
            case "SUBTASK":
                return TaskType.SUBTASK;
        }
        return null;
    }
    public static Status statusFromString (String string) {
        switch (string) {
            case "NEW":
                return Status.NEW;
            case "IN_PROGRESS":
                return Status.IN_PROGRESS;
            case "DONE":
                return Status.DONE;
        }
        return null;
    }
    public static String getHeader() {
        return  "id,name,description,type,status,epicId";
    }
    static String historyToString(HistoryManager manager) { //Метод сохранения задачи в строку.
        List<Task> tasks = manager.getHistory();
        List<String> ids = new ArrayList<>();
        for (Task task : tasks) {
            int id = task.getId();
            ids.add(String.valueOf(id));
        }
        return String.join(",", ids);
    }
    public static String taskToString(Task task) {
        return String.format("%s,%s,%s,%s,%s", task.getId(), task.getName(), task.getDescription(), task.getTaskType(), task.getStatus());
    }
    public static String epicToString (Epic epic) {
        return String.format("%s,%s,%s,%s,%s", epic.getId(), epic.getName(), epic.getDescription(), epic.getTaskType(), epic.getStatus());
    }
    public static String subtaskToString (Subtask subtask) {
        return String.format("%s,%s,%s,%s,%s,%s", subtask.getId(), subtask.getName(), subtask.getDescription(), subtask.getTaskType(), subtask.getStatus(), subtask.getIdEpic());
    }
}

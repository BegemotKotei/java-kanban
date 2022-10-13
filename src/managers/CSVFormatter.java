package managers;

import tasks.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVFormatter {

    static DateTimeFormatter outputFormatter =  DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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
        TaskType taskType = typeFromString(lines[1]);
        String name = lines[2];
        Status status = statusFromString(lines[3]);
        String description = lines[4];
        LocalDateTime startTime = LocalDateTime.parse(lines[5], outputFormatter);
        long duration = Long.parseLong(lines[6]);
        LocalDateTime endTime = LocalDateTime.parse(lines[7], outputFormatter);

        Task task = new Task(name, description, taskType, startTime, duration);
        task.setId(id);
        task.setStatus(status);
        task.setEndTime(endTime);
        return task;
    }

    public static Epic epicFromString(String value) { //Метод создания epic из строк.
        String[] lines = value.split(",");
        int id = Integer.parseInt(lines[0]);
        TaskType taskType = typeFromString(lines[1]);
        String name = lines[2];
        Status status = statusFromString(lines[3]);
        String description = lines[4];
        LocalDateTime startTime = LocalDateTime.parse(lines[5], outputFormatter);
        long duration = Long.parseLong(lines[6]);
        LocalDateTime endTime = LocalDateTime.parse(lines[7], outputFormatter);

        Epic epic = new Epic(name, description, taskType);
        epic.setId(id);
        epic.setStatus(status);
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration(duration);
        return epic;
    }

    public static Subtask subtaskFromString(String value) { //Метод создания subtask из строк.
        String[] lines = value.split(",");
        int id = Integer.parseInt(lines[0]);
        TaskType taskType = typeFromString(lines[1]);
        String name = lines[2];
        Status status = statusFromString(lines[3]);
        String description = lines[4];
        LocalDateTime startTime = LocalDateTime.parse(lines[5], outputFormatter);
        long duration = Long.parseLong(lines[6]);
        LocalDateTime endTime = LocalDateTime.parse(lines[7], outputFormatter);
        int epicId = Integer.parseInt(lines[8]);

        Subtask subtask = new Subtask(name, description, epicId, taskType, startTime, duration);
        subtask.setId(id);
        subtask.setStatus(status);
        subtask.setEndTime(endTime);
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
        return  "id,TaskType,name,status,description,startTime,duration,endTime,epicId";
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
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(), task.getStatus(),
                task.getDescription(), task.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                task.getDuration(), task.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public static String epicToString (Epic epic) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s", epic.getId(), epic.getTaskType(), epic.getName(), epic.getStatus(),
                epic.getDescription(), epic.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                epic.getDuration(), epic.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public static String subtaskToString (Subtask subtask) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", subtask.getId(), subtask.getTaskType(), subtask.getName(), subtask.getStatus(),
                subtask.getDescription(), subtask.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                subtask.getDuration(), subtask.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), subtask.getIdEpic());
    }

}

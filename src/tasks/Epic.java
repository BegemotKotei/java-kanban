package tasks;

import managers.TaskType;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Epic extends Task {

    private final ArrayList<Integer> epicSubtasks = new ArrayList<>();

    public Epic(int id, String name, String description, TaskType taskType, Status status, LocalDateTime startTime, long duration) {
        super(id, name, description, taskType, status, startTime, duration);
    }

    public Epic(String name, String description, TaskType taskType) {
        super(name, description, taskType);
    }

    public ArrayList<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void addSubtaskId(int epicSubtask) {
        epicSubtasks.add(epicSubtask);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void removeIdFromSubtasksIdList(int subtaskId) {
        epicSubtasks.removeIf(element -> element == subtaskId);
    }
}
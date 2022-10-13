package tasks;

import managers.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected TaskType taskType;
    protected Status status;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected long duration;

    public Task(String name, String description, TaskType taskType,  LocalDateTime startTime, long duration) {
        this.name = name;
        this.description = description;
        this.taskType = taskType;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = getEndTime();
    }

    public Task(int id, String name, String description, TaskType taskType, Status status, LocalDateTime startTime, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description, TaskType taskType) {
        this.name = name;
        this.description = description;
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status Status) {
        this.status = Status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(Duration.ofMinutes(duration));
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return id + ","
                + taskType.toString() + ","
                + name + ","
                + status.toString() + ","
                + description + ","
                + (startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) + ","
                + duration + ","
                + (getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

    }
}
package Tasks;

import Managers.TaskType;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected TaskType taskType;
    protected Status status;

    public Task(String name, String description,TaskType taskType, Status status) {
        this.name = name;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
    }
    public Task(int id, String name, String description, TaskType taskType, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
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
    @Override
    public String toString() {
        return id + ","
                + name + ","
                + "," + description + ","
                + taskType.toString() +","
                + status.toString();

    }
}
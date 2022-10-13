package tasks;

import managers.TaskType;

import java.time.LocalDateTime;

public class Subtask extends Task {

    private int idEpic;

    public Subtask(int id, String name, String description, int idEpic, TaskType taskType, Status status, LocalDateTime startTime, long duration){
        super(id, name, description, taskType, status, startTime, duration);
        this.idEpic = idEpic;
    }

    public Subtask(String name, String description, int idEpic, TaskType taskType, LocalDateTime startTime, long duration) {
        super(name, description, taskType, startTime, duration);
        this.idEpic = idEpic;
    }

    public Subtask(String name, String description, int idEpic, TaskType taskType) {
        super(name, description, taskType);
        this.idEpic = idEpic;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    @Override
    public String toString() {
        return super.toString() + "," + idEpic;
    }
}
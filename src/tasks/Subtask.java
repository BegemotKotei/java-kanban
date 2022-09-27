package tasks;

import managers.TaskType;

public class Subtask extends Task {
    private int idEpic;
    public Subtask(int id, String name, String description, TaskType taskType, Status status, int idEpic){
        super(id, name, description, taskType, status);
        this.idEpic = idEpic;
    }
    public Subtask(String name, String description, TaskType taskType, Status status, int idEpic) {
        super(name, description, taskType, status);
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
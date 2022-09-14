package tasks;

import manager.TaskType;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> epicSubtasks;

    public Epic(String name, String description) {
        super(name, description, null);
        epicSubtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }
    public Epic(int id, String title, String description) {
        super(id, title, description, null);
        epicSubtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }
    public Epic(int id, String title, Status status, String description) {
        super(id, title, description, null);
        epicSubtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
        this.status = status;
    }

    public ArrayList<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void setEpicSubtasks(ArrayList<Integer> epicSubtasks) {
        this.epicSubtasks = epicSubtasks;
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "№=" + id +
                ", Название='" + name + '\'' +
                ", Описание='" + description + '\'' +
                ", Статус='" + status + '\'' +
                '}' +
                "\n";
    }
}
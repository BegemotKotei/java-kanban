package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> epicSubtasks; 

    public Epic(String name, String description) {
        super(name, description, "");
        epicSubtasks = new ArrayList<>();
    }

    public ArrayList<Subtask> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void setEpicSubtasks(ArrayList<Subtask> epicSubtasks) {
        this.epicSubtasks = epicSubtasks;
    }

    @Override
    public String toString() {
        return "Epic{id=" + id + ", Название='" + name + ", Описание='" + description + ", Статус='" + Status + '}';
    }
}
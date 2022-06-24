package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> epicSubtasks; //Изменил метод

    public Epic(String name, String description) {
        super(name, description, "");
        epicSubtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void setEpicSubtasks(ArrayList<Integer> epicSubtasks) {
        this.epicSubtasks = epicSubtasks;
    }

    @Override
    public String toString() {
        return "Epic{id=" + id + ", Название='" + name + ", Описание='" + description + ", Статус='" + Status + '}';
    }
}

package tasks;

public class Subtask extends Task {
    private Epic id;

    public Subtask(String name, String description, String Status) {
        super(name, description, Status);
        this.id = id;
    }

    public Subtask(String name, String description, String Status, Epic id) {
        super(name, description, Status);
        this.id = id;
    }

    public Epic getEpic() {
        return id;
    }

    public void setEpic(Epic id) {
        this.id = id;
    }

    @Override
    public void setStatus(String Status) {
        super.setStatus(Status);
    }

    @Override
    public String toString() {
        return "Subtask{id=" + id + ", Название='" + name + ", Описание='" + description + ", Статус='" + Status + '}';
    }
}

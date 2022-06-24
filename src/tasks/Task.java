package tasks;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected String Status;

    public Task(String name, String description, String Status) {
        this.name = name;
        this.description = description;
        this.Status = Status;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", Название='" + name + ", Описание='" + description + ", Статус='" + Status + '}';
    }
}



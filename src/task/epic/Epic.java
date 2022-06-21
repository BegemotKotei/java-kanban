package task.epic;

import task.Task;
import task.TaskStatus;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Epic extends Task {
    private final List<Integer> subTaskId;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.subTaskId =  new ArrayList<>();
    }

    public void addSubTask(SubTask subtask) {
        if (!subTaskId.contains(subtask.getId())) {
            subTaskId.add(subtask.getId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskId, epic.subTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskId);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskId=" + subTaskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

}

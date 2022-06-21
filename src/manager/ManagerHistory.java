package manager;

import task.Task;


public class ManagerHistory {

    private final int id;
    private final Type itemType;


    @Override
    public String toString() {
        return "ManagerHistory{" +
                "id=" + id +
                ", itemType=" + itemType +
                '}';
    }

    public ManagerHistory(Task item) {
        id = item.getId();
        String className = item.getClass().getName();
        switch (className) {
            case "Task":
                itemType = Type.TASK;
                break;
            case "Epic":
                itemType = Type.EPIC;
                break;
            case "SubTask":
                itemType = Type.SUBTASK;
                break;
            default:
                itemType = Type.UNDEFINED;
                break;
        }
    }
}


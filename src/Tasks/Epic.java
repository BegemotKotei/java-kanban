package Tasks;

import Managers.TaskType;

import java.util.ArrayList;
import java.util.Iterator;

public class Epic extends Task {

    private ArrayList<Integer> epicSubtasks = new ArrayList<>();

    public Epic(int id, String name, String description, TaskType taskType) {
        super(id, name, description, taskType, null);
    }

    public Epic(String name, String description, TaskType taskType) {
        super(name, description, taskType);
    }

    public ArrayList<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void addSubtaskId(int epicSubtask) {
        epicSubtasks.add(epicSubtask);
    }

    public void removeIdFromSubtasksIdList(int subtaskId) {
        Iterator<Integer> iterator = epicSubtasks.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            if ((int) element == subtaskId) {
                iterator.remove();
            }
        }
    }
}
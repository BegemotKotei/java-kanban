package manager;

import task.Task;
import task.TaskStatus;
import task.epic.Epic;
import task.epic.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListHistory implements TaskManager {
    private int idCounter;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subTasks;

    private final List<ManagerHistory> history;

    public ListHistory() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.idCounter = 0;
        this.history = new ArrayList<>();
    }


    @Override
    public List<Task> getTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTask() {
        tasks.clear();
    }

    @Override
    public Task getTaskId(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            addToHistory(task);
        }
        return task;
    }

    @Override
    public void createTask(Task task) {
        task.setId(idCounter);
        tasks.put(idCounter, task);
        idCounter++;
    }

    @Override
    public void updateTask(Task task) {
        var taskList = tasks.get(task.getId());
        if (taskList != null) {
            taskList.setName(task.getName());
            taskList.setDescription(task.getDescription());
            taskList.setStatus(task.getStatus());
        }
    }

    @Override
    public void removeTaskId(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public List<Task> getEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllEpic() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Task getEpicId(int EpicId) {
        Epic epic = epics.get(EpicId);
        if (epic != null) {
            addToHistory(epic);
        }
        return epic;
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(idCounter);
        epics.put(idCounter, epic);
        updateEpicStatus(epic.getId());
        idCounter++;

    }

    @Override
    public void updateEpic(Epic epic) {
        var epicList = epics.get(epic.getId());
        if (epicList != null) {
            epicList.setName(epic.getName());
            epicList.setDescription(epic.getDescription());
        }
    }

    @Override
    public void removeEpicId(int EpicId) {
        epics.remove(EpicId);
        subTasks.remove(EpicId);
    }

    @Override
    public List<Task> getSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void removeAllSubTask() {
        subTasks.clear();
    }

    @Override
    public Task getSubTaskId(int SubTaskId) {
        SubTask subTask = subTasks.get(SubTaskId);
        if (subTask != null) {
            addToHistory(subTask);
        }
        return subTask;
    }

    @Override
    public void createSubTask(SubTask subTask) {
        subTask.setId(idCounter);
        subTasks.put(idCounter, subTask);
        var subTaskEpic = epics.get(subTask.getEpicId());
        if (subTaskEpic != null) {
            subTaskEpic.addSubTask(subTask);
            updateEpicStatus(subTask.getEpicId());
        }
        idCounter++;
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        var subTaskFromList = subTasks.get(subTask.getId());
        if (subTaskFromList != null) {
            subTaskFromList.setName(subTask.getName());
            subTaskFromList.setDescription(subTask.getDescription());
            subTaskFromList.setStatus(subTask.getStatus());
            var epicFromList = epics.get(subTask.getEpicId());
            epicFromList.addSubTask(subTask);
            updateEpicStatus(epicFromList.getId());
        }
    }

    @Override
    public void removeSubTaskId(int SubTaskId) {
        subTasks.remove(SubTaskId);

    }

    private void updateEpicStatus(int EpicId) {
        for (var id : epics.keySet()) {
            if (id == EpicId) {
                List<SubTask> epicsSubtasks = subTasks.values().stream().filter(k -> Objects.equals(k.getEpicId(), id)).collect(Collectors.toList());
                List<SubTask> newSubTasks = epicsSubtasks.stream().filter(k -> k.getStatus() == TaskStatus.NEW).collect(Collectors.toList());
                List<SubTask> doneSubtasks = epicsSubtasks.stream().filter(k -> k.getStatus() == TaskStatus.DONE).collect(Collectors.toList());
                if (epicsSubtasks.isEmpty() || newSubTasks.size() == epicsSubtasks.size()) {
                    epics.get(id).setStatus(TaskStatus.NEW);
                } else if (doneSubtasks.size() == epicsSubtasks.size()) {
                    epics.get(id).setStatus(TaskStatus.DONE);
                } else {
                    epics.get(id).setStatus(TaskStatus.IN_PROGRESS);
                }
            }
        }
    }

    private void addToHistory(Task task)
    {
        ManagerHistory ManagerHistory = new ManagerHistory(task);
        if (history.size() == 10) {
            history.remove(0);
        }
        history.add(ManagerHistory);
    }
}



package manager;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.Status;

import java.util.ArrayList;
import java.util.HashMap;
public  class SupportManager implements TaskManager {
    private int id;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;

    public SupportManager() {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, Subtask> getSubTasks() {
        return subtasks;
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubTasks() {
        ArrayList<Epic> epicsForStatusUpdate = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            subtask.getEpic().setEpicSubtasks(new ArrayList<>());
            if (!epicsForStatusUpdate.contains(subtask.getEpic())) {
                epicsForStatusUpdate.add(subtask.getEpic());
            }
        }
        subtasks.clear();
        for (Epic epic : epicsForStatusUpdate) {
            epic.setStatus(String.valueOf(Status.NEW));
        }
    }

    @Override
    public Task getTasksById(int id) {
        return tasks.getOrDefault(id, null);
    }

    @Override
    public Epic getEpicsId(int id) {
        return epics.getOrDefault(id, null);
    }

    @Override
    public Subtask getSubTasksById(int id) {
        return subtasks.getOrDefault(id, null);
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(id, task);
        return task;
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(generateId());
        epic.setStatus(String.valueOf(Status.NEW));
        epics.put(id, epic);
    }

    @Override
    public void createSubTask(Subtask subtask) {
        subtask.setId(generateId());
        subtasks.put(id, subtask);
        subtask.getEpic().getEpicSubtasks().add(id);
        checkEpicStatus(subtask.getEpic());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epic.setEpicSubtasks(epics.get(epic.getId()).getEpicSubtasks());
        epics.put(epic.getId(), epic);
        checkEpicStatus(epic);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(subtask.getEpic());
    }

    @Override
    public void removeTaskById(int Id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    @Override
    public void removeEpicById(int Id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epics.remove(id);
            for (Integer subtaskId : epic.getEpicSubtasks()) {
                subtasks.remove(subtaskId);
            }
            epic.setEpicSubtasks(new ArrayList<>());
        }
    }

    @Override
    public void removeSubTaskById(int Id) {
        if (subtasks.containsKey(id)) {
            Epic epic = subtasks.get(id).getEpic();
            epic.getEpicSubtasks().remove((Integer) id);
            checkEpicStatus(epic);
            subtasks.remove(id);
        }
    }

    @Override
    public int generateId() {
        id++;
        return id;
    }
    private void checkEpicStatus(Epic epic) {

        if (epic.getEpicSubtasks().size() == 0) {
            epic.setStatus(String.valueOf(Status.NEW));
            return;
        }

        boolean allTaskIsNew = true;
        boolean allTaskIsDone = true;

        for (Integer epicSubtaskId : epic.getEpicSubtasks()) {
            String status = subtasks.get(epicSubtaskId).getStatus();
            if (!status.equals(Status.NEW)) {
                allTaskIsNew = false;
            }
            if (!status.equals(Status.DONE)) {
                allTaskIsDone = false;
            }
        }

        if (allTaskIsDone) {
            epic.setStatus(String.valueOf(Status.DONE));
        } else if (allTaskIsNew) {
            epic.setStatus(String.valueOf(Status.NEW));
        } else {
            epic.setStatus(String.valueOf(Status.IN_PROGRESS));
        }

    }
}





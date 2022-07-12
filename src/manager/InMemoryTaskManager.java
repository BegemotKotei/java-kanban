package manager;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class InMemoryTaskManager implements TaskManager {
    private int id;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> subtasks;
    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        historyManager = Managers.getDefaulHistory();
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
    public Epic getEpicsById(int id) {
        Epic epic = epics.getOrDefault(id, null);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubTasksById(int id) {
        Subtask subtask = subtasks.getOrDefault(id, null);
        historyManager.add(subtask);
        return subtask;
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
        subtask.getEpic().getEpicSubtasks().add(subtask);
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
            historyManager.remove(id);
        }
    }

    @Override
    public void removeEpicById(int Id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epics.remove(id);
            historyManager.remove(id);
            for (Subtask subtaskId : epic.getEpicSubtasks()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId.getId());
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
            historyManager.remove(id);
        }
    }

    @Override
    public int generateId() {
        id++;
        return id;
    }

    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

    private void checkEpicStatus(Epic epic) {

        if (epic.getEpicSubtasks().size() == 0) {
            epic.setStatus(String.valueOf(Status.NEW));
            return;
        }

        boolean allTaskIsNew = true;
        boolean allTaskIsDone = true;
        for (Subtask subtask : epic.getEpicSubtasks()) {
            if (!subtask.equals(Status.NEW)) {
                allTaskIsNew = false;
            }
            if (!subtask.equals(Status.DONE)) {
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

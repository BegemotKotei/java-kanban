package Managers;

import Tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class InMemoryTasksManager implements TaskManager {
    int id;
    final HashMap<Integer, Task> tasks;
    final HashMap<Integer, Epic> epics;
    final HashMap<Integer, Subtask> subtasks;
    final HistoryManager historyManager;
    HistoryManager inMemoryHistoryManager = Managers.getDefaulHistory();

    public InMemoryTasksManager() {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        historyManager = Managers.getDefaulHistory();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList< Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllTasks() {
        for (Task task : tasks.values()) {
            this.inMemoryHistoryManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        removeAllSubTasks();
        for (Epic epic : epics.values()) {
            this.inMemoryHistoryManager.remove(epic.getId());
        }
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        for (Subtask subtask: subtasks.values()) {
            this.inMemoryHistoryManager.remove(subtask.getId());
        }
        subtasks.clear();
    }

    @Override
    public Task getTasksById(int id) {
        if (checkId(id)) {
            inMemoryHistoryManager.add(tasks.get(id));
            return tasks.get(id);
        }
        return null;
    }

    @Override
    public Epic getEpicsById(int id) {
        if (checkId(id)) {
            inMemoryHistoryManager.add(epics.get(id));
            return epics.get(id);
        }
        return null;
    }

    @Override
    public Subtask getSubTasksById(int id) {
        if (checkId(id)) {
            inMemoryHistoryManager.add(subtasks.get(id));
            return subtasks.get(id);
        }
        return null;
    }

    @Override
    public Task createTask(Task task) {
        if (task != null) {
            task.setId(generateId());
            task.setStatus(Status.NEW);
            tasks.put(task.getId(), task);
            return task;
        }
        return null;
    }

    @Override
    public Epic createEpic(Epic epic) {
        if (epic != null) {
            epic.setId(generateId());
            epic.setStatus(Status.NEW);
            epics.put(epic.getId(), epic);
            return epic;
        }
        return null;
    }

    @Override
    public Subtask createSubTask(Subtask subtask, int epicId) {
        if (subtask != null && checkId(epicId)) {
            subtask.setId(generateId());
            subtask.setStatus(Status.NEW);
            subtasks.put(subtask.getId(), subtask);
            epics.get(epicId).addSubtaskId(subtask.getId());
            return subtask;
        }
        return null;
    }

    @Override
    public void updateTask(Task task, Status status) {
        if (task != null) {
            task.setStatus(status);
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic != null) {
            int countNew = 0;
            int countDone = 0;
            if (epic.getEpicSubtasks().isEmpty()) {
                epic.setStatus(Status.NEW);
                epics.put(epic.getId(), epic);
                return;
            }
            for (Integer i : epic.getEpicSubtasks()) {
                if(subtasks.get(i).getStatus() == Status.DONE) {
                    countDone++;
                } else if (subtasks.get(i).getStatus() == Status.NEW) {
                    countNew++;
                }
            }
            if (countNew == epic.getEpicSubtasks().size()) {
                epic.setStatus(Status.NEW);
            } else if (countDone == epic.getEpicSubtasks().size()) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubTask(Subtask subtask, Status status, int idEpic) {
        if (subtask != null) {
            int id = subtask.getId();
            subtask.setStatus(status);
            subtasks.put(id,subtask);
            subtask.setIdEpic(idEpic);
            updateEpic(epics.get(idEpic));
        }
    }

    @Override
    public void removeTaskById(int Id) {
        if (checkId(id)) {
            tasks.remove(id);
            inMemoryHistoryManager.remove(id);
        }
    }

    @Override
    public void removeEpicById(int Id) {
        if (checkId(id)) {
            for (Integer i : epics.get(id).getEpicSubtasks()) {
                subtasks.remove(i);
            }
            epics.remove(id);
        }
    }

    @Override
    public void removeSubTaskById(int Id) {
        if(checkId(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getIdEpic());
            epic.removeIdFromSubtasksIdList(id);
            subtasks.remove(id);
        }
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasksList(Epic epic) {
        if (epic != null) {
            ArrayList<Subtask> subtaskList = new ArrayList<>();
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getIdEpic() == epic.getId()) {
                    subtaskList.add(subtask);
                }
            }
            return subtaskList;
        }
        return null;
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

    protected boolean checkId (int id) {
        return 0 < id && id <= generateId();
    }
}

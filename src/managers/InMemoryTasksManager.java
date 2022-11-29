package managers;

import tasks.*;

import java.time.LocalDateTime;
import java.util.*;

import exception.ValidationException;

public  class InMemoryTasksManager implements TaskManager {
    int id;
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Epic> epics;
    protected final HashMap<Integer, Subtask> subtasks;
    protected Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    public HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    protected static int generateId = 0;

    public InMemoryTasksManager() {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    @Override
    public ArrayList<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    protected void validate(Task task) throws ValidationException {
        LocalDateTime startTimeInput = task.getStartTime();
        LocalDateTime endTimeInput = task.getEndTime();
        if (prioritizedTasks.isEmpty()) {
            return;
        }
        for (Task taskOld: prioritizedTasks) {
            if (taskOld.equals(task)) {
                break;
            }
            if(startTimeInput.isBefore(taskOld.getStartTime()) && endTimeInput.isAfter(taskOld.getEndTime())) {
                throw new ValidationException("Задача не может начинаться раньше и заканчиваться позже чем другие задачи");
            }
            if (endTimeInput.isBefore(taskOld.getStartTime())) {
                throw new ValidationException("Задача не может заканчиваться позже начала другой задачи");
            }
            if (startTimeInput.isBefore(taskOld.getEndTime())) {
                throw new ValidationException("Задача не может начинаться раньше конца другой задачи");
            }
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllTasks() {
        for (Task task : tasks.values()) {
            inMemoryHistoryManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        removeAllSubTasks();
        for (Epic epic : epics.values()) {
            inMemoryHistoryManager.remove(epic.getId());
        }
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        for (Subtask subtask : subtasks.values()) {
            inMemoryHistoryManager.remove(subtask.getId());
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
            try {
                validate(task);
            } catch (ValidationException e) {
                return null;
            }
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
            return task;
        }
        return null;
    }

    @Override
    public Epic createEpic(Epic epic) {
        if (epic != null) {
            epic.setId(generateId());
            epic.setStatus(Status.NEW);
            updateEpicDurationAndStarTime(epic);
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
            try {
                validate(subtask);
            } catch (ValidationException e) {
                return null;
            }
            subtasks.put(subtask.getId(), subtask);
            epics.get(epicId).addSubtaskId(subtask.getId());
            updateEpicDurationAndStarTime(epics.get(epicId));
            prioritizedTasks.add(subtask);
            return subtask;
        }
        return null;
    }

    @Override
    public void updateTask(Task task, Status status) {
        if (task != null) {
            task.setStatus(status);
            try {
                validate(task);
            } catch (ValidationException e) {
                return;
            }
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
            for (Integer integer : epic.getEpicSubtasks()) {
                if (subtasks.get(integer).getStatus() == Status.DONE) {
                    countDone++;
                } else if (subtasks.get(integer).getStatus() == Status.NEW) {
                    countNew++;
                }
            }
            if (countNew == epic.getEpicSubtasks().size()) {
                epic.setStatus(Status.NEW);
            } else if (countDone == epic.getEpicSubtasks().size()) {
                epic.setStatus(Status.DONE);
            } else epic.setStatus(Status.IN_PROGRESS);
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubTask(Subtask subtask, Status status, int idEpic) {
        if (subtask != null) {
            int id = subtask.getId();
            subtask.setStatus(status);
            try {
                validate(subtask);
            } catch (ValidationException e) {
                return;
            }
            subtasks.put(id, subtask);
            subtask.setIdEpic(idEpic);
            updateEpic(epics.get(idEpic));
            updateEpicDurationAndStarTime(epics.get(idEpic));
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
        if (checkId(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getIdEpic());
            epic.removeIdFromSubtasksIdList(id);
            subtasks.remove(id);
        }
    }

    @Override
    public List<Subtask> getEpicSubtasksList(Epic epic) {
        if (epic != null) {
            List<Subtask> subtaskList = new ArrayList<>();
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getIdEpic() == epic.getId()) {
                    subtaskList.add(subtask);
                }
            }
            return subtaskList;
        }
        return null;
    }

    public static int generateId() {
        return ++generateId;
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public Task findTask(Integer taskId) {
        if (tasks.containsKey(taskId)) {
            return tasks.get(taskId);
        } else if (epics.containsKey(taskId)) {
            return epics.get(taskId);
        } else {
            return subtasks.getOrDefault(taskId, null);
        }
    }

    protected boolean checkId(int id) {
        return 0 < id && id <= generateId;
    }

    private void updateEpicDurationAndStarTime(Epic epic) {  // продолжительность Эпика
        LocalDateTime startEpic = LocalDateTime.MAX;
        LocalDateTime endEpic = LocalDateTime.MIN;
        long durationEpic = 0L;
        ArrayList<Integer> subtasksId = epic.getEpicSubtasks();
        if (subtasksId.isEmpty()) {
            epic.setDuration(0L);
            epic.setStartTime(startEpic);
            return;
        }
        for (int i : subtasksId) {
            Subtask subtask = subtasks.get(i);
            LocalDateTime startTime = subtask.getStartTime();
            LocalDateTime endTime = subtask.getEndTime();
            if (startTime.isBefore(startEpic)) {
                startEpic = startTime;
            }
            if (endEpic.isAfter(endTime)) {
                endEpic = endTime;
            }
            durationEpic = durationEpic + subtask.getDuration();
        }
        epic.setStartTime(startEpic);
        epic.setEndTime(endEpic);
        epic.setDuration(durationEpic);
    }
}

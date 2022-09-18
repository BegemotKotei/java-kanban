package Managers;

import Tasks.Task;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Status;

import java.util.*;

public interface TaskManager {

    ArrayList<Task> getTasks();
    ArrayList<Epic> getEpics();
    ArrayList<Subtask> getSubTasks();

    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubTasks();

    Task getTasksById(int id);
    Epic getEpicsById(int id);
    Subtask getSubTasksById(int id);

    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubTask(Subtask subTask, int idEpic);

    void updateTask(Task task, Status status);
    void updateEpic(Epic epic);
    void updateSubTask(Subtask subTask, Status status, int idEpic);

    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);

    ArrayList<Subtask> getEpicSubtasksList(Epic epic);

    int generateId();

    List<Task> history();

}

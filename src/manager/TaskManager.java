package manager;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

import java.util.*;

public interface TaskManager {

    HashMap<Integer, Task> getTasks();
    HashMap<Integer, Epic> getEpics();
    HashMap<Integer, Subtask> getSubTasks();
    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubTasks();
    Task getTasksById(int id);
    Epic getEpicsById(int id);
    Subtask getSubTasksById(int id);
    void createTask(Task task);
    void createEpic(Epic epic);
    void createSubTask(Subtask subTask);
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubTask(Subtask subTask);
    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);
    int generateId();
    List<Task> history();

}

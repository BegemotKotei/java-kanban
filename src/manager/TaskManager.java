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
    Epic getEpicsId(int id);
    Subtask getSubTasksById(int id);
    Task createTask(Task task);
    void createEpic(Epic epic);
    void createSubTask(Subtask subTask);
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubTask(Subtask subTask);
    void removeTaskById(int Id);
    void removeEpicById(int Id);
    void removeSubTaskById(int Id);
    int generateId();

}

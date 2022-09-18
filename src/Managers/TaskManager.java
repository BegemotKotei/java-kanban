package manager;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.Status;

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

    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubTask(Subtask subTask, int idEpic);

    void updateTask(Task task, Status status);
    void updateEpic(Epic epic);
    void updateSubTask(Subtask subTask, Status status, int idEpic);

    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);

    int generateId();

    List<Task> history();

}

package manager;

import task.Task;
import task.epic.Epic;
import task.epic.SubTask;

import java.util.*;

public interface TaskManager {
    List<Task> getTask();
    void removeAllTask();
    Task getTaskId(int TaskId);
    void createTask(Task task);
    void updateTask(Task task);
    void removeTaskId(int taskId);
    List<Task> getEpic();
    void removeAllEpic();
    Task getEpicId(int EpicId);
    void createEpic(Epic epic);
    void updateEpic(Epic epic);
    void removeEpicId(int EpicId);
    List<Task> getSubTask();
    void removeAllSubTask();
    Task getSubTaskId(int SubTaskId);
    void createSubTask(SubTask subTask);
    void updateSubTask(SubTask subTask);
    void removeSubTaskId(int SubTaskId);

}

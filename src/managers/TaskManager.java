package managers;

import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

import java.util.*;

public interface TaskManager {

    List<Task> getPrioritizedTasks();

    // Получение списка задач.
    List<Task> getTasks();
    List<Epic> getEpics();
    List<Subtask> getSubTasks();

    //
    List<Subtask> getAllSubTasksByEpicId(int id);

    // Удаление задач.
    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubTasks();

    // Удаление
    void removeAllSubTasksByEpic(Epic epic);

    // Получение задач по id.
    Task getTasksById(int id);
    Task getEpicsById(int id);
    Task getSubTasksById(int id);

    // Создание задачи
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubTask(Subtask subTask);

    // Обновление задачи.
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateStatusEpic(Epic epic);
    void updateSubTask(Subtask subTask);

    // Удаление задачи по id.
    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);

    // История просмотра задач.
    List<Task> getHistory();

    //
    void remove(int id);

    //
    void printTasks();
    void printEpics();
    void printSubtasks();
}

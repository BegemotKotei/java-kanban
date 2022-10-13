package managers;

import exception.ValidationException;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.Status;

import java.util.*;

public interface TaskManager {

    ArrayList<Task> getPrioritizedTasks();

    // Получение списка задач.
    ArrayList<Task> getTasks();
    ArrayList<Epic> getEpics();
    ArrayList<Subtask> getSubTasks();

    // Удаление задач.
    void removeAllTasks();
    void removeAllEpics();
    void removeAllSubTasks();

    // Получение задач по id.
    Task getTasksById(int id);
    Epic getEpicsById(int id);
    Subtask getSubTasksById(int id);

    // Создание задачи
    Task createTask(Task task);
    Epic createEpic(Epic epic);
    Subtask createSubTask(Subtask subTask, int idEpic);

    // Обновление задачи.
    void updateTask(Task task, Status status);
    void updateEpic(Epic epic);
    void updateSubTask(Subtask subTask, Status status, int idEpic);

    // Удаление задачи по id.
    void removeTaskById(int id);
    void removeEpicById(int id);
    void removeSubTaskById(int id);

    // Получение списка сабтасков определённого эпика.
    List<Subtask> getEpicSubtasksList(Epic epic);


    // История просмотра задач.
    List<Task> history();

    // Поиск задачи по id;
    Task findTask(Integer taskId);



}

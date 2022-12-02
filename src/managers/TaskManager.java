package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    // Методы добавления tasks.Task && tasks.Epic && tasks.Subtask
    String addTask(Task task);
    String addEpic(Epic epic);
    String addSubTask(Subtask subtask);

    // Методы обновления tasks.Task && tasks.Epic && tasks.Subtask
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    // Методы удаления tasks.Task && tasks.Epic && tasks.Subtask
    void deleteTask(String taskID);
    void deleteEpic(String id);
    void deleteSubTask(String subtaskID);


    //Методы удаления всех tasks.Task && tasks.Epic && tasks.Subtask
    void deleteAllTasks();
    void deleteAllEpics();

    void deleteAllSubTasks();


    // Методы запроса конкретной tasks.Task && tasks.Epic && tasks.Subtask
    Task getTaskById(String id);
    Epic getEpicById(String id);
    Subtask getSubtaskById(String id);


    // Методы запроса списка tasks.Task && tasks.Epic && tasks.SubtaskМетод удаления всех
    LinkedHashMap<String, Task> listTasks();
    LinkedHashMap<String, Epic> listEpics();
    LinkedHashMap<String, Subtask> listAllSubtasks();




    // Метод запроса списка tasks.Subtask конкретного tasks.Epic
    LinkedHashMap<String, Subtask> listEpicSubtasks(String epicID);

    // Метод вывода сразу всех задач, эпиков и подзадач
    List<Task> listEveryTaskAndEpicAndSubtask();

    TreeSet<Task> listPrioritizedTasks();
    <T extends Task> void updateSortedByStartDateList (T task);
    List<Task> getHistory();
    boolean isDataLoaded();
}

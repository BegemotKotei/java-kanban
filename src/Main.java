import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.Status;
import manager.Managers;
import manager.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Первая большая задача!", "Первая задача! У неё нет подзадач");
        Epic epic2 = new Epic("Вторая большая задача", "Вторая задача:)");
        Subtask st1 = new Subtask("Первая подзадач", "Вторая задача:)", (String.valueOf(Status.NEW)), epic2);
        Subtask st2 = new Subtask("Вторая подзадач", "Вторая задача:)", (String.valueOf(Status.NEW)), epic2);
        Subtask st3 = new Subtask("Третья подзадач", "Вторая задача:)", (String.valueOf(Status.NEW)), epic2);
        Subtask st3New = new Subtask("Третья подзадач", "Вторая задача:) новый статус", (String.valueOf(Status.IN_PROGRESS)), epic2);
        Task task1 = new Task("Первая главная задача", "Первая", (String.valueOf(Status.NEW)));
        Task task2 = new Task("Вторая главная задача", "Вторая", (String.valueOf(Status.NEW)));
        manager.createEpic(epic);
        manager.createEpic(epic2);
        manager.createSubTask(st1);
        manager.createSubTask(st2);
        manager.createSubTask(st3);
        manager.updateSubTask(st3New);
        manager.createTask(task1);
        manager.createTask(task2);
        System.out.println("\n Пересчет всех эпиков : \n" + manager.getEpics());
        System.out.println("\n Пересчет всех задач : \n" + manager.getTasks());
        System.out.println("\n Пересчет всех подзадач : \n" + manager.getSubTasks());
        System.out.println(manager.getTasksById(7));
        System.out.println(manager.getTasksById(6));
        System.out.println(manager.history()); // Не понимаю почему тут не показывается истоия:(

        manager.removeAllEpics();
        manager.removeAllSubTasks();
        manager.removeAllTasks();

        System.out.println("\n Всего эпиков : \n" + manager.getEpics());
        System.out.println("\n всего задач : \n" + manager.getTasks());
        System.out.println("\n всего подзадач : \n" + manager.getSubTasks());
        System.out.println("История : " + manager.history());


    }
}

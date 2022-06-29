import manager.HistoryManager;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;
import tasks.Status;
import manager.Managers;
import manager.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("Первая большая задача!", "Первая задача!");
        Subtask st1 = new Subtask("Первая подзадача!", "Первая задача!", (String.valueOf(Status.IN_PROGRESS)), epic);
        Epic epic2 = new Epic("Вторая большая задача", "Вторая задача:)");
        Subtask st2 = new Subtask("Первая подзадач", "Вторая задача:)", (String.valueOf(Status.NEW)), epic2);
        Subtask st3 = new Subtask("Вторая подзадач", "Вторая задача:)", (String.valueOf(Status.DONE)), epic2);
        Task task1 = new Task("Первая главная задача", "Первая", (String.valueOf(Status.DONE)));
        Task task2 = new Task("Вторая главная задача", "Вторая", (String.valueOf(Status.DONE)));
        manager.createEpic(epic);
        manager.createSubTask(st1);
        manager.createEpic(epic2);
        manager.createSubTask(st2);
        manager.createSubTask(st3);
        manager.createTask(task1);
        manager.createTask(task2);
        /*manager.updateSubTask(st1);
        *//*manager.removeAllSubTasks();*//*
        manager.removeTaskById(7);*/
        HistoryManager manager1 = Managers.getDefaulHistory();
        manager1.add(task1);
        manager1.add(task2);
        System.out.println(manager1.getHistory());


        System.out.println(manager.getTasksById(7));
        System.out.println(manager.getEpicsById(1));
        System.out.println(manager.getSubTasksById(2));
    }
}

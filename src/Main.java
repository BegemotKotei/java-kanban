import manager.*;
import tasks.*;

import java.io.File;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        Epic epic = new Epic("Первая большая задача!", "Первая задача! У неё нет подзадач");
        Epic epic2 = new Epic("Вторая большая задача", "Вторая задача:)");
        Subtask st1 = new Subtask("Первая подзадач", "Вторая задача:)", Status.NEW, epic2);
        Subtask st2 = new Subtask("Вторая подзадач", "Вторая задача:)", Status.NEW, epic2);
        Subtask st3 = new Subtask("Третья подзадач", "Вторая задача:)", Status.NEW, epic2);
        Task task1 = new Task("Первая главная задача", "Первая", Status.NEW);
        Task task2 = new Task("Вторая главная задача", "Вторая", Status.NEW);
        manager.createEpic(epic);
        manager.createEpic(epic2);
        manager.createSubTask(st1);
        manager.createSubTask(st2);
        manager.createSubTask(st3);
        manager.createTask(task1);
        manager.createTask(task2);
        System.out.println("\n Пересчет всех эпиков : \n" + manager.getEpics());
        System.out.println("\n Пересчет всех задач : \n" + manager.getTasks());
        System.out.println("\n Пересчет всех подзадач : \n" + manager.getSubTasks());
        System.out.println(manager.getTasksById(7));
        System.out.println(manager.getTasksById(6));
        System.out.println(manager.history());

        manager.removeAllEpics();
        manager.removeAllSubTasks();
        manager.removeAllTasks();

        System.out.println("\n Всего эпиков : \n" + manager.getEpics());
        System.out.println("\n всего задач : \n" + manager.getTasks());
        System.out.println("\n всего подзадач : \n" + manager.getSubTasks());
        System.out.println("История : " + manager.history());

        TaskManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File ("C:\\Users\\theha\\dev\\java-kanban\\data\\data.csv"));
    }
}

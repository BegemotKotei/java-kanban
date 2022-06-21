import task.Task;
import task.TaskStatus;
import task.epic.Epic;
import task.epic.SubTask;
import manager.ListHistory;

public class Main {
    public static void main(String[] args) {

        ListHistory listHistory = new ListHistory();
        Epic epic = new Epic("Первая большая задача!", "Первая задача!");
        listHistory.createEpic(epic);
        SubTask st1 = new SubTask("Первая подзадача!", "Первая задача!", TaskStatus.NEW, 0);
        listHistory.createSubTask(st1);
        Epic epic2 = new Epic("Вторая большая задача", "Вторая задача:)");
        listHistory.createEpic(epic2);
        SubTask st2 = new SubTask("Первая подзадач", "Вторая задача:)", TaskStatus.NEW, 2);
        listHistory.createTask(st2);
        SubTask st3 = new SubTask("Вторая подзадач", "Вторая задача:)", TaskStatus.NEW, 2);
        listHistory.createSubTask(st3);
        st2.setId(3);
        st2.setStatus(TaskStatus.IN_PROGRESS);
        listHistory.updateSubTask(st2);
        st2.setStatus(TaskStatus.DONE);
        listHistory.updateSubTask(st2);
        st3.setId(4);
        st3.setStatus(TaskStatus.IN_PROGRESS);
        listHistory.updateSubTask(st3);
        st3.setStatus(TaskStatus.DONE);
        listHistory.updateSubTask(st3);

        Task task1 = new Task("Первая главная задача", "Первая", TaskStatus.NEW);
        listHistory.createTask(task1);
        Task task2 = new Task("Вторая главная задача", "Вторая", TaskStatus.NEW);
        listHistory.createTask(task2);
        task1.setStatus(TaskStatus.IN_PROGRESS);
        listHistory.updateTask(task1);
        System.out.println(listHistory.getTask());
        System.out.println(listHistory.getEpic());
        System.out.println(listHistory.getSubTask());
    }
}

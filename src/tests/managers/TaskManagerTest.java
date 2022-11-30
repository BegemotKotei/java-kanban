package managers;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest <T extends TaskManager> {

    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    protected void taskManagerSetUp() {
        task = new Task(1,"Task name", "Task description", TaskType.TASK, Status.NEW, LocalDateTime.of(2022,10,18,10, 0), 90);
        taskManager.createTask(task);

        epic = new Epic(2,"Epic name", "Epic description", TaskType.EPIC, Status.NEW, LocalDateTime.of(2022,10,19,10, 0), 90);
        taskManager.createEpic(epic);

        subtask = new Subtask(3,"Subtask name", "Subtask description",epic.getId(), TaskType.SUBTASK, Status.NEW, LocalDateTime.of(2022,10,20,10, 0), 90);
        taskManager.createSubTask(subtask, epic.getId());

        HistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);

    }
    @Test
    public void getTaskListTest() {
        ArrayList<Task> taskList = new ArrayList<> (taskManager.getTasks());
        assertEquals(task, taskList.get(0), "getTaskListTest - не пройден");
    }

    @Test
    public void getTaskListTestWhenListIsEmptyTest() {
        ArrayList<Task> taskList = new ArrayList<> (taskManager.getTasks());
        assertEquals(task, taskList.get(0), "getTaskListTest - не пройден");
        taskManager.removeAllTasks();
        taskList = new ArrayList<> (taskManager.getTasks());
        assertTrue(taskList.isEmpty(), "getTaskListTestWhenListIsEmpty - не пройден");
    }

    @Test
    public void getSubtasksListTest() {
        ArrayList<Task> subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertEquals(subtask, subtaskList.get(0), "getSubtasksListTest - не пройден");
    }

    @Test
    public void getSubtasksListTestWhenListIsEmptyTest() {
        ArrayList<Task> subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertEquals(subtask, subtaskList.get(0), "getSubtasksListTest - не пройден");
        taskManager.removeAllSubTasks();
        subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertTrue(subtaskList.isEmpty(), "getSubtasksListTestWhenListIsEmpty - не пройден");
    }

    @Test
    public void getEpicLisTest() {
        ArrayList<Task> epicList = new ArrayList<> (taskManager.getEpics());
        assertEquals(epic, epicList.get(0), "getSubtasksListTest - не пройден");
    }

    @Test
    public void getEpicListTestWhenListIsEmptyTest() {
        ArrayList<Task> epicList = new ArrayList<> (taskManager.getEpics());
        assertEquals(epic, epicList.get(0), "getSubtasksListTest - не пройден");
        taskManager.removeAllEpics();
        epicList = new ArrayList<> (taskManager.getEpics());
        assertTrue(epicList.isEmpty(), "getSubtasksListTestWhenListIsEmpty - не пройден");
    }

    @Test
    public void cleanTaskListTest() {
        ArrayList<Task> taskList = new ArrayList<> (taskManager.getTasks());
        assertEquals(task, taskList.get(0), "getTaskListTest - не пройден");
        taskManager.removeAllTasks();
        taskList = new ArrayList<> (taskManager.getTasks());
        assertTrue(taskList.isEmpty(), "cleanTaskListTest - не пройден");
    }

    @Test
    public void cleanSubtaskListTest() {
        ArrayList<Task> subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertEquals(subtask, subtaskList.get(0), "getSubtasksListTest - не пройден");
        taskManager.removeAllSubTasks();
        subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertTrue(subtaskList.isEmpty(), "cleanSubtaskListTest - не пройден");
    }

    @Test
    public void cleanEpicListTest() {
        ArrayList<Task> epicList = new ArrayList<> (taskManager.getEpics());
        assertEquals(epic, epicList.get(0), "getSubtasksListTest - не пройден");
        taskManager.removeAllEpics();
        epicList = new ArrayList<> (taskManager.getEpics());
        assertTrue(epicList.isEmpty(), "cleanEpicListTest - не пройден");
    }

    @Test
    public void getTaskFromIdTest() {
        assertEquals(task, taskManager.getTasksById(task.getId()), "getTaskFromIdTest - не пройден");
    }

    @Test
    public void getTaskFromIdWhenWrongIdTest() {
        assertNull(taskManager.getTasksById(0), "getTaskFromIdWhenWrongIdTest - не пройден");
    }

    @Test
    public void getSubtaskFromIdTest() {
        assertEquals(subtask, taskManager.getSubTasksById(subtask.getId()), "getSubtaskFromIdTest - не пройден");
    }

    @Test
    public void getSubtaskFromIdWhenWrongIdTest() {
        assertNull(taskManager.getSubTasksById(0), "getSubtaskFromIdWhenWrongIdTest - не пройден");
    }

    @Test
    public void getEpicFromIdTest() {
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "getEpicFromIdTest - не пройден");
    }

    @Test
    public void creationTaskTest() {
        assertNotNull(task, "task не null - не пройден");
        assertTrue(task.getId() > 0 , "Присвоен некорректный Id");
        assertEquals(Status.NEW, task.getStatus(), "Присвоен некорректный статус");
    }

    @Test
    public void creationTaskWhenWrongInputData() {
        assertNull(taskManager.createTask(null), "creationTaskWhenWrongInputData - не пройден");
    }

    @Test
    public void creationSubtaskTest() {
        assertNotNull(subtask, "subtask не null - не пройден");
        assertTrue(subtask.getId() > 0 , "Присвоен некорректный Id");
        assertEquals(Status.NEW, subtask.getStatus(), "Присвоен некорректный статус");

    }

    @Test
    public void creationSubtaskWhenWrongInputData() {
        assertNull(taskManager.createSubTask(null, epic.getId()), "creationTaskWhenWrongInputData - не пройден");
    }

    @Test
    public void creationSubtaskWhenWrongEpic() {
        assertNull(taskManager.createSubTask(subtask, 0), "creationSubtaskWhenWrongEpic - не пройден");
    }

    @Test
    public void creationEpicTest() {
        assertNotNull(epic, "epic не null - не пройден");
        assertTrue(epic.getId() > 0 , "Присвоен некорректный Id");
        assertEquals(Status.NEW, epic.getStatus(), "Присвоен некорректный статус");
    }

    @Test
    public void updateTaskTest() {
        assertEquals(Status.NEW, task.getStatus(), "начальный статус неверный");
        taskManager.updateTask(task, Status.DONE);
        assertEquals(Status.DONE, task.getStatus(), "updateTaskTest - не пройден");
    }

    @Test
    public void updateTaskWhenWrongInputDataTest() {
        assertEquals(Status.NEW, task.getStatus(), "начальный статус неверный");
        taskManager.updateTask(null, Status.DONE);
        assertEquals(Status.NEW, task.getStatus(), "конечный статус неверный");
    }

    @Test
    public void updateSubtaskTest() {
        assertEquals(Status.NEW, subtask.getStatus(), "начальный статус неверный");
        taskManager.updateSubTask(subtask, Status.DONE, epic.getId());
        assertEquals(Status.DONE, subtask.getStatus(), "updateSubtaskTest - не пройден");
    }

    @Test
    public void updateSubtaskWhenWrongInputDataTest() {
        assertEquals(Status.NEW, subtask.getStatus(), "начальный статус неверный");
        taskManager.updateSubTask(null, Status.DONE, epic.getId());
        assertEquals(Status.NEW, subtask.getStatus(), "конечный статус неверный");
    }

    @Test
    public void updateEpicTest() {
        assertEquals(Status.NEW, epic.getStatus(), "начальный статус неверный");
        taskManager.updateSubTask(subtask, Status.DONE, epic.getId());
        assertEquals(Status.DONE, subtask.getStatus(), "updateEpicTest - не пройден");
    }

    @Test
    public void updateEpicTestWhenNoSubtask() {
        assertEquals(Status.NEW, epic.getStatus(), "начальный статус неверный");
        taskManager.removeAllSubTasks();
        ArrayList<Subtask> list = taskManager.getSubTasks();
        assertTrue(list.isEmpty(), "Удаление всех подзадач, проверка Эпика - не пройден");
        assertEquals(Status.NEW, epic.getStatus(), "updateEpicTestWhenNoSubtask - не пройден");
    }

    @Test
    public void updateEpicTestWhenAllSubtaskDONE() {
        Subtask subtask2 = new Subtask("Subtask2 name", "Subtask2 description",epic.getId(), TaskType.SUBTASK, LocalDateTime.of(2022,10,3,10, 0), 90);
        taskManager.createSubTask(subtask2, epic.getId());
        taskManager.updateSubTask(subtask, Status.DONE, epic.getId());
        taskManager.updateSubTask(subtask2, Status.DONE, epic.getId());
        assertEquals(Status.DONE, epic.getStatus(), "updateSubtaskTest - не пройден");
    }

    @Test
    public void updateEpicTestWhenSubtaskDONEAndNEW() {
        Subtask subtask2 = new Subtask("Subtask2 name", "Subtask2 description",epic.getId(), TaskType.SUBTASK, LocalDateTime.of(2022,10,4,10, 0), 90);
        taskManager.createSubTask(subtask2, epic.getId());
        taskManager.updateSubTask(subtask2, Status.DONE, epic.getId());
        assertEquals(Status.NEW, epic.getStatus(), "updateEpicTestWhenSubtaskDONEAndNEW - не пройден");
    }

    @Test
    public void deleteTaskOfIdTest() {
        assertEquals(task, taskManager.getTasksById(task.getId()), "getTaskListTest в deleteTaskOfIdTest - не пройден");
        taskManager.removeAllTasks(); //Надо фиксить
        assertNull(taskManager.getTasksById(task.getId()), "deleteTaskOfIdTest - не пройден");
    }

    @Test
    public void deleteTaskOfIdWhenWrongIdTest() {
        assertEquals(task, taskManager.getTasksById(task.getId()), "getTaskFromIdTest в deleteTaskOfIdWhenWrongIdTest - не пройден");
        taskManager.removeTaskById(0);
        assertEquals(task, taskManager.getTasksById(task.getId()), "deleteTaskOfIdWhenWrongIdTest - не пройден");
    }

    @Test
    public void deleteSubtaskOfIdTest() {
        assertEquals(subtask, taskManager.getSubTasksById(subtask.getId()), "getSubtaskOfIdTest в deleteSubtaskOfIdTest - не пройден");
        taskManager.removeAllSubTasks(); //Надо фиксить
        assertNull(taskManager.getSubTasksById(subtask.getId()), "deleteSubtaskOfIdTest - не пройден");
    }

    @Test
    public void deleteSubtaskOfIdWhenWrongIdTest() {
        assertEquals(subtask, taskManager.getSubTasksById(subtask.getId()), "getSubtaskOfIdTest в deleteSubtaskOfIdWhenWrongIdTest - не пройден");
        taskManager.removeSubTaskById(0);
        assertEquals(subtask,taskManager.getSubTasksById(subtask.getId()), "deleteSubtaskOfIdWhenWrongIdTest - не пройден");
    }

    @Test
    public void deleteEpicOfIdTest() {
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "getEpicOfIdTest в deleteEpicOfIdTest - не пройден");
        taskManager.removeAllEpics(); //Надо фиксить
        assertNull(taskManager.getEpicsById(epic.getId()), "deleteSubtaskOfIdTest - не пройден");
    }

    @Test
    public void deleteEpicOfIdWhenWrongIdTest() {
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "getEpicOfIdTest в deleteEpicOfIdWhenWrongIdTest - не пройден");
        taskManager.removeEpicById(0);
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "deleteEpicOfIdWhenWrongIdTest - не пройден");
    }

    @Test
    public void getEpicSubtasksListTest() {
        ArrayList<Subtask> subtasks = new ArrayList<> (taskManager.getEpicSubtasksList(epic));
        assertEquals(subtask, subtasks.get(0), "getEpicSubtasksList - не пройден"); //Надо фиксить
    }

    @Test
    void getEpicSubtasksListWhenWrongInputDataTest() {
        assertNull(taskManager.getEpicSubtasksList(null));
    }
}
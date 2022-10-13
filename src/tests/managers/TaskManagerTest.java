package managers;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest <T extends TaskManager>  {

    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    protected void taskManagerSetUp() {
        task = new Task(1,"Task name", "Task description", TaskType.TASK, Status.NEW,
                LocalDateTime.of(2022,10,13,10, 0), 40);
        taskManager.createTask(task);

        epic = new Epic(2,"Epic name", "Epic description", TaskType.EPIC, Status.NEW,
                LocalDateTime.of(2022,10,13,10, 0), 40);
        taskManager.createEpic(epic);

        subtask = new Subtask(3,"Subtask name", "Subtask description",epic.getId(), TaskType.SUBTASK, Status.NEW,
                LocalDateTime.of(2022,10,13,10, 0), 40);
        taskManager.createSubTask(subtask, epic.getId());
    }

    @Test
    public void getTasksTest() {
        ArrayList<Task> taskList = new ArrayList<> (taskManager.getTasks());
        assertEquals(task, taskList.get(0), "getTaskListTest - не пройден");
    }

    @Test
    public void getTasksTestWhenListIsEmptyTest() {
        ArrayList<Task> taskList = new ArrayList<>(taskManager.getTasks());
        assertEquals(task, taskList.get(0), "getTasksTest - не пройден");
        taskManager.removeAllTasks();
        taskList = new ArrayList<>(taskManager.getTasks());
        assertTrue(taskList.isEmpty(), "getTasksTestWhenListIsEmpty - не пройден");
    }

    @Test
    public void getSubTasksTest() {
        ArrayList<Task> subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertEquals(subtask, subtaskList.get(0), "getSubTasksTest - не пройден");
    }

    @Test
    public void getSubTasksTestWhenListIsEmptyTest() {
        ArrayList<Task> subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertEquals(subtask, subtaskList.get(0), "getSubTasksTest - не пройден");
        taskManager.removeAllSubTasks();
        subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertTrue(subtaskList.isEmpty(), "getSubtasksTestWhenListIsEmpty - не пройден");
    }

    @Test
    public void getEpicsTest() {
        ArrayList<Task> epicList = new ArrayList<> (taskManager.getEpics());
        assertEquals(epic, epicList.get(0), "getSubTasksTest - не пройден");
    }

    @Test
    public void getEpicsTestWhenListIsEmptyTest() {
        ArrayList<Task> epicList = new ArrayList<> (taskManager.getEpics());
        assertEquals(epic, epicList.get(0), "getSubTasksTest - не пройден");
        taskManager.removeAllEpics();
        epicList = new ArrayList<> (taskManager.getEpics());
        assertTrue(epicList.isEmpty(), "getSubTasksTestWhenListIsEmpty - не пройден");
    }

    @Test
    public void cleanTasksTest() {
        ArrayList<Task> taskList = new ArrayList<> (taskManager.getTasks());
        assertEquals(task, taskList.get(0), "getTasksTest - не пройден");
        taskManager.removeAllTasks();
        taskList = new ArrayList<> (taskManager.getTasks());
        assertTrue(taskList.isEmpty(), "cleanTasksTest - не пройден");
    }

    @Test
    public void cleanSubTasksTest() {
        ArrayList<Task> subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertEquals(subtask, subtaskList.get(0), "getSubTasksTest - не пройден");
        taskManager.removeAllSubTasks();
        subtaskList = new ArrayList<> (taskManager.getSubTasks());
        assertTrue(subtaskList.isEmpty(), "cleanSubTaskTest - не пройден");
    }

    @Test
    public void cleanEpicsTest() {
        ArrayList<Task> epicList = new ArrayList<> (taskManager.getEpics());
        assertEquals(epic, epicList.get(0), "getSubTasksTest - не пройден");
        taskManager.removeAllEpics();
        epicList = new ArrayList<> (taskManager.getEpics());
        assertTrue(epicList.isEmpty(), "cleanEpicsTest - не пройден");
    }

    @Test
    public void getTasksByIdTest() {
        assertEquals(task, taskManager.getTasksById(task.getId()), "getTasksByIdTest - не пройден");
    }

    @Test
    public void getTasksByIdWhenWrongIdTest() {
        assertNull(taskManager.getTasksById(0), "getTasksByIdWhenWrongIdTest - не пройден");
    }

    @Test
    public void getEpicsByIdTest() {
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "getEpicsByIdTest - не пройден");
    }

    @Test
    public void getSubTasksByIdTest() {
        assertEquals(subtask, taskManager.getSubTasksById(subtask.getId()), "getSubTasksByIdTest - не пройден");
    }

    @Test
    public void getSubTasksByIdWhenWrongIdTest() {
        assertNull(taskManager.getSubTasksById(0), "getSubTasksByIdWhenWrongIdTest - не пройден");
    }

    @Test
    public void createTaskTest() {
        assertNotNull(task, "task не null - не пройден");
        assertTrue(task.getId() > 0 , "Присвоен некорректный Id");
        assertEquals(Status.NEW, task.getStatus(), "Присвоен некорректный статус");
    }

    @Test
    public void createTaskWhenWrongInputData() {
        assertNull(taskManager.createTask(null), "createTaskWhenWrongInputData - не пройден");
    }

    @Test
    public void createSubtaskTest() {
        assertNotNull(subtask, "subtask не null - не пройден");
        assertTrue(subtask.getId() > 0 , "Присвоен некорректный Id");
        assertEquals(Status.NEW, subtask.getStatus(), "Присвоен некорректный статус");

    }

    @Test
    public void createSubTaskWhenWrongInputData() {
        assertNull(taskManager.createSubTask(null, epic.getId()), "createSubTaskWhenWrongInputData - не пройден");
    }

    @Test
    public void createSubTaskWhenWrongEpic() {
        assertNull(taskManager.createSubTask(subtask, 0), "createSubTaskWhenWrongEpic - не пройден");
    }

    @Test
    public void createEpicTest() {
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
        assertEquals(Status.NEW, task.getStatus(), "крайний статус неверный");
    }

    @Test
    public void updateSubTaskTest() {
        assertEquals(Status.NEW, subtask.getStatus(), "начальный статус неверный");
        taskManager.updateSubTask(subtask, Status.DONE, epic.getId());
        assertEquals(Status.DONE, subtask.getStatus(), "updateSubTaskTest - не пройден");
    }

    @Test
    public void updateSubTaskWhenWrongInputDataTest() {
        assertEquals(Status.NEW, subtask.getStatus(), "начальный статус неверный");
        taskManager.updateSubTask(null, Status.DONE, epic.getId());
        assertEquals(Status.NEW, subtask.getStatus(), "крайний статус неверный");
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
        Subtask subtask2 = new Subtask("Subtask2 name", "Subtask2 description",epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);
        taskManager.createSubTask(subtask2, epic.getId());
        taskManager.updateSubTask(subtask, Status.DONE, epic.getId());
        taskManager.updateSubTask(subtask2, Status.DONE, epic.getId());
        assertEquals(Status.DONE, epic.getStatus(), "updateSubTaskTest - не пройден");
    }

    @Test
    public void updateEpicTestWhenSubtaskDONEAndNEW() {
        Subtask subtask2 = new Subtask("Subtask2 name", "Subtask2 description",epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,9,4,10, 0), 90);
        taskManager.createSubTask(subtask2, epic.getId());
        taskManager.updateSubTask(subtask2, Status.DONE, epic.getId());
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "updateEpicTestWhenSubtaskDONEAndNEW - не пройден");
    }
    @Test
    public void deleteTaskOfIdTest() {
        assertEquals(task, taskManager.getTasksById(task.getId()), "getTasksTest в removeTaskByIdTest - не пройден");
        taskManager.removeTaskById(task.getId());
        assertNull(taskManager.getTasksById(task.getId()), "deleteTaskOfIdTest - не пройден");
    }
    @Test
    public void deleteTaskOfIdWhenWrongIdTest() {
        assertEquals(task, taskManager.getTasksById(task.getId()), "getTasksByIdTest в removeTaskByIdWhenWrongIdTest - не пройден");
        taskManager.removeTaskById(0);
        assertEquals(task, taskManager.getTasksById(task.getId()), "removeTaskByIdIdWhenWrongIdTest - не пройден");
    }
    @Test
    public void removeSubTaskByIdTest() {
        assertEquals(subtask, taskManager.getSubTasksById(subtask.getId()), "getSubTasksByIdTest в removeSubTaskByIdTest - не пройден");
        taskManager.removeSubTaskById(subtask.getId());
        assertNull(taskManager.getSubTasksById(subtask.getId()), "removeSubTaskByIdTest - не пройден");
    }
    @Test
    public void removeSubTaskByIdWhenWrongIdTest() {
        assertEquals(subtask, taskManager.getSubTasksById(subtask.getId()), "getSubTasksByIdTest в removeSubTaskByIdWhenWrongIdTest - не пройден");
        taskManager.removeSubTaskById(0);
        assertEquals(subtask,taskManager.getSubTasksById(subtask.getId()), "removeSubTaskByIddWhenWrongIdTest - не пройден");
    }
    @Test
    public void removeEpicByIdTest() {
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "getEpicsByIdTest в deleteEpicOfIdTest - не пройден");
        taskManager.removeEpicById(epic.getId());
        assertNull(taskManager.getEpicsById(epic.getId()), "removeEpicByIdTest - не пройден");
    }
    @Test
    public void removeEpicByIdWhenWrongIdTest() {
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "getEpicsByIdTest в removeEpicByIdWhenWrongIdTest - не пройден");
        taskManager.removeEpicById(0);
        assertEquals(epic, taskManager.getEpicsById(epic.getId()), "removeEpicByIdWhenWrongIdTest - не пройден");
    }
    @Test
    public void getEpicSubtasksListTest() {
        ArrayList<Subtask> subtasks = new ArrayList<> (taskManager.getEpicSubtasksList(epic));
        assertEquals(subtask, subtasks.get(0), "getEpicSubtasksList - не пройден");
    }
    @Test
    void getEpicSubtasksListWhenWrongInputDataTest() {
        assertNull(taskManager.getEpicSubtasksList(null));
    }

    }
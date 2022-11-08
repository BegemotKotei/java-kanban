package managers;

import exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Status;
import tasks.Task;
import tasks.Subtask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTasksManagerTest extends TaskManagerTest <InMemoryTasksManager>  {

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTasksManager();
        taskManagerSetUp();
    }
    @Test
    public void generateId() {
        int i = InMemoryTasksManager.generateId;
        InMemoryTasksManager.generateId();
        assertEquals(i+1, InMemoryTasksManager.generateId);
    }

    @Test
    public void getEpicSubtasksList() {
        List<Subtask> list = taskManager.getEpicSubtasksList(epic);
        assertEquals(subtask, list.get(0));
    }

    @Test
    public void getPrioritizedTasksTest() {
        ArrayList<Task> list = new ArrayList<> (taskManager.getPrioritizedTasks());
        assertEquals(task, list.get(0));
        assertEquals(subtask, list.get(1), "getPrioritizedTasksTest - не пройден");
    }

    @Test
    public void validateTest() {
        ArrayList<Task> list = new ArrayList<> (taskManager.getPrioritizedTasks());
        assertEquals(2, list.size());
        Task task1 = new Task(1,"Task name", "Task description", TaskType.TASK, Status.NEW, LocalDateTime.of(2022,9,1,10, 0), 90);
        taskManager.createTask(task1);
        list = new ArrayList<> (taskManager.getPrioritizedTasks());
        assertEquals(2, list.size());
    }

    @Test
    public void ValidationExceptionTest() {
        final ValidationException ex = assertThrows(
                ValidationException.class,
                () -> {
                    Task task1 = new Task(2,"Task name2", "Task description2", TaskType.TASK, Status.NEW, LocalDateTime.of(2022,9,1,9, 0), 600);
                    taskManager.validate(task1);
                });
        assertEquals("Задача не может заканчиваться позже начала другой задачи", ex.getMessage());
    }

}
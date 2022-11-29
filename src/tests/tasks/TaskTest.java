package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import managers.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TaskTest {

    Task task;

    @BeforeEach
    public void BeForeEach() {
        task = new Task(1, "Task Name", "Description task", TaskType.TASK, Status.NEW, LocalDateTime.of(2022,10,13,10,0), 60);
    }

    @Test
    void getNameTest() {
        assertEquals("Task Name", task.getName(), "getNameTest - не пройден");
    }

    @Test
    void getDescriptionTest() {
        assertEquals("Description task", task.getDescription(), "getDescriptionTest - не пройден");
    }

    @Test
    void getStatusTest() {
        assertEquals(Status.NEW, task.getStatus(), "getStatusTest - не пройден");
    }

    @Test
    void getIdTest() {
        task.setId(1);
        assertEquals(1, task.getId(), "getIdTest");
    }

    @Test
    void setIdTest() {
        task.setId(2);
        assertEquals(2, task.getId(), "setIdTest - не пройден");
    }

    @Test
    void setNameTest() {
        task.setName("Task new name");
        assertEquals("Task new name", task.getName(), "setNameTest - не пройден");
    }

    @Test
    void setDescriptionTest() {
        task.setDescription("Task new description");
        assertEquals("Task new description", task.getDescription(), "setDescriptionTest - не пройден");
    }

    @Test
    void setStatusTest() {
        task.setStatus(Status.DONE);
        assertEquals(Status.DONE, task.getStatus(), "setStatusTest - не пройден");
    }

    @Test
    void testToStringTest() {
        task.setId(1);
        assertEquals("1,TASK,Task Name,NEW,Description task,2022-10-13T10:00:00,60,2022-10-13T11:00:00", task.toString(), "testToStringTest - не пройден");
    }

}
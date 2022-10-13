package tasks;

import managers.InMemoryTasksManager;
import managers.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    Epic epic;
    Subtask subtask;
    InMemoryTasksManager taskManager;

    @BeforeEach
    public void BeForeEach() {
        taskManager = new InMemoryTasksManager();
        epic = new Epic(1,"Epic name", "Epic description", TaskType.EPIC, Status.NEW,
                LocalDateTime.of(2022,10,13,10,0), 60);

        subtask = new Subtask(2,"Subtask name", "Subtask description",epic.getId(), TaskType.SUBTASK, Status.NEW,
                LocalDateTime.of(2022,10,13,10,0), 60);
    }

    @Test
    void setIdEpicTest() {
        subtask.setIdEpic(1);
        assertEquals(1, subtask.getIdEpic(), "setIdEpicTest - не пройден");
    }

    @Test
    void getIdEpicTest() {
        subtask.setIdEpic(1);
        assertEquals(1, subtask.getIdEpic(), "getIdEpicTest - не пройден");
    }

    @Test
    void testToString() {
        assertEquals("2,SUBTASK,Subtask name,NEW,Subtask description,2022-10-13T10:00:00,60,2022-10-13T11:00:00,1"  ,subtask.toString());
    }


}
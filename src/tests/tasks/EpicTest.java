package tasks;

import managers.InMemoryTasksManager;
import managers.TaskType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    Epic epic;
    private final InMemoryTasksManager inMemoryTasksManager = new InMemoryTasksManager();

    @BeforeEach
    public void  beForeEach() {
        epic = new Epic("Epic name", "Epic description", TaskType.EPIC);
        inMemoryTasksManager.createEpic(epic);
    }

    @Test
    public void shouldBeZeroSubtaskIdListWhenNoSubtask() {
        ArrayList<Integer> subtasksId = epic.getEpicSubtasks();
        Integer[] test = new Integer[0];
        Integer[] subtasksArray = new Integer[subtasksId.size()];
        for (int i = 0; i < subtasksArray.length; i++) {
            subtasksArray[i] = subtasksId.get(i);
        }
        assertArrayEquals(test, subtasksArray, " Пустой список подзадач - не пройден");
    }

    @Test
    public void shouldBeStatusNewWhenAllSubtasksNew() {
        Subtask subtask1 = new Subtask("sb name1", "sb1 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);
        inMemoryTasksManager.createSubTask(subtask1, epic.getId());

        Subtask subtask2 = new Subtask("sb name2", "sb2 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);
        inMemoryTasksManager.createSubTask(subtask2, epic.getId());

        assertEquals(Status.NEW, epic.getStatus(), "Все подзадачи со статусом NEW - не пройден");
    }

    @Test
    public void shouldBeStatusDoneWhenAllSubtasksDone() {
        Subtask subtask1 = new Subtask("sb name1", "sb1 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);

        Subtask subtask2 = new Subtask("sb name2", "sb2 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);

        inMemoryTasksManager.createSubTask(subtask1, epic.getId());
        inMemoryTasksManager.createSubTask(subtask2, epic.getId());
        inMemoryTasksManager.updateSubTask(subtask1, Status.DONE, subtask1.getIdEpic());
        inMemoryTasksManager.updateSubTask(subtask2, Status.DONE, subtask1.getIdEpic());
        assertEquals(Status.DONE, epic.getStatus(), "Все подзадачи со статусом DONE - не пройден");
    }

    @Test
    public void shouldBeStatusIN_PROGRESSWhenSubtasksDoneAndNew() {
        Subtask subtask1 = new Subtask("sb name1", "sb1 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);

        inMemoryTasksManager.createSubTask(subtask1, epic.getId());
        Subtask subtask2 = new Subtask("sb name2", "sb2 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);
        inMemoryTasksManager.createSubTask(subtask2, epic.getId());

        inMemoryTasksManager.updateSubTask(subtask1, Status.IN_PROGRESS, subtask1.getIdEpic());
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Подзадачи со статусами NEW и DONE - не пройден");
    }

    @Test
    public void shouldBeStatusIN_PROGRESSWhenSubtasksIN_PROGRESS() {
        Subtask subtask1 = new Subtask("sb name1", "sb1 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);

        Subtask subtask2 = new Subtask("sb name2", "sb2 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);

        inMemoryTasksManager.createSubTask(subtask1, epic.getId());
        inMemoryTasksManager.createSubTask(subtask2, epic.getId());
        inMemoryTasksManager.updateSubTask(subtask1, Status.IN_PROGRESS, subtask1.getIdEpic());
        inMemoryTasksManager.updateSubTask(subtask2, Status.IN_PROGRESS, subtask1.getIdEpic());
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Подзадачи со статусом IN_PROGRESS - не пройден");
    }

    @Test
    public void getEpicSubtasksTest() {
        assertTrue(epic.getEpicSubtasks().isEmpty());
        Subtask subtask1 = new Subtask("sb name1", "sb1 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);

        inMemoryTasksManager.createSubTask(subtask1, epic.getId());
        ArrayList<Integer> test = new ArrayList<>();
        test.add(subtask1.getId());
        assertEquals(test, epic.getEpicSubtasks(),"getEpicSubtasks - не прошел тест");
    }

    @Test
    public void addSubtasksIdTest() {
        assertTrue(epic.getEpicSubtasks().isEmpty());
        Subtask subtask1 = new Subtask("sb name1", "sb1 description", epic.getId(), TaskType.SUBTASK,
                LocalDateTime.of(2022,10,13,10, 0), 40);

        inMemoryTasksManager.createSubTask(subtask1, epic.getId());
        ArrayList<Integer> test = new ArrayList<>();
        test.add(subtask1.getId());
        assertEquals(test, epic.getEpicSubtasks(),"addSubtasksIdTest - не прошел тест");
    }
}
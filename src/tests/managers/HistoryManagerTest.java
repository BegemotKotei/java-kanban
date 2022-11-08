package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private HistoryManager historyManager;
    private InMemoryTasksManager inMemoryTaskManager;
    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
        inMemoryTaskManager = new InMemoryTasksManager();
        task = new Task("Task name", "Task description", TaskType.TASK, LocalDateTime.of(2022,10,13,10, 0), 40);
        inMemoryTaskManager.createTask(task);

        epic = new Epic("Epic name", "Epic description", TaskType.EPIC);
        inMemoryTaskManager.createEpic(epic);

        subtask = new Subtask("Subtask name", "Subtask description",epic.getId(), TaskType.SUBTASK,LocalDateTime.of(2022,10,13,10, 0), 40);
        inMemoryTaskManager.createSubTask(subtask, epic.getId());
    }

    @Test
    public void getHistoryWhenEmptyHistory() {
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertTrue(history.isEmpty(), "История не пустая.");
    }

    @Test
    public void shouldBeOneWhenAddTwice() {
        historyManager.add(task);
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Добавили две одинаковых задачи, а в истории может быть одна");
        assertEquals(task, history.get(0),"задача, которую добавили");
    }

    @Test
    public void add() {
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    public void removeFirst() {
        historyManager.add(task);
        historyManager.add(subtask);
        historyManager.add(epic);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(3, history.size(), "Правильное количество элементов");
        historyManager.remove(task.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(2, history.size(), "Правильное количество элементов");
        assertEquals(subtask, history.get(0), "Правильный порядок элементов");
        assertEquals(epic, history.get(1), "Правильный порядок элементов");
    }

    @Test
    public void removeMiddle() {
        historyManager.add(task);
        historyManager.add(subtask);
        historyManager.add(epic);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(3, history.size(), "Правильное количество элементов");
        historyManager.remove(subtask.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(2, history.size(), "Правильное количество элементов");
        assertEquals(task, history.get(0), "Правильный порядок элементов");
        assertEquals(epic, history.get(1), "Правильный порядок элементов");
    }

    @Test
    public void removeLast() {
        historyManager.add(task);
        historyManager.add(subtask);
        historyManager.add(epic);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(3, history.size(), "Правильное количество элементов");
        historyManager.remove(epic.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(2, history.size(), "Правильное количество элементов");
        assertEquals(task, history.get(0), "Правильный порядок элементов");
        assertEquals(subtask, history.get(1), "Правильный порядок элементов");
    }

    @Test
    public void removeSingle() {
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(1, history.size(), "Содержит правильное количество элементов");
        historyManager.remove(task.getId());
        history = historyManager.getHistory();
        assertNotNull(history, "История не пустая");
        assertEquals(0, history.size(), "Содержит правильное количество элементов");
    }

    @Test
    public void getHistory() {
    }
}
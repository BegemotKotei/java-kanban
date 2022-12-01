package managers.http;

import managers.*;
import managers.http.server.KVServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpTaskManagerTest <T extends TaskManagerTest<HttpTaskManager>> {
    private KVServer server;
    private TaskManager manager;

    @BeforeEach
    public void createManager() {
        try {
            server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            manager = Managers.getDefault(historyManager);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при создании менеджера");
        }
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void shouldLoadTasks() {
        Task task1 = new Task("description1", "name1", Status.NEW, Instant.now(), 1);
        Task task2 = new Task("description2", "name2", Status.NEW, Instant.now(), 2);
        manager.createTask(task1);
        manager.createTask(task2);
        manager.getTasksById(task1.getId());
        manager.getTasksById(task2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getTasks(), list);
    }

    @Test
    public void shouldLoadEpics() {
        Epic epic1 = new Epic("description1", "name1", Status.NEW, Instant.now(), 3);
        Epic epic2 = new Epic("description2", "name2", Status.NEW, Instant.now(), 4);
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.getEpicsById(epic1.getId());
        manager.getEpicsById(epic2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getEpics(), list);
    }

    @Test
    public void shouldLoadSubtasks() {
        Epic epic1 = new Epic("description1", "name1", Status.NEW, Instant.now(), 5);
        Subtask subtask1 = new Subtask("description1", "name1", Status.NEW, epic1.getId()
                , Instant.now(), 6);
        Subtask subtask2 = new Subtask("description2", "name2", Status.NEW, epic1.getId(),
                Instant.now(), 7);
        manager.createSubTask(subtask1);
        manager.createSubTask(subtask2);
        manager.getSubTasksById(subtask1.getId());
        manager.getSubTasksById(subtask2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getSubTasks(), list);
    }

}

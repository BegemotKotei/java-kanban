package server;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import managers.Managers;
import managers.FileBackedTasksManager;
import managers.TaskType;

import tasks.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    private HttpTaskServer server;
    private FileBackedTasksManager fileBackedTasksManager;

    private Task task;
    private Subtask subtask;
    private Epic epic;

    Gson gson = Managers.getGson();

    @BeforeEach
    void setUp() throws IOException {
        server = new HttpTaskServer();
        fileBackedTasksManager = Managers.getDefaultFileBackedTasks();

        task = new Task(1,"Task name", "Task description", TaskType.TASK, Status.NEW, LocalDateTime.of(2022,9,1,10, 0), 90);
        fileBackedTasksManager.createTask(task);

        epic = new Epic(2,"Epic name", "Epic description", TaskType.EPIC, Status.NEW, LocalDateTime.of(2022,9,2,10, 0), 90);
        fileBackedTasksManager.createEpic(epic);

        subtask = new Subtask(3,"Subtask name", "Subtask description",epic.getId(), TaskType.SUBTASK, Status.NEW, LocalDateTime.of(2022,9,3,10, 0), 90);
        fileBackedTasksManager.createSubTask(subtask, epic.getId());
        server.start();
    }
    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void getTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());

        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        List<Task> list = gson.fromJson(response.body(), taskType);
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");

    }

    @Test
    void getTaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());

        Type taskType = new TypeToken<Task>() {
        }.getType();

        Task received = gson.fromJson(response.body(), taskType);
        assertNotNull(received, "Задачи не возвращаются");

        //assertEquals(task.getId(), received.getId(), "getTaskById - не пройден");
        assertEquals(task.getName(), received.getName(), "getTaskById - не пройден");
        assertEquals(task.getStatus(), received.getStatus(), "getTaskById - не пройден");
        assertEquals(task.getDescription(), received.getDescription(), "getTaskById - не пройден");
        assertEquals(task.getStartTime(), received.getStartTime(), "getTaskById - не пройден");
        assertEquals(task.getEndTime(), received.getEndTime(), "getTaskById - не пройден");
    }
    @Test
    void creationTask() throws IOException, InterruptedException {
        Task task2 = new Task("Task name2", "Task description2", TaskType.TASK, LocalDateTime.of(2022,9,1,10, 0), 90);
        fileBackedTasksManager.createTask(task2);

        String str = gson.toJson(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());


        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/tasks/task?id=4");
        request = HttpRequest.newBuilder().uri(url).GET().build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());


    }


}

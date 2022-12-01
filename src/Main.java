import adapters.InstantAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import managers.*;
import managers.http.server.KVServer;
import tasks.*;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        KVServer server;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
            server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            TaskManager httpTaskManager = Managers.getDefault(historyManager);

            Task task1 = new Task(
                    "Выйти на улицу","улица",
                    Status.NEW, Instant.now(), 1);
            httpTaskManager.createTask(task1);

            Epic epic1 = new Epic(
                    "Собрать стол", "стол",
                    Status.NEW, Instant.now(), 2);
            httpTaskManager.createEpic(epic1);

            Subtask subtask1 = new Subtask(
                    "Взять инструменты", "Инструменты",
                    Status.NEW, epic1.getId(), Instant.now(), 3);
            httpTaskManager.createSubTask(subtask1);


            httpTaskManager.getTasksById(task1.getId());
            httpTaskManager.getEpicsById(epic1.getId());
            httpTaskManager.getSubTasksById(subtask1.getId());

            System.out.println("Печать всех задач: ");
            System.out.println(gson.toJson(httpTaskManager.getTasks()));
            System.out.println("Печать всех эпиков: ");
            System.out.println(gson.toJson(httpTaskManager.getEpics()));
            System.out.println("Печать всех подзадач: ");
            System.out.println(gson.toJson(httpTaskManager.getSubTasks()));
            System.out.println("Загруженный менеджер: ");
            System.out.println(httpTaskManager);
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

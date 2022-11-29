package managers;

import adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.KVServer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public final class Managers {

    public static final TaskManager taskManager = new InMemoryTasksManager();

    public static final FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(new File("data\\csv.csv"));

    public static HttpTasksManager getDefaultHttpTaskManager() {
        return new HttpTasksManager(KVServer.PORT);
    }

    public static TaskManager getDefault() {
        return taskManager;
    }

    public static FileBackedTasksManager getDefaultFileBackedTasks() {
        return fileBackedTasksManager;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static KVServer getDefaultKVServer() throws IOException {
        return new KVServer();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}
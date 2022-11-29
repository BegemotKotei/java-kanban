import com.google.gson.Gson;
import exception.*;
import managers.*;
import server.KVServer;
import tasks.Task;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException, ManagerSaveException, ManagerLoadException {

        KVServer kvServer = Managers.getDefaultKVServer();
        kvServer.start();
        HttpTasksManager httpTaskManager = new HttpTasksManager(KVServer.PORT);
        Task task1 = new Task("Забрать посылку","Сходить на почту и забрать посылку", TaskType.TASK, LocalDateTime.of(2022,9,1,10, 0), 90);
        httpTaskManager.createTask(task1);
        httpTaskManager.save();

        httpTaskManager.load();

        /*new KVServer().start();   
        KVTaskClient kvTaskClient = new KVTaskClient(8078);
        kvTaskClient.put("1", "{\\\"name\\\":\\\"Забрать посылку\\\",\\\"description\\\":\\\"Сходить на почту и забрать посылку\\\",\\\"id\\\":1,\\\"status\\\":\\\"NEW\\\",\\\"typeTask\\\":\\\"TASK\\\",\\\"startTime\\\":{\\\"date\\\":{\\\"year\\\":2022,\\\"month\\\":9,\\\"day\\\":1},\\\"time\\\":{\\\"hour\\\":10,\\\"minute\\\":0,\\\"second\\\":0,\\\"nano\\\":0}},\\\"duration\\\":90}");
        String str = kvTaskClient.load("1");*/
    }
}
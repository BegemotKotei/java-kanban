import adapters.InstantAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.*;
import managers.*;
import server.KVServer;
import tasks.*;

import java.time.Instant;
import java.time.LocalDateTime;

public class Main {
    //Запускает, но ругается на gson и прочее, пытаюсь понять в чём дело:(Можно подсказку, если есть идеи?
    // Мне кажется, просто, неправильно импортировл его:д
    public static void main(String[] args) throws ManagerSaveException {

        KVServer server;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();

            server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            TaskManager httpTaskManager = Managers.getDefault();

            Task task1 = new Task(
                    "Забрать книги","Поехать в пункт выдачи", TaskType.TASK,
                    LocalDateTime.of(2022,11,13,12, 30), 50);
            httpTaskManager.createTask(task1);

            Epic epic1 = new Epic("Почистить пк", "Чистка пк", TaskType.EPIC);
            httpTaskManager.createEpic(epic1);

            Subtask subtask1 = new Subtask("Купить термопасту", "Сходить в днс", 1, TaskType.SUBTASK,
                    LocalDateTime.of(2022,10,13,10, 0), 20);
            httpTaskManager.createSubTask(subtask1, epic1.getId());


            httpTaskManager.getTasksById(task1.getId());
            httpTaskManager.getEpicsById(epic1.getId());
            httpTaskManager.getSubTasksById(subtask1.getId());

            System.out.println("Печать всех задач");
            System.out.println(gson.toJson(httpTaskManager.getTasks()));
            System.out.println("Печать всех эпиков");
            System.out.println(gson.toJson(httpTaskManager.getEpics()));
            System.out.println("Печать всех подзадач");
            System.out.println(gson.toJson(httpTaskManager.getSubTasks()));
            System.out.println("Загруженный менеджер");
            System.out.println(httpTaskManager);
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
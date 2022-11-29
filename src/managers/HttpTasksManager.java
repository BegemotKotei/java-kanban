package managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class HttpTasksManager extends FileBackedTasksManager {

    private final KVTaskClient client;
    private final Gson gson;

    public HttpTasksManager(int port) {
        super(new File("data\\csv.csv"));
        gson = Managers.getGson();
        client = new KVTaskClient(port);
    }

    public void load() {    //Переопределяю метод loadFromFile.
        Type tasksType = new TypeToken<ArrayList<Task>>() {
        }.getType();

        ArrayList<Task> tasks = gson.fromJson(client.load("tasks"), tasksType);
        if (Objects.nonNull(tasks)) {
            tasks.forEach(task -> {
                int id = task.getId();
                this.tasks.put(id, task);
                this.prioritizedTasks.add(task);
                if (id > generateId) {
                    generateId = id;
                }
            });
        }

        Type subtasksType = new TypeToken<ArrayList<Subtask>>() {
        }.getType();
        ArrayList<Subtask> subtasks = gson.fromJson(client.load("subtasks"), subtasksType);
        if (Objects.nonNull(subtasks)) {
            subtasks.forEach(subtask -> {
                int id = subtask.getId();
                this.subtasks.put(id, subtask);
                this.prioritizedTasks.add(subtask);
                if (id > generateId) {
                    generateId = id;
                }
            });
        }

        Type epicsType = new TypeToken<ArrayList<Epic>>() {
        }.getType();
        ArrayList<Epic> epics = gson.fromJson(client.load("epics"), epicsType);
        if (Objects.nonNull(epics)) {
            epics.forEach(epic -> {
                int id = epic.getId();
                this.epics.put(id, epic);
                //this.prioritizedTasks.add(epic);
                if (id > generateId) {
                    generateId = id;
                }
            });
        }

        Type historyType = new TypeToken<ArrayList<Task>>(){
        }.getType();
        ArrayList<Task> history = gson.fromJson(client.load("history"), historyType);

        if (Objects.nonNull(history)) {
            for (Task task:history) {
                inMemoryHistoryManager.add(this.findTask(task.getId()));
            }
        }
    }



    public void save() {       //Переопределяю метод save.

        String tasksJson = gson.toJson(getTasks());
        client.put("tasks", tasksJson);

        String epicsJson = gson.toJson(getEpics());
        client.put("epics", epicsJson);

        String subtasksJson = gson.toJson(getSubTasks());
        client.put("subtasks" , subtasksJson);

        String historyJson = gson.toJson(getSubTasks());
        client.put("history" , historyJson);
    }

    public Task findTask(Integer id) {
        final Task task = tasks.get(id);
        if (task != null) {
            return task;
        }
        final Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            return subtask;
        }
        return epics.get(id);
    }
}
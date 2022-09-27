package managers;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTasksManager();
    }
    public static HistoryManager getDefaulHistory() {
        return new InMemoryHistoryManager();
    }
}

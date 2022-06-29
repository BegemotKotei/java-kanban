package manager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaulHistory() {
        return new InMemoryHistoryManager();
    }
}

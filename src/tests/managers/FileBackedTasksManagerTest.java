package managers;

import exception.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tasks.Task;

import java.io.File;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest <FileBackedTasksManager> {
    private File file;
    @BeforeEach
    public void setUp() {
        file = new File ("data\\csv.csv");
        taskManager = new FileBackedTasksManager(file);
        taskManagerSetUp();
    }

    @Test
    public void main() {
    }

    @Test
    public void saveTest()  {
    }

    @Test
    public void loadFromFileTest()  {
        taskManager.getTasksById(task.getId());
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);

        ArrayList<Task> list = fileBackedTasksManager.getTasks();
        assertNotNull(list,"лист не null");
        assertEquals(task.getId(), list.get(0).getId(), "loadFromFileTest - не пройден");
        assertEquals(task.getName(), list.get(0).getName(), "loadFromFileTest - не пройден");
        assertEquals(task.getStatus(), list.get(0).getStatus(), "loadFromFileTest - не пройден");
        assertEquals(task.getDescription(), list.get(0).getDescription(), "loadFromFileTest - не пройден");
        assertEquals(task.getStartTime(), list.get(0).getStartTime(), "loadFromFileTest - не пройден");
        assertEquals(task.getEndTime(), list.get(0).getEndTime(), "loadFromFileTest - не пройден");

    }

    @Disabled
    @Test
    void ManagerSaveExceptionTest() {
        final ManagerSaveException ex = assertThrows(
                ManagerSaveException.class,
                () -> {
                    FileBackedTasksManager f = new FileBackedTasksManager(new File("data\\csv.csv"));
                    f.save();
                });
        assertEquals("", ex.getMessage());
    }
}
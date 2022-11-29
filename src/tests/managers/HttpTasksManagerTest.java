package managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import server.KVServer;

import java.io.IOException;

class HttpTasksManagerTest extends TaskManagerTest<HttpTasksManager> {

    private KVServer kvServer;

    @BeforeEach
    void setUp() throws IOException {

        kvServer = Managers.getDefaultKVServer();
        kvServer.start();
        taskManager = Managers.getDefaultHttpTaskManager();
        taskManagerSetUp();
    }

    @AfterEach
    void tearDown() {
        kvServer.stop();
    }

    @Test
    void load() {
        taskManager.getTasksById(task.getId());
        taskManager.save();
        taskManager.load();
    }

    @Disabled
    @Test
    void save() {
        //проходит в тесте void load()
    }
}

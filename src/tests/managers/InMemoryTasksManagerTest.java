package managers;

import org.junit.jupiter.api.BeforeEach;


class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTasksManager> {
    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTasksManager(Managers.getDefaultHistory());
    }
}
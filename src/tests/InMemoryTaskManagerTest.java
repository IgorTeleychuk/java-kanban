
import main.service.InMemoryTaskManager;
import main.status.Status;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest extends TaskManagersTest<InMemoryTaskManager> {

    @BeforeEach
    public void init() { manager = new InMemoryTaskManager(); }
}

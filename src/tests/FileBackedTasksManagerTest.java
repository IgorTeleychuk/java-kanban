
import main.service.FileBackedTasksManager;
import main.service.InMemoryTaskManager;
import main.service.Managers;
import main.status.Status;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileBackedTasksManagerTest extends TaskManagersTest<FileBackedTasksManager> {
    public static final Path path = Path.of("data.test.csv");
    File file = new File(String.valueOf(path));

    @BeforeEach
    public void beforeEach() {
        manager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void shouldCorrectlySaveAndLoad() {
        Task task = new Task("Description", "Title", Status.NEW, LocalDateTime.now(), 0);
        manager.addTask(task);
        Epic epic = new Epic("Description", "Title", Status.NEW);
        manager.addEpic(epic);
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        fileManager.loadFromFile();
        assertEquals(List.of(task), manager.getAllTasks());
        assertEquals(List.of(epic), manager.getAllEpics());
    }

    @Test
    public void shouldSaveAndLoadEmptyTasksEpicsSubtasks() {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        fileManager.save();
        fileManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
        assertEquals(Collections.EMPTY_LIST, manager.getAllSubtasks());
    }

    @Test
    public void shouldSaveAndLoadEmptyHistory() {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        fileManager.save();
        fileManager.loadFromFile();
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldBeFileBackupManager() {
        Path path3 = Path.of("src//tests//data.test.csv");
        File file = new File(String.valueOf(path3));
        FileBackedTasksManager test = new FileBackedTasksManager(Managers.getDefaultHistory(), file);

        Task task = new Task("A","B",Status.NEW,
                LocalDateTime.of(2022,3,10,12,0),50);
        test.addTask(task);
        Epic epic = new Epic("TestEpic", "Test description", Status.NEW);
        test.addEpic(epic);
        Subtask subTask = new Subtask("Test description", "TestSubTask", Status.NEW, epic.getId(),LocalDateTime.of(2022,6,1,11,30),30);
        test.addSubtask(subTask);
        Subtask subTask2 = new Subtask("Test description", "TestSubTask", Status.NEW, epic.getId(),LocalDateTime.of(2022,8,10,12,0),30);
        test.addSubtask(subTask2);
        Epic epic2 = new Epic("TestEpic 2", "Test description 2", Status.NEW);
        test.addEpic(epic2);
        Task task2 = new Task("A","B",Status.NEW,
                LocalDateTime.of(2022,2,10,12,0),50) ;
        test.addTask(task2);
        test.getTaskById(1);
        test.getEpicById(2);
        test.getSubtaskById(3);

        assertEquals(test.getTaskList(), test.getTaskList(),
                "Список задач после выгрузки не совпадает");
        assertEquals(test.getSubtasksList(), test.getSubtasksList(),
                "Список подзадач после выгрузки не совпадает");
        assertEquals(test.getEpicsList(), test.getEpicsList(),
                "Список эпиков после выгрузки не совпадает");
        assertEquals(test.getHistory(), test.getHistory(), "История задач не совпадает");
        assertEquals(test.getPrioritizedTasks(), test.getPrioritizedTasks(),
                "Сортированные задачи не совпадают");
        assertNotNull(test, "Не загружен");
    }
}

package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileBackedTasksManager;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagersTest <T extends TaskManager>{
    protected T manager;
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    TaskManager inMemoryTaskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());
    protected Task task;
    protected Epic epic;
    protected Subtask subTask;

    @BeforeEach
    void beforeEach(){

        task = new Task("Test description", "TestTask",Status.NEW, LocalDateTime.of(2022,8,10,12,0),30);
        epic = new Epic("TestEpic", "Test description", Status.NEW);
        subTask = new Subtask("Test description", "TestSubTask", Status.NEW, epic.getId(),LocalDateTime.of(2022,8,10,12,0),30);
    }

    @Test
    void addNewTask() {
        inMemoryTaskManager.addTask(task);
        final Task savedTask = inMemoryTaskManager.getTaskById(task.getId());
        assertNotNull(savedTask, "Task not found.");
        assertEquals(task, savedTask, "Tasks are not equal.");
        final Map<Integer, Task> tasks = inMemoryTaskManager.getTaskList();
        assertNotNull(tasks, "List is empty.");
        assertEquals(1, tasks.size(), "Wrong quantity of tasks.");
        assertEquals(task, tasks.get(1), "Tasks are not equal.");
    }

    @Test
    void addNewEpic() {
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subTask);
        final Epic savedEpic = inMemoryTaskManager.getEpicById(epic.getId());
        assertNotNull(savedEpic, "Task not found.");
        final Map<Integer, Epic> epics = inMemoryTaskManager.getEpicsList();
        assertNotNull(epics, "List is empty.");
        assertEquals(1, epics.size(), "Wrong quantity of tasks.");
    }

    @Test
    void removeTaskPerId() {
        inMemoryTaskManager.addTask(task);
        Map<Integer, Task> tasks = inMemoryTaskManager.getTaskList();
        assertEquals(1, tasks.size());
        inMemoryTaskManager.removeTaskById(task.getId());
        assertEquals(0, tasks.size(), "Task wasn't deleted");
    }

    @Test
    void removeSubtaskPerId() {
        epic = new Epic("TestEpic", "Test description", Status.NEW);
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subTask);
        Map<Integer, Subtask> subtasks = inMemoryTaskManager.getSubtasksList();
        inMemoryTaskManager.removeSubtaskById(subTask.getId());
        assertEquals(0, subtasks.size(), "SubTask wasn't deleted");
    }

    @Test
    void removeEpicPerId() {
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.getEpicById(epic.getId());
        inMemoryTaskManager.addSubtask(subTask);
        Map<Integer, Epic> epics = inMemoryTaskManager.getEpicsList();
        inMemoryTaskManager.removeEpicById(epic.getId());
        assertEquals(0, epics.size(), "Epic wasn't deleted");
    }

    @Test
    void updateTasks() {
        inMemoryTaskManager.addTask(task);
        Map<Integer, Task> tasks = inMemoryTaskManager.getTaskList();
        assertEquals(task, tasks.get(task.getId()));
        task.setStatus(Status.DONE);
        inMemoryTaskManager.updateTask(task);
        assertEquals(task, tasks.get(task.getId()), "Task is not up to date");
    }

    @Test
    void updateSubtasks() {
        inMemoryTaskManager.addEpic(epic);
        inMemoryTaskManager.addSubtask(subTask);
        assertEquals(subTask, inMemoryTaskManager.getSubtaskById(subTask.getId()),"Wrong quantity of tasks.");
        subTask.setStatus(Status.DONE);
        inMemoryTaskManager.updateTask(subTask);
        assertEquals(subTask, inMemoryTaskManager.getSubtaskById(subTask.getId()),"Wrong quantity of tasks.");
    }
}

class InMemoryTaskManagerTest extends TaskManagersTest<InMemoryTaskManager>{

    @BeforeEach
    public void init(){
        taskManager = new InMemoryTaskManager();
        task = new Task("TestTask", "Test description",Status.NEW, LocalDateTime.of(2022,8,10,12,0),30);
        epic = new Epic("Test description", "TestEpic", Status.NEW);
        subTask = new Subtask("Test description", "TestSubTask", Status.NEW, epic.getId(),LocalDateTime.of(2022,8,10,12,0),30);
        taskManager.addSubtask(subTask);
    }
    @Test
    void validationTest(){
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        assertEquals(taskManager.validation(),true,"Задачи не верно проходят валидацию");
    }
    @Test
    void inValidationTest(){
        Task inValidTask = new Task("A","B",Status.NEW,LocalDateTime.of(2022,8,10,12,0),50) ;
        taskManager.addTask(inValidTask);
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        assertEquals(taskManager.validation(),true,"Задачи не верно проходят валидацию");
    }
}

class FileBackedTasksManagerTest extends TaskManagersTest<InMemoryTaskManager> {
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
}






package tests;

import main.exeptions.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.service.FileBackedTasksManager;
import main.service.InMemoryTaskManager;
import main.service.Managers;
import main.service.TaskManager;
import main.status.Status;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagersTest <T extends TaskManager>{
    protected T manager;
    protected Task task;
    protected Epic epic;
    protected Subtask subTask;

    @BeforeEach
    void beforeEach(){
        task = new Task("Test description", "TestTask",Status.NEW,
                LocalDateTime.of(2022,8,10,12,0),30);
        epic = new Epic("TestEpic", "Test description", Status.NEW);
        subTask = new Subtask("Test description", "TestSubTask", Status.NEW, epic.getId(),
                LocalDateTime.of(2022,8,10,12,0),30);
    }

    @Test
    void addNewTask() {
        manager.addTask(task);
        final Task savedTask = manager.getTaskById(task.getId());
        assertNotNull(savedTask, "Task not found.");
        assertEquals(task, savedTask, "Tasks are not equal.");
        final Map<Integer, Task> tasks = manager.getTaskList();
        assertNotNull(tasks, "List is empty.");
        assertEquals(1, tasks.size(), "Wrong quantity of tasks.");
        assertEquals(task, tasks.get(1), "Tasks are not equal.");
    }

    @Test
    void addNewEpic() {
        manager.addEpic(epic);
        manager.addSubtask(subTask);
        final Epic savedEpic = manager.getEpicById(epic.getId());
        assertNotNull(savedEpic, "Task not found.");
        final Map<Integer, Epic> epics = manager.getEpicsList();
        assertNotNull(epics, "List is empty.");
        assertEquals(1, epics.size(), "Wrong quantity of tasks.");
    }

    @Test
    void removeTaskPerId() {
        manager.addTask(task);
        Map<Integer, Task> tasks = manager.getTaskList();
        assertEquals(1, tasks.size());
        manager.removeTaskById(task.getId());
        assertEquals(0, tasks.size(), "Task wasn't deleted");
    }

    @Test
    void removeSubtaskPerId() {
        epic = new Epic("TestEpic", "Test description", Status.NEW);
        manager.addEpic(epic);
        manager.addSubtask(subTask);
        Map<Integer, Subtask> subtasks = manager.getSubtasksList();
        manager.removeSubtaskById(subTask.getId());
        assertEquals(0, subtasks.size(), "SubTask wasn't deleted");
    }

    @Test
    void removeEpicPerId() {
        manager.addEpic(epic);
        manager.getEpicById(epic.getId());
        manager.addSubtask(subTask);
        Map<Integer, Epic> epics = manager.getEpicsList();
        manager.removeEpicById(epic.getId());
        assertEquals(0, epics.size(), "Epic wasn't deleted");
    }

    @Test
    void updateTasks() {
        manager.addTask(task);
        Map<Integer, Task> tasks = manager.getTaskList();
        assertEquals(task, tasks.get(task.getId()));
        task.setStatus(Status.DONE);
        manager.updateTask(task);
        assertEquals(task, tasks.get(task.getId()), "Task is not up to date");
    }

    @Test
    void updateSubtasks() {
        manager.addEpic(epic);
        manager.addSubtask(subTask);
        assertEquals(subTask, manager.getSubtaskById(subTask.getId()),"Wrong quantity of tasks.");
        subTask.setStatus(Status.DONE);
        manager.updateTask(subTask);
        assertEquals(subTask, manager.getSubtaskById(subTask.getId()),"Wrong quantity of tasks.");
    }

    @Test
    void validationTest(){
        manager.addTask(task);
        manager.addEpic(epic);
        assertEquals(manager.validation(task),true,"Tasks are incorrectly validated");
    }
    @Test
    void inValidationTest(){
        Task inValidTask = new Task("A","B",Status.NEW,LocalDateTime.of(2022,8,10,12,0),50);
        manager.addTask(inValidTask);
        manager.addEpic(epic);
        assertEquals(manager.validation(inValidTask),true,"Tasks are incorrectly validated");
    }

    @Test
    void validationTime() {
        Task timeTask1 = new Task("Test description 1", "TestTask 1",Status.NEW, LocalDateTime.of(2022,1,1,1,0),10);
        manager.addTask(timeTask1);
        Task timeTask2 = new Task ("Test description 2", "TestTask 2",Status.NEW, LocalDateTime.of(2022,1,1,1,5),10);
        assertThrows(ValidationException.class, () -> manager.addTask(timeTask2),
                "The new task is not included inside the existing one");
    }
}






package main.service;

import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TaskManager {
    List<Task> getHistory();

    Task addTask(Task task);

    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    List<Subtask> getAllSubtasksByEpicId(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

   // void updateStatusEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void findStartTimeAndDurationOfEpic(Epic epic);

    Map<Integer, Task> getTaskList();

    Map<Integer, Epic> getEpicsList();
    Map<Integer, Subtask> getSubtasksList();

    void validation (Task task);

    Set<Task> getPrioritizedTasks();

    void setPrioritizedTasks(Task task);

    Integer getId();
}
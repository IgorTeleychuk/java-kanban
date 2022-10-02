package service;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    List<Task> getHistory();

    int addTask(Task task);

    int addEpic(Epic epic);

    int addSubtask(Subtask subtask);

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

}
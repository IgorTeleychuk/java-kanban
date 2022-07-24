package service;

import tasks.Task;
import tasks.Epic;
import tasks.SubTask;


import java.util.*;

public interface TaskManager {

    void addTask(Task task);

    void updateTask(Task task);

    Collection<Task> getTasks();

    void addEpic(Epic epic);

    void updateEpic (Epic epic);

    Collection<Epic> getEpics();

    Collection<SubTask> getAllSubtasks();

    void addSubTask(SubTask subTask);

    void updateSubtask (SubTask subTask);

    ArrayList<SubTask> getSubTask(int epicId);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks ();

    void removeTaskById (int taskId);

    void removeEpicById (int epicId);

    void removeSubtaskById (int epicId);

    Task getTaskByID (int taskId);

    Epic getEpicByID (int epicId);

    SubTask getSubtaskByID (int subtaskId);

    List<Task>getHistory();

}
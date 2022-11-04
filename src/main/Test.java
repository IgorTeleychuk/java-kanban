package main;

import main.service.FileBackedTasksManager;
import main.service.Managers;
import main.service.TaskManager;
import main.tasks.Task;
import main.status.Status;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.service.*;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Test {

    public void testing1() {
        TaskManager inMemoryTaskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());
        System.out.println("������� ��� ������, ���� � ����� ����������� � ���� ��� ��������.");
        inMemoryTaskManager.addTask(new Task("Cleaning", "Task_1", Status.NEW, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 12)), 40)); // id 1
        inMemoryTaskManager.addTask(new Task("Training", "Task_2", Status.NEW, LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 12)), 40)); // id 2

        inMemoryTaskManager.addEpic(new Epic("Work", "Epic_1", Status.NEW)); // id 3
        inMemoryTaskManager.addSubtask(new Subtask("Meeting", "Subtask_1", Status.NEW, 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 12)), 40)); // id 4
        inMemoryTaskManager.addSubtask(new Subtask("Project", "Subtask_2", Status.NEW, 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 12)), 40)); // id 5
        inMemoryTaskManager.addSubtask(new Subtask("Report", "Subtask_3", Status.NEW, 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(3, 12)), 40)); // id 6

        inMemoryTaskManager.addEpic(new Epic("Sport", "Epic_2", Status.NEW)); // id 7

        System.out.println("����������� ��������� ������ ��������� ��� � ������ �������.");

        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getSubtaskById(6);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getSubtaskById(5);
        inMemoryTaskManager.getSubtaskById(4);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getSubtaskById(6);
        inMemoryTaskManager.getEpicById(7);
        inMemoryTaskManager.getEpicById(7);

        System.out.println("������� ������� � ����������, ��� � ��� ��� ��������.");
        List<Task> history = inMemoryTaskManager.getHistory();
        System.out.println(history);

        System.out.println("������� ������, ������� ���� � �������, � ���������, ��� ��� ������ ��� �� ����� ����������.");
        inMemoryTaskManager.removeTaskById(1);
        history = inMemoryTaskManager.getHistory();
        System.out.println(history);

        System.out.println("������� ���� � ����� ����������� � �����������, ��� �� ������� �������� ��� ��� ����, ��� � ��� ��� ���������.");
        inMemoryTaskManager.removeEpicById(3);
        history = inMemoryTaskManager.getHistory();
        System.out.println(history);


    }

    public void testing2() {
        Path path = Path.of("src//main//data.csv");
        File file = new File(String.valueOf(path));
        FileBackedTasksManager manager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        Task firstTask = new Task("Write a song", "Task_3", Status.NEW, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10)), 40);
        manager.addTask(firstTask);
        Task secondTask = new Task("Purchases", "Task_4", Status.NEW, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 10)), 40);
        manager.addTask(secondTask);

        Epic firstEpic = new Epic("Fix the table", "Epic_3", Status.NEW);
        manager.addEpic(firstEpic);

        Subtask firstSubtask = new Subtask("Buy nails", "Subtask_4", Status.NEW, firstEpic.getId(), LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 10)), 40);
        manager.addSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask("Buy nails Two", "Subtask_5", Status.NEW, firstEpic.getId(), LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 10)), 40);
        manager.addSubtask(secondSubtask);
        Subtask thirdSubtask = new Subtask("Buy nails Three", "Subtask_6", Status.NEW, firstEpic.getId(), LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 10)), 40);
        manager.addSubtask(thirdSubtask);

        manager.getTaskById(firstTask.getId());
        manager.getTaskById(secondTask.getId());
        System.out.println();
    }

    public void testing3() {
        System.out.println("���������� �� �����");
        Path path2 = Path.of("src//main//data.csv");
        File file2 = new File(String.valueOf(path2));
        FileBackedTasksManager manager2 = new FileBackedTasksManager(Managers.getDefaultHistory(), file2);
        manager2.loadFromFile();
        System.out.println("������");
        System.out.println(manager2.getAllTasks());
        System.out.println("�����");
        System.out.println(manager2.getAllEpics());
        System.out.println("���������");
        System.out.println(manager2.getAllSubtasks());
        System.out.println("�������");
        System.out.println(manager2.getHistory());
    }
}

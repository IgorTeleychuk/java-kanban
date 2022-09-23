import tasks.Task;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import service.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class Test {

    public void testing1() {
        TaskManager inMemoryTaskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());
        System.out.println("Создаем две задачи, эпик с тремя подзадачами и эпик без подзадач.");
        inMemoryTaskManager.addTask(new Task("Cleaning", "Task_1", Status.NEW)); // id 1
        inMemoryTaskManager.addTask(new Task("Training", "Task_2", Status.NEW)); // id 2

        inMemoryTaskManager.addEpic(new Epic("Work", "Epic_1", Status.NEW)); // id 3
        inMemoryTaskManager.addSubtask(new Subtask("Meeting", "Subtask_1", Status.NEW, 3)); // id 4
        inMemoryTaskManager.addSubtask(new Subtask("Project", "Subtask_2", Status.NEW, 3)); // id 5
        inMemoryTaskManager.addSubtask(new Subtask("Report", "Subtask_3", Status.NEW, 3)); // id 6

        inMemoryTaskManager.addEpic(new Epic("Sport", "Epic_2", Status.NEW)); // id 7

        System.out.println("Запрашиваем созданные задачи несколько раз в разном порядке.");

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

        System.out.println("Выводим историю и убеждаемся, что в ней нет повторов.");
        List<Task> history = inMemoryTaskManager.getHistory();
        System.out.println(history);

        System.out.println("Удаляем задачу, которая есть в истории, и проверяем, что при печати она не будет выводиться.");
        inMemoryTaskManager.removeTaskById(1);
        history = inMemoryTaskManager.getHistory();
        System.out.println(history);

        System.out.println("Удаляем эпик с тремя подзадачами и убедждаемся, что из истории удалился как сам эпик, так и все его подзадачи.");
        inMemoryTaskManager.removeEpicById(3);
        history = inMemoryTaskManager.getHistory();
        System.out.println(history);


    }

    public void testing2() {
        Path path = Path.of("src//data.csv");
        File file = new File(String.valueOf(path));
        FileBackedTasksManager manager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        Task firstTask = new Task("Write a song", "Task_3", Status.NEW);
        manager.addTask(firstTask);
        Task secondTask = new Task("Purchases", "Task_4", Status.NEW);
        manager.addTask(secondTask);

        Epic firstEpic = new Epic("Fix the table", "Epic_3", Status.NEW);
        manager.addEpic(firstEpic);

        Subtask firstSubtask = new Subtask("Buy nails", "Subtask_4", Status.NEW, firstEpic.getId());
        manager.addSubtask(firstSubtask);

        manager.getTaskById(firstTask.getId());
        manager.getTaskById(secondTask.getId());
        System.out.println();
    }

    public void testing3() {
        System.out.println("Считывание из файла");
        Path path2 = Path.of("src//data.csv");
        File file2 = new File(String.valueOf(path2));
        FileBackedTasksManager manager2 = new FileBackedTasksManager(Managers.getDefaultHistory(), file2);
        manager2.loadFromFile();
        System.out.println("Задачи");
        System.out.println(manager2.getAllTasks());
        System.out.println("Эпики");
        System.out.println(manager2.getAllEpics());
        System.out.println("Подзадачи");
        System.out.println(manager2.getAllSubtasks());
        System.out.println("История");
        System.out.println(manager2.getHistory());
    }
}

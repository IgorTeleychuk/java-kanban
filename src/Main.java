import service.Manager;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Задача 1", "Купить продукты");
        Task task2 = new Task("Задача 2", "Постирать вещи");
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic3 = new Epic("Эпик 1", "Важная встреча");
        Epic epic4 = new Epic("Эпик 2", "Видеоконференция");
        manager.addEpic(epic3);
        manager.addEpic(epic4);

        SubTask subtask5 = new SubTask("Подзадача 1", "Подготовиться", 3);
        SubTask subtask6 = new SubTask("Подзадача 2", "Взять документы", 3);
        SubTask subtask7 = new SubTask("Подзадача 3", "Настроить камеру", 4);
        SubTask subtask8 = new SubTask("Подзадача 4", "Настроить софт", 4);
        manager.addSubtask(subtask5);
        manager.addSubtask(subtask6);
        manager.addSubtask(subtask7);
        manager.addSubtask(subtask8);

        Task updateTask3 = new Task("Задача Один", "Купить продукты", task1.getId(), "IN_PROGRESS");
        manager.updateTask(updateTask3);

        Epic updateEpic3 = new Epic("Эпичная задача", "Важная встреча", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
        manager.updateEpic(updateEpic3);

        SubTask updateSubtask5 = new SubTask("Подзадача",
                "123", subtask5.getId(),"IN_PROGRESS", 3);
        manager.updateSubtask(updateSubtask5);
        SubTask updateSubtask7 = new SubTask("Подзадача",
                "123", subtask7.getId(),"DONE", 4);
        manager.updateSubtask(updateSubtask7);

        manager.removeTaskById(task1.getId());
        manager.removeEpicById(epic4.getId());
        manager.removeSubtaskById(subtask6.getId());

        manager.removeAllTasks();
        manager.removeAllSubtasks();
        manager.removeAllEpics();
    }
}
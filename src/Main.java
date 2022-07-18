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

        Subtask subtask5 = new Subtask("Подзадача 1", "Подготовиться", 3);
        Subtask subtask6 = new Subtask("Подзадача 2", "Взять документы", 3);
        Subtask subtask7 = new Subtask("Подзадача 3", "Настроить камеру", 4);
        Subtask subtask8 = new Subtask("Подзадача 4", "Настроить софт", 4);
        manager.addSubtask(subtask5);
        manager.addSubtask(subtask6);
        manager.addSubtask(subtask7);
        manager.addSubtask(subtask8);

        Task updateTask3 = new Task("Задача Один", "Купить продукты", task1.getId(), "IN_PROGRESS");
        manager.updateTask(updateTask3);

        Epic updateEpic3 = new Epic("Эпичная задача", "Важная встреча", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
        manager.updateEpic(updateEpic3);

        Subtask updateSubtask5 = new Subtask("Подзадача",
                "123", subtask5.getId(),"IN_PROGRESS", 3);
        manager.updateSubtask(updateSubtask5);
        Subtask updateSubtask7 = new Subtask("Подзадача",
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
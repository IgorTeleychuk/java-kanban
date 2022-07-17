public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Задача 1", "Купить продукты");
        Task task2 = new Task("Задача 2", "Постирать вещи");
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic3 = new Epic("Эпик 1", "Важная встреча");
        Epic epic4 = new Epic("Эпик 2", "Видеоконференция");
        manager.createEpic(epic3);
        manager.createEpic(epic4);

        Subtask subtask5 = new Subtask("Подзадача 1", "Подготовиться", 3);
        Subtask subtask6 = new Subtask("Подзадача 2", "Взять документы", 3);
        Subtask subtask7 = new Subtask("Подзадача 3", "Настроить камеру", 4);
        Subtask subtask8 = new Subtask("Подзадача 4", "Настроить софт", 4);
        manager.createSubtask(subtask5);
        manager.createSubtask(subtask6);
        manager.createSubtask(subtask7);
        manager.createSubtask(subtask8);

        System.out.printf("Получение по ID: \n %s \n %s \n %s \n %s \n", manager.getTaskId(task1.getId()), manager.getEpicId(epic4.getId()),
                manager.getSubtaskId(subtask7.getId()), manager.getListOfSubtasksOneEpic(epic3.getId()));

        System.out.println("Добавили задачи, эпики, подзадачи:");
        System.out.println(manager.print());

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

        System.out.println("Обновили название и статус Задача 1, название Эпика 1, статус подзадач:");
        System.out.println(manager.print());

        manager.removeTaskById(task1.getId());
        manager.removeEpicById(epic4.getId());
        manager.removeSubtaskById(subtask6.getId());

        manager.removeAllTasks();
        manager.removeAllSubtasks();
        manager.removeAllEpics();

        System.out.println("Обнулили списки:");
        System.out.println(manager.print());
    }
}
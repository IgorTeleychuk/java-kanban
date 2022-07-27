import tasks.Task;
import tasks.Status;
import tasks.Epic;
import tasks.SubTask;
import service.*;

public class Test {
    public void testing() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
//        Добавляем 13 тасков 6 эпиков и 3 сабтаска
        inMemoryTaskManager.addTask(new Task("Таск 1", Status.NEW, "Убраться"));
        inMemoryTaskManager.addTask(new Task("Таск 2", Status.NEW, "Приготовить завтрак"));
        inMemoryTaskManager.addTask(new Task("Таск 3", Status.NEW, "Оплатить интернет"));
        inMemoryTaskManager.addTask(new Task("Таск 4", Status.NEW, "Поставить бассейн"));
        inMemoryTaskManager.addTask(new Task("Таск 5", Status.NEW, "Полить сад"));
        inMemoryTaskManager.addTask(new Task("Таск 6", Status.NEW, "Сменить мобильный тариф"));
        inMemoryTaskManager.addTask(new Task("Таск 7", Status.NEW, "Посмотреть вебинар"));
        inMemoryTaskManager.addTask(new Task("Таск 8", Status.NEW, "Записать расходы"));
        inMemoryTaskManager.addTask(new Task("Таск 9", Status.NEW, "Позвонить родителям"));
        inMemoryTaskManager.addTask(new Task("Таск 10", Status.NEW, "Оформить страховку"));
        inMemoryTaskManager.addTask(new Task("Таск 11", Status.NEW, "Выехать на пикник"));
        inMemoryTaskManager.addTask(new Task("Таск 12", Status.NEW, "Дочитать книгу"));
        inMemoryTaskManager.addTask(new Task("Таск 13", Status.NEW, "Посмотреть сериал"));
        inMemoryTaskManager.addEpic(new Epic("Эпик 1", Status.NEW, "Путешествие"));
        inMemoryTaskManager.addEpic(new Epic("Эпик 2", Status.NEW, "Учеба"));
        inMemoryTaskManager.addEpic(new Epic("Эпик 3", Status.NEW, "Работа"));
        inMemoryTaskManager.addEpic(new Epic("Эпик 4", Status.NEW, "Планы"));
        inMemoryTaskManager.addEpic(new Epic("Эпик 5", Status.NEW, "Расходы"));
        inMemoryTaskManager.addEpic(new Epic("Эпик 6", Status.NEW, "Спорт"));
        inMemoryTaskManager.addSubTask(new SubTask("Сабтаск 1", Status.NEW, "Составить план обучения", 15));
        inMemoryTaskManager.addSubTask(new SubTask("Сабтаск 2", Status.NEW, "Выполнить домашнюю работу", 15));
        inMemoryTaskManager.addSubTask(new SubTask("Сабтаск 3", Status.NEW, "Созвониться с руководителем", 16));

        System.out.println("Инициализируем тесты по истории просмторов:");
        System.out.println("Тест 1: Выборочный вызов. taskid - 4, 9; subtaskid - 20, 21, 22; epicid - 15;");
        inMemoryTaskManager.getTaskByID(4);
        inMemoryTaskManager.getSubtaskByID(20);
        inMemoryTaskManager.getSubtaskByID(21);
        inMemoryTaskManager.getTaskByID(9);
        inMemoryTaskManager.getSubtaskByID(22);
        inMemoryTaskManager.getEpicByID(15);
        System.out.println("История просмотра \n" + inMemoryTaskManager.getHistory());

        System.out.println("Доводим до 10 просмотров");
        inMemoryTaskManager.getTaskByID(1);
        inMemoryTaskManager.getTaskByID(2);
        inMemoryTaskManager.getTaskByID(3);
        inMemoryTaskManager.getTaskByID(5);
        System.out.println("История просмотра \n" + inMemoryTaskManager.getHistory());

        System.out.println("Добавляем 11 просмотр");
        inMemoryTaskManager.getTaskByID(6);
        System.out.println("История просмотра \n" + inMemoryTaskManager.getHistory());
        System.out.println("Самый старый taskid - 4 удален, всего лимит просмотра 10 задач");

        System.out.println("Инициализируем тесты функциональности:");
        System.out.println("Смена статуса задачи:");
        System.out.println(inMemoryTaskManager.getTaskByID(3));
        inMemoryTaskManager.updateTask(new Task("Таск 3", Status.DONE, "Оплатить интернет"));
        System.out.println(inMemoryTaskManager.getTaskByID(3));
        System.out.println("Смена описания подзадачи:");
        System.out.println(inMemoryTaskManager.getSubtaskByID(20));
        inMemoryTaskManager.updateSubtask(new SubTask("Сабтаск 1", Status.NEW,
                "Расписать задачи по дням", 15));
        System.out.println(inMemoryTaskManager.getSubtaskByID(20));
        System.out.println("Удаление задачи:");
        System.out.println(inMemoryTaskManager.getTaskByID(13));
        inMemoryTaskManager.removeTaskById(13);
        System.out.println(inMemoryTaskManager.getTaskByID(13));
        System.out.println("Удаление всех добавленных задач:");
        inMemoryTaskManager.removeAllTasks();
        inMemoryTaskManager.removeAllEpics();
        inMemoryTaskManager.removeAllSubtasks();
        System.out.printf(" %s \n %s \n %s", inMemoryTaskManager.getTasks(), inMemoryTaskManager.getEpics(), inMemoryTaskManager.getAllSubtasks());
    }
}

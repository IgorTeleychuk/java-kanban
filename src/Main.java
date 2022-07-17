public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("������ 1", "������ ��������");
        Task task2 = new Task("������ 2", "��������� ����");
        manager.createTask(task1);
        manager.createTask(task2);

        Epic epic3 = new Epic("���� 1", "������ �������");
        Epic epic4 = new Epic("���� 2", "����������������");
        manager.createEpic(epic3);
        manager.createEpic(epic4);

        Subtask subtask5 = new Subtask("��������� 1", "�������������", 3);
        Subtask subtask6 = new Subtask("��������� 2", "����� ���������", 3);
        Subtask subtask7 = new Subtask("��������� 3", "��������� ������", 4);
        Subtask subtask8 = new Subtask("��������� 4", "��������� ����", 4);
        manager.createSubtask(subtask5);
        manager.createSubtask(subtask6);
        manager.createSubtask(subtask7);
        manager.createSubtask(subtask8);

        System.out.printf("��������� �� ID: \n %s \n %s \n %s \n %s \n", manager.getTaskId(task1.getId()), manager.getEpicId(epic4.getId()),
                manager.getSubtaskId(subtask7.getId()), manager.getListOfSubtasksOneEpic(epic3.getId()));

        System.out.println("�������� ������, �����, ���������:");
        System.out.println(manager.print());

        Task updateTask3 = new Task("������ ����", "������ ��������", task1.getId(), "IN_PROGRESS");
        manager.updateTask(updateTask3);

        Epic updateEpic3 = new Epic("������� ������", "������ �������", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
        manager.updateEpic(updateEpic3);

        Subtask updateSubtask5 = new Subtask("���������",
                "123", subtask5.getId(),"IN_PROGRESS", 3);
        manager.updateSubtask(updateSubtask5);
        Subtask updateSubtask7 = new Subtask("���������",
                "123", subtask7.getId(),"DONE", 4);
        manager.updateSubtask(updateSubtask7);

        System.out.println("�������� �������� � ������ ������ 1, �������� ����� 1, ������ ��������:");
        System.out.println(manager.print());

        manager.removeTaskById(task1.getId());
        manager.removeEpicById(epic4.getId());
        manager.removeSubtaskById(subtask6.getId());

        manager.removeAllTasks();
        manager.removeAllSubtasks();
        manager.removeAllEpics();

        System.out.println("�������� ������:");
        System.out.println(manager.print());
    }
}
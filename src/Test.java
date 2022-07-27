import tasks.Task;
import tasks.Status;
import tasks.Epic;
import tasks.SubTask;
import service.*;

public class Test {
    public void testing() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
//        ��������� 13 ������ 6 ������ � 3 ��������
        inMemoryTaskManager.addTask(new Task("���� 1", Status.NEW, "��������"));
        inMemoryTaskManager.addTask(new Task("���� 2", Status.NEW, "����������� �������"));
        inMemoryTaskManager.addTask(new Task("���� 3", Status.NEW, "�������� ��������"));
        inMemoryTaskManager.addTask(new Task("���� 4", Status.NEW, "��������� �������"));
        inMemoryTaskManager.addTask(new Task("���� 5", Status.NEW, "������ ���"));
        inMemoryTaskManager.addTask(new Task("���� 6", Status.NEW, "������� ��������� �����"));
        inMemoryTaskManager.addTask(new Task("���� 7", Status.NEW, "���������� �������"));
        inMemoryTaskManager.addTask(new Task("���� 8", Status.NEW, "�������� �������"));
        inMemoryTaskManager.addTask(new Task("���� 9", Status.NEW, "��������� ���������"));
        inMemoryTaskManager.addTask(new Task("���� 10", Status.NEW, "�������� ���������"));
        inMemoryTaskManager.addTask(new Task("���� 11", Status.NEW, "������� �� ������"));
        inMemoryTaskManager.addTask(new Task("���� 12", Status.NEW, "�������� �����"));
        inMemoryTaskManager.addTask(new Task("���� 13", Status.NEW, "���������� ������"));
        inMemoryTaskManager.addEpic(new Epic("���� 1", Status.NEW, "�����������"));
        inMemoryTaskManager.addEpic(new Epic("���� 2", Status.NEW, "�����"));
        inMemoryTaskManager.addEpic(new Epic("���� 3", Status.NEW, "������"));
        inMemoryTaskManager.addEpic(new Epic("���� 4", Status.NEW, "�����"));
        inMemoryTaskManager.addEpic(new Epic("���� 5", Status.NEW, "�������"));
        inMemoryTaskManager.addEpic(new Epic("���� 6", Status.NEW, "�����"));
        inMemoryTaskManager.addSubTask(new SubTask("������� 1", Status.NEW, "��������� ���� ��������", 15));
        inMemoryTaskManager.addSubTask(new SubTask("������� 2", Status.NEW, "��������� �������� ������", 15));
        inMemoryTaskManager.addSubTask(new SubTask("������� 3", Status.NEW, "����������� � �������������", 16));

        System.out.println("�������������� ����� �� ������� ����������:");
        System.out.println("���� 1: ���������� �����. taskid - 4, 9; subtaskid - 20, 21, 22; epicid - 15;");
        inMemoryTaskManager.getTaskByID(4);
        inMemoryTaskManager.getSubtaskByID(20);
        inMemoryTaskManager.getSubtaskByID(21);
        inMemoryTaskManager.getTaskByID(9);
        inMemoryTaskManager.getSubtaskByID(22);
        inMemoryTaskManager.getEpicByID(15);
        System.out.println("������� ��������� \n" + inMemoryTaskManager.getHistory());

        System.out.println("������� �� 10 ����������");
        inMemoryTaskManager.getTaskByID(1);
        inMemoryTaskManager.getTaskByID(2);
        inMemoryTaskManager.getTaskByID(3);
        inMemoryTaskManager.getTaskByID(5);
        System.out.println("������� ��������� \n" + inMemoryTaskManager.getHistory());

        System.out.println("��������� 11 ��������");
        inMemoryTaskManager.getTaskByID(6);
        System.out.println("������� ��������� \n" + inMemoryTaskManager.getHistory());
        System.out.println("����� ������ taskid - 4 ������, ����� ����� ��������� 10 �����");

        System.out.println("�������������� ����� ����������������:");
        System.out.println("����� ������� ������:");
        System.out.println(inMemoryTaskManager.getTaskByID(3));
        inMemoryTaskManager.updateTask(new Task("���� 3", Status.DONE, "�������� ��������"));
        System.out.println(inMemoryTaskManager.getTaskByID(3));
        System.out.println("����� �������� ���������:");
        System.out.println(inMemoryTaskManager.getSubtaskByID(20));
        inMemoryTaskManager.updateSubtask(new SubTask("������� 1", Status.NEW,
                "��������� ������ �� ����", 15));
        System.out.println(inMemoryTaskManager.getSubtaskByID(20));
        System.out.println("�������� ������:");
        System.out.println(inMemoryTaskManager.getTaskByID(13));
        inMemoryTaskManager.removeTaskById(13);
        System.out.println(inMemoryTaskManager.getTaskByID(13));
        System.out.println("�������� ���� ����������� �����:");
        inMemoryTaskManager.removeAllTasks();
        inMemoryTaskManager.removeAllEpics();
        inMemoryTaskManager.removeAllSubtasks();
        System.out.printf(" %s \n %s \n %s", inMemoryTaskManager.getTasks(), inMemoryTaskManager.getEpics(), inMemoryTaskManager.getAllSubtasks());
    }
}

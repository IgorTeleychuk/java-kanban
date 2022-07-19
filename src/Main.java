import service.Manager;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("������ 1", "������ ��������");
        Task task2 = new Task("������ 2", "��������� ����");
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic3 = new Epic("���� 1", "������ �������");
        Epic epic4 = new Epic("���� 2", "����������������");
        manager.addEpic(epic3);
        manager.addEpic(epic4);

        SubTask subtask5 = new SubTask("��������� 1", "�������������", 3);
        SubTask subtask6 = new SubTask("��������� 2", "����� ���������", 3);
        SubTask subtask7 = new SubTask("��������� 3", "��������� ������", 4);
        SubTask subtask8 = new SubTask("��������� 4", "��������� ����", 4);
        manager.addSubtask(subtask5);
        manager.addSubtask(subtask6);
        manager.addSubtask(subtask7);
        manager.addSubtask(subtask8);

        Task updateTask3 = new Task("������ ����", "������ ��������", task1.getId(), "IN_PROGRESS");
        manager.updateTask(updateTask3);

        Epic updateEpic3 = new Epic("������� ������", "������ �������", epic3.getId(), epic3.getStatus(), epic3.getSubtaskIds());
        manager.updateEpic(updateEpic3);

        SubTask updateSubtask5 = new SubTask("���������",
                "123", subtask5.getId(),"IN_PROGRESS", 3);
        manager.updateSubtask(updateSubtask5);
        SubTask updateSubtask7 = new SubTask("���������",
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
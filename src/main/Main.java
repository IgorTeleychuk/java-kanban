package main;

import main.adapters.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.http.KVServer;
import main.service.HistoryManager;
import main.service.Managers;
import main.service.TaskManager;
import main.status.Status;
import main.tasks.Epic;
import main.tasks.Subtask;
import main.tasks.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Test test = new Test();
        // ������ 5-7
        //test.testing1(); // ���� �������
        //test.testing2(); // ���� ������ � ����
        //test.testing3(); // ���� �������� �� �����

        // ������ 8
        KVServer server;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

            server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            TaskManager httpTaskManager = Managers.getDefault(historyManager);

            Task task1 = new Task("Write a song", "Task_3", Status.NEW,
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 10)), 40);
            httpTaskManager.addTask(task1);

            Epic epic1 = new Epic("Fix the table", "Epic_3", Status.NEW);
            httpTaskManager.addEpic(epic1);

            Subtask subtask1 = new Subtask("Buy nails", "Subtask_4", Status.NEW, epic1.getId(),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 10)), 40);
            httpTaskManager.addSubtask(subtask1);


            httpTaskManager.getTaskById(task1.getId());
            httpTaskManager.getEpicById(epic1.getId());
            httpTaskManager.getSubtaskById(subtask1.getId());

            System.out.println("������ ���� �����");
            System.out.println(gson.toJson(httpTaskManager.getAllTasks()));
            System.out.println("������ ���� ������");
            System.out.println(gson.toJson(httpTaskManager.getAllEpics()));
            System.out.println("������ ���� ��������");
            System.out.println(gson.toJson(httpTaskManager.getAllSubtasks()));
            System.out.println("����������� ��������");
            System.out.println(httpTaskManager);
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
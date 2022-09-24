package service;

import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import util.TaskType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    private static final String HEADER_CSV_FILE = "id,type,name,status,description,epic\n";

    public FileBackedTasksManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    static void main(String[] args) {
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


    public void loadFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

            String line = bufferedReader.readLine();
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (line.isEmpty()) {
                    break;
                }
                Task task = fromString(line);
                if(task.getId() > super.id) {super.id = task.getId();}

                switch (task.getType()){
                    case EPIC:
                        epics.put(task.getId(), (Epic) task);
                        break;
                    case SUBTASK:
                        Epic epic = null;
                        if (task instanceof Subtask) {
                            epic = epics.get(((Subtask) task).getEpicId());
                        }
                        if (epic != null) {
                            subtasks.put(task.getId(), (Subtask) task);
                            epic.addSubtaskIds(task.getId());
                            updateStatusEpic(epic);
                        } else {
                            System.out.println("Epic not found");
                        }
                        break;
                    default:
                        tasks.put(task.getId(), task);

                }
            }

            String lineWithHistory = bufferedReader.readLine();
            for (int id : historyFromString(lineWithHistory)) {
                addToHistory(id);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Could not read data from file");
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEADER_CSV_FILE);

            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }

            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }

            writer.write("\n");
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл", e);
        }
    }

    private String getParentEpicId(Task task) {
        if (task instanceof Subtask) {
            return Integer.toString(((Subtask) task).getEpicId());
        }
        return "";
    }

    private String toString(Task task) {
        String[] toJoin = {Integer.toString(task.getId()), task.getType().toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), getParentEpicId(task)};
        return String.join(",", toJoin);
    }

    private Task fromString(String value) {
        String[] params = value.split(",");

        switch (TaskType.valueOf(params[1])){
            case EPIC:
                Epic epic = new Epic(params[4], params[2], Status.valueOf(params[3]));
                epic.setId(Integer.parseInt(params[0]));
                epic.setStatus(Status.valueOf(params[3]));
                return epic;
            case SUBTASK:
                Subtask subtask = new Subtask(params[4], params[2], Status.valueOf(params[3]),
                        Integer.parseInt(params[5]));
                subtask.setId(Integer.parseInt(params[0]));
                return subtask;
            default:
                Task task = new Task(params[4], params[2], Status.valueOf(params[3]));
                task.setId(Integer.parseInt(params[0]));
                return task;
        }
    }

    @Override
    public int addTask(Task task) {
        int newTaskId = super.addTask(task);
        save();
        return newTaskId;
    }

    @Override
    public int addEpic(Epic epic) {
        int newEpicId = super.addEpic(epic);
        save();
        return newEpicId;
    }

    @Override
    public int addSubtask(Subtask subtask) {
        int newSubtaskId = super.addSubtask(subtask);
        save();
        return newSubtaskId;
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    private static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder str = new StringBuilder();

        for (Task task : history) {
            str.append(task.getId()).append(",");
        }

        if (str.length() != 0) {
            str.deleteCharAt(str.length() - 1);
        }

        return str.toString();
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> toReturn = new ArrayList<>();
        if (value != null) {
            String[] id = value.split(",");

            for (String number : id) {
                toReturn.add(Integer.parseInt(number));
            }
        }
        return toReturn;
    }
}



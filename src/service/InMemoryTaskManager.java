package service;

import tasks.Status;
import tasks.Task;
import tasks.Epic;
import tasks.SubTask;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    public HistoryManager historyManager = Managers.getDefaultHistory();

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private int i = 0;


    @Override
    public void addTask(Task task) {
        task.setId(newId());
        tasks.put(task.getId(), task);
    }

    @Override
    public Collection<Task> getTasks() {
        return tasks.values();
    }

    @Override
    public Collection<SubTask> getAllSubtasks() {
        return subTasks.values();
    }

    @Override
    public Task getTaskByID(int taskId) {
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpicByID(int epicId) {
        historyManager.add(epics.get(epicId));
        return epics.get(epicId);
    }

    @Override
    public SubTask getSubtaskByID(int subtaskId) {
        historyManager.add(subTasks.get(subtaskId));
        return subTasks.get(subtaskId);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    @Override
    public void addEpic(Epic epic) {
        epic.setId(newId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public Collection<Epic> getEpics() {
        return epics.values();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        int subTaskId = this.newId();
        subTask.setId(subTaskId);
        subTasks.put(subTaskId, subTask);
        int epicIdOfSubTask = subTask.getEpicId();
        Epic epic = epics.get(epicIdOfSubTask);
        if (epic != null) {
            epic.addSubTaskID(subTask);
            this.setEpicStatus(epics.get(subTask.getEpicId()));
        }
    }

    @Override
    public ArrayList<SubTask> getSubTask(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
        List<Integer> subtasksFromEpic = epic.getSubTaskIDs();
        for (Integer integer : subtasksFromEpic) {
            subTaskArrayList.add(subTasks.get(integer));
        }
        return subTaskArrayList;
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeSubtaskById(int subtaskId) {
        int epicIdOfSubTask = subTasks.get(subtaskId).getEpicId();
        Epic epic = epics.get(epicIdOfSubTask);
        subTasks.remove(subtaskId);
        List<Integer> subtasksFromEpic = epic.getSubTaskIDs();
        for (int i = 0; i < subtasksFromEpic.size(); i++) {
            if (subtasksFromEpic.get(i) == subtaskId) {
                epic.removeSubTaskID(i);
            }

        }

        this.setEpicStatus(epic);
    }

    @Override
    public void removeAllSubtasks() {
        subTasks.clear();
    }


    @Override
    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public void updateTask(Task task) {
        int idUpdTask = -1;
        for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
            if (Objects.equals(entry.getValue().getName(), task.getName()))
                idUpdTask = entry.getValue().getId();
        }
        if (idUpdTask >= 0) {
            task.setId(idUpdTask);
            tasks.put(idUpdTask, task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        int idUpdEpic = -1;
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            if (Objects.equals(entry.getValue().getName(), epic.getName()))
                idUpdEpic = entry.getValue().getId();
        }
        if (idUpdEpic >= 0) {
            epic.setId(idUpdEpic);
            for (int i = 0; i < epics.get(idUpdEpic).getSubTaskIDs().size(); i++) {
                SubTask subTask = subTasks.get(epics.get(idUpdEpic).getSubTaskIDs().get(i));
                epic.addSubTaskID(subTask);
            }
            epics.put(idUpdEpic, epic);
        }
    }

    @Override
    public void updateSubtask(SubTask subTask) {
        int idUpdSubtask = -1;
        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            if (Objects.equals(entry.getValue().getName(), subTask.getName()))
                idUpdSubtask = entry.getValue().getId();
        }
        if (idUpdSubtask >= 0) {
            subTask.setId(idUpdSubtask);
            subTasks.put(idUpdSubtask, subTask);
            this.setEpicStatus(epics.get(subTask.getEpicId()));
        }

    }

    @Override
    public void removeTaskById(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void removeEpicById(int epicId) {
        epics.remove(epicId);

        ArrayList<Integer> idDelSubtasks = new ArrayList<>();
        for (Map.Entry<Integer, SubTask> entry : subTasks.entrySet()) {
            if (entry.getValue().getEpicId() == epicId)
                idDelSubtasks.add(entry.getValue().getId());
        }

        for (Integer idDelSubtask : idDelSubtasks) {
            subTasks.remove(idDelSubtask);
        }
    }


    private void setEpicStatus(Epic epic) {
        Status oldTaskStatus = epic.getStatus();
        ArrayList<SubTask> subTasksUpd = new ArrayList<>();
        for (int i = 0; i < epic.getSubTaskIDs().size(); i++) {
            subTasksUpd.add(subTasks.get(epic.getSubTaskIDs().get(i)));
        }

        int counterDone = 0;
        int counterNew = 0;
        for (SubTask subTask : subTasksUpd) {
            switch (subTask.getStatus()) {
                case NEW:
                    counterNew++;
                    break;
                case IN_PROGRESS:
                    break;
                case DONE:
                    counterDone++;
                    break;
            }
        }

        if (subTasksUpd.size() == 0) {
            epic.setStatus(Status.NEW);
        } else if (counterDone == subTasksUpd.size()) {
            epic.setStatus(Status.DONE);
        } else if (counterNew == subTasksUpd.size()) {
            epic.setStatus(oldTaskStatus);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }



    private int newId() {
        return ++i;
    }


}
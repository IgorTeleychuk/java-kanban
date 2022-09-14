package service;

import tasks.Task;
import util.CustomLinkedList;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList<Task> list;

    public InMemoryHistoryManager() {
        this.list = new CustomLinkedList<>();
    }
    @Override
    public void add(Task task){
        remove(task.getId());
        list.linkLast(task);
    }

    @Override
    public void remove(int id){
        list.remove(id);
    }
    @Override
    public List<Task> getHistory(){
        return list.getTasks();
    }
}


package tasks;

import tasks.Task;
import tasks.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<Integer> subTaskIDs = new ArrayList<>();

    public Epic(String name, Status status, String description) {
        super(name, status, description);
    }

    public void addSubTaskID(SubTask subTask) {
        subTaskIDs.add(subTask.getId());
    }

    public List<Integer> getSubTaskIDs() {
        return subTaskIDs;
    }

    public void removeSubTaskID(int subTaskId) {
        subTaskIDs.remove(subTaskId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskIDs, epic.subTaskIDs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskIDs);
    }



    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                ", description='" + getDescription() + '\'' +
                ", subTasksIDs=" + subTaskIDs +
                '}';
    }
}


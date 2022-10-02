package tasks;

import status.Status;


import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import util.TaskType;
import java.util.Objects;
import java.time.LocalDateTime;

public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    private LocalDateTime endTime;

    public Epic(String description, String name, Status status) {
        super(description, name, status, LocalDateTime.of(2022,01,01,00,00),0);
        this.startTime = getStartTime();
        this.endTime = getEndTime();
        this.duration = getDuration();
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }
    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    @Override
    public LocalDateTime getEndTime(){
        return this.startTime.plusMinutes(this.duration);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskIds(int id) {
        subtaskIds.add(id);
    }

    public void addSubtaskAllIds(List<Integer> allIds) {
        this.subtaskIds = allIds;
    }

    @Override
    public TaskType getType() { return TaskType.EPIC; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy");
        return getId() + "," + TaskType.EPIC + "," + getName() + "," + getStatus() + "," + getDescription() +","+
                startTime.format(formatter)+","+ this.getEndTime().format(formatter)+ ","+ duration +",\n";
    }
}


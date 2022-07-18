import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String specification, int epicId) {
        super(title, specification);
        this.epicId = epicId;
    }

    public Subtask(String title, String specification, int id, String status, int epicId) {
        super(title, specification, id, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subtask otherSubtask = (Subtask) obj;
        return Objects.equals(getTitle(), otherSubtask.getTitle()) &&
                Objects.equals(getSpecification(), otherSubtask.getSpecification()) &&
                (getId() == otherSubtask.getId()) &&
                Objects.equals(getStatus(), otherSubtask.getStatus()) &&
                (epicId == otherSubtask.epicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getSpecification(), getId(), getStatus(), epicId);
    }

    @Override
    public String toString() {
        return "���������{" +
                "��������='" + getTitle() + '\'' +
                ", ��������='" + getSpecification() + '\'' +
                ", id='" + getId() + '\'' +
                ", ������='" + getStatus() + '\'' +
                ", id �����='" + epicId + '}' + '\'';
    }
}
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String specification) {
        super(title, specification);
    }

    public Epic(String title, String specification, int id, String status, ArrayList<Integer> subtaskIds) {
        super(title, specification, id, status);
        this.subtaskIds = subtaskIds;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override  public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Epic otherEpic = (Epic) obj;
        return Objects.equals(getTitle(), otherEpic.getTitle()) &&
                Objects.equals(getSpecification(), otherEpic.getSpecification()) &&
                (getId() == otherEpic.getId()) &&
                Objects.equals(getStatus(), otherEpic.getStatus()) &&
                Objects.equals(subtaskIds, otherEpic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getSpecification(), getId(), getStatus(), subtaskIds);
    }

    @Override
    public String toString() {
        return "Ёпик{" +
                "название='" + getTitle() + '\'' +
                ", описание='" + getSpecification() + '\'' +
                ", id='" + getId() + '\'' +
                ", статус='" + getStatus() + '\'' +
                ", id подзадач(и)='" + subtaskIds + '}' + '\'';
    }
}

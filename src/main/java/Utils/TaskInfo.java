package Utils;

import java.time.LocalDateTime;

public class TaskInfo {
    private int id;
    private int team_id;
    private String info;
    private String status;
    private String priority;
    private LocalDateTime deadline;

    public TaskInfo(int id, int team_id, String info, String status, String priority, LocalDateTime deadline){
        this.id = id;
        this.team_id = team_id;
        this.info = info;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}

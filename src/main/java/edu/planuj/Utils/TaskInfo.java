package edu.planuj.Utils;

import java.time.LocalDateTime;
import java.util.List;

public class TaskInfo implements java.io.Serializable {
    private int id;
    private int team_id;
    private String info;
    private Status status;
    private int priority;
    private LocalDateTime deadline;
    private List<UserInfo> assignedUsers;

    public static enum Status{
        TODO,
        IN_PROGRESS,
        PEER_REVIEW,
        PENDING,
        PENDING_MERGE,
        TRIAGE,
        DONE
    }

    public TaskInfo(int id, int team_id, String info, Status status, int priority, LocalDateTime deadline, List<UserInfo> assignedUsers){
        this.id = id;
        this.team_id = team_id;
        this.info = info;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
        this.assignedUsers = assignedUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamID() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<UserInfo> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<UserInfo> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}

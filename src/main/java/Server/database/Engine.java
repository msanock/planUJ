package Server.database;

public interface Engine{
    int addUser(String name);
    int addTask(int team_id, String name, String info, String status, String priority, String deadline);
    int addTeam(String name);
    int addTeamUser(int team_id, int user_id, String role, String position);
    int addUserTask(int user_id, int task_id);
}

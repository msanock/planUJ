package Server.database;

public interface TaskDatabaseOperations {
    int addTask(int team_id, String name, String info, String status, String priority, String deadline);
    int addUserTask(int user_id, int task_id);
}

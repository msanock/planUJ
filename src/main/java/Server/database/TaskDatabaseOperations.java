package Server.database;

import Utils.TaskInfo;

public interface TaskDatabaseOperations {
    void addTask(TaskInfo taskInfo);
    void addUserTask(int user_id, int task_id);
}

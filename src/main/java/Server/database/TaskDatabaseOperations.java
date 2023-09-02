package Server.database;

import Server.sql.DatabaseException;
import Utils.OperationResults.GetTasksResult;
import Utils.OperationResults.IdResult;
import Utils.TaskInfo;

public interface TaskDatabaseOperations {
    IdResult addTask(TaskInfo taskInfo) throws DatabaseException;
    void addUserTask(int user_id, int task_id) throws DatabaseException;
    GetTasksResult getTeamTasks(int team_id) throws DatabaseException;
    GetTasksResult getUserTasks(int user_id) throws DatabaseException;
    void updateTask(TaskInfo taskInfo) throws DatabaseException;
}

package edu.planuj.Server.database;

import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.GetTasksResult;
import edu.planuj.Utils.OperationResults.IdResult;
import edu.planuj.Utils.TaskInfo;

public interface TaskDatabaseOperations {
    IdResult addTask(TaskInfo taskInfo) throws DatabaseException;
    void addUserTask(int user_id, int task_id) throws DatabaseException;
    GetTasksResult getTeamTasks(int team_id) throws DatabaseException;
    GetTasksResult getUserTasks(int user_id) throws DatabaseException;
    void updateTask(TaskInfo taskInfo) throws DatabaseException;
    void removeUserFromTask(int user_id, int task_id) throws DatabaseException;
}

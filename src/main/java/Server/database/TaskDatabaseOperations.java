package Server.database;

import Utils.OperationResults.GetTasksResult;
import Utils.OperationResults.IdResult;
import Utils.TaskInfo;

public interface TaskDatabaseOperations {
    IdResult addTask(TaskInfo taskInfo);
    void addUserTask(int user_id, int task_id);
    GetTasksResult getTeamTasks(int team_id);
    GetTasksResult getUserTasks(int user_id);

}

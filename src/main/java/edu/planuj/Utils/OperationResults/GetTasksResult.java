package edu.planuj.Utils.OperationResults;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class GetTasksResult extends OperationResult{
    List<TaskInfo> tasks;
    @SuppressWarnings("unchecked")
    public GetTasksResult(ResultSet resultSet) throws SQLException {
        super();
        tasks = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer[] userIds = (Integer[]) resultSet.getArray("ids").getArray();
                String[] userNames = (String[]) resultSet.getArray("names").getArray();
                List<UserInfo> users = new ArrayList<>();
                for (int i = 0; i < userIds.length; i++) {
                    users.add(new UserInfo(userNames[i], userIds[i]));
                }

                TaskInfo taskInfo = new TaskInfo(resultSet.getInt("id"), resultSet.getInt("team_id"),
                        resultSet.getString("info"), TaskInfo.Status.valueOf(resultSet.getString("status")), resultSet.getInt("priority"),
                        resultSet.getTimestamp("deadline").toLocalDateTime(), users);
                this.tasks.add(taskInfo);
            }
            this.success = true;
        } catch (Exception e) {
            this.success = false;
            this.exception = e;
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while getting tasks: ", e);
            throw new SQLException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public GetTasksResult(ResponsePackage responsePackage){
        Object taskList =  responsePackage.getData(ResponsePackage.Dictionary.TASKS_LIST);
        if(taskList instanceof List) {
            this.tasks = (List<TaskInfo>) taskList;
        }
    }

    @Override
    public Packable toResponsePackage(UUID uuid ) {
        ResponsePackage.Builder builder = new ResponsePackage.Builder();
        builder.addData(ResponsePackage.Dictionary.TASKS_LIST, tasks);
        builder.setUuid(uuid);
        builder.setSuccess(success);
        return builder.build();
    }


    public List<TaskInfo> getTasks() {
        return tasks;
    }
}

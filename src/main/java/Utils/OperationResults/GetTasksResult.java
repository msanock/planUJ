package Utils.OperationResults;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import Utils.TaskInfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class GetTasksResult extends OperationResult{
    List<TaskInfo> tasks;
    public GetTasksResult(ResultSet resultSet) {
        super();
        tasks = new ArrayList<>();
        try {
            while (resultSet.next()) {
                TaskInfo taskInfo = new TaskInfo(resultSet.getInt("id"), resultSet.getInt("team_id"),
                        resultSet.getString("info"), resultSet.getString("status"), resultSet.getString("priority"),
                        resultSet.getTimestamp("deadline").toLocalDateTime());
                this.tasks.add(taskInfo);
            }
            this.success = true;
        } catch (Exception e) {
            this.success = false;
            this.exception = e;
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while getting tasks: ", e);
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

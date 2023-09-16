package edu.planuj.Utils.OperationResults;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Utils.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class GetUsersResult extends  OperationResult{
    List<UserInfo> users;
    public GetUsersResult(ResultSet resultSet) throws SQLException {
        super();
        this.users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                UserInfo userInfo = new UserInfo(resultSet.getString("name"), resultSet.getInt("id"));
                this.users.add(userInfo);
            }
            this.success = true;
        } catch (Exception e) {
            this.success = false;
            this.exception = e;
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while getting users: ", e);
            throw new SQLException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public GetUsersResult(ResponsePackage responsePackage){
        Object userList =  responsePackage.getData(ResponsePackage.Dictionary.USERS_LIST);
        if(userList instanceof List) {
            this.users = (List<UserInfo>) userList;
        }
    }

    @Override
    public Packable toResponsePackage(UUID uuid){
        ResponsePackage.Builder builder = new ResponsePackage.Builder();
        builder.addData(ResponsePackage.Dictionary.USERS_LIST, users);
        builder.setSuccess(success);
        builder.setUuid(uuid);
        return builder.build();
    }

    public List<UserInfo> getUsers() {
        return users;
    }
}

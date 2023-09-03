package Utils.OperationResults;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import Utils.UserInfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class GetUsersResult extends  OperationResult{
    List<UserInfo> users;
    public GetUsersResult(ResultSet resultSet) {
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

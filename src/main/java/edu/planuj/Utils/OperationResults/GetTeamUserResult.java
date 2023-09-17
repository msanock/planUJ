package edu.planuj.Utils.OperationResults;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.TeamUser;
import edu.planuj.Utils.UserInfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class GetTeamUserResult extends OperationResult {
    List<TeamUser> teamUsers;

    public GetTeamUserResult(ResultSet resultSet) throws DatabaseException {
        super();
        this.teamUsers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                TeamUser userInfo = new TeamUser(
                        resultSet.getString("name"),
                        resultSet.getInt("id"),
                        TeamUser.Role.valueOf(resultSet.getString("role")),
                        resultSet.getString("position")
                );
                this.teamUsers.add(userInfo);
            }
            this.success = true;
        } catch (Exception e) {
            this.success = false;
            this.exception = e;
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while getting users: ", e);
            throw new DatabaseException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public GetTeamUserResult(ResponsePackage responsePackage){
        this.success = responsePackage.isSuccess();
        if(!success) {
            this.exception = new Exception(responsePackage.getData(ResponsePackage.Dictionary.ERROR).toString());
        }
        Object userList =  responsePackage.getData(ResponsePackage.Dictionary.TEAM_USERS_LIST);
        if(userList instanceof List) {
            this.teamUsers = (List<TeamUser>) userList;
        }
    }

    @Override
    public Packable toResponsePackage(UUID uuid) {
        ResponsePackage.Builder builder = new ResponsePackage.Builder();
        builder.addData(ResponsePackage.Dictionary.TEAM_USERS_LIST, teamUsers);
        builder.setSuccess(success);
        builder.setUuid(uuid);
        return builder.build();
    }


    public List<TeamUser> getTeamUsers() {
        return teamUsers;
    }


}

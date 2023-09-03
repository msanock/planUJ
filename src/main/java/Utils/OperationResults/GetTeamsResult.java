package Utils.OperationResults;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import Utils.TeamInfo;
import Utils.TeamUser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class GetTeamsResult extends OperationResult{
    List<TeamInfo> teams;
    public GetTeamsResult(ResultSet resultSet){
        super();
        teams = new ArrayList<>();
        try {
            int prevTeamId = -1;
            TeamInfo currTeamInfo = null;
            while (resultSet.next()) {
                if(prevTeamId != resultSet.getInt("tid")) {
                    if(prevTeamId != -1){
                        this.teams.add(currTeamInfo);
                    }
                    prevTeamId = resultSet.getInt("tid");
                    currTeamInfo = new TeamInfo(resultSet.getString("tname"), resultSet.getInt("tid"), new ArrayList<>());
                }
                currTeamInfo.getUsers().add(new TeamUser(resultSet.getString("name"), resultSet.getInt("user_id"), resultSet.getString("role"), resultSet.getString("position")));
            }
            this.teams.add(currTeamInfo);
            this.success = true;
        } catch (Exception e) {
            this.success = false;
            this.exception = e;
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while getting teams: ", e);
        }
    }

    @Override
    public Packable toResponsePackage(UUID uuid) {
        ResponsePackage.Builder builder = new ResponsePackage.Builder();
        builder.addData(ResponsePackage.Dictionary.TEAMS_LIST, teams);
        builder.setUuid(uuid);
        builder.setSuccess(success);
        return builder.build();
    }

    public List<TeamInfo> getTeams() {
        return teams;
    }
}
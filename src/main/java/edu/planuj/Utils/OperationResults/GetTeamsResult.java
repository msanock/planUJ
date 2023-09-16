package edu.planuj.Utils.OperationResults;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class GetTeamsResult extends OperationResult{
    List<TeamInfo> teams;
    public GetTeamsResult(ResultSet resultSet) throws SQLException {
        super();
        teams = new ArrayList<>();
        try {
            int prevTeamId = -1;
            TeamInfo currTeamInfo = null;
            while (resultSet.next()) {
                int tid = resultSet.getInt("tid");
                if(prevTeamId != tid) {
                    if(prevTeamId != -1){
                        this.teams.add(currTeamInfo);
                    }
                    prevTeamId = tid;
                    currTeamInfo = new TeamInfo(resultSet.getString("tname"), tid, new ArrayList<>());
                }
                currTeamInfo.getUsers().add(new TeamUser(resultSet.getString("name"), resultSet.getInt("user_id"), TeamUser.Role.valueOf(resultSet.getString("role")), resultSet.getString("position")));
            }
            if(currTeamInfo!= null) this.teams.add(currTeamInfo);
            this.success = true;
        } catch (Exception e) {
            this.success = false;
            this.exception = e;
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while getting teams: ", e);
            throw new SQLException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public GetTeamsResult(ResponsePackage responsePackage){
        Object teamList= responsePackage.getData(ResponsePackage.Dictionary.TEAMS_LIST);
        if(teamList instanceof List) {
            this.teams = (List<TeamInfo>) teamList;
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

package Server.database;

import Utils.OperationResults.GetTeamsResult;
import Utils.OperationResults.GetUsersResult;
import Utils.OperationResults.IdResult;
import Utils.OperationResults.OperationResult;
import Utils.TeamInfo;
import Utils.TeamUser;

public interface TeamDatabaseOperations {
    IdResult addTeam(TeamInfo teamInfo);
    void addTeamUser(TeamUser teamUser, int team_id);
    GetTeamsResult getTeams();
    GetUsersResult getTeamUsers(int team_id);
}

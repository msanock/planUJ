package Server.database;

import Server.sql.DatabaseException;
import Utils.OperationResults.GetTeamsResult;
import Utils.OperationResults.GetUsersResult;
import Utils.OperationResults.IdResult;
import Utils.TeamInfo;
import Utils.TeamUser;

public interface TeamDatabaseOperations {
    IdResult addTeam(TeamInfo teamInfo) throws DatabaseException;
    void addTeamUser(TeamUser teamUser, int team_id) throws DatabaseException;
    GetTeamsResult getTeams() throws DatabaseException;
    GetUsersResult getTeamUsers(int team_id) throws DatabaseException;
    GetTeamsResult getUserTeams(int user_id) throws DatabaseException;
}

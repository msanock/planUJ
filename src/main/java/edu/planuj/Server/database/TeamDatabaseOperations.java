package edu.planuj.Server.database;

import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.GetTeamsResult;
import edu.planuj.Utils.OperationResults.GetUsersResult;
import edu.planuj.Utils.OperationResults.IdResult;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;

public interface TeamDatabaseOperations {
    IdResult addTeam(TeamInfo teamInfo) throws DatabaseException;
    void addTeamUser(TeamUser teamUser, int team_id) throws DatabaseException;
    GetTeamsResult getTeams() throws DatabaseException;
    GetUsersResult getTeamUsers(int team_id) throws DatabaseException;
    GetTeamsResult getUserTeams(int user_id) throws DatabaseException;
}

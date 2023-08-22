package Server.database;

import Utils.TeamInfo;
import Utils.TeamUser;

public interface TeamDatabaseOperations {
    void addTeam(TeamInfo teamInfo);
    void addTeamUser(TeamUser teamUser, int team_id);
}

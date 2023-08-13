package Server.database;

public interface TeamDatabaseOperations {
    int addTeam(String name);
    int addTeamUser(int team_id, int user_id, String role, String position);
}

package Connection;

import Server.database.Database;
import Utils.UserInfo;

public class ServerDatabase implements Database {
    @Override
    public int addTask(int team_id, String name, String info, String status, String priority, String deadline) {
        return 0;
    }

    @Override
    public int addUserTask(int user_id, int task_id) {
        return 0;
    }

    @Override
    public int addTeam(String name) {
        return 0;
    }

    @Override
    public int addTeamUser(int team_id, int user_id, String role, String position) {
        return 0;
    }

    @Override
    public int addUser(UserInfo userInfo) {
        return 0;
    }
    //placeholder
}

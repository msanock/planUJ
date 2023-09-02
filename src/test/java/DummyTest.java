// Remove this file when there are other tests

import Presentation.database.DatabaseFactory;
import Server.database.Database;
import Utils.OperationResults.GetTasksResult;
import Utils.OperationResults.GetTeamsResult;
import Utils.OperationResults.GetUsersResult;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DummyTest {
    @Test
    void test() {
        DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
        Database database = databaseFactory.getLocalDatabase();

        UserInfo user = new UserInfo("username", 0);
        database.addUser(user);
        System.out.println(user.getId());

        TeamInfo team = new TeamInfo("teamname", 0, List.of());
        database.addTeam(team);
        System.out.println(team.getId());

        TeamUser teamUser = new TeamUser(user.getUsername(), user.getId(), "role", "position");
        database.addTeamUser(teamUser, team.getId());

        TaskInfo task = new TaskInfo(0, team.getId(), "info", "doin", "low", LocalDateTime.now());
        database.addTask(task);
        System.out.println(task.getId());

        database.addUserTask(user.getId(), task.getId());
    }


    @Test
    void Test2(){
        DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
        Database database = databaseFactory.getLocalDatabase();

        /*GetUsersResult getUsersResult = database.getUsers();
        System.out.println(getUsersResult.getUsers());

        GetUsersResult getTeamUsersResult = database.getTeamUsers(1);
        System.out.println(getTeamUsersResult.getUsers());*/

        GetTeamsResult getTeamsResult = database.getTeams();
        System.out.println(getTeamsResult.getTeams());

        /*GetTasksResult getTeamTasksResult = database.getTeamTasks(1);
        System.out.println(getTeamTasksResult.getTasks());

        GetTasksResult getUserTasksResult = database.getUserTasks(1);
        System.out.println(getUserTasksResult.getTasks());*/
    }
}

package edu.planuj.Presentation.database;

import edu.planuj.Server.database.Database;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.*;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;
import edu.planuj.Presentation.database.SecureDatabase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SecureDatabaseTest {

    Database delegate;

    void prepareForTesting(){
        delegate = Mockito.mock(Database.class);
    }

    @Test
    void addTask() {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);

        //when
        assertDoesNotThrow(() -> secureDatabase.addTask(null));

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).addTask(null));
    }

    @Test
    void addUserTask() {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);

        //when
        assertDoesNotThrow(() -> secureDatabase.addUserTask(0, 0));

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).addUserTask(0, 0));
    }

    @Test
    void getTeamTasks() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        GetTasksResult getTasksResult = Mockito.mock(GetTasksResult.class);
        Mockito.when(delegate.getTeamTasks(0)).thenReturn(getTasksResult);


        //when
        GetTasksResult actual = secureDatabase.getTeamTasks(0);

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).getTeamTasks(0));
        assertEquals(getTasksResult, actual);
    }

    @Test
    void getUserTasks() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        GetTasksResult result = Mockito.mock(GetTasksResult.class);
        Mockito.when(delegate.getUserTasks(0)).thenReturn(result);


        //when
        GetTasksResult actual = secureDatabase.getUserTasks(0);

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).getUserTasks(0));
        assertEquals(result, actual);
    }

    @Test
    void updateTask() {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);

        //when
        assertDoesNotThrow(() -> secureDatabase.updateTask(null));

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).updateTask(null));
    }

    @Test
    void addTeam() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        TeamInfo teamInfo = Mockito.mock(TeamInfo.class);
        IdResult idResult = Mockito.mock(IdResult.class);
        Mockito.when(delegate.addTeam(teamInfo)).thenReturn(idResult);

        //when
        IdResult actual = secureDatabase.addTeam(teamInfo);

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).addTeam(teamInfo));
        assertEquals(idResult, actual);
    }

    @Test
    void addTeamUser() {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        TeamUser teamUser = Mockito.mock(TeamUser.class);

        //when
        assertDoesNotThrow(() -> secureDatabase.addTeamUser(teamUser, 0));

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).addTeamUser(teamUser, 0));
    }

    @Test
    void getTeams() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        GetTeamsResult getTeamsResult = Mockito.mock(GetTeamsResult.class);
        Mockito.when(delegate.getTeams()).thenReturn(getTeamsResult);

        //when
        GetTeamsResult actual = secureDatabase.getTeams();

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).getTeams());
        assertEquals(getTeamsResult, actual);
    }

    @Test
    void getTeamUsers() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        GetTeamUserResult getUsersResult = Mockito.mock(GetTeamUserResult.class);
        Mockito.when(delegate.getTeamUsers(0)).thenReturn(getUsersResult);

        //when
        GetTeamUserResult actual = secureDatabase.getTeamUsers(0);

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).getTeamUsers(0));
        assertEquals(getUsersResult, actual);
    }

    @Test
    void getUserTeams() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        GetTeamsResult getTeamsResult = Mockito.mock(GetTeamsResult.class);
        Mockito.when(delegate.getUserTeams(0)).thenReturn(getTeamsResult);

        //when
        GetTeamsResult actual = secureDatabase.getUserTeams(0);

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).getUserTeams(0));
        assertEquals(getTeamsResult, actual);
    }

    @Test
    void addUser() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        IdResult idResult = Mockito.mock(IdResult.class);
        Mockito.when(delegate.addUser(null)).thenReturn(idResult);

        //when
        IdResult actual = secureDatabase.addUser(null);

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).addUser(null));
        assertEquals(idResult, actual);
    }

    @Test
    void getUsers() throws DatabaseException {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);
        GetUsersResult getUsersResult = Mockito.mock(GetUsersResult.class);
        Mockito.when(delegate.getUsers()).thenReturn(getUsersResult);

        //when
        GetUsersResult actual = secureDatabase.getUsers();

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).getUsers());
        assertEquals(getUsersResult, actual);
    }

    @Test
    void removeUserFromTask() {
        //given
        prepareForTesting();
        SecureDatabase secureDatabase = new SecureDatabase(delegate);

        //when
        assertDoesNotThrow(() -> secureDatabase.removeUserFromTask(0, 0));

        //then
        assertDoesNotThrow(() -> Mockito.verify(delegate).removeUserFromTask(0, 0));
    }
}
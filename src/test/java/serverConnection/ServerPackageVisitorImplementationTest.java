package serverConnection;

import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import Connection.protocol.packages.taskOperations.*;
import Connection.protocol.packages.teamOperations.*;
import Connection.protocol.packages.userOperations.GetUsersPackage;
import Connection.protocol.packages.userOperations.LoginPackage;
import Server.database.Database;
import Utils.OperationResults.*;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;
import oracle.ucp.util.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.SocketSelector;

import javax.xml.crypto.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServerPackageVisitorImplementationTest {

    Database database;
    SocketSelector socketSelector;
    ServerClient sender;
    UUID uuid;
    Packable response;


    private ServerPackageVisitorImplementation createServerPackageVisitorImplementation() {
        database = Mockito.mock(Database.class);
        socketSelector = Mockito.mock(SocketSelector.class);
        return new ServerPackageVisitorImplementation(database, socketSelector);
    }

    private void prepareStuffForTesting(OperationResult result, UUIDHolder uuidHolder){
        sender = Mockito.mock(ServerClient.class);
        Mockito.when(sender.getClientID()).thenReturn(1L);
        response = Mockito.mock(Packable.class);
        if(result != null ) Mockito.when(result.toResponsePackage(Mockito.any())).thenReturn(response);
        uuid = UUID.randomUUID();
        Mockito.when(uuidHolder.getUUID()).thenReturn(uuid);
    }

    private void compareRespondInformation(RespondInformation respondInformation){
        assertNotNull(respondInformation);
        Map<Long , Packable> responses = respondInformation.getResponses();
        assertEquals(1, responses.size());
        Packable packable1 = responses.get(1L);
        assertEquals(response, packable1);
    }

    private void compareEmptyRespondInformation(RespondInformation respondInformation){
        assertNotNull(respondInformation);
        Map<Long , Packable> responses = respondInformation.getResponses();
        assertEquals(1, responses.size());
        assertNotNull(responses.get(1L));
    }

    @Test
    void handleEmptyPack() {
        //TODO?
    }

    @Test
    void handleUserInfoRequestPack() {
        //TODO?
    }

    @Test
    void handleResponseInformation() {
        //TODO?
    }

    @Test
    void handleLoginPackage() {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        LoginPackage packable = Mockito.mock(LoginPackage.class);
        IdResult idResult = Mockito.mock(IdResult.class);
        UserInfo userInfo = new UserInfo("name", 0);
        assertDoesNotThrow(()->Mockito.when(database.addUser(userInfo)).thenReturn(idResult));
        Mockito.when(packable.getUserInfo()).thenReturn(userInfo);
        Mockito.when(idResult.getId()).thenReturn(1);
        prepareStuffForTesting( idResult, packable);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleLoginPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addUser(userInfo));
        Mockito.verify(socketSelector, Mockito.times(1)).setClientID(1L, sender);
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleGetUsersPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        GetUsersPackage packable = Mockito.mock(GetUsersPackage.class);
        GetUsersResult result = Mockito.mock(GetUsersResult.class);
        prepareStuffForTesting( result, packable);
        assertDoesNotThrow(()->Mockito.when(database.getUsers()).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleGetUsersPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getUsers());
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleAddTeamPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        AddTeamPackage packable = Mockito.mock(AddTeamPackage.class);
        IdResult result = Mockito.mock(IdResult.class);
        TeamInfo teamInfo = new TeamInfo("name", 0, null);
        prepareStuffForTesting( result, packable);
        Mockito.when(result.getId()).thenReturn(1);
        Mockito.when(packable.getTeamInfo()).thenReturn(teamInfo);
        assertDoesNotThrow(()->Mockito.when(database.addTeam(teamInfo)).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleAddTeamPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addTeam(teamInfo));
        compareRespondInformation(respondInformation);
        assertEquals(1, teamInfo.getId());
    }

    @Test
    void handleAddTeamUserPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        AddTeamUserPackage packable = Mockito.mock(AddTeamUserPackage.class);
        TeamUser teamUser = new TeamUser("name",1, TeamUser.Role.ADMIN, "bob");
        Mockito.when(packable.getTeamUser()).thenReturn(teamUser);
        Mockito.when(packable.getTeamID()).thenReturn(1);
        prepareStuffForTesting( null, packable);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleAddTeamUserPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addTeamUser(teamUser, 1));
        compareEmptyRespondInformation(respondInformation);

    }

    @Test
    void handleGetTeamUsersPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        GetTeamUsersPackage packable = Mockito.mock(GetTeamUsersPackage.class);
        GetUsersResult result = Mockito.mock(GetUsersResult.class);
        prepareStuffForTesting( result, packable);
        Mockito.when(packable.getTeamID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getTeamUsers(1)).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleGetTeamUsersPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getTeamUsers(1));
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleGetTeamsPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        GetTeamsPackage packable = Mockito.mock(GetTeamsPackage.class);
        GetTeamsResult result = Mockito.mock(GetTeamsResult.class);
        prepareStuffForTesting( result, packable);
        assertDoesNotThrow(()->Mockito.when(database.getTeams()).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleGetTeamsPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getTeams());
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleGetUserTeamsPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        GetUserTeamsPackage packable = Mockito.mock(GetUserTeamsPackage.class);
        GetTeamsResult result = Mockito.mock(GetTeamsResult.class);
        prepareStuffForTesting(result, packable);
        Mockito.when(packable.getUserID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getUserTeams(1)).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleGetUserTeamsPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getUserTeams(1));
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleAddTaskPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        AddTaskPackage packable = Mockito.mock(AddTaskPackage.class);
        IdResult result = Mockito.mock(IdResult.class);
        TaskInfo taskInfo = new TaskInfo(0, 1, "name", TaskInfo.Status.TODO, 1, LocalDateTime.now(), null);
        prepareStuffForTesting( result, packable);
        Mockito.when(result.getId()).thenReturn(1);
        Mockito.when(packable.getTaskInfo()).thenReturn(taskInfo);
        assertDoesNotThrow(()->Mockito.when(database.addTask(taskInfo)).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleAddTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addTask(taskInfo));
        compareRespondInformation(respondInformation);
        assertEquals(1, taskInfo.getId());
    }

    @Test
    void handleGetTeamTasksPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        GetTeamTasksPackage packable = Mockito.mock(GetTeamTasksPackage.class);
        GetTasksResult result = Mockito.mock(GetTasksResult.class);
        prepareStuffForTesting( result, packable);
        Mockito.when(packable.getTeamID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getTeamTasks(1)).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleGetTeamTasksPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getTeamTasks(1));
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleGetUserTasksPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        GetUserTasksPackage packable = Mockito.mock(GetUserTasksPackage.class);
        GetTasksResult result = Mockito.mock(GetTasksResult.class);
        prepareStuffForTesting( result, packable);
        Mockito.when(packable.getUserID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getUserTasks(1)).thenReturn(result));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleGetUserTasksPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getUserTasks(1));
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleAddUserTaskPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        AddUserTaskPackage packable = Mockito.mock(AddUserTaskPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTaskID()).thenReturn(1);
        Mockito.when(packable.getUserID()).thenReturn(1);
        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleAddUserTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addUserTask(1,1));
        compareEmptyRespondInformation(respondInformation);
    }

    @Test
    void handleUpdateTaskPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        UpdateTaskPackage packable = Mockito.mock(UpdateTaskPackage.class);
        prepareStuffForTesting( null, packable);
        TaskInfo taskInfo = new TaskInfo(0, 1, "name", TaskInfo.Status.TODO, 1, LocalDateTime.now(), null);
        Mockito.when(packable.getTaskInfo()).thenReturn(taskInfo);
        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleUpdateTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).updateTask(taskInfo));
        compareEmptyRespondInformation(respondInformation);
    }

    @Test
    void handleRemoveUserFromTaskPackage() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        RemoveUserFromTaskPackage packable = Mockito.mock(RemoveUserFromTaskPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTaskID()).thenReturn(1);
        Mockito.when(packable.getUserID()).thenReturn(1);
        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleRemoveUserFromTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).removeUserFromTask(1,1));
        compareEmptyRespondInformation(respondInformation);
    }
}
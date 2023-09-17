package edu.planuj.serverConnection;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.Connection.protocol.packages.notifications.BatchNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NotificationPackage;
import edu.planuj.Connection.protocol.packages.taskOperations.*;
import edu.planuj.Connection.protocol.packages.teamOperations.*;
import edu.planuj.Connection.protocol.packages.userOperations.GetUsersPackage;
import edu.planuj.Connection.protocol.packages.userOperations.LoginPackage;
import edu.planuj.Server.database.Database;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Server.sql.PsqlEngine;
import edu.planuj.Utils.OperationResults.*;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;
import edu.planuj.Utils.UserInfo;
import edu.planuj.serverConnection.ServerPackageVisitorImplementation;
import javafx.concurrent.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.SocketSelector;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServerPackageVisitorImplementationTest {

    PsqlEngine database;
    SocketSelector socketSelector;
    ServerClient sender;
    UUID uuid;
    Packable response;
    DatabaseException dbException;
    RespondInformation respondInformation;


    private ServerPackageVisitorImplementation createServerPackageVisitorImplementation() {
        database = Mockito.mock(PsqlEngine.class);
        socketSelector = Mockito.mock(SocketSelector.class);
        return new ServerPackageVisitorImplementation(database, socketSelector);
    }

    private void prepareStuffForTesting(OperationResult result, UUIDHolder uuidHolder){
        sender = Mockito.mock(ServerClient.class);
        Mockito.when(sender.getClientID()).thenReturn(1L);
        dbException = Mockito.mock(DatabaseException.class);
        response = Mockito.mock(Packable.class);
        if(result != null ) Mockito.when(result.toResponsePackage(Mockito.any())).thenReturn(response);
        uuid = UUID.randomUUID();
        Mockito.when(uuidHolder.getUUID()).thenReturn(uuid);
        respondInformation = Mockito.mock(RespondInformation.class);
    }

    private void compareRespondInformation(RespondInformation respondInformation){
        assertNotNull(respondInformation);
        Map<Long , List<Packable>> responses = respondInformation.getResponses();
        assertEquals(1, responses.size());
        Packable packable1 = responses.get(1L).get(0);
        assertEquals(response, packable1);
    }

    private void compareEmptyRespondInformation(RespondInformation respondInformation){
        assertNotNull(respondInformation);
        Map<Long , List<Packable>> responses = respondInformation.getResponses();
        assertEquals(1, responses.size());
        assertNotNull(responses.get(1L));
    }

    @Test
    void handleEmptyPack() {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleEmptyPack(null, sender);
    }

    @Test
    void handleUserInfoRequestPack() {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleUserInfoRequestPack(null, sender);
    }

    @Test
    void handleResponseInformation() {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleResponseInformation(null, sender);
    }

    @Test
    void handleLoginPackage() throws DatabaseException {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        LoginPackage packable = Mockito.mock(LoginPackage.class);
        IdResult idResult = Mockito.mock(IdResult.class);
        UserInfo userInfo = new UserInfo("name", 0);
        assertDoesNotThrow(()->Mockito.when(database.addUser(userInfo)).thenReturn(idResult));
        Mockito.when(packable.getUserInfo()).thenReturn(userInfo);
        Mockito.when(idResult.getId()).thenReturn(1);
        prepareStuffForTesting( idResult, packable);

        GetTeamsResult getTeamsResult = Mockito.mock(GetTeamsResult.class);
        GetTasksResult getTasksResult = Mockito.mock(GetTasksResult.class);
        Mockito.when(database.getUnNotifiedTeamsForUser(1L)).thenReturn(getTeamsResult);
        Mockito.when(database.getUnNotifiedTasksForUser(1L)).thenReturn(getTasksResult);
        Mockito.when(getTeamsResult.getTeams()).thenReturn(List.of());
        Mockito.when(getTasksResult.getTasks()).thenReturn(List.of());

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleLoginPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addUser(userInfo));
        Mockito.verify(socketSelector, Mockito.times(1)).setClientID(1L, sender);
        compareRespondInformation(respondInformation);
    }

    @Test
    void handleLoginWithDatabaseException(){
        //given
        createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = Mockito.spy(
                new ServerPackageVisitorImplementation(database, socketSelector)
        );
        LoginPackage packable = Mockito.mock(LoginPackage.class);
        UserInfo userInfo = new UserInfo("name", 0);
        Mockito.when(packable.getUserInfo()).thenReturn(userInfo);
        prepareStuffForTesting( null, packable);
        Mockito.when(serverPackageVisitorImplementation.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        assertDoesNotThrow(()->Mockito.when(database.addUser(userInfo)).thenThrow(dbException));

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementation.handleLoginPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addUser(userInfo));
        Mockito.verify(socketSelector, Mockito.times(0)).setClientID(1L, sender);
        Mockito.verify(serverPackageVisitorImplementation, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleGetUsersPackageDBException(){
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        GetUsersPackage packable = Mockito.mock(GetUsersPackage.class);
        GetUsersResult result = Mockito.mock(GetUsersResult.class);
        prepareStuffForTesting( result, packable);
        assertDoesNotThrow(()->Mockito.when(database.getUsers()).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleGetUsersPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getUsers());
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleAddTeamPackageWithDBExcpetion() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        AddTeamPackage packable = Mockito.mock(AddTeamPackage.class);
        TeamInfo teamInfo = new TeamInfo("name", 0, null);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTeamInfo()).thenReturn(teamInfo);
        assertDoesNotThrow(()->Mockito.when(database.addTeam(teamInfo)).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleAddTeamPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addTeam(teamInfo));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleAddTeamUserPackageWithDBEXception() throws DatabaseException {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        AddTeamUserPackage packable = Mockito.mock(AddTeamUserPackage.class);
        TeamUser teamUser = new TeamUser("name",1, TeamUser.Role.ADMIN, "bob");
        Mockito.when(packable.getTeamUser()).thenReturn(teamUser);
        Mockito.when(packable.getTeamID()).thenReturn(1);
        prepareStuffForTesting( null, packable);
        Mockito.doThrow(dbException).when(database).addTeamUser(teamUser, 1);
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleAddTeamUserPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addTeamUser(teamUser, 1));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleGetTeamUsersPackageWithDBException(){
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        GetTeamUsersPackage packable = Mockito.mock(GetTeamUsersPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTeamID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getTeamUsers(1)).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleGetTeamUsersPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getTeamUsers(1));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleGetTeamsPackageWithDBException(){
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        GetTeamsPackage packable = Mockito.mock(GetTeamsPackage.class);
        prepareStuffForTesting( null, packable);
        assertDoesNotThrow(()->Mockito.when(database.getTeams()).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleGetTeamsPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getTeams());
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleGetUserTeamsPackageWithDBException() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        GetUserTeamsPackage packable = Mockito.mock(GetUserTeamsPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getUserID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getUserTeams(1)).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleGetUserTeamsPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getUserTeams(1));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleAddTaskPackageWithDBException() {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        AddTaskPackage packable = Mockito.mock(AddTaskPackage.class);
        TaskInfo taskInfo = new TaskInfo(0, 1, "name", TaskInfo.Status.TODO, 1, LocalDateTime.now(), null);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTaskInfo()).thenReturn(taskInfo);
        assertDoesNotThrow(()->Mockito.when(database.addTask(taskInfo)).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleAddTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addTask(taskInfo));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleGetTeamTasksPackageWithDBException(){
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        GetTeamTasksPackage packable = Mockito.mock(GetTeamTasksPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTeamID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getTeamTasks(1)).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleGetTeamTasksPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getTeamTasks(1));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleGetUserTasksPackageWithDBException(){
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        GetUserTasksPackage packable = Mockito.mock(GetUserTasksPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getUserID()).thenReturn(1);
        assertDoesNotThrow(()->Mockito.when(database.getUserTasks(1)).thenThrow(dbException));
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleGetUserTasksPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).getUserTasks(1));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleAddUserTaskPackageDBException() throws DatabaseException {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        AddUserTaskPackage packable = Mockito.mock(AddUserTaskPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTaskID()).thenReturn(1);
        Mockito.when(packable.getUserID()).thenReturn(1);
        Mockito.doThrow(dbException).when(database).addUserTask(1,1);
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleAddUserTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addUserTask(1,1));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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
    void handleUpdateTaskPackageDBException() throws DatabaseException {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        UpdateTaskPackage packable = Mockito.mock(UpdateTaskPackage.class);
        prepareStuffForTesting( null, packable);
        TaskInfo taskInfo = new TaskInfo(0, 1, "name", TaskInfo.Status.TODO, 1, LocalDateTime.now(), null);
        Mockito.when(packable.getTaskInfo()).thenReturn(taskInfo);
        Mockito.doThrow(dbException).when(database).updateTask(taskInfo);
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleUpdateTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).updateTask(taskInfo));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
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

    @Test
    void handleRemoveUserFromTaskPackageDBException() throws DatabaseException {
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerPackageVisitorImplementation serverPackageVisitorImplementationSpy = Mockito.spy(serverPackageVisitorImplementation);
        RemoveUserFromTaskPackage packable = Mockito.mock(RemoveUserFromTaskPackage.class);
        prepareStuffForTesting( null, packable);
        Mockito.when(packable.getTaskID()).thenReturn(1);
        Mockito.when(packable.getUserID()).thenReturn(1);
        Mockito.doThrow(dbException).when(database).removeUserFromTask(1,1);
        Mockito.when(serverPackageVisitorImplementationSpy.prepareBasicErrorResponse(sender, dbException)).thenReturn(respondInformation);

        //when
        RespondInformation respondInformation= serverPackageVisitorImplementationSpy.handleRemoveUserFromTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).removeUserFromTask(1,1));
        Mockito.verify(serverPackageVisitorImplementationSpy, Mockito.times(1)).prepareBasicErrorResponse(sender, dbException);
    }

    @Test
    void prepareBasicErrorResponse(){
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerClient sender = Mockito.mock(ServerClient.class);
        Exception exception = Mockito.mock(Exception.class);
        Mockito.when(sender.getClientID()).thenReturn(1L);

        //when
        RespondInformation respondInformation = serverPackageVisitorImplementation.prepareBasicErrorResponse(sender, exception);

        //then
        Map<Long, List<Packable>> packableMap = respondInformation.getResponses();
        assertEquals(1, packableMap.size());
        ResponsePackage packable = (ResponsePackage) packableMap.get(1L).get(0);
        assertFalse(packable.isSuccess());
        assertEquals(exception, packable.getData(ResponsePackage.Dictionary.ERROR));
    }

    @Test
    void prepareBasicResponse(){
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        ServerClient sender = Mockito.mock(ServerClient.class);
        Mockito.when(sender.getClientID()).thenReturn(1L);
        OperationResult operationResult = Mockito.mock(OperationResult.class);
        UUIDHolder response = Mockito.mock(UUIDHolder.class);
        UUID uuid = UUID.randomUUID();
        Mockito.when(response.getUUID()).thenReturn(uuid);
        Packable packable = Mockito.mock(Packable.class);
        Mockito.when(operationResult.toResponsePackage(uuid)).thenReturn(packable);

        //when
        RespondInformation respondInformation = serverPackageVisitorImplementation.prepareBasicResponse(sender, operationResult, response);

        //then
        Map<Long, List<Packable>> packableMap = respondInformation.getResponses();
        assertEquals(1, packableMap.size());
        assertEquals(packable, packableMap.get(1L).get(0));
    }

    @Test
    void notificationsAtLoginTest() throws DatabaseException {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        LoginPackage packable = Mockito.mock(LoginPackage.class);
        IdResult idResult = Mockito.mock(IdResult.class);
        UserInfo userInfo = new UserInfo("name", 0);
        assertDoesNotThrow(()->Mockito.when(database.addUser(userInfo)).thenReturn(idResult));
        Mockito.when(packable.getUserInfo()).thenReturn(userInfo);
        Mockito.when(idResult.getId()).thenReturn(1);
        prepareStuffForTesting( idResult, packable);

        GetTeamsResult getTeamsResult = Mockito.mock(GetTeamsResult.class);
        GetTasksResult getTasksResult = Mockito.mock(GetTasksResult.class);
        Mockito.when(database.getUnNotifiedTeamsForUser(1L)).thenReturn(getTeamsResult);
        Mockito.when(database.getUnNotifiedTasksForUser(1L)).thenReturn(getTasksResult);
        TeamInfo team1 = new TeamInfo("team1", 1, null);
        TeamInfo team2 = new TeamInfo("team2", 2, null);
        Mockito.when(getTeamsResult.getTeams()).thenReturn(List.of(
            team1, team2
        ));
        TaskInfo task1 = new TaskInfo(1, 1, "task1", TaskInfo.Status.TODO, 1, LocalDateTime.now(), null);
        TaskInfo task2 = new TaskInfo(2, 1, "task2", TaskInfo.Status.TODO, 1, LocalDateTime.now(), null);
        Mockito.when(getTasksResult.getTasks()).thenReturn(List.of(
            task1, task2
        ));

        //when
        RespondInformation respondInformation = serverPackageVisitorImplementation.handleLoginPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addUser(userInfo));
        Mockito.verify(socketSelector, Mockito.times(1)).setClientID(1L, sender);
        Mockito.verify(sender, Mockito.times(1)).setClientID(1L);
        Map<Long, List<Packable>> responses = respondInformation.getResponses();
        assertEquals(1, responses.size());
        List<Packable> packables = responses.get(1L);
        assertEquals(2, packables.size());
        BatchNotificationPackage batchNotificationPackage = (BatchNotificationPackage) packables.get(1);
        List<NotificationPackage> notificationPackages = batchNotificationPackage.getNotifications();
        assertEquals(4, notificationPackages.size());
        assertEquals(team1, ((NewTeamNotificationPackage)notificationPackages.get(0)).getTeamInfo());
        assertEquals(team2, ((NewTeamNotificationPackage)notificationPackages.get(1)).getTeamInfo());
        assertEquals(task1, ((NewTaskNotificationPackage)notificationPackages.get(2)).getTaskInfo());
        assertEquals(task2, ((NewTaskNotificationPackage)notificationPackages.get(3)).getTaskInfo());

        Mockito.verify(database, Mockito.times(1)).getUnNotifiedTeamsForUser(1L);
        Mockito.verify(database, Mockito.times(1)).getUnNotifiedTasksForUser(1L);
    }


    @Test
    void addUserTaskPackageNotification() throws DatabaseException {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        AddUserTaskPackage packable = Mockito.mock(AddUserTaskPackage.class);
        Mockito.when(packable.getTaskID()).thenReturn(1);
        Mockito.when(packable.getUserID()).thenReturn(1);
        prepareStuffForTesting( null, packable);

        UserInfo userInfo = new UserInfo("name", 1);
        TaskInfo task1 = new TaskInfo(1, 1, "task1", TaskInfo.Status.TODO, 1, LocalDateTime.now(), List.of(userInfo));
        TaskInfo task2 = new TaskInfo(2, 1, "task2", TaskInfo.Status.TODO, 1, LocalDateTime.now(), List.of(userInfo));
        GetTasksResult getTasksResult = Mockito.mock(GetTasksResult.class);
        Mockito.when(getTasksResult.getTasks()).thenReturn(List.of(task1, task2));
        Mockito.when(database.getUserTasks(1)).thenReturn(getTasksResult);
        Mockito.when(sender.getClientID()).thenReturn(2L);

        //when
        RespondInformation respondInformation = serverPackageVisitorImplementation.handleAddUserTaskPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addUserTask(1,1));
        Map<Long, List<Packable>> responses = respondInformation.getResponses();
        assertEquals(2, responses.size());
        List<Packable> packables = responses.get(1L);
        assertEquals(1, packables.size());
        assertEquals(task1, ((NewTaskNotificationPackage)packables.get(0)).getTaskInfo());

        packables = responses.get(2L);

        assertEquals(1, packables.size());
        assertTrue(packables.get(0) instanceof ResponsePackage);
    }

    @Test
    void addTeamUserPackageNotification() throws DatabaseException {
        //given
        ServerPackageVisitorImplementation serverPackageVisitorImplementation = createServerPackageVisitorImplementation();
        AddTeamUserPackage packable = Mockito.mock(AddTeamUserPackage.class);
        TeamUser teamUser = Mockito.mock(TeamUser.class);
        Mockito.when(packable.getTeamID()).thenReturn(1);
        Mockito.when(packable.getTeamUser()).thenReturn(teamUser);
        Mockito.when(teamUser.getId()).thenReturn(1);
        prepareStuffForTesting( null, packable);
        TeamUser teamUser1 = new TeamUser("user", 1, TeamUser.Role.ADMIN, "dev");
        TeamInfo team1 = new TeamInfo("team", 1, List.of(teamUser1));
        TeamInfo team2 = new TeamInfo("team2", 2, List.of(teamUser1));

        GetTeamsResult getTeamsResult = Mockito.mock(GetTeamsResult.class);
        Mockito.when(getTeamsResult.getTeams()).thenReturn(List.of(team1, team2));
        Mockito.when(database.getUserTeams(1)).thenReturn(getTeamsResult);
        Mockito.when(sender.getClientID()).thenReturn(2L);

        //when
        RespondInformation respondInformation = serverPackageVisitorImplementation.handleAddTeamUserPackage(packable, sender);

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).addTeamUser(teamUser,1));
        Map<Long, List<Packable>> responses = respondInformation.getResponses();
        assertEquals(2, responses.size());
        List<Packable> packables = responses.get(1L);
        assertEquals(1, packables.size());
        assertEquals(team1, ((NewTeamNotificationPackage)packables.get(0)).getTeamInfo());

        packables = responses.get(2L);

        assertEquals(1, packables.size());
        assertTrue(packables.get(0) instanceof ResponsePackage);
    }
}
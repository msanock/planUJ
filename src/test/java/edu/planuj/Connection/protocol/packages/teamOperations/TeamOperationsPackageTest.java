package edu.planuj.Connection.protocol.packages.teamOperations;

import edu.planuj.Connection.protocol.packages.BasicPackageTestPrep;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TeamOperationsPackageTest extends BasicPackageTestPrep {

    @Test
    void acceptAddTeam() {
        //given
        AddTeamPackage addTeamPackage = new AddTeamPackage(null);
        prepare();
        Mockito.when(packageVisitor.handleAddTeamPackage(addTeamPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(addTeamPackage);

        //then
        Mockito.verify(packageVisitor).handleAddTeamPackage(addTeamPackage, serverClient);
    }

    @Test
    void acceptAddTeamUser(){
        //given
        AddTeamUserPackage addTeamUserPackage = new AddTeamUserPackage(null, 1);
        prepare();
        Mockito.when(packageVisitor.handleAddTeamUserPackage(addTeamUserPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(addTeamUserPackage);

        //then
        Mockito.verify(packageVisitor).handleAddTeamUserPackage(addTeamUserPackage, serverClient);
    }

    @Test
    void acceptGetTeams(){
        //given
        GetTeamsPackage getTeamsPackage = new GetTeamsPackage();
        prepare();
        Mockito.when(packageVisitor.handleGetTeamsPackage(getTeamsPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(getTeamsPackage);

        //then
        Mockito.verify(packageVisitor).handleGetTeamsPackage(getTeamsPackage, serverClient);
    }

    @Test
    void acceptGetTeamUser(){
        //given
        GetTeamUsersPackage getTeamUserPackage = new GetTeamUsersPackage(1);
        prepare();
        Mockito.when(packageVisitor.handleGetTeamUsersPackage(getTeamUserPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(getTeamUserPackage);

        //then
        Mockito.verify(packageVisitor).handleGetTeamUsersPackage(getTeamUserPackage, serverClient);
    }

    @Test
    void acceptGetUserTeams(){
        //given
        GetUserTeamsPackage getUserTeamsPackage = new GetUserTeamsPackage(1);
        prepare();
        Mockito.when(packageVisitor.handleGetUserTeamsPackage(getUserTeamsPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(getUserTeamsPackage);

        //then
        Mockito.verify(packageVisitor).handleGetUserTeamsPackage(getUserTeamsPackage, serverClient);
    }
}
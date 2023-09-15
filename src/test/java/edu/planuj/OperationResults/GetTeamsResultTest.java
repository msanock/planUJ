package edu.planuj.OperationResults;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Utils.OperationResults.GetTeamsResult;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetTeamsResultTest {

    private ResultSet createResultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
           Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

           Mockito.when(rs.getInt("tid")).thenReturn(1).thenReturn(2).thenReturn(2);
           Mockito.when(rs.getString("tname")).thenReturn("tname1").thenReturn("tname2").thenReturn("tname2");
           Mockito.when(rs.getString("name")).thenReturn("name1").thenReturn("name2").thenReturn("name3");
           Mockito.when(rs.getInt("user_id")).thenReturn(1).thenReturn(2).thenReturn(3);
           Mockito.when(rs.getString("role")).thenReturn("ADMIN").thenReturn("ADMIN").thenReturn("MEMBER");
           Mockito.when(rs.getString("position")).thenReturn("position1").thenReturn("position2").thenReturn("position3");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    @Test
    void creatingTeamResults(){
        ResultSet rs = createResultSet();
        GetTeamsResult getTeamsResult = new GetTeamsResult(rs);
    }

    @Test
    void emptyResultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(rs.next()).thenReturn(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        GetTeamsResult getTeamsResult = new GetTeamsResult(rs);
        assertTrue(getTeamsResult.getTeams().isEmpty());
    }


    @Test
    void toResponsePackage() {
        ResultSet rs = createResultSet();
        GetTeamsResult getTeamsResult = new GetTeamsResult(rs);
        Packable result = getTeamsResult.toResponsePackage(UUID.randomUUID());
        assertTrue(result instanceof ResponsePackage);
        assertEquals(2, ((List<?>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).size());
        assertEquals(TeamInfo.class, ((List<?>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(0).getClass());
        assertEquals(1, ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(0).getId());
        assertEquals(2, ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getId());
        assertEquals("tname2", ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getName());
        assertEquals(2, ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getUsers().size());
        assertEquals(2, ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getUsers().get(0).getId());
        assertEquals("name2", ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getUsers().get(0).getUsername());
        assertEquals(TeamUser.Role.ADMIN, ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getUsers().get(0).getRole());
        assertEquals("position2", ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getUsers().get(0).getPosition());
        assertEquals(3, ((List<TeamInfo>)((ResponsePackage) result).getData(ResponsePackage.Dictionary.TEAMS_LIST)).get(1).getUsers().get(1).getId());
    }

    @Test
    void getTeams() {
        ResultSet rs = createResultSet();
        GetTeamsResult getTeamsResult = new GetTeamsResult(rs);
        assertEquals(2,getTeamsResult.getTeams().size(), 2);
        assertEquals(1,getTeamsResult.getTeams().get(0).getId(), 1);
        assertEquals(2,getTeamsResult.getTeams().get(1).getId(), 2);
        assertEquals("tname2",getTeamsResult.getTeams().get(1).getName(), "tname2");
        assertEquals(2, getTeamsResult.getTeams().get(1).getUsers().size(), 2);
        assertEquals(2, getTeamsResult.getTeams().get(1).getUsers().get(0).getId(), 2);
        assertEquals("name2", getTeamsResult.getTeams().get(1).getUsers().get(0).getUsername(), "name2");
        assertEquals(TeamUser.Role.ADMIN, getTeamsResult.getTeams().get(1).getUsers().get(0).getRole(), "role2");
        assertEquals("position2", getTeamsResult.getTeams().get(1).getUsers().get(0).getPosition(), "position2");
        assertEquals(3, getTeamsResult.getTeams().get(1).getUsers().get(1).getId());
    }
}
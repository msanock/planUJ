package Utils.OperationResults;

import Utils.UserInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GetUsersResultTest {

    private ResultSet createResultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            Mockito.when(rs.getInt("id")).thenReturn(1).thenReturn(2);
            Mockito.when(rs.getString("name")).thenReturn("username1").thenReturn("username2");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
    @Test
    void emptyResultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(rs.next()).thenReturn(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        GetUsersResult getUsersResult = new GetUsersResult(rs);
        assertTrue(getUsersResult.getUsers().isEmpty());
    }

    @Test
    void createGetUsersResult() {
        ResultSet rs = createResultSet();
        GetUsersResult getUsersResult = new GetUsersResult(rs);
    }


    @Test
    void toResponsePackage() {
        ResultSet rs = createResultSet();
        GetUsersResult getUsersResult = new GetUsersResult(rs);
        getUsersResult.toResponsePackage(java.util.UUID.randomUUID());
        assertEquals("username1",getUsersResult.getUsers().get(0).getUsername());
        assertEquals("username2",getUsersResult.getUsers().get(1).getUsername());
        assertEquals(1,getUsersResult.getUsers().get(0).getId());
        assertEquals(2,getUsersResult.getUsers().get(1).getId());
    }

    @Test
    void getUsers() {
        ResultSet rs = createResultSet();
        GetUsersResult getUsersResult = new GetUsersResult(rs);
        assertEquals("username1",getUsersResult.getUsers().get(0).getUsername());
        assertEquals("username2",getUsersResult.getUsers().get(1).getUsername());
        assertEquals(1,getUsersResult.getUsers().get(0).getId());
        assertEquals(2,getUsersResult.getUsers().get(1).getId());
    }
}
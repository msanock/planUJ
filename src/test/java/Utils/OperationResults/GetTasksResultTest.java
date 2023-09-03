package Utils.OperationResults;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetTasksResultTest {

    private ResultSet createResultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            Mockito.when(rs.getInt("id")).thenReturn(1).thenReturn(2);
            Mockito.when(rs.getInt("team_id")).thenReturn(1).thenReturn(2);
            Mockito.when(rs.getString("info")).thenReturn("info1").thenReturn("info2");
            Mockito.when(rs.getString("status")).thenReturn("status1").thenReturn("status2");
            Mockito.when(rs.getString("priority")).thenReturn("priority1").thenReturn("priority2");
            Mockito.when(rs.getTimestamp("deadline")).thenReturn(Timestamp.valueOf("2020-01-01 00:00:00")).thenReturn(Timestamp.valueOf("2020-01-01 00:00:00"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    @Test
    void creatingTaskResult(){
        ResultSet rs = createResultSet();
        GetTasksResult getTasksResult = new GetTasksResult(rs);
    }

    @Test
    void emptyResultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(rs.next()).thenReturn(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        GetTasksResult getTasksResult = new GetTasksResult(rs);
        assertTrue(getTasksResult.getTasks().isEmpty());
    }

    @Test
    void toResponsePackage() {
        ResultSet rs = createResultSet();
        GetTasksResult getTasksResult = new GetTasksResult(rs);
        Packable result = getTasksResult.toResponsePackage(UUID.randomUUID());
        assertTrue(result instanceof ResponsePackage);
        assertTrue(((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST) instanceof java.util.List);
        assertEquals(2, ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).size());
        assertTrue(((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0) instanceof Utils.TaskInfo);
        assertEquals(1, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getId());
        assertEquals(1, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getTeamID());
        assertEquals("info1", ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getInfo());
        assertEquals("status1", ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getStatus());
        assertEquals("priority1", ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getPriority());
        assertEquals(Timestamp.valueOf("2020-01-01 00:00:00").toLocalDateTime(), ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getDeadline());
        assertEquals(2, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getId());
        assertEquals(2, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getTeamID());
        assertEquals("info2", ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getInfo());
        assertEquals("status2", ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getStatus());
        assertEquals("priority2", ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getPriority());
    }

    @Test
    void getTasks() {
        ResultSet rs = createResultSet();
        GetTasksResult getTasksResult = new GetTasksResult(rs);
        assertEquals(2, getTasksResult.getTasks().size());
        assertEquals(1, getTasksResult.getTasks().get(0).getId());
        assertEquals(1, getTasksResult.getTasks().get(0).getTeamID());
        assertEquals("info1", getTasksResult.getTasks().get(0).getInfo());
        assertEquals("status1", getTasksResult.getTasks().get(0).getStatus());
        assertEquals("priority1", getTasksResult.getTasks().get(0).getPriority());
        assertEquals(Timestamp.valueOf("2020-01-01 00:00:00").toLocalDateTime(), getTasksResult.getTasks().get(0).getDeadline());
        assertEquals(2, getTasksResult.getTasks().get(1).getId());
        assertEquals(2, getTasksResult.getTasks().get(1).getTeamID());
        assertEquals("info2", getTasksResult.getTasks().get(1).getInfo());
        assertEquals("status2", getTasksResult.getTasks().get(1).getStatus());
        assertEquals("priority2", getTasksResult.getTasks().get(1).getPriority());
    }
}
package Utils.OperationResults;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import Utils.TaskInfo;
import Utils.UserInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetTasksResultTest {

    private ResultSet createResultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Array ids = Mockito.mock(Array.class);
            Array names = Mockito.mock(Array.class);
            Mockito.when(ids.getArray()).thenReturn(new Integer[]{1, 2});
            Mockito.when(names.getArray()).thenReturn(new String[]{"name1", "name2"});

            Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            Mockito.when(rs.getInt("id")).thenReturn(1).thenReturn(2);
            Mockito.when(rs.getInt("team_id")).thenReturn(1).thenReturn(2);
            Mockito.when(rs.getString("info")).thenReturn("info1").thenReturn("info2");
            Mockito.when(rs.getString("status")).thenReturn(String.valueOf(TaskInfo.Status.DONE)).thenReturn(String.valueOf(TaskInfo.Status.TODO));
            Mockito.when(rs.getInt("priority")).thenReturn(10).thenReturn(20);
            Mockito.when(rs.getTimestamp("deadline")).thenReturn(Timestamp.valueOf("2020-01-01 00:00:00")).thenReturn(Timestamp.valueOf("2020-01-01 00:00:00"));
            Mockito.when(rs.getArray("ids")).thenReturn(ids).thenReturn(ids);
            Mockito.when(rs.getArray("names")).thenReturn(names).thenReturn(names);
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
        assertEquals(TaskInfo.Status.DONE, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getStatus());
        assertEquals(10, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getPriority());
        assertEquals(Timestamp.valueOf("2020-01-01 00:00:00").toLocalDateTime(), ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getDeadline());


        assertEquals(2, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getId());
        assertEquals(2, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getTeamID());
        assertEquals("info2", ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getInfo());
        assertEquals(TaskInfo.Status.TODO, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getStatus());
        assertEquals(20, ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getPriority());
        assertEquals(Timestamp.valueOf("2020-01-01 00:00:00").toLocalDateTime(), ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getDeadline());
        List<UserInfo> list1 = ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(1)).getAssignedUsers();
        List<UserInfo> list2 = ((Utils.TaskInfo) ((java.util.List) ((ResponsePackage) result).getData(ResponsePackage.Dictionary.TASKS_LIST)).get(0)).getAssignedUsers();

        assertEquals(2, list1.size());
        assertEquals(2, list2.size());
        assertEquals(1, list1.get(0).getId());
        assertEquals(2, list1.get(1).getId());
        assertEquals(1, list2.get(0).getId());
        assertEquals(2, list2.get(1).getId());
        assertEquals("name1", list1.get(0).getUsername());
        assertEquals("name2", list1.get(1).getUsername());
        assertEquals("name1", list2.get(0).getUsername());
        assertEquals("name2", list2.get(1).getUsername());

    }

    @Test
    void getTasks() {
        ResultSet rs = createResultSet();
        GetTasksResult getTasksResult = new GetTasksResult(rs);
        assertEquals(2, getTasksResult.getTasks().size());
        assertEquals(1, getTasksResult.getTasks().get(0).getId());
        assertEquals(1, getTasksResult.getTasks().get(0).getTeamID());
        assertEquals("info1", getTasksResult.getTasks().get(0).getInfo());
        assertEquals(TaskInfo.Status.DONE, getTasksResult.getTasks().get(0).getStatus());
        assertEquals(10, getTasksResult.getTasks().get(0).getPriority());
        assertEquals(Timestamp.valueOf("2020-01-01 00:00:00").toLocalDateTime(), getTasksResult.getTasks().get(0).getDeadline());
        assertEquals(2, getTasksResult.getTasks().get(1).getId());
        assertEquals(2, getTasksResult.getTasks().get(1).getTeamID());
        assertEquals("info2", getTasksResult.getTasks().get(1).getInfo());
        assertEquals(TaskInfo.Status.TODO, getTasksResult.getTasks().get(1).getStatus());
        assertEquals(20, getTasksResult.getTasks().get(1).getPriority());
    }
}
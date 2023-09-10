package Connection.protocol.packages.taskOperations;

import Connection.protocol.packages.BasicPackageTestPrep;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskOperationsPackageTest extends BasicPackageTestPrep {

    @Test
    void acceptAddTask() {
        //given
        AddTaskPackage aPackage = new AddTaskPackage(null);
        prepare();
        Mockito.when(packageVisitor.handleAddTaskPackage(aPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(aPackage);

        //then
        Mockito.verify(packageVisitor).handleAddTaskPackage(aPackage, serverClient);
    }

    @Test
    void acceptAddUserTask(){
        //given
        AddUserTaskPackage aPackage = new AddUserTaskPackage(1, 1);
        prepare();
        Mockito.when(packageVisitor.handleAddUserTaskPackage(aPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(aPackage);

        //then
        Mockito.verify(packageVisitor).handleAddUserTaskPackage(aPackage, serverClient);
    }

    @Test
    void acceptGetTeamTasks(){
        //given
        GetTeamTasksPackage aPackage = new GetTeamTasksPackage(1);
        prepare();
        Mockito.when(packageVisitor.handleGetTeamTasksPackage(aPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(aPackage);

        //then
        Mockito.verify(packageVisitor).handleGetTeamTasksPackage(aPackage, serverClient);
    }

    @Test
    void acceptGetUserTasks(){
        //given
        GetUserTasksPackage aPackage = new GetUserTasksPackage(1);
        prepare();
        Mockito.when(packageVisitor.handleGetUserTasksPackage(aPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(aPackage);

        //then
        Mockito.verify(packageVisitor).handleGetUserTasksPackage(aPackage, serverClient);
    }

    @Test
    void acceptRemoveUserFromTask(){
        //given
        RemoveUserFromTaskPackage aPackage = new RemoveUserFromTaskPackage(1, 1);
        prepare();
        Mockito.when(packageVisitor.handleRemoveUserFromTaskPackage(aPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(aPackage);

        //then
        Mockito.verify(packageVisitor).handleRemoveUserFromTaskPackage(aPackage, serverClient);
    }

    @Test
    void acceptUpdateTask(){
        //given
        UpdateTaskPackage aPackage = new UpdateTaskPackage(null);
        prepare();
        Mockito.when(packageVisitor.handleUpdateTaskPackage(aPackage, serverClient)).thenReturn(respondInformation);

        //when
        basicTest(aPackage);

        //then
        Mockito.verify(packageVisitor).handleUpdateTaskPackage(aPackage, serverClient);
    }
}
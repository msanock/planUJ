package Connection.manager;

import Connection.protocol.RespondInformation;
import Connection.protocol.packages.taskOperations.*;
import serverConnection.abstraction.ServerClient;

public interface TaskOperationsPackageVisitor {
    RespondInformation handleAddTaskPackage(AddTaskPackage addTaskPackage, ServerClient sender);
    RespondInformation handleGetTeamTasksPackage(GetTeamTasksPackage getTasksPackage, ServerClient sender);
    RespondInformation handleGetUserTasksPackage(GetUserTasksPackage getUserTasksPackage, ServerClient sender);
    RespondInformation handleAddUserTaskPackage(AddUserTaskPackage updateTaskPackage, ServerClient sender);
    RespondInformation handleUpdateTaskPackage(UpdateTaskPackage updateTaskPackage, ServerClient sender);

    RespondInformation handleRemoveUserFromTaskPackage(RemoveUserFromTaskPackage removeUserFromTaskPackage, ServerClient sender);
}

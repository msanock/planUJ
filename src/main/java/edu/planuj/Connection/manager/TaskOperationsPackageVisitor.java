package edu.planuj.Connection.manager;

import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.taskOperations.*;
import edu.planuj.serverConnection.abstraction.ServerClient;

public interface TaskOperationsPackageVisitor {
    RespondInformation handleAddTaskPackage(AddTaskPackage addTaskPackage, ServerClient sender);
    RespondInformation handleGetTeamTasksPackage(GetTeamTasksPackage getTasksPackage, ServerClient sender);
    RespondInformation handleGetUserTasksPackage(GetUserTasksPackage getUserTasksPackage, ServerClient sender);
    RespondInformation handleAddUserTaskPackage(AddUserTaskPackage updateTaskPackage, ServerClient sender);
    RespondInformation handleUpdateTaskPackage(UpdateTaskPackage updateTaskPackage, ServerClient sender);

    RespondInformation handleRemoveUserFromTaskPackage(RemoveUserFromTaskPackage removeUserFromTaskPackage, ServerClient sender);
}

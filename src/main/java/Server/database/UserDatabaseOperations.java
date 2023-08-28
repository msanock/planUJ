package Server.database;

import Utils.OperationResults.GetUsersResult;
import Utils.OperationResults.IdResult;
import Utils.UserInfo;

public interface UserDatabaseOperations {
    IdResult addUser(UserInfo userInfo);
    GetUsersResult getUsers();
}

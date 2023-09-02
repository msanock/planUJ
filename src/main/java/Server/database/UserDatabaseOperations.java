package Server.database;

import Server.sql.DatabaseException;
import Utils.OperationResults.GetUsersResult;
import Utils.OperationResults.IdResult;
import Utils.UserInfo;

public interface UserDatabaseOperations {
    IdResult addUser(UserInfo userInfo) throws DatabaseException;
    GetUsersResult getUsers() throws DatabaseException;
}

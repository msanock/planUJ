package edu.planuj.Server.database;

import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.GetUsersResult;
import edu.planuj.Utils.OperationResults.IdResult;
import edu.planuj.Utils.UserInfo;

public interface UserDatabaseOperations {
    IdResult addUser(UserInfo userInfo) throws DatabaseException;
    GetUsersResult getUsers() throws DatabaseException;
}

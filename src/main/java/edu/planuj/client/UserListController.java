package edu.planuj.client;

import edu.planuj.Utils.UserInfo;

import java.util.Collection;

public interface UserListController {
    void addUser(UserInfo user);
    void deleteUser(UserInfo user);

    void cancel();
}

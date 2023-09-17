package edu.planuj.Utils;

import java.util.Objects;

public class UserInfo implements java.io.Serializable {
    String username;
    int id;

    public UserInfo(String username, int id){
        this.username = username;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserInfo)) return false;

        UserInfo userInfo = (UserInfo) o;

        if (id != userInfo.id) return false;
        return Objects.equals(username, userInfo.username);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected void setName(String name) {
        this.username = name;
    }
}

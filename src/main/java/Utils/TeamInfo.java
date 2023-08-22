package Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TeamInfo{
    private String name;
    private int id;
    private ArrayList<TeamUser> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<TeamUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<TeamUser> users) {
        this.users = users;
    }
}

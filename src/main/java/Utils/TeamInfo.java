package Utils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TeamInfo implements Serializable {
    private String name;
    private int id;
    private List<TeamUser> users;

    public TeamInfo(String name, int id, List<TeamUser> users){
        this.name = name;
        this.id = id;
        if(users == null){
            users = new ArrayList<>();
        }else {
            this.users = users;
        }
    }
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

    public List<TeamUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<TeamUser> users) {
        this.users = users;
    }
}

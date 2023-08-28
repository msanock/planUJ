package Utils;

public class UserInfo{
    String username;
    int id;

    public UserInfo(String username, int id){
        this.username = username;
        this.id = id;
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
}

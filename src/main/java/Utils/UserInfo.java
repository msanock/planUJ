package Utils;

public class UserInfo implements java.io.Serializable {
    //  TODO refactor it!
    //   UserInfo should only hold information that is being sent during login, id is kept only on server and isn't a part of a logic imo
    //   also information stored should
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

    protected void setName(String name) {
        this.username = name;
    }
}

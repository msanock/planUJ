package Utils;

public class TeamUser extends UserInfo{
    private String role;
    private String position;

    public TeamUser(String username, int id, String role, String position){
        super(username, id);
        this.role = role;
        this.position = position;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

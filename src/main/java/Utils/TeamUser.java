package Utils;

public class TeamUser extends UserInfo {

    public static enum Role{
        ADMIN,
        MEMBER
    }

    private Role role;
    private String position;

    public TeamUser(String username, int id, Role role, String position){
        super(username, id);
        this.role = role;
        this.position = position;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

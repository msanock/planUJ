package edu.planuj.Utils;

public class TeamUser extends UserInfo implements java.io.Serializable{

    public static enum Role{
        ADMIN,
        MEMBER
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private Role role;
    private String position;

    public TeamUser(String username, int id, Role role, String position){
        super(username, id);
        this.role = role;
        this.position = position;
    }

    public TeamUser(UserInfo userInfo, Role role, String position){
        super(userInfo.getUsername(), userInfo.getId());
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

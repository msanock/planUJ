package edu.planuj.client;

import edu.planuj.Utils.TeamUser;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Collection;
import java.util.HashMap;

public class MembersView extends VBox {

    HashMap<TeamUser, SingleMemberView> members;


    public MembersView() {
        members = new HashMap<>();

    }




    public void setMembers(Collection<TeamUser> members) {
        this.members.clear();
        this.getChildren().clear();
        for (var member : members) {
            SingleMemberView newMember = new SingleMemberView(member);
            this.members.put(member, newMember);
            this.getChildren().add(newMember);
        }

    }

    public void addMember(TeamUser member) {
        if (!this.members.containsKey(member)) {
            SingleMemberView newMember = new SingleMemberView(member);
            this.members.put(member, newMember);
            this.getChildren().add(newMember);
        }
    }

    public void deleteMember(String member) {
        this.getChildren().remove(members.remove(member));
    }


}

class SingleMemberView extends Button {
    TeamUser member;
    SingleMemberView(TeamUser member) {
        this.member = member;
        //this.setGraphic("jakaś grafika );
        this.setMinHeight(60);
        this.setMinWidth(20);
        this.autosize();
        this.setText(member.getUsername());
        this.setFont(new Font(50)); // TODO set font family, and bold
        //this.setTextOverrun(OverrunStyle.CLIP);
        this.setWrapText(false);
        this.setPadding(new Insets(7));

        this.setTextFill(Color.AQUA);
    }
}

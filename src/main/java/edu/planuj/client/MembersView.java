package edu.planuj.client;

import edu.planuj.Utils.TeamUser;
import edu.planuj.Utils.UserInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Collection;
import java.util.HashMap;

public class MembersView extends VBox {
    UserListController observer;

    HashMap<UserInfo, SingleMemberView> members;


    class SingleMemberView extends Button {
        TeamUser member;
        boolean isIncluded;

        EventHandler<ActionEvent> handleOnAction = (event -> {
            if (observer != null) {
                if (isIncluded) {
                    markExcluded();
                    observer.deleteUser(member);
                }else {
                    markIncluded();
                    observer.addUser(member);
                }
            }
        });

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
            this.setOnAction(handleOnAction);
            this.setTextFill(Color.AQUA);
        }
        public void markExcluded() {
            isIncluded = false;
            this.setTextFill(Color.GREEN);
        }

        public void markIncluded() {
            isIncluded = true;
            this.setTextFill(Color.RED);
        }

        public void unmark() {
            this.setTextFill(Color.AQUA);
        }
    }


    public MembersView() {
        members = new HashMap<>();
        observer = null;
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

    public void markMembers(Collection<UserInfo> toMark, UserListController controller) {
        if (observer != null)
            observer.cancel();

        observer = controller;
        members.values().forEach(SingleMemberView::markExcluded);

        toMark.forEach((m) -> members.get(m).markIncluded());
    }

    public void unmarkAll() {
        members.values().forEach(SingleMemberView::unmark);
        observer = null;
    }

    public void deleteMember(String member) {
        this.getChildren().remove(members.remove(member));
    }


}



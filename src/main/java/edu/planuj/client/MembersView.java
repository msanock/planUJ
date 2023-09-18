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

    AddNewMemberSection addNewMemberSection;
    boolean clickable;


    class SingleMemberView extends Button {
        UserInfo member;
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

        SingleMemberView(UserInfo member) {
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

        public void setNotClickable(){
            this.setDisable(true);
        }

        public void setClickable(){
            this.setDisable(false);
        }

        public void unmark() {
            this.setTextFill(Color.AQUA);
        }
    }


    public MembersView() {
        members = new HashMap<>();
        observer = null;
        addNewMemberSection = new AddNewMemberSection();
        clickable = false;
    }




    public void setMembers(Collection<TeamUser> members) {
        this.members.clear();
        this.getChildren().clear();
        this.getChildren().add(addNewMemberSection);
        for (var member : members) {
            SingleMemberView newMember = new SingleMemberView(member);
            this.members.put(member, newMember);
            this.getChildren().add(newMember);
        }
        unmarkAll();
    }

    public void addMember(TeamUser member) {
        if (!this.members.containsKey(member)) {
            SingleMemberView newMember = new SingleMemberView(member);
            this.members.put(member, newMember);
            this.getChildren().add(newMember);
        }
    }

    public void markMembers(Collection<? extends UserInfo> toMark, UserListController controller) {
        members.values().forEach(SingleMemberView::setClickable);
        clickable = true;
        if (observer != null)
            observer.cancel();

        observer = controller;
        members.values().forEach(SingleMemberView::markExcluded);

        toMark.forEach((m) -> members.get(m).markIncluded());
    }
    public void action(Collection<? extends UserInfo> toMark, UserListController controller, boolean isClicked){
        if(!isClicked){
            unmarkAll();
        }else{
            markMembers(toMark, controller);
        }
    }

    public void unmarkAll() {
        members.values().forEach(SingleMemberView::unmark);
        members.values().forEach(SingleMemberView::setNotClickable);
        observer = null;
        clickable = false;
    }

    public void deleteMember(String member) {
        this.getChildren().remove(members.remove(member));
    }


}



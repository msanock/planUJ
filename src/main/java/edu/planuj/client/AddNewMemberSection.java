package edu.planuj.client;

import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;
import edu.planuj.Utils.UserInfo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Collection;
import java.util.List;

public class AddNewMemberSection extends VBox {
    Button addButton;
    Button discardButton;
    SearchableComboBox<UserInfo> users;

    HBox buttons;

    boolean isButtonClicked;


    AddNewMemberSection() {
        buttons = new HBox(5.0);
        addButton = new Button("ADD NEW TEAM MEMBER");
        discardButton = new Button("CANCEL");
        buttons.alignmentProperty().set(Pos.CENTER);
        VBox.setVgrow(buttons, Priority.ALWAYS);


        addButton.setGraphic(new FontIcon("bi-plus:20:BLUE"));
        discardButton.setGraphic(new FontIcon("bi-dash:20:RED"));
        isButtonClicked = false;
        fillWidthProperty().set(false);

        users = new SearchableComboBox<>();
        users.setPromptText("user");

        buttons.getChildren().add(addButton);
        this.getChildren().add(buttons);

        addButton.setOnAction(event -> {
            if (isButtonClicked) {
                //add new task
                UserInfo chosen = users.getValue();

                if (chosen == null)
                    return;

                TeamUser newMember = new TeamUser(chosen, TeamUser.Role.MEMBER, "");

                if (!AppHandler.getInstance().AddUserToTeam(newMember))
                    return;


                AppHandler.getInstance().updateTeamUsers();
                isButtonClicked = false;
                this.getChildren().remove(users);
                buttons.getChildren().remove(discardButton);
            } else {
                Collection<UserInfo> listOfUsers =  AppHandler.getInstance().getUsers();
                users.getItems().addAll(listOfUsers);
                this.getChildren().add(0, users);
                isButtonClicked = true;
                buttons.getChildren().add(0, discardButton);
            }
        });

        discardButton.setOnAction((event) -> {
            isButtonClicked = false;
            this.getChildren().remove(users);
            buttons.getChildren().remove(discardButton);
        });
    }


}

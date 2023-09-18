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
    SearchableComboBox<UserInfo> usersList;

    AddNewMemberSection() {
        addButton = new Button("ADD NEW TEAM MEMBER");

        addButton.setGraphic(new FontIcon("bi-plus:20:BLUE"));
        fillWidthProperty().set(false);

        usersList = new SearchableComboBox<>();
        usersList.setPromptText("user");

        Collection<UserInfo> listOfUsers =  AppHandler.getInstance().getUsers();
        usersList.getItems().clear();
        usersList.getItems().addAll(listOfUsers);

        this.getChildren().add(usersList);
        this.getChildren().add(addButton);

        addButton.setOnAction(event -> {
            UserInfo chosen = usersList.getValue();

            if (chosen == null)
                return;

            TeamUser newMember = new TeamUser(chosen, TeamUser.Role.MEMBER, "");

            if (! AppHandler.getInstance().AddUserToTeam(newMember)) {
                return;
            }

            AppHandler.getInstance().updateTeamUsers();
        });
    }


}
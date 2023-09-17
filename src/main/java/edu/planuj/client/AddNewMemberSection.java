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
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class AddNewMemberSection extends VBox {
    Button addButton;
    Button discardButton;
    TextField teamName;

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

        teamName = new TextField();
        teamName.setPromptText("New Member Name");

        buttons.getChildren().add(addButton);
        this.getChildren().add(buttons);

        addButton.setOnAction(event -> {
            if (isButtonClicked) {
                //add new task

                int id = 0;
                try {
                    id = Integer.parseInt(teamName.getText());
                } catch (NumberFormatException e) {
                    return;
                }
                if (id < 0)
                    return;

                TeamUser newMember = new TeamUser("", id, TeamUser.Role.MEMBER, "");

                if (!AppHandler.getInstance().AddUserToTeam(newMember))
                    return;


                AppHandler.getInstance().updateTeamUsers();
                isButtonClicked = false;
                this.getChildren().remove(teamName);
                buttons.getChildren().remove(discardButton);
            } else {
                this.getChildren().add(0, teamName);
                isButtonClicked = true;
                buttons.getChildren().add(0, discardButton);
            }
        });

        discardButton.setOnAction((event) -> {
            isButtonClicked = false;
            this.getChildren().remove(teamName);
            buttons.getChildren().remove(discardButton);
        });
    }


}

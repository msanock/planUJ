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

public class AddNewTeamSection extends VBox {
    Button addButton;
    Button discardButton;
    TextField teamName;

    HBox buttons;

    boolean isButtonClicked;


    AddNewTeamSection() {
        buttons = new HBox(5.0);
        addButton = new Button("ADD NEW TEAM");
        discardButton = new Button("DISCARD");
        buttons.alignmentProperty().set(Pos.CENTER);
        VBox.setVgrow(buttons, Priority.ALWAYS);


        addButton.setGraphic(new FontIcon("bi-plus:20:BLUE"));
        discardButton.setGraphic(new FontIcon("bi-dash:20:RED"));
        isButtonClicked = false;
        fillWidthProperty().set(false);

        teamName = new TextField();
        teamName.setPromptText("New Team name");

        buttons.getChildren().add(addButton);
        this.getChildren().add(buttons);

        addButton.setOnAction(event -> {
            if (isButtonClicked) {
                //add new task
                String newName = teamName.getText();
                if (newName.isBlank())
                    return;

                TeamInfo newTeam = new TeamInfo(newName, -1, List.of(new TeamUser(new UserInfo(ClientInformation.getInstance().getUsername(), ClientInformation.getInstance().getId()), TeamUser.Role.ADMIN, "")));
                if (!AppHandler.getInstance().addNewTeam(newTeam))
                    return;


                MainScreenController.getInstance().teamsView.addTeam(newTeam);
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

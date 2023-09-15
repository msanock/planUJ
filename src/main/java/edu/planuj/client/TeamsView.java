package edu.planuj.client;

import edu.planuj.Utils.TeamInfo;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Collection;
import java.util.HashMap;

public class TeamsView extends HBox {
    HashMap<TeamInfo, SingleTeamView> teams;
    VBox teamList;

    public TeamsView() {

        teams = new HashMap<>();
        //this.setBackground(new Background(new BackgroundFill(Color.BLACK,new CornerRadii(10), new Insets(0))));

        teamList = new TeamList();
        Pane rest = new AnchorPane();
        HBox.setHgrow(rest, Priority.ALWAYS);
        rest.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        //this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().add(teamList);
        this.getChildren().add(rest);
        rest.setOnMouseClicked(this::onClickOutside);

    }

    private void onClickOutside(MouseEvent mouseEvent) {
        MainScreenController.getInstance().closeTeams();
    }


    public void setTeams(Collection<TeamInfo> newTeams) {
        this.teams.clear();
        teamList.getChildren().clear();
        for (var team : newTeams) {
            SingleTeamView newTeam = new SingleTeamView(team);
            this.teams.put(team, newTeam);
            teamList.getChildren().add(newTeam);
        }
    }

    public void addTeam(TeamInfo team) {
        SingleTeamView newTeam = new SingleTeamView(team);
        this.teams.put(team, newTeam);
        teamList.getChildren().add(newTeam);
    }

    public void deleteTeam(String team) {
        teamList.getChildren().remove(teams.remove(team));
    }


}

class TeamList extends VBox {
    TeamList() {
        this.setFillWidth(true);
        this.prefWidth(500);
        this.minWidth(400);
        this.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));

    }
}


class SingleTeamView extends Button {
    TeamInfo team;
    SingleTeamView(TeamInfo team) {
        this.team = team;
        //this.setGraphic("jakaś grafika );
        this.setMinHeight(60);
        this.setMinWidth(20);
        this.autosize();
        this.setText(team.getName());
        this.setFont(new Font(50)); // TODO set font family, and bold
        //this.setTextOverrun(OverrunStyle.CLIP);
        this.setWrapText(false);
        this.setPadding(new Insets(7));
        this.setOnAction(this::handleTeamButton);

        this.setTextFill(Color.AQUA);
    }


    void handleTeamButton(ActionEvent event) {
        AppHandler.getInstance().setTeam(team);
    }
}

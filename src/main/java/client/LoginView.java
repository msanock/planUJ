package client;

import Server.sql.DatabaseException;
import Utils.OperationResults.IdResult;
import Utils.UserInfo;
import client.ClientInformation;
import client.MainScreen;
import client.RealApplication;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginView implements Initializable {


    public TextField textField;
    private boolean exitable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitable = false;
    }

    public void setExitable(boolean exitable) {
        this.exitable = exitable;
    }
    public boolean getExitable() {
        return exitable;
    }


    public void handleLogInButton(ActionEvent actionEvent) {
        String login = textField.getText().trim();

        AppHandler.getInstance().tryNewLogIn(login);


    }
}

package edu.planuj.client;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
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


    public void handleLogInButton(ActionEvent actionEvent) throws IOException {
        String login = textField.getText().trim();

        if(!AppHandler.getInstance().tryNewLogIn(login)) {
            //TODO: report something is wrong;

        }
    }
}

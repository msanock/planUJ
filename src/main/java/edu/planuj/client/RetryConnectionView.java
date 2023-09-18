package edu.planuj.client;

import edu.planuj.clientConnection.ClientConnectionFactory;
import javafx.event.ActionEvent;

public class RetryConnectionView {
    public void retryConnectionButton(ActionEvent actionEvent) {
        RealApplication.retryConnection();
    }
}

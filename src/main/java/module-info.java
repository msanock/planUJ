module planuj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;


    exports edu.planuj.client;
    opens edu.planuj.client to javafx.fxml;
}
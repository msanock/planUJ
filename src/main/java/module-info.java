module edu.planuj.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.controlsfx.controls;


    exports edu.planuj.client;
    opens edu.planuj.client to javafx.fxml;
}
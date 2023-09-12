module client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.pixelduke.fxskins;
    requires java.logging;
    requires java.sql;
    requires java.xml.crypto;
//    requires eu.hansolo.tilesfx;

    opens client to javafx.fxml;
    exports client;
}
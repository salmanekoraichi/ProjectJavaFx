module server.projectfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens server.projectfinal to javafx.fxml;
    exports server.projectfinal;
}
module server.projectfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;
    requires java.sql.rowset;

    opens server.projectfinal to javafx.fxml;
    exports server.projectfinal;


    opens server.projectfinal.Controllers to javafx.fxml; // Opens Controllers package to FXML
    exports server.projectfinal.Controllers; // Export the Controllers package

}
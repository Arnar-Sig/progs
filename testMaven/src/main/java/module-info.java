module com.example.testmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.testmaven to javafx.fxml;
    exports com.example.testmaven;
}
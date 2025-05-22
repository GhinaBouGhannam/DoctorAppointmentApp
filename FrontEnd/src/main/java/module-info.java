module com.example.midtermproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires android.json;


    opens com.example.midtermproject to javafx.fxml;
    exports com.example.midtermproject;
}
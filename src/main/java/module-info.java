module com.example.act2_sisa2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.act2_sisa2 to javafx.fxml;
    exports com.example.act2_sisa2;
}
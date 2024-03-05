module com.example.socialmediastatistic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.socialmediastatistic to javafx.fxml;
    exports com.example.socialmediastatistic;
}
module com.example.diamondcircle {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.diamondcircle to javafx.fxml;
    exports com.example.diamondcircle;
}
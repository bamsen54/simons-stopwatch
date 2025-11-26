module com.simon.simonsstopwatch {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens com.simon.simonsstopwatch to javafx.fxml;
    exports com.simon.simonsstopwatch;
}
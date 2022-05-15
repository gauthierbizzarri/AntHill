module ant.ants {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ant.ants to javafx.fxml;
    exports ant.ants;
}
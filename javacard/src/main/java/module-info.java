module fr.afpa {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens fr.afpa to javafx.fxml;
    opens fr.afpa.controllers to javafx.fxml;

    exports fr.afpa;
    exports fr.afpa.controllers;
    exports fr.afpa.models;
}

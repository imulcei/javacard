module fr.afpa {
    requires transitive javafx.controls;
    requires javafx.fxml;

    opens fr.afpa to javafx.fxml;

    exports fr.afpa;
    exports fr.afpa.controllers;
}

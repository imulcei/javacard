module fr.afpa {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.graphics;

    opens fr.afpa to javafx.fxml, com.google.zxing, com.google.zxing.javase;
    opens fr.afpa.controllers to javafx.fxml;

    exports fr.afpa;
    exports fr.afpa.controllers;
    exports fr.afpa.models;
}

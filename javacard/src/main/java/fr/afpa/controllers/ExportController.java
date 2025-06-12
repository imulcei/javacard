package fr.afpa.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExportController {
    @FXML
    private CheckBox csvCheckBox, jsonCheckBox, vCardCheckBox;
    @FXML
    private Button exportContactsButton, cancelExportButton;
    @FXML
    private VBox exportVBox;

    @FXML
    public void initialize() {
        // Logique spécifique à l'export
        if (cancelExportButton != null) {
            cancelExportButton.setOnAction(event -> closePopup());
        }
    }

    private void closePopup() {
        Stage stage = (Stage) exportVBox.getScene().getWindow();
        stage.close();
    }
}

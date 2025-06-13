package fr.afpa.controllers;

import fr.afpa.models.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExportController {
    @FXML
    private RadioButton csvRadioButton, jsonRadioButton, vCardRadioButton;
    @FXML
    private Button exportContactsButton, cancelExportButton;
    @FXML
    private VBox exportVBox;
    private Contact contact;

    public void setContact(Contact contact) {
        this.contact = contact;
        // Optionnel : mettre à jour l'UI avec les infos du contact si besoin
    }
    
    @FXML
    public void initialize() {
        // Logique spécifique à l'export
        if (cancelExportButton != null) {
            cancelExportButton.setOnAction(event -> closePopup());
        }
    }

    private void exportToCSV() {
        System.out.println("Export CSV : " + contact);
        // TODO: implémenter export CSV
    }

    private void exportToJSON() {
        System.out.println("Export JSON : " + contact);
        // TODO: implémenter export JSON
    }

    private void exportToVCard() {
        System.out.println("Export vCard : " + contact);
        // TODO: implémenter export vCard
    }

    private void closePopup() {
        Stage stage = (Stage) exportVBox.getScene().getWindow();
        stage.close();
    }
}

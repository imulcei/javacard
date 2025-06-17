package fr.afpa.controllers;

import java.util.ArrayList;

import fr.afpa.models.Contact;
import fr.afpa.serializers.VCardSerializer;
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
    private ArrayList<Contact> contacts;

    public void setContact(Contact contact) {
        this.contact = contact;
        // Optionnel : mettre à jour l'UI avec les infos du contact si besoin
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        this.contact = null;
    }

    @FXML
    public void initialize() {
        // Logique spécifique à l'export
        if (csvRadioButton != null) {
            exportContactsButton.setOnAction(event -> exportToCSV());
        }
        if (jsonRadioButton != null) {
            exportContactsButton.setOnAction(event -> exportToJSON());
        }
        if (vCardRadioButton != null) {
            exportContactsButton.setOnAction(event -> exportToVCard());
        }
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

        VCardSerializer vCardSerializer = new VCardSerializer();

        if (contact != null) {
            System.out.println("Export vCard : " + contact);
            String fileName = (contact.getFirstName() + "_" + contact.getLastName()).replaceAll("[^a-zA-Z0-9]", "_")
                    + ".vcf";
            vCardSerializer.saveOne(fileName, contact);
            System.out.println("Contact exporté en .vcf");
        } else if (contacts != null && !contacts.isEmpty()) {
            System.out.println("Export vCard : " + contacts.size() + contacts);
            String fileName = "contacts_export.vcf";
            vCardSerializer.saveAll(fileName, contacts);
            System.out.println("Contacts exportés en .vcf");
        } else {
            System.out.println("Aucun contact à exporter");
            return;
        }
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) exportVBox.getScene().getWindow();
        stage.close();
    }
}

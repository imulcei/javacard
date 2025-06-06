package fr.afpa.controllers;

import java.util.ArrayList;
import java.util.List;

import fr.afpa.models.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ContactController {
    // variables du formulaire
    @FXML
    private TextField firstNameTextField, lastNameTextField,
            emailTextField, personalPhoneNumTextField, genderTextField,
            birthdayTextField, addressTextField, proPhoneNumTextField,
            pseudoTextField, githubTextField;
    // boutons du formulaire
    @FXML
    private Button sendFormButton, cancelButton;
    // variables fiche Contact
    @FXML
    private Text contactName, emailField, genderField, addressField,
            pseudoField, personalPhoneNumField, birthdayField, proPhoneNumField,
            githubField;
    // boutons fiche Contact
    @FXML
    private Button modifyButton, qrCodeButton;
    // variables liste de contacts
    // search bar
    @FXML
    private TextField textFieldSearchBar;
    @FXML
    private ImageView iconSearchBar;
    // list
    @FXML
    private ListView<Contact> listViewContacts;
    // boutons
    @FXML
    private Button addButton, deleteButton, exportButton;

    // variables pop-up Supprimer
    @FXML
    private Button deleteYesButton, deleteNoButton;

    // variables pop-up Exporter
    @FXML
    private CheckBox csvCheckBox, jsonCheckBox, vCardCheckBox;
    @FXML
    private Button exportContactsButton, cancelExportButton;

    // variables QR Code
    @FXML
    private Button quitQrCodeButton;

    private ArrayList<Contact> contactsList;

    public ContactController() {
        // this.contactsList = new ArrayList<Contact>();
    }

    @FXML
    public void initialize() {
        
    }

    /**
     * Créer un nouveau contact
     */
    public void addContact() {
        // récupérer les données entrées dans le form
        // créer un new Contact avec les données
    }

    /**
     * Afficher les infos des contacts
     */
    public void getContact() {

    }

    /**
     * Modifier un contact
     */
    public void updateContact() {

    }

    /**
     * Supprimer un ou plusieurs contacts
     */
    public void deleteContact() {

    }

    /**
     * Créer un QR Code pour un contact
     */
    public void createQrCode() {

    }

    public ArrayList<Contact> getContactsList() {
        return contactsList;
    }

    public void setContactsList(ArrayList<Contact> contactsList) {
        this.contactsList = contactsList;
    }

}

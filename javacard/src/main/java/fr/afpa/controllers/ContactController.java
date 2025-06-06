package fr.afpa.controllers;

import java.util.ArrayList;

import fr.afpa.models.Contact;
import fr.afpa.models.Gender;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ContactController {
    // variables du formulaire
    @FXML
    private TextField firstNameTextField, lastNameTextField,
            emailTextField, personalPhoneNumTextField,
            birthdayTextField, addressTextField, proPhoneNumTextField,
            pseudoTextField, githubTextField;
    @FXML
    private ComboBox<Gender> genderComboBox;
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
        genderComboBox.getItems().setAll(Gender.values());
    }

    /**
     * Créer un nouveau contact
     */
    public void addContact() {
        // récupérer les données entrées dans le form
        // créer un new Contact avec les données
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String emailString = emailTextField.getText();
        String persoPhoneNum = personalPhoneNumTextField.getText();
        String birthday = birthdayTextField.getText();
        String proPhoneNum = proPhoneNumTextField.getText();
        Gender gender = genderComboBox.getValue();
        String address = addressTextField.getText();
        String pseudo = pseudoTextField.getText();
        String github = githubTextField.getText();

        Contact newContact = new Contact(firstName, lastName, gender, birthday, pseudo, address, persoPhoneNum,
                proPhoneNum, emailString, github);
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

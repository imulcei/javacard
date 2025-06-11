package fr.afpa.controllers;

import java.util.ArrayList;
import java.util.Optional;

import fr.afpa.models.Contact;
import fr.afpa.models.Gender;
import fr.afpa.tools.ContactChecker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContactController {
    @FXML
    GridPane gridPaneContent;

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

    private String messageStatut = "";

    // variables QR Code
    @FXML
    private Button quitQrCodeButton;

    private ArrayList<Contact> contactsList;

    public ContactController() {
        // this.contactsList = new ArrayList<Contact>();
    }

    @FXML
    public void initialize() {
        if (addButton != null) {
            addButton.setOnAction(event -> addContact());
        }
        if (deleteButton != null) {
            deleteButton.setOnAction(event -> showPopUpDelete());
        }
        if (modifyButton != null) {
            modifyButton.setOnAction(event -> updateContact());
        }

        // Initialisation de la liste
        contactsList = new ArrayList<>();

        // Exemple: ajouter quelques contacts de test
        contactsList.add(new Contact("Alice", "Dupont", Gender.FEMME, "01/01/1990",
                "Ali", "Paris", "0123456789", "0987654321",
                "alice@example.com", "https://github.com/alice"));
        contactsList.add(new Contact("Bob", "Martin", Gender.HOMME, "02/02/1985",
                "Bobby", "Lyon", "0234567890", "0876543210",
                "bob@example.com", "https://github.com/bob"));

        if (listViewContacts != null) {
            listViewContacts.getItems().setAll(contactsList);

            // Configuration du listener pour la sélection
            listViewContacts.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        getContact();
                        if (newValue != null) {
                            modifyButton.setDisable(false);
                            qrCodeButton.setDisable(false);
                        } else {
                            modifyButton.setDisable(true);
                            qrCodeButton.setDisable(true);
                        }
                    });
        }
    }

    /**
     * Créer un nouveau contact
     */
    public void addContact() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
            GridPane formRoot = loader.load();
            FormController formController = loader.getController();
            Stage formStage = new Stage();
            Scene scene = new Scene(formRoot);
            formStage.setScene(scene);
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.setResizable(false);

            formController.setOnSendAction(() -> {
                Contact newContact = formController.createContact();
                if (newContact != null) {
                    contactsList.add(newContact);
                    listViewContacts.getItems().setAll(contactsList);
                    formStage.close();
                }
            });

            formController.setOnCancelAction(() -> {
                formStage.close();
            });

            formStage.showAndWait();

        } catch (Exception e) {
            System.out.println("Erreur chargement du formulaire.");
        }
    }

    /**
     * Afficher les infos des contacts
     */
    public void getContact() {

        // Cette méthode devrait être appelée lorsque l'utilisateur sélectionne un
        // contact
        // dans la liste des contacts. Elle mettra à jour les champs de la fiche contact
        // avec les informations du contact sélectionné.
        Contact selectedContact = listViewContacts.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
            contactName.setText(selectedContact.getFirstName() + " " + selectedContact.getLastName());
            emailField.setText(selectedContact.getEmail());
            genderField.setText(selectedContact.getGender().toString());
            addressField.setText(selectedContact.getAddress());
            pseudoField.setText(selectedContact.getPseudo());
            personalPhoneNumField.setText(selectedContact.getPersoPhoneNum());
            birthdayField.setText(selectedContact.getBirthday());
            proPhoneNumField.setText(selectedContact.getProPhoneNum());
            githubField.setText(selectedContact.getGithubPage());
        } else {
            // Si aucun contact n'est sélectionné, vider les champs
            contactName.setText("");
            emailField.setText("");
            genderField.setText("");
            addressField.setText("");
            pseudoField.setText("");
            personalPhoneNumField.setText("");
            birthdayField.setText("");
            proPhoneNumField.setText("");
            githubField.setText("");
        }

        // Vous pouvez également mettre à jour l'état des boutons (par exemple, activer
        // le bouton de modification)
        modifyButton.setDisable(selectedContact == null);
        qrCodeButton.setDisable(selectedContact == null);
    }

    /**
     * Modifier un contact
     */
    public void updateContact() {

    }

    /**
     * Afficher la popup Supprimer un contact
     */
    public void showPopUpDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce contact ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteContact();
        }
    }

    /**
     * Supprimer un ou plusieurs contacts
     */
    public void deleteContact() {
        ObservableList<Contact> selectedContacts = listViewContacts.getSelectionModel().getSelectedItems();
        if (selectedContacts == null || selectedContacts.isEmpty()) {
            return;
        }
        contactsList.removeAll(selectedContacts);
        listViewContacts.getItems().removeAll(selectedContacts);
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

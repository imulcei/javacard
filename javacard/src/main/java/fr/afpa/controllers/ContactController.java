package fr.afpa.controllers;

import java.util.ArrayList;

import fr.afpa.models.Contact;
import fr.afpa.models.Gender;
import fr.afpa.tools.ContactChecker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContactController {
    // variables du formulaire
    @FXML
    private TextField firstNameTextField, lastNameTextField,
            emailTextField, personalPhoneNumTextField,
            birthdayTextField, addressTextField, proPhoneNumTextField,
            pseudoTextField, githubTextField;
    @FXML
    private ComboBox<Gender> genderComboBox;

    @FXML
    private TextArea errorTextArea;

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
        if (genderComboBox != null) {
            genderComboBox.getItems().setAll(Gender.values());
        }
        if (addButton != null) {
            addButton.setOnAction(event -> addContact());
        }
        if (deleteButton != null) {
            deleteButton.setOnAction(event -> deleteContact());
        }

        // Initialiser la liste contacts
        contactsList = new ArrayList<>();

        // Exemple: ajouter quelques contacts de test
        contactsList.add(new Contact("Alice", "Dupont", Gender.FEMME, "01/01/1990",
                                     "Ali", "Paris", "0123456789", "0987654321",
                                     "alice@example.com", "https://github.com/alice"));
        contactsList.add(new Contact("Bob", "Martin", Gender.HOMME, "02/02/1985",
                                     "Bobby", "Lyon", "0234567890", "0876543210",
                                     "bob@example.com", "https://github.com/bob"));

        // Charger la liste dans la ListView
        listViewContacts.getItems().setAll(contactsList);

        // Désactiver les boutons au départ
        modifyButton.setDisable(true);
        qrCodeButton.setDisable(true);

        // Ajouter un listener sur la sélection
        listViewContacts.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> getContact()
        );  
        
    }

    /**
     * Créer un nouveau contact
     */
    public void addContact() {
        try {
            Stage formStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            formStage.setScene(scene);
            formStage.initModality(Modality.APPLICATION_MODAL);

            sendFormButton.setOnAction(e -> {
                try {
                    messageStatut = "";
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

                    // Vérifier les données entrées

                    // Utiliser ContactChecker pour valider les données
                    if (!ContactChecker.isNomPrenomValid(firstName)) {
                        messageStatut = messageStatut + "Prénom invalide\n";
                    }
                    if (!ContactChecker.isNomPrenomValid(lastName)) {
                        messageStatut = messageStatut + "Nom invalide\n";
                    }
                    if (!ContactChecker.isEmailValid(emailString)) {
                        messageStatut = messageStatut + "Email invalide\n";
                    }
                    if (!ContactChecker.isNumero(persoPhoneNum)) {
                        messageStatut = messageStatut + "Numéro de téléphone personnel invalide\n";
                    }
                    if (!ContactChecker.isNumero(proPhoneNum)) {
                        messageStatut = messageStatut + "Numéro de téléphone professionnel invalide\n";
                    }
                    if (!ContactChecker.isDateValid(birthday)) {
                        messageStatut = messageStatut + "Date de naissance invalide\n";
                    }
                    if (!ContactChecker.isAdresse(address)) {
                        messageStatut = messageStatut + "Adresse invalide\n";
                    }
                    if (!ContactChecker.isPseudoValid(pseudo)) {
                        messageStatut = messageStatut + "Pseudo invalide\n";
                    }
                    if (!ContactChecker.isGithubValid(github)) {
                        messageStatut = messageStatut + "GitHub invalide\n";
                    }

                    if (!messageStatut.isEmpty()) {
                        // Afficher les erreurs dans la zone de texte d'erreur
                        errorTextArea.setText(messageStatut);
                        return; // Sortir de la méthode si des erreurs sont présentes
                    }

                    // Si toutes les vérifications sont passées, créer le contact
                    // Créer un nouveau contact
                    Contact newContact = new Contact(firstName, lastName, gender, birthday, pseudo, address,
                            persoPhoneNum,
                            proPhoneNum, emailString, github);

                    // Ajouter le contact à la liste des contacts
                    if (contactsList == null) {
                        contactsList = new ArrayList<>();
                    }
                    contactsList.add(newContact);

                    // Affirmation de la création du contact
                    messageStatut = "Contact créé avec succès : " + newContact.getFirstName() + " "
                            + newContact.getLastName();
                    // Réinitialiser le formulaire
                    clearForm();
                } catch (Exception ex) {
                    System.out.println("Erreur dans l'ajout");
                }
            });

            cancelButton.setOnAction(e -> {
                clearForm();
                formStage.close();
            });

            formStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Erreur chargement du formulaire.");
        }
    }

    private void clearForm() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        personalPhoneNumTextField.clear();
        birthdayTextField.clear();
        proPhoneNumTextField.clear();
        addressTextField.clear();
        pseudoTextField.clear();
        githubTextField.clear();
        genderComboBox.setValue(null);
        // errorTextArea.clear();
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
     * Supprimer un ou plusieurs contacts
     */
    public void deleteContact() {
        if (listViewContacts == null || deleteYesButton == null || deleteNoButton == null) {
            System.err.println("Les composants ne sont pas initialisés");
            return;
        }
        ObservableList<Contact> selectedContacts = listViewContacts.getSelectionModel().getSelectedItems();
        if (selectedContacts == null || selectedContacts.isEmpty()) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/afpa/delete-popup.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.setResizable(false);

            // Gestion du bouton confirmer la suppression
            deleteYesButton.setOnAction(event -> {
                contactsList.removeAll(selectedContacts);
                listViewContacts.getItems().removeAll(selectedContacts);
                popupStage.close();
            });

            // Gestion du bouton annuler la suppression
            deleteNoButton.setOnAction(event -> {
                popupStage.close();
            });

            // Affiche la pop-up
            popupStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Erreur.");
        }
        // Création d'une nouvelle liste sans les contacts à supprimer
        ArrayList<Contact> newContactsList = new ArrayList<>(contactsList);

        // gestion du bouton supprimer
        newContactsList.removeAll(selectedContacts);

        contactsList = newContactsList;
        listViewContacts.getItems().clear();
        listViewContacts.getItems().addAll(contactsList);
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

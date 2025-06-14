package fr.afpa.controllers;

import fr.afpa.models.Contact;
import fr.afpa.models.Gender;
import fr.afpa.tools.ContactChecker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FormController {

    /**
     * Récupération d'une référence vers le conteneur parent de façon à pouvoir
     * fermer le fenêtre.
     */
    @FXML
    private GridPane formGridPane;

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

    private ObservableList<Contact> contacts;
    private Contact contact;

    @FXML
    public void initialize() {
        if (genderComboBox != null) {
            genderComboBox.getItems().setAll(Gender.values());
        }
    }

    /**
     * Gère la création d'un contact.
     * 
     * @return Un nouveau contact.
     */
    public Contact createContact() {
        String messageStatut = "";

        // Récupérer les données du formulaire
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String persoPhoneNum = personalPhoneNumTextField.getText().trim();
        String birthday = birthdayTextField.getText().trim();
        String proPhoneNum = proPhoneNumTextField.getText().trim();
        Gender gender = genderComboBox.getValue();
        String address = addressTextField.getText().trim();
        String pseudo = pseudoTextField.getText().trim();
        String github = githubTextField.getText().trim();

        // Vérifier les données
        if (!ContactChecker.isNomPrenomValid(firstName)) {
            messageStatut += "Prénom invalide\n";
        }
        if (!ContactChecker.isNomPrenomValid(lastName)) {
            messageStatut += "Nom invalide\n";
        }
        if (!ContactChecker.isEmailValid(email)) {
            messageStatut += "Email invalide\n";
        }
        if (!ContactChecker.isNumero(persoPhoneNum)) {
            messageStatut += "Numéro de téléphone personnel invalide\n";
        }
        if (!ContactChecker.isNumero(proPhoneNum)) {
            messageStatut += "Numéro de téléphone professionnel invalide\n";
        }
        if (!ContactChecker.isDateValid(birthday)) {
            messageStatut += "Date de naissance invalide\n";
        }
        if (!ContactChecker.isAdresse(address)) {
            messageStatut += "Adresse invalide\n";
        }
        if (!ContactChecker.isPseudoValid(pseudo)) {
            messageStatut += "Pseudo invalide\n";
        }
        if (!ContactChecker.isGithubValid(github)) {
            messageStatut += "GitHub invalide\n";
        }
        if (gender == null) {
            messageStatut += "Veuillez sélectionner un genre\n";
        }

        if (!messageStatut.isEmpty()) {
            // Afficher les erreurs
            errorTextArea.setText(messageStatut);
            return null;
        }

        // Créer et retourner le contact
        return new Contact(firstName, lastName, gender, birthday, pseudo, address,
                persoPhoneNum, proPhoneNum, email, github);
    }

    /**
     * Affiche la fenêtre du formulaire de modification d'un contact
     */
    public void showFormToModify() {
        // Récupérer les données du contact pour pré-remplir les champs du formulaire.
        firstNameTextField.setText(contact.getFirstName());
        lastNameTextField.setText(contact.getLastName());
        emailTextField.setText(contact.getEmail());
        personalPhoneNumTextField.setText(contact.getPersoPhoneNum());
        birthdayTextField.setText(contact.getBirthday());
        proPhoneNumTextField.setText(contact.getProPhoneNum());
        genderComboBox.setValue(contact.getGender());
        addressTextField.setText(contact.getAddress());
        pseudoTextField.setText(contact.getPseudo());
        githubTextField.setText(contact.getGithubPage());
    }

    /**
     * Gère la modification d'un contact
     */
    public void updateContact() {
        String messageStatut = "";
        // Récupérer les données du formulaire
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String persoPhoneNum = personalPhoneNumTextField.getText().trim();
        String birthday = birthdayTextField.getText().trim();
        String proPhoneNum = proPhoneNumTextField.getText().trim();
        Gender gender = genderComboBox.getValue();
        String address = addressTextField.getText().trim();
        String pseudo = pseudoTextField.getText().trim();
        String github = githubTextField.getText().trim();

        // Vérifier les données
        if (!ContactChecker.isNomPrenomValid(firstName)) {
            messageStatut += "Prénom invalide\n";
        }
        if (!ContactChecker.isNomPrenomValid(lastName)) {
            messageStatut += "Nom invalide\n";
        }
        if (!ContactChecker.isEmailValid(email)) {
            messageStatut += "Email invalide\n";
        }
        if (!ContactChecker.isNumero(persoPhoneNum)) {
            messageStatut += "Numéro de téléphone personnel invalide\n";
        }
        if (!ContactChecker.isNumero(proPhoneNum)) {
            messageStatut += "Numéro de téléphone professionnel invalide\n";
        }
        if (!ContactChecker.isDateValid(birthday)) {
            messageStatut += "Date de naissance invalide\n";
        }
        if (!ContactChecker.isAdresse(address)) {
            messageStatut += "Adresse invalide\n";
        }
        if (!ContactChecker.isPseudoValid(pseudo)) {
            messageStatut += "Pseudo invalide\n";
        }
        if (!ContactChecker.isGithubValid(github)) {
            messageStatut += "GitHub invalide\n";
        }
        if (gender == null) {
            messageStatut += "Veuillez sélectionner un genre\n";
        }

        if (!messageStatut.isEmpty()) {
            // Afficher les erreurs
            errorTextArea.setText(messageStatut);
        }

        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        contact.setGender(gender);
        contact.setAddress(address);
        contact.setPseudo(pseudo);
        contact.setPersoPhoneNum(persoPhoneNum);
        contact.setBirthday(birthday);
        contact.setProPhoneNum(proPhoneNum);
        contact.setGithubPage(github);
    }

    public void setContactsList(ObservableList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setContactInfos(Contact contact) {
        this.contact = contact;
    }

    public ObservableList<Contact> getModifiedList() {
        return this.contacts;
    }

    @FXML
    public void closeForm() {
        Stage stage = (Stage) formGridPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addOrModifyContact() {
        if (contacts == null) {
            System.err.println("Liste inexistante.");
            return;
        }

        if (contact == null) {
            Contact newContact = createContact();
            if (newContact != null) {
                contacts.add(newContact);
                closeForm();
            }

        } else {
            updateContact();
            closeForm();
        }
    }
}

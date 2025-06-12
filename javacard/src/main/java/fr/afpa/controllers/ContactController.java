package fr.afpa.controllers;

import java.io.FilterOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.afpa.models.Contact;
import fr.afpa.models.Gender;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    /**
     * Liste des contacts filtrés associée à la "listViewContacts".
     */
    private FilteredList<Contact> filteredListContacts;

    public ContactController() {
    }

    @FXML
    public void initialize() {
        if (addButton != null) {
            addButton.setOnAction(event -> showAddContactForm());
        }
        if (deleteButton != null) {
            deleteButton.setOnAction(event -> showPopUpDelete());
        }
        if (modifyButton != null) {
            modifyButton.setOnAction(event -> showModifyContactForm());
        }
        if (qrCodeButton != null) {
            qrCodeButton.setOnAction(event -> showQRCodePopUp());
        }

        List<Contact> contacts = listViewContacts.getItems();

        // création d'une liste pour filtrer les éléments
        filteredListContacts = new FilteredList<>(listViewContacts.getItems());
        // association de cette liste au composant ListView
        listViewContacts.setItems(filteredListContacts);

        modifyButton.setDisable(true);
        qrCodeButton.setDisable(true);

        // Exemple: ajouter quelques contacts de test
        contacts.add(new Contact("Alice", "Dupont", Gender.FEMME, "01/01/1990",
                "Ali", "Paris", "0123456789", "0987654321",
                "alice@example.com", "https://github.com/alice"));
        contacts.add(new Contact("Bob", "Martin", Gender.HOMME, "02/02/1985",
                "Bobby", "Lyon", "0234567890", "0876543210",
                "bob@example.com", "https://github.com/bob"));

        listViewContacts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        // Configuration de la barre de recherche
        if (textFieldSearchBar != null) {
            textFieldSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {

                // changement du prédicat (fonction flêchée/lambda) pour prendre en compte les
                // informations du filtre
                filteredListContacts.setPredicate(contact -> {
                    // vérifie si les contacts match avec la saisi
                    if (newValue == null || newValue.isEmpty()) {
                        // si "true" on garde l'élément
                        return true;
                    } else if (contact.toString().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    }
                    // si "false" l'élément est filtré
                    return false;
                });
            });
        }
    }

    /**
     * Affiche le formulaire de création d'un contact
     */
    public void showAddContactForm() {
        try {
            URL url = getClass().getResource("/fr/afpa/form.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            GridPane formRoot = loader.load();
            FormController formController = loader.getController();
            Stage formStage = new Stage();
            Scene scene = new Scene(formRoot);
            formStage.setScene(scene);
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.setResizable(false);

            formController.setContactsList(listViewContacts.getItems());
            formStage.showAndWait();

        } catch (Exception e) {
            System.out.println("Erreur chargement du formulaire.");
            e.printStackTrace();
        }
    }

    /**
     * Afficher les infos des contacts
     */
    public void getContact() {

        // Cette méthode devrait être appelée lorsque l'utilisateur sélectionne un
        // contact dans la liste des contacts. Elle mettra à jour les champs de la fiche
        // contact avec les informations du contact sélectionné.
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

        // Vous pouvez également mettre à jour l'état des boutons
        // (par exemple, activer le bouton de modification)
        modifyButton.setDisable(selectedContact == null);
        qrCodeButton.setDisable(selectedContact == null);
    }

    /**
     * Affiche le formulaire pour modifier un contact
     */
    public void showModifyContactForm() {
        try {
            URL url = getClass().getResource("/fr/afpa/form.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            GridPane formRoot = loader.load();
            FormController formController = loader.getController();
            Stage formStage = new Stage();
            Scene scene = new Scene(formRoot);
            formStage.setScene(scene);
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.setResizable(false);

            Contact contactSelected = listViewContacts.getSelectionModel().getSelectedItem();
            formController.setContactInfos(contactSelected);
            formController.setContactsList(listViewContacts.getItems());
            formController.showFormToModify();

            formStage.setOnHidden(e -> {
                listViewContacts.refresh();
                getContact();
            });

            formStage.showAndWait();

        } catch (Exception e) {
            System.out.println("Erreur chargement du formulaire.");
            e.printStackTrace();
        }
    }

    /**
     * Affiche la popup Supprimer un contact
     */
    public void showPopUpDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce(s) contact(s) ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteContact();
        }
    }

    /**
     * Supprime un ou plusieurs contacts
     */
    public void deleteContact() {
        ObservableList<Contact> selectedContacts = listViewContacts.getSelectionModel().getSelectedItems();
        if (selectedContacts == null || selectedContacts.isEmpty()) {
            return;
        }
        listViewContacts.getItems().removeAll(selectedContacts);
    }

    /**
     * Affiche la popup QR Code
     */
    public void showQRCodePopUp() {
        try {
            Contact contactSelected = listViewContacts.getSelectionModel().getSelectedItem();
            if (contactSelected == null) {
                System.out.println("Aucun contact sélectionné");
                return;
            }

            URL url = getClass().getResource("/fr/afpa/qrcode-popup.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            VBox qrCode = loader.load();

            QRCodeController qrCodeController = loader.getController();
            qrCodeController.setContact(contactSelected);

            Stage qrCodeStage = new Stage();
            Scene scene = new Scene(qrCode);
            qrCodeStage.setScene(scene);
            qrCodeStage.initModality(Modality.APPLICATION_MODAL);
            qrCodeStage.setResizable(false);

            qrCodeStage.showAndWait();

        } catch (Exception e) {
            System.out.println("Erreur chargement de la pop-up QR Code.");
            e.printStackTrace();
        }
    }

    /** Gestion barre de recherche */

}

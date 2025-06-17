package fr.afpa.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.afpa.models.Contact;
import fr.afpa.serializers.BinarySerializerDeserializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    /**
     * Liste des contacts filtrés associée à la "listViewContacts".
     */
    private FilteredList<Contact> filteredListContacts;

    /**
     * Serializer pour sauvegarder/charger les contacts
     */
    private BinarySerializerDeserializer serializer;

    /**
     * Chemin du fichier de sauvegarde
     */
    private static final String SAVE_FILE_PATH = "contacts.dat";

    public ContactController() {
        serializer = new BinarySerializerDeserializer();
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
        if (exportButton != null) {
            exportButton.setOnAction(event -> showExportPopUp());
        }

        // Charger les contacts existants au démarrage
        loadContactsAtStartup();

        // création d'une liste pour filtrer les éléments
        filteredListContacts = new FilteredList<>(listViewContacts.getItems());
        // association de cette liste au composant ListView
        listViewContacts.setItems(filteredListContacts);

        modifyButton.setDisable(true);
        qrCodeButton.setDisable(true);

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
                filteredListContacts.setPredicate(contact -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    } else if (contact.toString().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
            });
        }
    }

    /**
     * Charge les contacts au démarrage de l'application
     */
    private void loadContactsAtStartup() {
        if (serializer.fileExists(SAVE_FILE_PATH)) {
            loadContacts();
        } else {
            // // Si aucun fichier n'existe, créer quelques contacts de test
            // List<Contact> contacts = listViewContacts.getItems();
            // contacts.add(new Contact("Alice", "Dupont", Gender.FEMME, "01/01/1990",
            // "Ali", "Paris", "0123456789", "0987654321",
            // "alice@example.com", "https://github.com/alice"));
            // contacts.add(new Contact("Bob", "Martin", Gender.HOMME, "02/02/1985",
            // "Bobby", "Lyon", "0234567890", "0876543210",
            // "bob@example.com", "https://github.com/bob"));
            // System.out.println("Contacts de démonstration chargés.");
            System.out.println("Aucun fichier de sauvegarde trouvé. Aucun contact chargé.");
        }

    }

    /**
     * Sauvegarde tous les contacts (appelée à la fermeture de l'application)
     */
    public void saveContacts() {
        try {
            ObservableList<Contact> contacts = listViewContacts.getItems();
            serializer.saveAll(SAVE_FILE_PATH, new ArrayList<>(contacts));
            System.out.println("Contacts sauvegardés automatiquement à la fermeture.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la sauvegarde automatique : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Charge tous les contacts (appelée au lancement de l'application)
     */
    private void loadContacts() {
        if (!serializer.fileExists(SAVE_FILE_PATH)) {
            System.out.println("Aucun fichier de sauvegarde trouvé. Chargement des contacts par défaut.");
            return;
        }

        try {
            List<Contact> loadedContacts = serializer.loadAll(SAVE_FILE_PATH);
            listViewContacts.setItems(FXCollections.observableArrayList(loadedContacts));
            System.out.println(loadedContacts.size() + " contact(s) chargé(s) automatiquement au démarrage.");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement automatique : " + e.getMessage());
            e.printStackTrace();
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

            ObservableList<Contact> modifiableList = FXCollections.observableArrayList(listViewContacts.getItems());
            formController.setContactsList(modifiableList);

            formStage.setOnHidden(e -> {
                listViewContacts.setItems(formController.getModifiedList());
            });

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

            ObservableList<Contact> contactListCopy = FXCollections.observableArrayList(listViewContacts.getItems());
            formController.setContactsList(contactListCopy);

            Contact contactSelected = listViewContacts.getSelectionModel().getSelectedItem();
            formController.setContactInfos(contactSelected);
            formController.showFormToModify();

            Stage formStage = new Stage();
            formStage.setScene(new Scene(formRoot));
            formStage.initModality(Modality.APPLICATION_MODAL);
            formStage.setResizable(false);

            formStage.setOnHidden(e -> {
                listViewContacts.setItems(formController.getModifiedList());
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
        filteredListContacts.getSource().removeAll(selectedContacts);
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

    public void showExportPopUp() {
        try {
            Contact contactSelected = listViewContacts.getSelectionModel().getSelectedItem();
            if (contactSelected == null) {
                System.out.println("Aucun contact sélectionné");
                return;
            }
            ObservableList<Contact> contactsList = listViewContacts.getItems();
            ArrayList<Contact> contactsArrayList = new ArrayList<>(contactsList);

            URL url = getClass().getResource("/fr/afpa/export-popup.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            VBox exportRoot = loader.load();

            ExportController exportController = loader.getController();
            exportController.setContact(contactSelected);
            exportController.setContacts(contactsArrayList);

            Stage exporStage = new Stage();
            Scene scene = new Scene(exportRoot);
            exporStage.setScene(scene);
            exporStage.initModality(Modality.APPLICATION_MODAL);
            exporStage.setResizable(false);

            exporStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Erreur chargement de la pop-up Export.");
            e.printStackTrace();
        }
    }
}
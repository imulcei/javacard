package fr.afpa.controllers;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import fr.afpa.models.Contact;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QRCodeController {
    // variables QR Code
    @FXML
    private VBox qrCodeVBox;
    @FXML
    private Button quitQrCodeButton;
    @FXML
    private ImageView qrCodeImageView;

    private Contact contact;

    public void initialize() {
        if (quitQrCodeButton != null) {
            quitQrCodeButton.setOnAction(event -> closeQRCode());
        }
    }

    /**
     * Attribue un contact à la classe QRCodeController
     * afin de générer et afficher le QR Code.
     * 
     * @param contact Contact sélectionné
     */
    public void setContact(Contact contact) {
        this.contact = contact;
        try {
            displayQRCode();
        } catch (Exception e) {
            System.err.println("Erreur lors de la génération du QR code.");
            e.printStackTrace();
        }
    }

    /**
     * Génère et affiche le QR Code du contact.
     * 
     * @throws WriterException
     */
    public void displayQRCode() throws WriterException {
        if (contact != null) {
            Image qrCodeImage = generateQRCode(contact, 300, 300);
            qrCodeImageView.setImage(qrCodeImage);
        }
    }

    /**
     * 
     * @param contact Contact sélectionné.
     * @param width   Largeur du QR Code souhaité.
     * @param height  Hauteur du QR Code souhaité.
     * @return Un appel à la fonction generateQRCodeImage de type Image.
     * @throws WriterException
     */
    public static Image generateQRCode(Contact contact, int width, int height) throws WriterException {
        String vCardInfos = contact.toVCard();
        return generateQRCodeImage(vCardInfos, width, height);
    }

    /**
     * 
     * @param vCardInfos VCard du contact sélectionné.
     * @param width      Largeur du QR Code souhaité.
     * @param height     Hauteur du QR Code souhaité.
     * @return La conversion des infos du contact sélectionné de VCard à Image.
     * @throws WriterException
     */
    public static Image generateQRCodeImage(String vCardInfos, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(vCardInfos, BarcodeFormat.QR_CODE, width, height);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    @FXML
    public void closeQRCode() {
        Stage stage = (Stage) qrCodeVBox.getScene().getWindow();
        stage.close();
    }

}

package fr.afpa.serializers;

import java.util.ArrayList;

import fr.afpa.models.Contact;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class VCardSerializer implements Serializer {

    @Override
    public void saveOne(String filePath, Contact contact) {
        if (filePath == null || filePath.isEmpty()) {
            System.out.println("Le chemin du fichier est manquant ou vide.");
            return;
        }
        if (contact == null) {
            System.out.println("Le contact est invalide.");
            return;
        }
        String contactVCard = contact.toVCard();
        String filePathExports = "exports/" + filePath;

        // création du dossier Parent
        Path path = Path.of(filePathExports);
        Path parentDir = path.getParent();
        try {
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            // écriture du contenu dans le fichier
            Files.write(path, contactVCard.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    @Override
    public void saveAll(String filePath, ArrayList<Contact> contacts) {
        if (filePath == null || filePath.isEmpty()) {
            System.out.println("Le chemin du fichier est manquant ou vide.");
            return;
        }
        if (contacts == null || contacts.isEmpty()) {
            System.out.println("Le contact est invalide ou vide.");
            return;
        }

        String filePathExports = "exports/" + filePath;
        // création du dossier Parent
        Path path = Path.of(filePathExports);
        Path parentDir = path.getParent();
        try {
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            StringBuilder allVCards = new StringBuilder();
            for (Contact contact : contacts) {
                if (contact != null) {
                    allVCards.append(contact.toVCard());
                }
            }
            Files.write(path, allVCards.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

}

package fr.afpa.serializers;

import java.io.*;
import java.util.ArrayList;
import fr.afpa.models.Contact;

/**
 * Implémentation de la sérialisation/désérialisation binaire pour les contacts
 * Utilise ObjectOutputStream et ObjectInputStream pour la sérialisation Java native
 */
public class BinarySerializerDeserializer implements Serializer, Deserializer {

    /**
     * Sauvegarde un seul contact dans un fichier binaire
     * @param filePath le chemin du fichier de destination
     * @param contact le contact à sauvegarder
     */
    @Override
    public void saveOne(String filePath, Contact contact) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(contact);
            System.out.println("Contact sauvegardé avec succès dans : " + filePath);
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du contact : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sauvegarde une liste de contacts dans un fichier binaire
     * @param filePath le chemin du fichier de destination
     * @param contacts la liste des contacts à sauvegarder
     */
    @Override
    public void saveAll(String filePath, ArrayList<Contact> contacts) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(contacts);
            System.out.println(contacts.size() + " contact(s) sauvegardé(s) avec succès dans : " + filePath);
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des contacts : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Charge un seul contact depuis un fichier binaire
     * @param filePath le chemin du fichier à charger
     * @return le contact chargé, ou null en cas d'erreur
     */
    @Override
    public Contact loadOne(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            Contact contact = (Contact) ois.readObject();
            System.out.println("Contact chargé avec succès depuis : " + filePath);
            return contact;
            
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + filePath);
            return null;
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de désérialisation - classe Contact non trouvée : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Charge une liste de contacts depuis un fichier binaire
     * @param filePath le chemin du fichier à charger
     * @return la liste des contacts chargés, ou une liste vide en cas d'erreur
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Contact> loadAll(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            ArrayList<Contact> contacts = (ArrayList<Contact>) ois.readObject();
            System.out.println(contacts.size() + " contact(s) chargé(s) avec succès depuis : " + filePath);
            return contacts;
            
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + filePath);
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de désérialisation - classe non trouvée : " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Vérifie si un fichier existe
     * @param filePath le chemin du fichier à vérifier
     * @return true si le fichier existe, false sinon
     */
    public boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * Supprime un fichier binaire
     * @param filePath le chemin du fichier à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Fichier supprimé avec succès : " + filePath);
            } else {
                System.err.println("Impossible de supprimer le fichier : " + filePath);
            }
            return deleted;
        }
        return false;
    }
}
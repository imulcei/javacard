package fr.afpa.serializers;

import java.util.ArrayList;

import fr.afpa.models.Contact;

public interface Serializer {
    public void saveOne(String filePath, Contact contact);

    public void saveAll(String filePath, ArrayList<Contact> contact);
}

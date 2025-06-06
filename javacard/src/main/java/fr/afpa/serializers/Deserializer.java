package fr.afpa.serializers;

import java.util.ArrayList;

import fr.afpa.models.Contact;

public interface Deserializer {
    public Contact loadOne(String filePath);
    public ArrayList<Contact> loadAll(String filePath);
}

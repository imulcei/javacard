package fr.afpa.serializers;

import java.util.ArrayList;

import fr.afpa.models.Contact;

public class BinarySerializerDeserializer implements Serializer, Deserializer {

    @Override
    public void saveOne(String filePath, Contact contact) {

    }

    @Override
    public void saveAll(String filePath, ArrayList<Contact> contact) {

    }

    @Override
    public Contact loadOne(String filePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadOne'");
    }

    @Override
    public ArrayList<Contact> loadAll(String filePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadAll'");
    }

}

package backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private static final String testPath = "target/testDatabase.json";


    @Test
    void addContactsToDatabase() {
        var db = new Database(testPath);
        Contact contact1 = new Contact("contact1", "surname", "", "1234589",
                "", "", "", "");
        Contact contact2 = new Contact("contact2", "surname", "", "12345689",
                "", "", "", "");
        Contact contact3 = new Contact("contact3", "surname", "", "12345678",
                "", "", "", "");
        Assertions.assertTrue(db.getAllContacts().isEmpty());

        db.addContact(contact1);
        db.addContact(contact2);
        db.addContact(contact3);

        Assertions.assertEquals(db.getAllContacts().size(),3);
    }


    @Test
    void deleteContactDatabase(){
        var db = new Database(testPath);
        Contact contact1 = new Contact("contact1", "surname", "", "1234589",
                "", "", "", "");
        Contact contact2 = new Contact("contact2", "surname", "", "12345689",
                "", "", "", "");
        Contact contact3 = new Contact("contact3", "surname", "", "12345678",
                "", "", "", "");

        db.addContact(contact1);
        db.addContact(contact2);
        db.addContact(contact3);

        db.deleteContact(contact2);

        Assertions.assertEquals(2, db.getAllContacts().size());

        Assertions.assertEquals(contact1,db.getAllContacts().get(0));
        Assertions.assertEquals(contact3,db.getAllContacts().get(1));

    }

    @Test
    void saveLoadData(){
        new File(testPath).delete();
        var db = new Database(testPath);
        Contact contact1 = new Contact("contact1", "surname", "", "1234589",
                "", "", "", "");
        Contact contact2 = new Contact("contact2", "surname", "", "12345689",
                "", "", "", "");
        Contact contact3 = new Contact("contact3", "surname", "", "12345678",
                "", "", "", "");

        db.addContact(contact1);
        db.addContact(contact2);
        db.addContact(contact3);

        db.saveData();

        var db2 = new Database(testPath);

        Assertions.assertEquals(db.getAllContacts(),db2.getAllContacts());
    }

}
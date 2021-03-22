package backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Database {

    private List<Contact> data;
    private String dataPath = "target/testDatabase.json";
    private static final Gson mapper = FxGson.coreBuilder().setPrettyPrinting().create();


    /**
     * Retrieves all of the currently existing contacts from the database.
     *
     * @return A list containing database contacts.
     */
    public List<Contact> getAllContacts() {
        return data;
    }

    public boolean addContact(Contact contact) {
        if (contact == null) {
            return false;
        } else if (!contact.isInitialized()) {
            return false;
        }
        else if (hasDuplicate(contact)){
            return false;
        }
        data.add(contact);
        return true;
    }

    public boolean hasDuplicate(Contact contact) {
        return data.contains(contact);
    }

    public void deleteContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Cannot delete a null contact.");
        }
        data.remove(contact);
    }

    /**
     * Retrieves contacts from the database and filters them according to
     * the pattern match.
     * All contacts with names, surnames and patronymic names matching the pattern
     * will pass the filter.
     *
     * @param pattern A string that a contact should contain in either
     *                a name, a surname or a patronymic name.
     * @return A list of contacts with names, surnames or patronymic names,
     * matching the pattern.
     */
    public List<Contact> getSortedContacts(String pattern) {
        var dataStream = data.stream()
                .filter(contact -> contact.matchesPattern(pattern));
        return dataStream.collect(Collectors.toList());
    }

    public Database(String databasePath) {
        dataPath = databasePath;
        loadData();
    }


    private void loadData() {

        File file = new File(dataPath);
        if (!file.exists()) {
            data = FXCollections.observableArrayList();
            return;
        }

        StringBuilder sb = new StringBuilder();
        Scanner sc = null;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(sc.hasNextLine())
            sb.append(sc.nextLine());


        data = mapper.fromJson(sb.toString(), new TypeToken<List<Contact>>() {
        }.getType());

    }
    //TODO: proper exception handling

    public void saveData() {
        try {
            String json = mapper.toJson(data);
            File file = new File(dataPath);
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData(File file){
        try {
            String json = mapper.toJson(data);
            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importData(File importFile){
        StringBuilder sb = new StringBuilder();
        Scanner sc = null;

        try {
            sc = new Scanner(importFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(sc.hasNextLine())
            sb.append(sc.nextLine());


        List<Contact> importedData = mapper.fromJson(sb.toString(), new TypeToken<List<Contact>>() {
        }.getType());

        for(var contact: importedData){
            this.addContact(contact);
        }
    }

}

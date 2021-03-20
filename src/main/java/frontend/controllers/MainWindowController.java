package frontend.controllers;

import backend.Contact;
import backend.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController  {

    private String path;
    private Database database;

    @FXML
    private TableView<Contact> contactsTable;

    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();

    public void initialize() {

    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDatabase() {
        this.database = new Database(path);
    }

    public void setContactsTable() {
        contactsList = FXCollections.observableList(this.database.getAllContacts());
        contactsTable.setItems(contactsList);
    }

    public void onDeleteContact(ActionEvent event) {
        if (!contactsTable.getSelectionModel().getSelectedCells().isEmpty()
                && contactsTable.getSelectionModel().getSelectedCells().size() <= 1) {
            var selectedContact = contactsTable.getSelectionModel().getSelectedItem();
            deleteContact(selectedContact);
        } else {
            //TODO: warning
        }
    }

    public void onNewContact(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/AddContactWindow.fxml"));
        //TODO: maybe a better solution it looks ugly af
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddContactController controller = loader.<AddContactController>getController();
        controller.setDb(this.database);

        Scene scene = new Scene(root);
        var resource = getClass().getResource("/css/text-field-red-border.css");
        var style = resource.toExternalForm();
        scene.getStylesheets().add(style);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.showAndWait();
    }

    public void onAddNewContact(ActionEvent event){

    }



    private void deleteContact(Contact contact){
        this.database.deleteContact(contact);
        this.contactsTable.getItems().remove(contact);
    }
}

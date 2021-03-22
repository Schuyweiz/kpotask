package frontend.controllers;

import backend.Contact;
import backend.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow {

    //region fields
    public BorderPane pane;
    @FXML
    protected TableView<Contact> contactsTable;
    private static ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    protected Database database;
    protected Stage addContactStage;
    protected FileChooser fileChooserImport;
    protected FileChooser fileChooserExport;
    protected FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("JSON file (*.json)","*.json");
    protected AddContactController addContactController;

    //endregion

    public void setDatabase(Database db) {
        this.database = db;
    }
    public void setStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/fxml/addContactWindow.fxml"));
        Parent root = loader.load();

        addContactController = loader.<AddContactController>getController();
        addContactController.setDb(this.database);

        Scene scene = new Scene(root);
        var resource = getClass().getResource("/css/text-field-red-border.css");
        var style = resource.toExternalForm();
        scene.getStylesheets().add(style);
        this.addContactStage = new Stage();
        addContactStage.initModality(Modality.APPLICATION_MODAL);
        addContactStage.setScene(scene);
        addContactStage.setTitle("Add a new contact.");
    }
    public void setContactsTable() {
        contactsList = FXCollections.observableList(this.database.getAllContacts());
        contactsTable.setItems(contactsList);
    }

}

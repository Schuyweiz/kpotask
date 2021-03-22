package frontend.controllers;

import backend.Contact;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import utilities.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends MainWindow implements Initializable {



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.fileChooserExport = new FileChooser();
        this.fileChooserExport.setTitle("Choose a place to save your contacts.");
        this.fileChooserExport.getExtensionFilters().add(this.filter);
        this.fileChooserExport.setInitialFileName("*.json");

        this.fileChooserImport = new FileChooser();
        this.fileChooserImport.setTitle("Open import file.");
        this.fileChooserImport.getExtensionFilters().add(this.filter);

        this.pane.setStyle(
                "-fx-background-image: url(/images/wallpaper.jpg);" +
                "-fx-background-size: 100% 100%");
        this.contactsTable.getStylesheets().add(
                getClass().getResource("/css/table-view-style.css").toExternalForm()
        );
    }

    //region event handlers

    public void onDeleteContact(ActionEvent event) {
        if (!contactsTable.getSelectionModel().getSelectedCells().isEmpty()
                && contactsTable.getSelectionModel().getSelectedCells().size() <= 1) {

            var selectedContact = contactsTable.getSelectionModel().getSelectedItem();
            deleteContact(selectedContact);
            contactsTable.getSelectionModel().clearSelection();
            event.consume();
        } else {
            Alerts.INFO.display("You need to select the contact, that you are going to delete.");
        }
    }

    public void onNewContact(ActionEvent event) {

        this.addContactStage.showAndWait();
        Contact addedContact = addContactController.getContact();
    }


    public void onImport(ActionEvent actionEvent) {
        var file = this.fileChooserImport.showOpenDialog(this.pane.getScene().getWindow());
        if(file!=null)
        {
            this.database.importData(file);
        }
        this.contactsTable.refresh();
    }

    public void onExport(ActionEvent actionEvent){
        var file = this.fileChooserExport.showSaveDialog(this.pane.getScene().getWindow());

        if (file!=null){
            this.database.saveData(file);
        }
    }
    //endregion



    private void deleteContact(Contact deleteContact){
        this.database.deleteContact(deleteContact);
        this.contactsTable.refresh();
    }
    private void addContact(Contact addedContact){
        if (this.database.addContact(addedContact)) {
            this.contactsTable.setItems(FXCollections.observableList(this.database.getAllContacts()));
            this.contactsTable.refresh();
        }
        else{
            Alerts.WARNING.display("You tried to add a duplicate contact!");
        }
    }
}

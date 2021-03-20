package frontend.controllers;

import backend.Contact;
import backend.Database;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import utilities.Checkers;

import javax.sql.DataSource;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddContactController implements Initializable {

    // region FXML fields
    @FXML
    public Button saveButton;


    @FXML
    public TextField nameField;

    @FXML
    public TextField surnameField;

    @FXML
    public TextField sursurnameField;

    @FXML
    public TextField phoneNumberField;

    @FXML
    public TextField homeNumberField;

    @FXML
    public TextField adressField;

    @FXML
    public DatePicker birthDatePicker;

    @FXML
    public TextArea noteField;

    // endregion

    // region Control fields

    private ObservableList<Contact> contacts;

    private final Checkers checkers = new Checkers();

    private List<TextField> mandatoryFields;

    private List<TextField> numbers;

    private List<Control> allFields;

    private static final String DATE_PATTERN = "dd.mm.yyyy";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

    // endregion

    public void onConfirm(ActionEvent event) {
        boolean canSave = validateNumbers()
                & validateMandatoryFields();

        if (canSave) {
            Contact newContact = createContact();
            db.addContact(newContact);
            var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }

    }

    public void onStartWriting(MouseEvent event) {
        TextField currentTextField = (TextField) event.getSource();
        currentTextField.clear();
        if (currentTextField.getStyleClass().contains("error")) {
            currentTextField.getStyleClass().clear();
            currentTextField.getStyleClass().addAll("text-field", "text-input");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mandatoryFields = Arrays.asList(
                nameField,
                surnameField,
                sursurnameField);

        this.numbers = Arrays.asList(
                phoneNumberField,
                homeNumberField
        );

        this.allFields = new ArrayList<>();
        this.allFields.addAll(mandatoryFields);
        this.allFields.addAll(numbers);

        for (var field : allFields) {
            field.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    onStartWriting(mouseEvent);
                }
            });
        }
    }

    public void setContacts(ObservableList<Contact> contacts) {
        this.contacts = contacts;
    }

    private Database db;
    public void setDb(Database db){
        this.db = db;
    }

    private boolean validateMandatoryFields() {
        boolean isValid = true;
        for (var field : mandatoryFields) {
            if (!checkers.checkName(field.getText())) {
                isValid = false;
                field.getStyleClass().add("error");
            }
        }
        return isValid;
    }

    private boolean validateNumbers() {
        boolean isValid = true;
        for (var field : numbers) {
            if (!checkers.checkNumber(field.getText())) {
                isValid = false;
                field.getStyleClass().add("error");
            }
        }
        return isValid;
    }



    private Contact createContact() {
        return new Contact(
                this.nameField.getText().trim(),
                this.surnameField.getText().trim(),
                this.sursurnameField.getText().trim(),
                this.phoneNumberField.getText(),
                this.homeNumberField.getText(),
                this.adressField.getText(),
                this.birthDatePicker.getValue().toString(),
                this.noteField.getText());
    }
}

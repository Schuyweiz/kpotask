package frontend.controllers;

import backend.Contact;
import backend.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utilities.Alerts;
import utilities.Checkers;

import java.net.URL;
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


    private final Checkers checkers = new Checkers();
    public Button cancelButton;
    public BorderPane bpane;

    private List<TextField> mandatoryFields;

    private List<TextField> numbers;

    private List<Control> allFields;

    private List<TextField> allTextFields;

    private static final String DATE_PATTERN = "dd.mm.yyyy";

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

    // endregion

    private void stageExit(Stage stage){
        clearFields();
        stage.close();
    }

    //region event handlers
    public void onConfirm(ActionEvent event) {
        boolean canSave = validateNumbers()
                & validateMandatoryFields();
        if (canSave) {
            Contact newContact = createContact();

                var stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                this.contact=newContact;
                this.stageExit(stage);

        }
        else{
            Alerts.WARNING.display("Some fields lack data and/or have erroneous data.");
        }

    }

    private Contact contact=null;
    public Contact getContact(){
        return contact;
    }

    public void onCancel(ActionEvent event){
        clearFields();
    }

    public void onStartWriting(MouseEvent event) {
        TextField currentTextField = (TextField) event.getSource();
        currentTextField.clear();
        if (currentTextField.getStyleClass().contains("error")) {
            currentTextField.getStyleClass().clear();
            currentTextField.getStyleClass().addAll("text-field", "text-input");
        }
    }

    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.bpane.setStyle(
                "-fx-background-image: url(/images/wallpaper3.png);" +
                        "-fx-background-size: 100% 100%");

        this.mandatoryFields = Arrays.asList(
                nameField,
                surnameField);

        this.numbers = Arrays.asList(
                phoneNumberField,
                homeNumberField
        );

        this.allFields = new ArrayList<>();
        this.allTextFields = new ArrayList<>();
        this.allFields.addAll(mandatoryFields);
        this.allFields.addAll(numbers);
        this.allFields.add(sursurnameField);

        for (var field : allFields) {
            field.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    onStartWriting(mouseEvent);
                }
            });
        }
    }

    private void clearFields(){
        this.nameField.clear();
        this.surnameField.clear();
        this.sursurnameField.clear();
        this.adressField.clear();
        this.phoneNumberField.clear();
        this.homeNumberField.clear();
        this.birthDatePicker.getEditor().clear();
        this.noteField.clear();

        for (var control: allFields
             ) {
            if (control.getStyleClass().contains("error")) {
                control.getStyleClass().clear();
                control.getStyleClass().addAll("text-field", "text-input");
            }
        }
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
        String number1 = this.phoneNumberField.getText();
        String number2 = this.homeNumberField.getText();
        boolean isValid = true;
        if(number1.equals("") && number2.equals("")){
            this.homeNumberField.getStyleClass().add("error");
            this.phoneNumberField.getStyleClass().add("error");

            isValid = false;
        }
        else{
            boolean numberValid = checkers.checkNumber(this.phoneNumberField.getText());
            boolean homenumberValid = checkers.checkNumber(this.homeNumberField.getText());

            if(!numberValid){
                this.phoneNumberField.getStyleClass().add("error");
            }
            if(!homenumberValid){
                this.homeNumberField.getStyleClass().add("error");
            }

            isValid = numberValid && homenumberValid;
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
                this.birthDatePicker.getEditor().getText(),
                this.noteField.getText());
    }
}

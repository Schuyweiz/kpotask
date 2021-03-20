package backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Contact {

    @Expose
    private StringProperty name;

    @Expose
    private StringProperty surname;

    @Expose
    private StringProperty sursurname;

    @Expose
    private StringProperty phoneNumber;

    @Expose
    private StringProperty homePhoneNumber;

    @Expose
    private StringProperty address;

    @Expose
    private StringProperty birthDate;

    @Expose
    private StringProperty note;

    public boolean matchesPattern(String pattern) {
        return name.get().contains(pattern) || surname.get().contains(pattern) || sursurname.get().contains(pattern);
    }

    public boolean isInitialized() {
        boolean hasName = name != null && !name.get().trim().isEmpty();
        boolean hasSurname = surname != null && !surname.get().trim().isEmpty();
        boolean hasNumber = (phoneNumber != null && !phoneNumber.get().trim().isEmpty())
                || (homePhoneNumber != null && !homePhoneNumber.get().trim().isEmpty());
        return hasNumber && hasSurname && hasName;
    }

    public Contact(String name, String surname, String sursurname, String phoneNumber, String homePhoneNumber,
                   String address, String birthDate, String note) {
        if (name == null && surname == null && (phoneNumber == null || homePhoneNumber == null)) {
            throw new IllegalArgumentException("A contact must contain a name, a surname and one of the phone numbers.");
        }


        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.sursurname = new SimpleStringProperty(sursurname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address = new SimpleStringProperty(address);
        this.birthDate = new SimpleStringProperty(birthDate);
        this.note = new SimpleStringProperty(note);
        this.homePhoneNumber = new SimpleStringProperty(homePhoneNumber);
    }

    @Override
    public boolean equals(Object o){
        if (! (o instanceof Contact)){
            throw new IllegalArgumentException("Cannot compare 2 different objects.");
        }
        Contact contact = (Contact) o;

        return contact.name.get().equals(this.name.get())
                && contact.surname.get().equals(this.surname.get())
                && contact.sursurname.get().equals(this.sursurname.get());
    }

    public Contact(){}

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getSursurname() {
        return sursurname.get();
    }

    public StringProperty sursurnameProperty() {
        return sursurname;
    }

    public void setSursurname(String sursurname) {
        this.sursurname.set(sursurname);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber.get();
    }

    public StringProperty homePhoneNumberProperty() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber.set(homePhoneNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public StringProperty birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }
}

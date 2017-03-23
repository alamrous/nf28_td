package model;

import java.time.LocalDate;

import javafx.beans.property.*;

public class Contact {

    private StringProperty name;
    private StringProperty firstname;
    private ObjectProperty<Address> address;
    private ObjectProperty<LocalDate> birthdate;
    private StringProperty gender;
    private StringProperty group;


    public Contact() {
        name = new SimpleStringProperty();
        firstname = new SimpleStringProperty();
        address = new SimpleObjectProperty<>();
        address.setValue(new Address());
        birthdate = new SimpleObjectProperty<>();
        gender = new SimpleStringProperty();
        group = new SimpleStringProperty();
    }


    /*
     Properties getters
     */

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    public ObjectProperty<LocalDate> birthdateProperty() {
        return birthdate;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty groupProperty() {
        return group;
    }
}

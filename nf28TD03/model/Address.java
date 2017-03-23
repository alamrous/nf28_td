package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Address {

    private StringProperty streetLine;
    private StringProperty postalCode;
    private StringProperty city;
    private StringProperty country;

    Address() {
        streetLine = new SimpleStringProperty();
        postalCode = new SimpleStringProperty();
        city = new SimpleStringProperty();
        country = new SimpleStringProperty();
    }

    public StringProperty streetLineProperty() {
        return streetLine;
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty countryProperty() {
        return country;
    }
}

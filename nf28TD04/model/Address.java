package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Address implements PropertiesMappable, Externalizable {

    private StringProperty streetLine;
    private StringProperty postalCode;
    private StringProperty city;
    private StringProperty country;

    public Address() {
        streetLine = new SimpleStringProperty("");
        postalCode = new SimpleStringProperty("");
        city = new SimpleStringProperty("");
        country = new SimpleStringProperty("");
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(streetLine.getValue());
        out.writeUTF(postalCode.getValue());
        out.writeUTF(city.getValue());
        out.writeUTF(country.getValue());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        streetLine.setValue(in.readUTF());
        postalCode.setValue(in.readUTF());
        city.setValue(in.readUTF());
        country.setValue(in.readUTF());
    }
}

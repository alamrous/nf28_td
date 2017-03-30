package model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Address implements PropertiesMappable {

    private StringProperty streetLine;
    private StringProperty postalCode;
    private StringProperty city;
    private StringProperty country;

    Address() {
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

    public Map<String, Property> toPropertiesMap() {

        Map<String, Property> members = new LinkedHashMap<>();
        members.put("streetLine", streetLine);
        members.put("postalCode", postalCode);
        members.put("city", city);
        members.put("country", country);

        return members;
    }
}

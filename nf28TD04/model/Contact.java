package model;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Contact implements PropertiesMappable {

    private StringProperty name;
    private StringProperty firstname;
    private ObjectProperty<Address> address;
    private ObjectProperty<LocalDate> birthdate;
    private StringProperty gender;
//    private StringProperty group;
    private ObjectProperty<Group> group;

    private String id;

    private ObservableMap<String, String> errors;
    private List<String> incorrectFields;

    private static String DEFAULT_ERROR_MESSAGE = "Ce champ n'est pas renseigné correctement !";

    public Contact() {
        name = new SimpleStringProperty("");
        firstname = new SimpleStringProperty("");
        address = new SimpleObjectProperty<>(new Address());
        birthdate = new SimpleObjectProperty<>();
        gender = new SimpleStringProperty("");
//        group = new SimpleStringProperty("");
        group = new SimpleObjectProperty<>();

        errors = FXCollections.observableHashMap();
        incorrectFields = new ArrayList<>();

        id = UUID.randomUUID().toString();
    }

    public ObservableMap<String, String> getErrors() {
        return errors;
    }

    public void saveContact() {
        incorrectFields.clear();

        checkNonEmpty(toPropertiesMap());

        checkCorrectField(toPropertiesMap());

//        System.out.println("incorrectFields: " + incorrectFields);

        errors.entrySet().removeIf(entry -> !incorrectFields.contains(entry.getKey()));

//        System.out.println("errors: " + errors);
    }

    private void checkCorrectField(Map<String, Property> members) {

        members.forEach((s, property) -> {

            Pattern pattern;
            Matcher matcher;

            if (property.getValue().getClass() == String.class) {

                if (!incorrectFields.contains(s)) {

                    String val = property.getValue().toString();

//                    System.out.println("key: " + s);
//                    System.out.println("val: " + property.getValue());

                    switch (s) {
                        case "name":
                        case "firstname":
                            pattern = Pattern.compile("(?i)(?=.{2,100})\\p{L}+(?:[-'\\s]?\\p{L}+)*");
                            break;
                        case "streetLine":
                            pattern = Pattern.compile("(?i)(?=.{2,100})[0-9]+\\p{L}*\\s\\p{L}+(?:[-'\\s]?\\p{L}+)*");
                            break;
                        case "postalCode":
                            pattern = Pattern.compile("(?=.{2,10})[0-9]{4,}");
                            break;
                        case "city":
                            pattern = Pattern.compile("(?i)(?=.{2,100})\\p{L}+(?:[-'\\s]?\\p{L}+)*");
                            break;
                        default:
                            pattern = Pattern.compile("");
                            break;
                    }

                    matcher = pattern.matcher(val);
                    if (!matcher.matches()) {
                        errors.put(s, DEFAULT_ERROR_MESSAGE);
                        incorrectFields.add(s);
                    }
                }
            } else {
                PropertiesMappable member = (PropertiesMappable) property.getValue();
                checkCorrectField(member.toPropertiesMap());
            }
        });

    }

    private void checkNonEmpty(Map<String, Property> members) {

        members.forEach((s, property) -> {
            if (property.getValue().getClass() == String.class) {

                String val = property.getValue().toString();
//                System.out.println("key: " + s);
//                System.out.println("val: " + property.getValue());

                if (val.isEmpty()) {
                    errors.put(s, "Ce champ est obligatoire !");
                    incorrectFields.add(s);
                }

            } else {
                PropertiesMappable member = (PropertiesMappable) property.getValue();
                checkNonEmpty(member.toPropertiesMap());
            }
        });

    }

    public Map<String, Property> toPropertiesMap() {

        Map<String, Property> members = new LinkedHashMap<>();
        members.put("name", name);
        members.put("firstname", firstname);
        members.put("address", address);

        return members;
    }


        /* Properties getters */

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

    String getId() {
        return id;
    }

//    public StringProperty groupProperty() {
//        return group;
//    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    @Override
    public String toString() {
        return this.firstname + " " + this.name;
    }

}

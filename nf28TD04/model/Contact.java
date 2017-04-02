package model;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

public class Contact implements PropertiesMappable {

    public static int MALE_GENDER_PROPERTY = 0;
    public static int FEMALE_GENDER_PROPERTY = 1;

    private static Image DEFAULT_CONTACT_ICON = new Image("file:view/contact.png");

    private StringProperty name;
    private StringProperty firstname;
    private ObjectProperty<Address> address;
    private ObjectProperty<LocalDate> birthdate;
    private IntegerProperty gender;
    private ObjectProperty<Group> group;

    private Image icon;

    private ObservableMap<String, String> errors;
    private List<String> incorrectFields;

    private static String DEFAULT_ERROR_MESSAGE = "Ce champ n'est pas renseigné correctement !";

    public Contact() {
        name = new SimpleStringProperty("");
        firstname = new SimpleStringProperty("");
        address = new SimpleObjectProperty<>(new Address());
        birthdate = new SimpleObjectProperty<>();
        gender = new SimpleIntegerProperty(FEMALE_GENDER_PROPERTY);
        group = new SimpleObjectProperty<>();

        icon = DEFAULT_CONTACT_ICON;

        errors = FXCollections.observableHashMap();
        incorrectFields = new ArrayList<>();
    }

    Contact(Contact contact) {
        name = new SimpleStringProperty(contact.name.getValue());
        firstname = new SimpleStringProperty(contact.firstname.getValue());

        address = new SimpleObjectProperty<>(new Address());
        address.getValue().streetLineProperty().setValue(contact.address.getValue().streetLineProperty().getValue());
        address.getValue().postalCodeProperty().setValue(contact.address.getValue().postalCodeProperty().getValue());
        address.getValue().cityProperty().setValue(contact.address.getValue().cityProperty().getValue());
        address.getValue().countryProperty().setValue(contact.address.getValue().countryProperty().getValue());

        birthdate = new SimpleObjectProperty<>(contact.birthdate.getValue());
        gender = new SimpleIntegerProperty(contact.gender.getValue());
        group = new SimpleObjectProperty<>(contact.group.getValue());

        icon = contact.icon;
    }

    public ObservableMap<String, String> getErrors() {
        return errors;
    }

    /**
     *
     * @return true if no error has been detected upon the form validation
     */
    public boolean contactCanBeSaved() {
        incorrectFields.clear();

        Map<String, Property> mapToCheck = new LinkedHashMap<>(toPropertiesMap());
        mapToCheck.remove("gender");
        mapToCheck.remove("group");
        mapToCheck.remove("country");

        checkNonEmpty(mapToCheck);

        checkCorrectField(mapToCheck);

        errors.entrySet().removeIf(entry -> !incorrectFields.contains(entry.getKey()));

        return errors.isEmpty();
    }

    private void checkCorrectField(Map<String, Property> members) {

        members.forEach((s, property) -> {

            Pattern pattern;
            Matcher matcher;

            if (property instanceof StringProperty) {

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
            if (property instanceof StringProperty) {

                String val = property.getValue().toString();

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

    @Override
    public String toString() {
        return this.firstname.getValue() + " " + this.name.getValue();
    }

    public Image getIcon() {
        return icon;
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

    public IntegerProperty genderProperty() {
        return gender;
    }

    public ObjectProperty<Group> groupProperty() {
        return group;
    }

}

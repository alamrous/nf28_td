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
    private StringProperty group;

    private ObservableMap<String, String> errors;
    private List<String> incorrectFields;
    private static Pattern pattern;
    private static Matcher matcher;

    public Contact() {
        name = new SimpleStringProperty("");
        firstname = new SimpleStringProperty("");
        address = new SimpleObjectProperty<>(new Address());
        birthdate = new SimpleObjectProperty<>();
        gender = new SimpleStringProperty("");
        group = new SimpleStringProperty("");

        errors = FXCollections.observableHashMap();
        incorrectFields = new ArrayList<>();
    }

    public ObservableMap<String, String> getErrors() {
        return errors;
    }

    public void saveContact() {
        incorrectFields.clear();

        checkNonEmpty(toPropertiesMap());
        if(incorrectFields.isEmpty())
        {
        	checkCorrectField(toPropertiesMap());
        }


//        System.out.println("incorrectFields: " + incorrectFields);

        errors.entrySet().removeIf(entry -> !incorrectFields.contains(entry.getKey()));
    }
    private void checkCorrectField(Map<String, Property> members) {

        members.forEach((s, property) -> {
            if (property.getValue().getClass() == String.class) {

                String val = property.getValue().toString();
                System.out.println("key: " + s);
//                System.out.println("val: " + property.getValue());
                if(s.contains("name"))
                {

                	 pattern = Pattern.compile("((?i)^[a-z]+([-]([a-z]+))*$)");
                 	matcher = pattern.matcher(val);
                 	if(matcher.find() == false)
                 	{
                 	      errors.put(s, "Ce champ n'est pas entr� correctement  !");
                          incorrectFields.add(s);
                 	}
                 	else
                 	incorrectFields.remove(	s);
                }
                else if (s.contentEquals("streetLine"))
                {
                  	 pattern = Pattern.compile("((?i)[0-9]+[a-z]*[\\s]([a-z]+[\\s]*)+$)");
                  	matcher = pattern.matcher(val);
                  	if(matcher.find() == false)
                  	{
                  	      errors.put(s, "Ce champ n'est pas entr� correctement  !");
                           incorrectFields.add(s);
                  	}
                  	else
                  	incorrectFields.remove(	s);

                }
                else if (s.contentEquals("postalCode"))
                {
                	 pattern = Pattern.compile("(^[0-9]{5}$)");
                   	matcher = pattern.matcher(val);
                   	if(matcher.find() == false)
                   	{
                   	      errors.put(s, "Ce champ n'est pas entr� correctement  !");
                            incorrectFields.add(s);
                   	}
                   	else
                   	incorrectFields.remove(	s);
                }
                else if (s.contentEquals("city"))
                {
                	 pattern = Pattern.compile("(?i)^(([a-z]+[\\s]*)+)$");
                   	matcher = pattern.matcher(val);
                   	if(matcher.find() == false)
                   	{
                   	      errors.put(s, "Ce champ n'est pas entr� correctement !");
                            incorrectFields.add(s);
                   	}
                   	else
                   	incorrectFields.remove(	s);
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
                else
                    incorrectFields.remove(s);

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

    public StringProperty groupProperty() {
        return group;
    }

}
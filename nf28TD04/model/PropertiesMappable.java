package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

interface PropertiesMappable {

    /**
     * Returns a map of all the Object properties
     *
     * @return a map of the object properties, where each key corresponds to the property name
     */
    default Map<String, Property> toPropertiesMap() {

        Map<String, Property> members = new LinkedHashMap<>();

        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Class type = field.getType();
                Object value = field.get(this);
                if (type == StringProperty.class) {
                    members.put(field.getName(), (StringProperty) value);
                } else if (type == ObjectProperty.class &&
                        ((ObjectProperty) value).getValue() instanceof PropertiesMappable) {
                    members.putAll(((PropertiesMappable) ((ObjectProperty) value).getValue()).toPropertiesMap());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return members;
    }

    default void initTextFields() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Class type = field.getType();
                Object value = field.get(this);
                if (type == StringProperty.class) {
                    StringProperty sp = (StringProperty) value;
                    sp.setValue("");
                } else if (type == ObjectProperty.class &&
                        ((ObjectProperty) value).getValue() instanceof PropertiesMappable) {
                    ((PropertiesMappable) ((ObjectProperty) value).getValue()).initTextFields();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

package model;

import javafx.beans.property.Property;

import java.util.Map;

interface PropertiesMappable {

    /**
     * Returns a map of all the Object properties
     *
     * @return a map of the object properties, where each key corresponds to the property name
     *
     */
    Map<String, Property> toPropertiesMap();

}

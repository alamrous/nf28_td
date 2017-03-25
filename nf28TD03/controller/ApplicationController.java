package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Contact;
import model.Country;
import model.Group;

public class ApplicationController implements Initializable {

    private Contact contact;

    private Map<String, Control> fieldNamesMap;


    @FXML
    private TextField nameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField streetLineTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private ChoiceBox<String> countryChoiceBox;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton femaleRadio;
    @FXML
    private RadioButton maleRadio;
    @FXML
    private ChoiceBox<String> groupChoiceBox;
    @FXML
    private Button saveButton;

    private void initCountryList() {
        countryChoiceBox.setItems(FXCollections.observableList(Country.getCountryList()));
        String DEFAULT_COUNTRY = "France";
        countryChoiceBox.getSelectionModel().select(DEFAULT_COUNTRY);
        setCountry(DEFAULT_COUNTRY);
    }

    private void initGroupList() {
        groupChoiceBox.setItems(FXCollections.observableList(Group.getGroupList()));
        String DEFAULT_GROUP = "Parti socialiste";
        groupChoiceBox.getSelectionModel().select(DEFAULT_GROUP);
        setGroup(DEFAULT_GROUP);
    }

    private void initGenderGroup() {
        femaleRadio.setUserData("F");
        maleRadio.setUserData("M");
        setGender();
    }

    private void initDatePicker() {
        birthDatePicker.setValue(LocalDate.now());
        setBirthDate(birthDatePicker.getValue());
    }

    private void setCountry(String country) {
        contact.addressProperty().get().countryProperty().setValue(country);
    }

    private void setGroup(String group) {
        contact.groupProperty().setValue(group);
    }

    private void setBirthDate(LocalDate date) {
        contact.birthdateProperty().setValue(date);
    }

    private void setGender() {
        contact.genderProperty().setValue(genderGroup.getSelectedToggle().getUserData().toString());
    }

    private void saveContact() {

//        System.out.println(contact.nameProperty().get());
//        System.out.println(contact.firstnameProperty().get());
//
//        System.out.println(contact.addressProperty().get().cityProperty().get());
//        System.out.println(contact.addressProperty().get().streetLineProperty().get());
//        System.out.println(contact.addressProperty().get().countryProperty().get());
//        System.out.println(contact.addressProperty().get().postalCodeProperty().get());
//
//        System.out.println(contact.birthdateProperty().get());
//        System.out.println(contact.genderProperty().get());
//        System.out.println(contact.groupProperty().get());

        contact.saveContact();

//        System.out.println(contact.getErrors());

        for (Object o : fieldNamesMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String fieldname = entry.getKey().toString();
            Control field = (Control) entry.getValue();
            if (contact.getErrors().containsKey(fieldname)) {
                field.requestFocus();
                break;
            }
        }


    }

    private void addErrorMessage(String fieldname, String error) {
        fieldNamesMap.get(fieldname).getStyleClass().add("error");
        fieldNamesMap.get(fieldname).setTooltip(new Tooltip(error));
    }

    private void removeErrorMessage(String fieldname) {
        ObservableList<String> styleClasses = fieldNamesMap.get(fieldname).getStyleClass();
        styleClasses.remove(styleClasses.indexOf("error"));
        fieldNamesMap.get(fieldname).setTooltip(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.contact = new Contact();

        fieldNamesMap = new LinkedHashMap<>();
        fieldNamesMap.put("name", nameTextField);
        fieldNamesMap.put("firstname", firstNameTextField);
        fieldNamesMap.put("birthdate", birthDatePicker);
        fieldNamesMap.put("streetLine", streetLineTextField);
        fieldNamesMap.put("postalCode", postalCodeTextField);
        fieldNamesMap.put("city", cityTextField);
        fieldNamesMap.put("country", countryChoiceBox);

        initCountryList();
        initGroupList();
        initGenderGroup();
        initDatePicker();


        nameTextField.textProperty().bindBidirectional(contact.nameProperty());
        firstNameTextField.textProperty().bindBidirectional(contact.firstnameProperty());


        // Bindings

        streetLineTextField.textProperty().bindBidirectional(contact.addressProperty().getValue().streetLineProperty());
        postalCodeTextField.textProperty().bindBidirectional(contact.addressProperty().getValue().postalCodeProperty());
        cityTextField.textProperty().bindBidirectional(contact.addressProperty().getValue().cityProperty());

        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> setCountry(newv));

        groupChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> setGroup(newv));

        birthDatePicker.valueProperty().addListener(((obs, oldv, newv) -> setBirthDate(newv)));

        genderGroup.selectedToggleProperty().addListener((obs, oldv, newv) -> setGender());

        saveButton.setOnAction(evt -> saveContact());

        // Binding erreurs

        MapChangeListener<String, String> listener = changed -> {
            if (changed.wasAdded()) {
                System.out.println("oops, i have received an error message: " +
                        changed.getKey() + " " + changed.getValueAdded());
                addErrorMessage(changed.getKey(), changed.getValueAdded());
            } else if (changed.wasRemoved()) {
                removeErrorMessage(changed.getKey());
            }
        };
        contact.getErrors().addListener(listener);
    }

}

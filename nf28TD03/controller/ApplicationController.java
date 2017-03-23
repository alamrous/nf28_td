package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Contact;
import model.Country;
import model.Group;

public class ApplicationController implements Initializable {

    private Contact contact;

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

    private void saveForm() {
        System.out.println(contact.nameProperty().get());
        System.out.println(contact.firstnameProperty().get());

        System.out.println(contact.addressProperty().get().cityProperty().get());
        System.out.println(contact.addressProperty().get().streetLineProperty().get());
        System.out.println(contact.addressProperty().get().countryProperty().get());
        System.out.println(contact.addressProperty().get().postalCodeProperty().get());

        System.out.println(contact.birthdateProperty().get());
        System.out.println(contact.genderProperty().get());
        System.out.println(contact.groupProperty().get());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.contact = new Contact();

        initCountryList();
        initGroupList();
        initGenderGroup();
        initDatePicker();


        nameTextField.textProperty().bindBidirectional(contact.nameProperty());
        firstNameTextField.textProperty().bindBidirectional(contact.firstnameProperty());

        contact.addressProperty().getValue().streetLineProperty();

        streetLineTextField.textProperty().bindBidirectional(contact.addressProperty().getValue().streetLineProperty());
        postalCodeTextField.textProperty().bindBidirectional(contact.addressProperty().getValue().postalCodeProperty());
        cityTextField.textProperty().bindBidirectional(contact.addressProperty().getValue().cityProperty());

        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> setCountry(newv));

        groupChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> setGroup(newv));

        birthDatePicker.valueProperty().addListener(((obs, oldv, newv) -> setBirthDate(newv)));

        genderGroup.selectedToggleProperty().addListener((obs, oldv, newv) -> setGender());

        // Bindings
        saveButton.setOnAction(evt -> saveForm());

    }

}

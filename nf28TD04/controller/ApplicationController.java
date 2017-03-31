package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import model.Contact;
import model.Country;
import model.Group;
import model.Model;

public class ApplicationController implements Initializable {

    private Contact contact;
    private Model model;

    private Map<String, Control> fieldNamesMap;

    @FXML
    private TreeView<Object> groupTreeView;
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
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;


    private final class TextFieldTreeCellImpl extends TreeCell<Object> {

        private TextField textField;

        TextFieldTreeCellImpl() {
        }

        @Override
        public void startEdit() {
            super.startEdit();

            // Only groups are editabled
            if (getItem().getClass() != Group.class)
                return;

            if (textField == null) {
                createTextField();
            }

            setText(null);
            setGraphic(textField);
            textField.requestFocus();
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().toString());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {

                    String newName = textField.getText();

                    // Model modification
                    model.setGroupName((Group) getItem(), newName);

                    commitEdit(getItem());

                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }


    private void initTreeView() {
        TreeItem<Object> rootItem = new TreeItem<>("Fiche de contacts");
        rootItem.setExpanded(true);
        groupTreeView.setRoot(rootItem);
        groupTreeView.setEditable(true);
    }

    private void initCountryList() {
        countryChoiceBox.setItems(FXCollections.observableList(Country.getCountryList()));
        String DEFAULT_COUNTRY = "France";
        countryChoiceBox.getSelectionModel().select(DEFAULT_COUNTRY);
        setCountry(DEFAULT_COUNTRY);
    }

    private void initGroupList() {
//        groupChoiceBox.setItems(FXCollections.observableList(Group.getGroupNamesList()));
//        String DEFAULT_GROUP = "Parti socialiste";
//        groupChoiceBox.getSelectionModel().select(DEFAULT_GROUP);
//        setGroup(DEFAULT_GROUP);
    }

    private void initGenderGroup() {
        femaleRadio.setUserData("F");
        maleRadio.setUserData("M");
        setGender();
    }

    private void initDatePicker() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
        birthDatePicker.setDayCellFactory(dayCellFactory);


        birthDatePicker.setValue(LocalDate.now());
        setBirthDate(birthDatePicker.getValue());
    }

    private void setCountry(String country) {
        contact.addressProperty().get().countryProperty().setValue(country);
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
        fieldNamesMap.get(fieldname).getStyleClass().removeAll("error");
        fieldNamesMap.get(fieldname).setTooltip(null);
    }

    // Treeview handlers

    private void addGroupItem(Group group) {
        TreeItem<Object> grpItem = new TreeItem<>(group, new ImageView(group.getIcon()));
        groupTreeView.getRoot().getChildren().add(grpItem);
        groupTreeView.setCellFactory(param -> new TextFieldTreeCellImpl());
    }

    private void addGroup() {
        model.addGroup();
    }

    private void addContactItem() {

    }

    private void addItem() {

        TreeItem<Object> item = groupTreeView.getSelectionModel().getSelectedItem();

        if (item == null || Objects.equals(item.getValue(), "Fiche de contacts")) {
            // Root -> create a group
            addGroup();
        } else {
            addContactItem();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.contact = new Contact();
        this.model = new Model();

        fieldNamesMap = new LinkedHashMap<>();
        fieldNamesMap.put("name", nameTextField);
        fieldNamesMap.put("firstname", firstNameTextField);
        fieldNamesMap.put("birthdate", birthDatePicker);
        fieldNamesMap.put("streetLine", streetLineTextField);
        fieldNamesMap.put("postalCode", postalCodeTextField);
        fieldNamesMap.put("city", cityTextField);
        fieldNamesMap.put("country", countryChoiceBox);

        initTreeView();
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

//        groupChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> setGroup(newv));

        birthDatePicker.valueProperty().addListener(((obs, oldv, newv) -> setBirthDate(newv)));

        genderGroup.selectedToggleProperty().addListener((obs, oldv, newv) -> setGender());

        saveButton.setOnAction(evt -> saveContact());

        // Bindings Treeview buttons

        addButton.setOnAction(event -> addItem());


        // Binding erreurs

        MapChangeListener<String, String> errorsListener = changed -> {
            if (changed.wasAdded()) {
//                System.out.println("oops, i have received an error message: " +
//                        changed.getKey() + " " + changed.getValueAdded());
                addErrorMessage(changed.getKey(), changed.getValueAdded());
            } else if (changed.wasRemoved()) {
                removeErrorMessage(changed.getKey());
            }
        };
        contact.getErrors().addListener(errorsListener);

        // Bindings model

        ListChangeListener<Group> groupsListener = changed -> {
            if (changed.next()) {
                if (changed.wasAdded()) {
                    addGroupItem(changed.getAddedSubList().get(0));
                }
//            else if (changed.wasRemoved()) {
//                removeErrorMessage(changed.getKey());
//            }
            }
        };

        model.getGroups().addListener(groupsListener);
    }

}

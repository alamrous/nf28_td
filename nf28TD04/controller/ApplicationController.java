package controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Contact;
import model.Country;
import model.Group;
import model.Model;

import static javafx.scene.control.Alert.*;

public class ApplicationController implements Initializable {

    private Stage stage;

    private static String MALE_GENDER_LABEL = "M";
    private static String FEMALE_GENDER_LABEL = "F";

    private Contact editingContact;
    private Contact originalContact;
    private Model model;

    private BooleanProperty onEdition;

    private Map<String, Control> fieldNamesMap;

    @FXML
    private TreeView<Object> treeView;
    @FXML
    private VBox contactForm;
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
    private Button saveButton;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private MenuItem loadMenuItem;
    @FXML
    private MenuItem saveMenuItem;


    private final class TextFieldTreeCellImpl extends TreeCell<Object> {

        private TextField textField;
        /**
         * prevent the Enter event to be catched twice for entering edition mode and commiting the edition
         */
        private long editTime;

        TextFieldTreeCellImpl() {
        }

        @Override
        public void startEdit() {
            super.startEdit();

            editTime = System.nanoTime();

            // Only groups are editabled
            if (getItem().getClass() != Group.class)
                return;

            if (textField == null) {
                createTextField();
            } else {
                textField.setText(getString());
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
            if (textField != null)
                textField.setText(getString());
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

            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                // On blur/out of focus
                if (!newValue) {
                    cancelEdit();
                }
            });

            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER && (System.nanoTime() - editTime) > 100_000_000) {

                    String newName = textField.getText();

                    if (newName.isEmpty())
                        return;

                    // Model modification
                    model.setGroupName((Group) getItem(), newName);

                    commitEdit(getItem());

                    treeView.getRoot().getChildren().sort(Comparator.comparing(treeItemGroup -> ((Group) treeItemGroup.getValue()).getName()));
                    treeView.getSelectionModel().select(getTreeItem());


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
        treeView.setRoot(rootItem);
        treeView.setCellFactory(param -> new TextFieldTreeCellImpl());
    }

    private void initTextFields() {
        editingContact.initTextFields();
    }

    private void initCountryList() {
        String DEFAULT_COUNTRY = "France";
        countryChoiceBox.getSelectionModel().select(DEFAULT_COUNTRY);
    }

    private void initGenderGroup() {
        femaleRadio.setSelected(true);
    }

    private void initDatePicker() {
        birthDatePicker.setValue(LocalDate.now());
    }

    private void setCountry(String country) {
        editingContact.addressProperty().get().countryProperty().setValue(country);
    }

    private void setGroup(Group group) {
        editingContact.groupProperty().setValue(group);
    }

    private void setBirthDate(LocalDate date) {
        editingContact.birthdateProperty().setValue(date);
    }

    private void setGender() {
        String gender = genderGroup.getSelectedToggle().getUserData().toString();
        editingContact.genderProperty().setValue(
                gender.equals(MALE_GENDER_LABEL) ? Contact.MALE_GENDER_PROPERTY : Contact.FEMALE_GENDER_PROPERTY
        );
    }

    private void saveContact() {

        if (!editingContact.contactCanBeSaved()) {
            for (Object o : fieldNamesMap.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                String fieldname = entry.getKey().toString();
                Control field = (Control) entry.getValue();
                if (editingContact.getErrors().containsKey(fieldname)) {
                    field.requestFocus();
                    break;
                }
            }
            return;
        }

        // New contact
        if (treeView.getSelectionModel().getSelectedItem().getValue().getClass() == Group.class) {
            setGroup((Group) treeView.getSelectionModel().getSelectedItem().getValue());
            model.addContact(editingContact);
        } else {
            model.updateContact(originalContact, editingContact);
        }

    }

    private void saveOnFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvegarder la liste des contacts");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/saves"));
        File f = fileChooser.showSaveDialog(stage);
        if (f != null)
            model.saveOnFile(f);
    }

    private void loadOnFile() {
        model.getGroups().clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger la liste des contacts");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/saves"));
        File f = fileChooser.showOpenDialog(stage);
        if (f != null)
            model.loadFromFile(f);
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

    private void addGroup() {
        model.addGroup();
    }

    private void removeGroup(Group groupToRemove) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation - supprimer un groupe");
        alert.setHeaderText("Attention, cette opération est irréversible !");
        alert.setContentText("Êtes-vous sûr(e) de vouloir supprimer le groupe " + groupToRemove + " ?\n" +
                "Tous les contacts de ce groupe seront perdus !");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            model.removeGroup(groupToRemove);

            initTextFields();
            initCountryList();
            initGenderGroup();
            initDatePicker();
        }
    }

    private void addGroupItem(Group group) {
        TreeItem<Object> groupItem = new TreeItem<>(group, new ImageView(group.getIcon()));
        treeView.getRoot().getChildren().add(groupItem);
        treeView.getRoot().getChildren().sort(Comparator.comparing(treeItemGroup -> ((Group) treeItemGroup.getValue()).getName()));
    }

    private void removeGroupItem(Group group) {
        treeView.getRoot().getChildren().removeIf(treeItemGroup -> treeItemGroup.getValue().equals(group));
    }

    private void addContact() {
        onEdition.setValue(true);

        initTextFields();
        initCountryList();
        initGenderGroup();
        initDatePicker();
    }

    private void removeContact(Contact contactToRemove) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation - supprimer un contact");
        alert.setHeaderText("Attention, cette opération est irréversible !");
        alert.setContentText("Êtes-vous sûr(e) de vouloir supprimer le contact " + contactToRemove + " ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            model.removeContact(contactToRemove);

            initTextFields();
            initCountryList();
            initGenderGroup();
            initDatePicker();
        }
    }

    private void addContactItem(Contact contact) {
        TreeItem<Object> contactItem = new TreeItem<>(contact, new ImageView(contact.getIcon()));
        TreeItem<Object> groupParent = treeView.getRoot().getChildren().filtered(objectTreeItem -> objectTreeItem.getValue() == contact.groupProperty().getValue()).get(0);

        if (groupParent.getChildren().isEmpty())
            groupParent.setExpanded(true);

        groupParent.getChildren().add(contactItem);
        groupParent.getChildren().sort(Comparator.comparing(treeItemContact -> ((Contact) treeItemContact.getValue()).firstnameProperty().getValue()));
        treeView.getSelectionModel().select(contactItem);
    }

    private void removeContactItem(Contact contact) {
        TreeItem<Object> groupParent = treeView.getRoot().getChildren().filtered(objectTreeItem -> objectTreeItem.getValue() == contact.groupProperty().getValue()).get(0);
        groupParent.getChildren().removeIf(treeItemContact -> treeItemContact.getValue().equals(contact));
    }

    private void addContactObject() {
        TreeItem<Object> item = treeView.getSelectionModel().getSelectedItem();

        if (item == null || Objects.equals(item.getValue(), "Fiche de contacts")) {
            // Root -> create a group
            addGroup();
        } else {
            addContact();
        }
    }

    private void removeContactObject() {
        Object item = treeView.getSelectionModel().getSelectedItem().getValue();

        if (item.getClass() == Group.class) {
            removeGroup((Group) item);
        } else if (item.getClass() == Contact.class) {
            removeContact((Contact) item);
        }
    }

    private void initEditingContactWithExisting(Contact contact) {
        editingContact.firstnameProperty().setValue(contact.firstnameProperty().getValue());
        editingContact.nameProperty().setValue(contact.nameProperty().getValue());
        editingContact.addressProperty().getValue().streetLineProperty().setValue(contact.addressProperty().getValue().streetLineProperty().getValue());
        editingContact.addressProperty().getValue().postalCodeProperty().setValue(contact.addressProperty().getValue().postalCodeProperty().getValue());
        editingContact.addressProperty().getValue().cityProperty().setValue(contact.addressProperty().getValue().cityProperty().getValue());
        editingContact.addressProperty().getValue().countryProperty().setValue(contact.addressProperty().getValue().countryProperty().getValue());
        editingContact.birthdateProperty().setValue(contact.birthdateProperty().getValue());
        editingContact.genderProperty().setValue(contact.genderProperty().getValue());
        editingContact.groupProperty().setValue(contact.groupProperty().getValue());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editingContact = new Contact();
        model = new Model();

        fieldNamesMap = new LinkedHashMap<>();
        fieldNamesMap.put("name", nameTextField);
        fieldNamesMap.put("firstname", firstNameTextField);
        fieldNamesMap.put("birthdate", birthDatePicker);
        fieldNamesMap.put("streetLine", streetLineTextField);
        fieldNamesMap.put("postalCode", postalCodeTextField);
        fieldNamesMap.put("city", cityTextField);
        fieldNamesMap.put("country", countryChoiceBox);

        // Tree view
        initTreeView();

        // Countries
        countryChoiceBox.setItems(FXCollections.observableList(Country.getCountryList()));

        // Birthdate
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

        // Gender
        femaleRadio.setUserData(FEMALE_GENDER_LABEL);
        maleRadio.setUserData(MALE_GENDER_LABEL);

        // Handles the edition panel state (enabled/disabled)
        onEdition = new SimpleBooleanProperty(false);
        contactForm.setDisable(true);
        onEdition.addListener((observable, oldValue, newValue) -> contactForm.setDisable(!newValue));

        // Remove button
        removeButton.setDisable(true);





        // Bindings view

        final KeyCombination kcTabBack = new KeyCodeCombination(KeyCode.TAB, KeyCombination.SHIFT_DOWN);

        nameTextField.textProperty().bindBidirectional(editingContact.nameProperty());
        firstNameTextField.textProperty().bindBidirectional(editingContact.firstnameProperty());

        firstNameTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB && !kcTabBack.match(event)) {
                event.consume();
                streetLineTextField.requestFocus();
            }
        });

        streetLineTextField.textProperty().bindBidirectional(editingContact.addressProperty().getValue().streetLineProperty());

        streetLineTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kcTabBack.match(event)) {
                event.consume();
                firstNameTextField.requestFocus();
            }
        });

        postalCodeTextField.textProperty().bindBidirectional(editingContact.addressProperty().getValue().postalCodeProperty());
        cityTextField.textProperty().bindBidirectional(editingContact.addressProperty().getValue().cityProperty());

        countryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> setCountry(newv));

        countryChoiceBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB && !kcTabBack.match(event)) {
                event.consume();
                birthDatePicker.requestFocus();
            }
        });

        birthDatePicker.valueProperty().addListener(((obs, oldv, newv) -> setBirthDate(newv)));

        birthDatePicker.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (kcTabBack.match(event)) {
                event.consume();
                countryChoiceBox.requestFocus();
            }
        });

        genderGroup.selectedToggleProperty().addListener((obs, oldv, newv) -> setGender());

        saveButton.setOnAction(evt -> saveContact());

        // Bindings menu items

        saveMenuItem.setOnAction(event -> saveOnFile());

        loadMenuItem.setOnAction(event -> loadOnFile());

        // Bindings Treeview

        addButton.setOnAction(event -> addContactObject());

        removeButton.setOnAction(event -> removeContactObject());

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;

            boolean contactIsSelected = newValue.getValue().getClass() == Contact.class;
            onEdition.setValue(contactIsSelected);
            addButton.setDisable(contactIsSelected);
            removeButton.setDisable(treeView.getRoot() == newValue);

            if (contactIsSelected) {
                Object item = treeView.getSelectionModel().getSelectedItem().getValue();
                originalContact = (Contact) item;
                initEditingContactWithExisting(originalContact);
            }

        });

        treeView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DELETE)
                removeContactObject();
        });

        // Binding errors

        MapChangeListener<String, String> errorsListener = changed -> {
            if (changed.wasAdded()) {
                addErrorMessage(changed.getKey(), changed.getValueAdded());
            } else if (changed.wasRemoved()) {
                removeErrorMessage(changed.getKey());
            }
        };
        editingContact.getErrors().addListener(errorsListener);


        // Bindings model

        ListChangeListener<Contact> contactsListener = changed -> {
            if (changed.next()) {
                if (changed.wasAdded()) {
                    Contact newContact = changed.getAddedSubList().get(0);
                    addContactItem(newContact);
                } else if (changed.wasRemoved()) {
                    Contact oldContact = changed.getRemoved().get(0);
                    removeContactItem(oldContact);
                }
            }
        };

        ListChangeListener<Group> groupsListener = changed -> {
            if (changed.next()) {
                if (changed.wasAdded()) {
                    Group newGroup = changed.getAddedSubList().get(0);
                    addGroupItem(newGroup);
                    newGroup.getContacts().addListener(contactsListener);

                    if (newGroup.getContacts().size() > 0) {
                        for (Contact contact : newGroup.getContacts()) {
                            addContactItem(contact);
                        }

                    }

                }
            else if (changed.wasRemoved()) {
                    Group oldGroup = changed.getRemoved().get(0);
                    removeGroupItem(oldGroup);
            }
            }
        };

        model.getGroups().addListener(groupsListener);

        // Bindings on editing contact (model) -> view
        editingContact.birthdateProperty().addListener((observable, oldValue, newValue) -> birthDatePicker.setValue(newValue));
        editingContact.genderProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == Contact.MALE_GENDER_PROPERTY)
                maleRadio.setSelected(true);
            else
                femaleRadio.setSelected(true);
        });
        editingContact.addressProperty().getValue().countryProperty().addListener((observable, oldValue, newValue) -> countryChoiceBox.setValue(newValue));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

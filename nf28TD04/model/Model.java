package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Model {

    private ObservableList<Group> groups;

    public Model() {
        groups = FXCollections.observableArrayList();
    }

    private boolean groupExists(String name) {
        Iterator<Group> it = groups.iterator();
        boolean found = false;

        while (it.hasNext() && !found) {
            Group g = it.next();
            if (g.getName().equals(name))
                found = true;
        }

        return found;
    }

    private Group getGroup(String name) {
        Iterator<Group> it = groups.iterator();
        Group group = null;

        while (it.hasNext()) {
            Group g = it.next();
            if (g.getName().equals(name)) {
                group = g;
                break;
            }
        }

        return group;
    }

    public void addGroup() {
        if (!groupExists(Group.DEFAULT_GROUP_NAME)) {
            groups.add(new Group());
            return;
        }

        int i = 1;
        while (groupExists(Group.DEFAULT_GROUP_NAME + i))
            i++;

        groups.add(new Group(Group.DEFAULT_GROUP_NAME + i));
    }

    public void removeGroup(String groupName) {
        groups.remove(getGroup(groupName));
    }

    public void setGroupName(Group group, String name) {
        if (!groupExists(name))
            group.setName(name);
    }

    public List<String> getGroupNamesList() {
        List<String> groupList = new LinkedList<>();
        for (Group group : groups) {
            groupList.add(group.toString());
        }
        Collections.sort(groupList);
        return groupList;
    }

    public ObservableList<Group> getGroups() {
        return groups;
    }

    public void addContact(Contact contact) {

//        System.out.println("addContact");
//        System.out.println("address to clone : " + contact.addressProperty());

        Contact newContact = new Contact(contact);

//        System.out.println(newContact.firstnameProperty().get());
//        System.out.println(newContact.birthdateProperty().get());
//        System.out.println(newContact.genderProperty().get());
//        System.out.println(newContact.addressProperty().get().cityProperty().get());

//        contact.firstnameProperty().setValue("AAA");
//        contact.addressProperty().getValue().cityProperty().setValue("BBB");
//        contact.birthdateProperty().setValue(LocalDate.now().minusDays(5));
//        contact.genderProperty().setValue(Contact.MALE_GENDER_PROPERTY);
//
//        System.out.println(newContact.firstnameProperty().get());
//        System.out.println(newContact.birthdateProperty().get());
//        System.out.println(newContact.genderProperty().get());
//        System.out.println(newContact.addressProperty().get().cityProperty().get());

        newContact.groupProperty().getValue().addContact(newContact);

    }

    public void removeContact(Contact contact) {
        contact.groupProperty().getValue().getContacts().remove(contact);
    }

    public void updateContact(Contact contactToUpdate, Contact contactModel) {
        contactToUpdate.firstnameProperty().setValue(contactModel.firstnameProperty().getValue());
        contactToUpdate.nameProperty().setValue(contactModel.nameProperty().getValue());
        contactToUpdate.addressProperty().getValue().streetLineProperty().setValue(contactModel.addressProperty().getValue().streetLineProperty().getValue());
        contactToUpdate.addressProperty().getValue().postalCodeProperty().setValue(contactModel.addressProperty().getValue().postalCodeProperty().getValue());
        contactToUpdate.addressProperty().getValue().cityProperty().setValue(contactModel.addressProperty().getValue().cityProperty().getValue());
        contactToUpdate.addressProperty().getValue().countryProperty().setValue(contactModel.addressProperty().getValue().countryProperty().getValue());
        contactToUpdate.birthdateProperty().setValue(contactModel.birthdateProperty().getValue());
        contactToUpdate.genderProperty().setValue(contactModel.genderProperty().getValue());
        contactToUpdate.groupProperty().setValue(contactModel.groupProperty().getValue());

        contactToUpdate.groupProperty().getValue().getContacts().remove(contactToUpdate);
        contactToUpdate.groupProperty().getValue().getContacts().add(contactToUpdate);
    }

}

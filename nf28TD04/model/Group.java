package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Group {

    private ObservableList<Contact> contacts;

    private String name;

    Group(String groupName) {
        name = groupName;
        contacts = FXCollections.observableArrayList();
    }

    private Contact getContact(String id) {
        Iterator<Contact> it = contacts.iterator();
        Contact contact = null;

        while (it.hasNext()) {
            Contact c = it.next();
            if (c.getId().equals(id)) {
                contact = c;
                break;
            }
        }

        return contact;
    }

    String createContact() {
        Contact c = new Contact();
        contacts.add(c);
        return c.getId();
    }

    void removeContact(String id) {
        contacts.remove(getContact(id));
    }

    String getName() {
        return name;
    }

//    static private List<String> groupList = new ArrayList<>();

//    public static List<String> getGroupList() {
//        groupList.add("Parti républicain");
//        groupList.add("Parti démocrate");
//        groupList.add("Modem");
//        groupList.add("Parti communiste");
//        groupList.add("Parti anarchiste");
//        groupList.add("Parti écologiste");
//        groupList.add("Front national");
//        groupList.add("Parti socialiste");
//        Collections.sort(groupList);
//        return groupList;
//    }

}

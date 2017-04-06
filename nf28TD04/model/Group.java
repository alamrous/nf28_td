package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

public class Group implements Externalizable {

    static String DEFAULT_GROUP_NAME = "Nouveau groupe";
    private static Image DEFAULT_GROUP_ICON = new Image("file:view/group.png");

    private ObservableList<Contact> contacts;

    private String name;
    private Image icon;

    @SuppressWarnings("WeakerAccess")
    public Group() {
        this(DEFAULT_GROUP_NAME);
    }

    Group(String groupName) {
        name = groupName;
        icon = DEFAULT_GROUP_ICON;
        contacts = FXCollections.observableArrayList();
    }

//    private Contact getContact(String id) {
//        Iterator<Contact> it = contacts.iterator();
//        Contact contact = null;
//
//        while (it.hasNext()) {
//            Contact c = it.next();
//            if (c.getId().equals(id)) {
//                contact = c;
//                break;
//            }
//        }
//
//        return contact;
//    }

    void addContact(Contact contact) {
        contacts.add(contact);
    }

//    public void removeContact(String id) {
//        contacts.remove(getContact(id));
//    }

    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }

    public ObservableList<Contact> getContacts() {
        return contacts;
    }

    void setName(String name) {
        this.name = name;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeObject(contacts.toArray());
//        for (Contact contact : contacts) {
//            out.writeObject(contact);
//        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         name = in.readUTF();

        Object contacts;
        contacts = in.readObject();
        for (Object o : ((Object[]) contacts)) {
            Contact contact = (Contact) o;
            contact.groupProperty().setValue(this);
            this.contacts.add(contact);
        }


//        Contact[] contacts;
//        contacts = (Contact[])in.readObject();
//        System.out.println(in.readObject().getClass());
//        this.contacts.addAll(Arrays.asList(contacts));

    }
}

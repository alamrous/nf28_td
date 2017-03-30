package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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

    void createGroup(String groupName) {
        if (!groupExists(groupName))
            groups.add(new Group(groupName));
    }

    void removeGroup(String groupName) {
        groups.remove(getGroup(groupName));
    }

    public List<String> getGroupList() {
//        String[] a = (String[]) groups.toArray();
        List<String> groupList = Arrays.asList((String[]) groups.toArray());
        Collections.sort(groupList);
        return groupList;
    }

}

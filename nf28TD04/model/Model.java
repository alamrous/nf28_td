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
//        String[] a = (String[]) groups.toArray();
        List<String> groupList = Arrays.asList((String[]) groups.toArray());
        Collections.sort(groupList);
        return groupList;
    }

    public ObservableList<Group> getGroups() {
        return groups;
    }

}

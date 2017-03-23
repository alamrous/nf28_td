package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {

    static private List<String> groupList = new ArrayList<>();

    public static List<String> getGroupList() {
        groupList.add("Parti républicain");
        groupList.add("Parti démocrate");
        groupList.add("Modem");
        groupList.add("Parti communiste");
        groupList.add("Parti anarchiste");
        groupList.add("Parti écologiste");
        groupList.add("Front national");
        groupList.add("Parti socialiste");
        Collections.sort(groupList);
        return groupList;
    }

}

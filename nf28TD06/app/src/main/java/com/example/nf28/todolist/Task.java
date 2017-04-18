package com.example.nf28.todolist;

import android.util.SparseArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Task {

    static final int LOW_PRIORITY = 0;
    static final int MEDIUM_PRIORITY = 1;
    static final int HIGH_PRIORITY = 2;

    static final int BEFORE_STATUS = 0;
    static final int DURING_STATUS = 1;
    static final int AFTER_STATUS = 2;

    static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    private static final SparseArray<String> statusMap = new SparseArray<String>() {
        {
            append(BEFORE_STATUS, "non effectuée");
            append(DURING_STATUS, "en cours");
            append(AFTER_STATUS, "terminée");
        }
    };

    private static final SparseArray<String> prioritysMap = new SparseArray<String>() {
        {
            append(LOW_PRIORITY, "faible");
            append(MEDIUM_PRIORITY, "moyenne");
            append(HIGH_PRIORITY, "élevée");
        }
    };

    private String name;
    private int status;
    private int priority;
    private Date deadline;

    Task(String name, int status, int priority, Date deadline) {
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    int getPriority() {
        return priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    String getPriorityLabel(boolean firstCap) {
        String res = prioritysMap.get(priority);
        if (!firstCap)
            return res;
        return res.substring(0, 1).toUpperCase() + res.substring(1);
    }

    String getStatusLabel(boolean firstCap) {
        String res = statusMap.get(status);
        if (!firstCap)
            return res;
        return res.substring(0, 1).toUpperCase() + res.substring(1);
    }

    String getDeadlineFormated() {
        return dateFormat.format(deadline);
    }
}
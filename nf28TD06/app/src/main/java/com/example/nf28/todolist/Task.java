package com.example.nf28.todolist;

import java.util.Calendar;
import java.util.Date;

public class Task {

    public static final int LOW_PRIORITY = 0;
    public static final int MEDIUM_PRIORITY = 1;
    public static final int HIGH_PRIORITY = 2;

    private String name = "";
    private int status;
    private int priority;
    private Date deadline = new Date();

}

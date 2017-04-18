package com.example.nf28.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShowTasksActivity extends AppCompatActivity {

    private AdapterTask adapter;

    // identifiant de la requête
    private final static int ADD_TASK = 0;

    // identifiant de la chaîne qui contient le résultat
    final static String NAME = "com.example.nf28.todolist.name";
    final static String STATUS = "com.example.nf28.todolist.status";
    final static String PRIORITY = "com.example.nf28.todolist.priority";
    final static String DATE = "com.example.nf28.todolist.date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tasks);

        List<Task> tasksList = new ArrayList<>();
        try {
            tasksList.add(
                    new Task(
                            "Aller en EI03",
                            Task.BEFORE_STATUS,
                            Task.HIGH_PRIORITY,
                            DateFormat.getDateInstance(DateFormat.SHORT).parse("16/04/2017")
                    )
            );
            tasksList.add(
                    new Task(
                            "Donner la bouffe à Paf",
                            Task.BEFORE_STATUS,
                            Task.MEDIUM_PRIORITY,
                            DateFormat.getDateInstance(DateFormat.SHORT).parse("20/04/2017")
                    )
            );
            tasksList.add(
                    new Task(
                            "Téléphoner à mamie, mais cette tâche est tellement longue qu'elle s'étend sur plusieurs lignes nom de Dieu !",
                            Task.BEFORE_STATUS,
                            Task.LOW_PRIORITY,
                            DateFormat.getDateInstance(DateFormat.SHORT).parse("16/04/2017")
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView tasksListView = (ListView) findViewById(R.id.tasksListView);
        adapter = new AdapterTask(this, R.layout.row_task, tasksList);
        tasksListView.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowTasksActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_TASK) {
            if (resultCode == RESULT_OK) {
                try {
                    adapter.add(new Task(
                            data.getStringExtra(ShowTasksActivity.NAME),
                            Integer.parseInt(data.getStringExtra(ShowTasksActivity.STATUS)),
                            Integer.parseInt(data.getStringExtra(ShowTasksActivity.PRIORITY)),
                            Task.dateFormat.parse(data.getStringExtra(ShowTasksActivity.DATE))
                    ));

                    Toast.makeText(this, "Tâche ajoutée ! :)", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Les modifications n'ont pas été sauvegardées", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
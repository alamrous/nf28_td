package com.example.nf28.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ShowTasksActivity extends AppCompatActivity {

    private List<Task> tasksList;

    // identifiant de la requête
    public final static int ADD_TASK = 0;
    //    // identifiant de la chaine qui contient le résultat
    public final static String NAME = "com.example.addTask.name";
    public final static String STATUS = "com.example.addTask.status";
    public final static String PRIORITY = "com.example.addTask.priority";
    public final static String DATE = "com.example.addTask.date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tasks);

        tasksList = new ArrayList<>();
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
        final AdapterTask adapter = new AdapterTask(this, R.layout.row_task, tasksList);
        tasksListView.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowTasksActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK);

//                adapter.add("TEST !!!");

//                Log.d("list", tasksList.toString());

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_TASK) {
            if (resultCode == RESULT_OK) {
                // Récupérer ici le résultat dans la partie Extra de l’intent
                // à l’aide d’une méthode de type getExtra
            }
        }
    }
}

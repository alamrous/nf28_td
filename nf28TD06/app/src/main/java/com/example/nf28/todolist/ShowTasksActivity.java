package com.example.nf28.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowTasksActivity extends AppCompatActivity {

//    1) Créer une activité permettant d’afficher une liste de taâches simple avec seulement le nom
//    de chaque tâche. On testera à l’aide d’un tableau de tâches prédéterminées. On ajoutera
//    un bouton en bas de l’écran qui servira par la suite à ajouter une nouvelle tâche.

    private List<String> tasksList;

    // identifiant de la requête
    public final static int ADD_TASK = 0;
    //    // identifiant de la chaine qui contient le résultat
    public final static String NAME ="com.example.addTask.name";
    public final static String STATUS ="com.example.addTask.status";
    public final static String PRIORITY ="com.example.addTask.priority";
    public final static String DATE ="com.example.addTask.date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tasks);

        tasksList = new ArrayList<>();
        tasksList.add("Aller en EI03");
        tasksList.add("Donner la bouffe à Paf");
        tasksList.add("Téléphoner à mamie");
        tasksList.add("Insulter le vilain voisin");


        ListView tasksListView = (ListView) findViewById(R.id.tasksListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        tasksListView.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowTasksActivity.this,
                        AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK);

            }
        });
    }


}

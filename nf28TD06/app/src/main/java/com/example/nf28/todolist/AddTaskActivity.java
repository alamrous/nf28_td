package com.example.nf28.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private ScrollView scrollView;

    private EditText taskName;
    private RadioGroup taskStatus;
    private RadioGroup taskPriority;
    private DatePicker deadlinePicker;

    private RadioButton beforeStatus;
    private RadioButton lowPriority;

    private Button cancelButton;
    private Button clearButton;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        scrollView = (ScrollView) findViewById(R.id.add_task_scrollView);

        taskName = (EditText) findViewById(R.id.taskName);

        taskStatus = (RadioGroup) findViewById(R.id.taskStatus);
        taskPriority = (RadioGroup) findViewById(R.id.taskPriority);

        deadlinePicker = (DatePicker) findViewById(R.id.deadlinePicker);
        deadlinePicker.setMinDate(System.currentTimeMillis() - 1000);

        beforeStatus = (RadioButton) findViewById(R.id.taskStatusButtonBefore);
        lowPriority = (RadioButton) findViewById(R.id.taskPriorityLow);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        addButton = (Button) findViewById(R.id.okButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                setResult(RESULT_CANCELED, result);
                finish();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taskName.setText("");
                beforeStatus.setChecked(true);
                lowPriority.setChecked(true);

                deadlinePicker.init(
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        null
                );

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (taskName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Il faut nommer la tâche !", Toast.LENGTH_SHORT).show();
                    taskName.requestFocus();
                    scrollView.scrollTo(0, taskName.getScrollY());
                    return;
                }

                int year = deadlinePicker.getYear();
                int month = deadlinePicker.getMonth();
                int day = deadlinePicker.getDayOfMonth();

                Calendar c = Calendar.getInstance();
                c.set(year, month, day, 0, 0);
                DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                String formatedDate = simpleDateFormat.format(c.getTime());


                String infoTask = taskName.getText().toString() +
                        " " +
                        ((RadioButton) findViewById(
                                taskStatus.getCheckedRadioButtonId()
                        )).getText().toString() +
                        " " +
                        ((RadioButton) findViewById(
                                taskPriority.getCheckedRadioButtonId()
                        )).getText().toString() +
                        " " + deadlinePicker.getDayOfMonth() +
                        " " + deadlinePicker.getMonth() +
                        " " + deadlinePicker.getYear();


                Toast.makeText(getApplicationContext(), infoTask, Toast.LENGTH_LONG).show();





                Toast.makeText(getApplicationContext(), formatedDate, Toast.LENGTH_LONG).show();


//                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
//                String deadline = dateFormat.format(new Date(Long.parseLong(deadlinePicker.toString())));

//                Toast.makeText(getApplicationContext(), deadline, Toast.LENGTH_LONG).show();

//                Intent result = new Intent();
//                result.putExtra(ShowTasksActivity.NAME, taskName.getText().toString());
//                result.putExtra(ShowTasksActivity.NAME,  taskName.getText().toString());
//                result.putExtra(ShowTasksActivity.NAME,  taskName.getText().toString());
//                result.putExtra(ShowTasksActivity.NAME,  taskName.getText().toString());
//                setResult(RESULT_OK, result);
                finish();


            }
        });

    }
}

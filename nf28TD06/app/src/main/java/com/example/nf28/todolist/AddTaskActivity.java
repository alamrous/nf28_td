package com.example.nf28.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskName;
    private RadioGroup taskStatus;
    private RadioGroup taskPriority;
    private DatePicker deadlinePicker;

    private RadioButton beforeStatus;
    private RadioButton lowPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName = (EditText) findViewById(R.id.taskName);
        taskStatus = (RadioGroup) findViewById(R.id.taskStatus);
        taskPriority = (RadioGroup) findViewById(R.id.taskPriority);
        deadlinePicker = (DatePicker) findViewById(R.id.deadlinePicker);

        beforeStatus = (RadioButton) findViewById(R.id.taskStatusButtonBefore);
        lowPriority = (RadioButton) findViewById(R.id.taskPriorityLow);

//        int   day  = datePicker.getDayOfMonth();
//        int   month= datePicker.getMonth();
//        int   year = datePicker.getYear();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        String formatedDate = sdf.format(new Date(year, month, day));

        Button addButton = (Button) findViewById(R.id.okButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String infoTask = ((TextView) findViewById(R.id.taskName)).getText().toString() +
                        " " +
                        ((RadioButton) findViewById(
                                ((RadioGroup) findViewById(R.id.taskStatus)).getCheckedRadioButtonId()
                        )).getText().toString() +
                        " " +
                        ((RadioButton) findViewById(
                                ((RadioGroup) findViewById(R.id.taskPriority)).getCheckedRadioButtonId()
                        )).getText().toString() +
                        " " +
                        ((DatePicker) findViewById(R.id.deadlinePicker)).getDayOfMonth() +
                        " " +
                        ((DatePicker) findViewById(R.id.deadlinePicker)).getMonth() +
                        " " +
                        ((DatePicker) findViewById(R.id.deadlinePicker)).getYear();


                Toast.makeText(getApplicationContext(), infoTask,
                        Toast.LENGTH_LONG).show();

//                DateFormat.getDateInstance().parse()


//                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
//                deadline = dateFormat.format(new Date(Long.parseLong(deadline)));


                Intent result = new Intent();
                result.putExtra(ShowTasksActivity.NAME,  taskName.getText().toString());
//                result.putExtra(ShowTasksActivity.NAME,  taskName.getText().toString());
//                result.putExtra(ShowTasksActivity.NAME,  taskName.getText().toString());
//                result.putExtra(ShowTasksActivity.NAME,  taskName.getText().toString());
                setResult(RESULT_OK, result);
                finish();


            }
        });

        Button eraseButton = (Button) findViewById(R.id.eraseButton);
        eraseButton.setOnClickListener(new View.OnClickListener() {
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

    }
}

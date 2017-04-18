package com.example.nf28.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddTaskActivity extends AppCompatActivity {

    private static Map<String, String> statusMap = new HashMap<>();
    private static Map<String, String> priorityMap = new HashMap<>();

    private ScrollView scrollView;

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

        scrollView = (ScrollView) findViewById(R.id.add_task_scrollView);

        taskName = (EditText) findViewById(R.id.taskName);

        taskStatus = (RadioGroup) findViewById(R.id.taskStatus);
        taskPriority = (RadioGroup) findViewById(R.id.taskPriority);

        deadlinePicker = (DatePicker) findViewById(R.id.deadlinePicker);
        deadlinePicker.setMinDate(System.currentTimeMillis() - 1000);

        beforeStatus = (RadioButton) findViewById(R.id.taskStatusButtonBefore);
        lowPriority = (RadioButton) findViewById(R.id.taskPriorityLow);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        Button addButton = (Button) findViewById(R.id.okButton);

        final String BEFORE_STATUS_LABEL = getResources().getText(R.string.newtask_before_state).toString();
        final String DURING_STATUS_LABEL = getResources().getText(R.string.newtask_during_state).toString();
        final String AFTER_STATUS_LABEL = getResources().getText(R.string.newtask_after_state).toString();

        statusMap.put(BEFORE_STATUS_LABEL, Integer.toString(Task.BEFORE_STATUS));
        statusMap.put(DURING_STATUS_LABEL, Integer.toString(Task.DURING_STATUS));
        statusMap.put(AFTER_STATUS_LABEL, Integer.toString(Task.AFTER_STATUS));

        final String LOW_PRIORITY_LABEL = getResources().getText(R.string.newtask_low_priority).toString();
        final String MEDIUM_PRIORITY_LABEL = getResources().getText(R.string.newtask_medium_priority).toString();
        final String HIGH_PRIORITY_LABEL = getResources().getText(R.string.newtask_high_priority).toString();

        priorityMap.put(LOW_PRIORITY_LABEL, Integer.toString(Task.LOW_PRIORITY));
        priorityMap.put(MEDIUM_PRIORITY_LABEL, Integer.toString(Task.MEDIUM_PRIORITY));
        priorityMap.put(HIGH_PRIORITY_LABEL, Integer.toString(Task.HIGH_PRIORITY));


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

                // No name given
                String name = taskName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Il faut nommer la tâche !", Toast.LENGTH_SHORT).show();
                    taskName.requestFocus();
                    scrollView.scrollTo(0, taskName.getScrollY());
                    return;
                }


                // Deadline
                int year = deadlinePicker.getYear();
                int month = deadlinePicker.getMonth();
                int day = deadlinePicker.getDayOfMonth();

                Calendar c = Calendar.getInstance();
                c.set(year, month, day, 0, 0);
                String formatedDate = Task.dateFormat.format(c.getTime());

                // Status
                String status = ((RadioButton) findViewById(
                        taskStatus.getCheckedRadioButtonId()
                )).getText().toString();

                // Priority
                String priority = ((RadioButton) findViewById(
                        taskPriority.getCheckedRadioButtonId()
                )).getText().toString();

                Intent result = new Intent();
                result.putExtra(ShowTasksActivity.NAME, taskName.getText().toString());
                result.putExtra(ShowTasksActivity.DATE, formatedDate);
                result.putExtra(ShowTasksActivity.STATUS, statusMap.get(status));
                result.putExtra(ShowTasksActivity.PRIORITY, priorityMap.get(priority));
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }
}

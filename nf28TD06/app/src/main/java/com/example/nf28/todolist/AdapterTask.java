package com.example.nf28.todolist;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterTask extends ArrayAdapter<Task> {

    private static final SparseIntArray priorityColorMap = new SparseIntArray() {
        {

            append(Task.LOW_PRIORITY, Color.rgb(25, 153, 2)); // Green
            append(Task.MEDIUM_PRIORITY, Color.rgb(188, 128, 31)); // Orange
            append(Task.HIGH_PRIORITY, Color.rgb(206, 12, 7)); // Red
        }
    };

    AdapterTask(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_task, parent, false);
        }

        Task task = getItem(position);

        if (task != null) {

            TextView taskName = (TextView) v.findViewById(R.id.taskName);
            TextView taskDeadline = (TextView) v.findViewById(R.id.taskDeadline);
            TextView taskStatus = (TextView) v.findViewById(R.id.taskStatus);
            TextView taskPriority = (TextView) v.findViewById(R.id.taskPriority);

            taskName.setText(task.getName());
            taskDeadline.setText(task.getDeadlineFormated());
            taskStatus.setText(task.getStatusLabel(true));

            taskPriority.setTextColor(priorityColorMap.get(task.getPriority()));
            taskPriority.setText(task.getPriorityLabel(true));
        }

        return v;
    }
}
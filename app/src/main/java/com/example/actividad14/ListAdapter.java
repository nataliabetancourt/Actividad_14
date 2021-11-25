package com.example.actividad14;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter {

    public ListAdapter(Context context, ArrayList<Task> tasks){
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get task to work with
        Task task = (Task) getItem(position);

        //Load view
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        convertView = inflater.inflate(R.layout.task_item, parent, false);

        //Name graphic elements
        TextView taskTitle = convertView.findViewById(R.id.taskTitle);
        TextView timeTxt = convertView.findViewById(R.id.timeTxt);
        TextView taskDesTxt = convertView.findViewById(R.id.taskDesTxt);
        TextView stateTxt = convertView.findViewById(R.id.stateTxt);
        Button deleteBtn = convertView.findViewById(R.id.deleteBtn);
        TextView stateBtn = convertView.findViewById(R.id.stateBtn);

        //Assign values to elements
        taskTitle.setText(task.getName());
        timeTxt.setText(task.getTime());
        taskDesTxt.setText(task.getDescription());
        stateTxt.setText(task.getState());

        //Make buttons work
        stateBtn.setOnClickListener((v)->{
            stateBtn.setSelected(!stateBtn.isSelected());
            stateTxt.setText("Completado");
        });

        deleteBtn.setOnClickListener((v)->{
            //Database
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference taskRef = db.getReference("users/" + task.getUserKey() + "/tasks");

            taskRef.setValue(null);
        });

        return convertView;
    }
}

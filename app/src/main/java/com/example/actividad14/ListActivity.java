package com.example.actividad14;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private EditText taskName, taskDes;
    private Button addTaskBtn;
    private String userID, state, time;
    private ListView listTxt;
    private ArrayList<Task> tasks;
    private SimpleDateFormat dateFormat;
    private FirebaseDatabase db;
    private ListAdapter taskAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Graphics
        taskName = findViewById(R.id.taskName);
        taskDes = findViewById(R.id.taskDes);
        addTaskBtn = findViewById(R.id.addTaskBtn);
        listTxt =  findViewById(R.id.listTxt);

        //Elements
        tasks = new ArrayList<Task>();
        taskAdapter = new ListAdapter(this, tasks);
        listTxt.setAdapter(taskAdapter);

        //User id received from previous screen
        Intent main = getIntent();
        userID = main.getStringExtra("userKey");
        state = "Pendiente";

        db = FirebaseDatabase.getInstance();

        addTaskBtn.setOnClickListener((v)->{
            //If the inputs aren't full, let user know
            if (taskName.getText().toString().isEmpty() || taskDes.getText().toString().isEmpty()){
                Toast.makeText(this, "Llena los campos", Toast.LENGTH_SHORT).show();
            } else {
                //Get reference (create task in user file)
                //Database reference
                DatabaseReference taskRef = db.getReference("users/" + userID + "/tasks");
                DatabaseReference newTaskRef = taskRef.push();

                //Set time that the task was added
                settingTime();

                //Create task and add to database
                Task task = new Task(
                        taskName.getText().toString(),
                        taskDes.getText().toString(),
                        newTaskRef.getKey(),
                        state,
                        time,
                        userID);
                newTaskRef.setValue(task);
            }
        });

        getTasks();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void settingTime(){
        dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm");
        time = dateFormat.format(new Date());
    }

    public void getTasks(){
        DatabaseReference taskRef = db.getReference("users/" + userID + "/tasks");
        taskRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot tasks) {
                updateList(tasks);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Firebase error: " + error.getDetails());
            }
        });
    }

    public void updateList(DataSnapshot tasks){
        this.tasks.clear();
        for (DataSnapshot taskSnapshot: tasks.getChildren()){
            Task task = taskSnapshot.getValue(Task.class);
            this.tasks.add(task);
        }
        taskAdapter.notifyDataSetChanged();
    }
}


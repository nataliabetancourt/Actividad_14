package com.example.actividad14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText usernameTxt;
    private Button signInBtn;
    private FirebaseDatabase db;
    private User userRef;
    private Intent listScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Graphics
        usernameTxt = findViewById(R.id.usernameTxt);
        signInBtn = findViewById(R.id.signInBtn);

        db = FirebaseDatabase.getInstance();

        compareUsers();

        //List screen intent
        listScreen = new Intent(this, ListActivity.class);

        //Click on sign in button
        signInBtn.setOnClickListener((v)->{

            //Check that the username has been filled and has no spaces
            if (usernameTxt.getText().toString().isEmpty() || usernameTxt.getText().toString().contains(" ")){
                Toast.makeText(this, "Escribe un nombre de usuario, sin espacios", Toast.LENGTH_SHORT).show();

                //Create user when database is empty
            } else if (userRef == null){
                createUser();

                //Compare user input with users database
            } else if (usernameTxt.getText().toString().equals(userRef.getUsername())){
                //Switch to list screen
                //Send id to see tasks inside of user file
                listScreen.putExtra("userKey", userRef.getId());
                startActivity(listScreen);

                //Create new users
            } else {
                createUser();
            }
        });
    }

    public void compareUsers(){
        DatabaseReference usersRef = db.getReference("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    userRef = userSnapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void createUser(){
        //Database reference
        DatabaseReference userRef = db.getReference("users");
        DatabaseReference newUserRef = userRef.push();

        //Create user and add to database
        User user = new User(usernameTxt.getText().toString(), newUserRef.getKey());
        newUserRef.setValue(user);

        //Switch to list screen
        //Send id to create task inside of user file
        listScreen.putExtra("userKey", user.getId());
        startActivity(listScreen);
    }
}
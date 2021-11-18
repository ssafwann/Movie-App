package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextView usernameLabel, ageLabel, creditsLabel, passwordLabel;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        usernameLabel = findViewById(R.id.username_profile);
        passwordLabel = findViewById(R.id.password_profile);
        ageLabel = findViewById(R.id.age_profile);
        creditsLabel = findViewById(R.id.credits_profile);

        // show all user data
        showAllUserData();
    }


    private void readData() {
        ref = FirebaseDatabase.getInstance().getReference("users");

    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_password = intent.getStringExtra("password");
        String user_age = intent.getStringExtra("age");
        String user_credits = intent.getStringExtra("credits");

        usernameLabel.setText(user_username);
        passwordLabel.setText(user_password);
        ageLabel.setText(user_age);
        creditsLabel.setText(user_credits);


    }
}
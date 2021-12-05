package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance("https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextInputLayout regUsername = findViewById(R.id.reg_username);
        TextInputLayout regPassword = findViewById(R.id.reg_password);
        TextInputLayout regAge = findViewById(R.id.reg_age);
        Button signUpBtn = (Button) findViewById(R.id.sign_up_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get all values
                String username = regUsername.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                String age = regAge.getEditText().getText().toString();
                int credits = 150; // give user 150 credits for registration
                String id = reference.push().getKey();

                if (username.isEmpty() && password.isEmpty() && age.isEmpty()) {
                    regUsername.setError("Field cannot be empty");
                    regPassword.setError("Field cannot be empty");
                    regAge.setError("Field cannot be empty");
                    return;
                }

                if (password.length() < 6) {
                    regPassword.setError("Enter a longer password");
                    return;
                }

                regPassword.setErrorEnabled(false);
                regUsername.setErrorEnabled(false);
                regAge.setErrorEnabled(false);

                User helperClass = new User(username,password,age,credits,id);
                reference.child(username).setValue(helperClass);
                Toast toast = Toast.makeText(getApplicationContext(),"Account created, you may login",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void goBack(View view) {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }

}
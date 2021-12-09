/*
    The class is responsible for letting users log in to the app
    They can also chose to register an account from this class
 */
package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextInputLayout username;
    TextInputLayout password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // button that sends to you to the sign up page
        Button button = (Button) findViewById(R.id.signup_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Button logInBtn = (Button) findViewById(R.id.log_in_btn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
            }
        });
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        // Validate the user login info
        if (!validatePassword() || !validateUsername()) {
            return;
        }
        else {
            isUser();
        }
    }

    // function responsible for checking user credentials and sending them to the home page if valid
    private void isUser() {
        String userEnteredUsername = username.getEditText().getText().toString().trim();
        String userEnteredPassword= password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {
                        username.setError(null);
                        username.setErrorEnabled(false);

                        String userNameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String ageFromDB = dataSnapshot.child(userEnteredUsername).child("age").getValue(String.class);
                        int creditsFromDB = dataSnapshot.child(userEnteredUsername).child("credits").getValue(int.class);
                        String idFromDB = dataSnapshot.child(userEnteredUsername).child("id").getValue(String.class);

                        // make a user object with all of the user's data from firebase and pass to
                        User user = new User(userNameFromDB, passwordFromDB, ageFromDB, creditsFromDB, idFromDB);
                        Intent intent = new Intent(getApplicationContext(),HomePage.class);
                        intent.putExtra("user", user);

                        Toast toast = Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(intent);
                    }
                    else {
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("Username doesn't exist");
                    username.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
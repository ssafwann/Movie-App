package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class UserProfile extends AppCompatActivity  {

    DrawerLayout drawer;
    TextView usernameLabel, creditsLabel;
    Button orderHistory;
    DatabaseReference ref;
    User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        createLayout();
        getLoggedUser();

        usernameLabel = findViewById(R.id.username_profile);
        creditsLabel = findViewById(R.id.credits_profile);

        // show all user data
        showAllUserData();

        orderHistory = (Button) findViewById(R.id.view_purchases);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplication(), OrderHistory.class);
                intent.putExtra("user", loggedInUser);
                startActivity(intent);
            }
        });
    }

    public void createLayout() {
        // toolbar and navigation drawer code
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        getSupportActionBar().setTitle(null);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    public void getLoggedUser() {
        Intent intent = getIntent();
        loggedInUser = (User) getIntent().getSerializableExtra("user");
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent (getApplicationContext(), HomePage.class);
                intent.putExtra("user", loggedInUser);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_cart:
                Intent intent2 = new Intent (getApplicationContext(), Cart.class);
                intent2.putExtra("user", loggedInUser);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                logout(UserProfile.this);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void readData() {
        ref = FirebaseDatabase.getInstance().getReference("users");

    }

    private void showAllUserData() {
        String user_username = loggedInUser.getUsername();
        int user_credits = loggedInUser.getCredits();

        usernameLabel.setText(user_username);
        creditsLabel.setText(String.valueOf(user_credits));

    }
}
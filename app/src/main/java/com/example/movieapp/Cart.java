package com.example.movieapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart extends AppCompatActivity {

    DrawerLayout drawer;
    User loggedInUser;
    Button addCredits;
    DatabaseReference reference = FirebaseDatabase.getInstance("https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        createLayout();
        getLoggedUser();

        addCredits = (Button) findViewById(R.id.crediting);
        addCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newCredits = loggedInUser.getCredits() + 50;
                loggedInUser.setCredits(newCredits);
                Toast toast = Toast.makeText(getApplication(), "credits added" ,Toast.LENGTH_SHORT);
                toast.show();
                reference.child(loggedInUser.getUsername()).child("credits").setValue(newCredits);
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
                Intent intent2 = new Intent (getApplicationContext(), UserProfile.class);
                intent2.putExtra("user", loggedInUser);
                startActivity(intent2);
                break;
            case R.id.nav_cart:
                break;
            case R.id.nav_logout:
                logout(Cart.this);
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
}

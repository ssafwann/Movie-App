package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    DrawerLayout drawer;
    RecyclerView recyclerView;
    MovieAdapter mainAdapter;
    User loggedInUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getLoggedUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // RecyclerView code with firebase
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MovieModel> options =
                new FirebaseRecyclerOptions.Builder<MovieModel>()
                        .setQuery(FirebaseDatabase.getInstance(
                                "https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference().child("movie"), MovieModel.class).build();
        mainAdapter = new MovieAdapter(options);
        recyclerView.setAdapter(mainAdapter);
        onRecyclerItemClick();

        // drawer and navigation bar code
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        getSupportActionBar().setTitle(null);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }


    public void getLoggedUser() {
        Intent intent = getIntent();
        loggedInUser = (User) getIntent().getSerializableExtra("user");
    }

    public void onRecyclerItemClick() {
        mainAdapter.setOnModelClickListener(movieModel -> {
            Intent intent = new Intent (getApplication(), MovieDetails.class);

            String test =  movieModel.getName() + " Selected";

            MovieModel movie = new MovieModel(movieModel.getGenre(),movieModel.getImage(), movieModel.getName(),
            movieModel.getPrice(),movieModel.getRating(),movieModel.getRelease(),movieModel.getShortDesc(),
                    movieModel.getRuntime(), movieModel.getWriter(), movieModel.getDirector(), movieModel.getCast());

            intent.putExtra("movie", movie);

            Toast toast = Toast.makeText(getApplication(), test ,Toast.LENGTH_SHORT);
            toast.show();

            startActivity(intent);
        });
    }

    // for the search bar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent (getApplicationContext(), UserProfile.class);
                intent2.putExtra("user", loggedInUser);
                startActivity(intent2);
                break;
            case R.id.nav_cart:
                Intent intent3 = new Intent (getApplicationContext(), Cart.class);
                intent3.putExtra("user", loggedInUser);
                startActivity(intent3);
                break;
            case R.id.nav_logout:
                logout(HomePage.this);
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

}
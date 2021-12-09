/*
    The class which is responsible for handling all the cart related stuff
    This class is accessed from the navigation drawer
 */
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    DrawerLayout drawer;
    User loggedInUser;
    RecyclerView recyclerView;
    CartAdapter mainAdapter;
    TextView totalPrice, totalQuantity;
    Button purchase;
    final Long[] total_price = {0L};
    int newCredits;
    List<MovieModel> allCartItems = new ArrayList<>();
    int oldCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        loggedInUser = (User) getIntent().getSerializableExtra("user");
        oldCredits = loggedInUser.getCredits();

        createLayout();

        totalPrice = (TextView) findViewById(R.id.cart_total_price);
        totalQuantity = (TextView) findViewById(R.id.cart_total_item);

        addCartItemsToArray();

        purchase = (Button) findViewById(R.id.cart_purchase_button);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchaseCartItems();
            }
        });
    }

    public void createLayout() {
        // RecyclerView code with firebase
        recyclerView = (RecyclerView) findViewById(R.id.cart_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MovieModel> options =
                new FirebaseRecyclerOptions.Builder<MovieModel>()
                        .setQuery(FirebaseDatabase.getInstance(
                                "https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("cart").child(loggedInUser.getUsername()), MovieModel.class).build();

        mainAdapter = new CartAdapter(options);
        recyclerView.setAdapter(mainAdapter);
        onRecyclerItemSwipe();

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



    // function which is does all the stuff related to purchasing all the items in the cart
    private void purchaseCartItems() {
        if (allCartItems.size() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
            builder.setTitle("Purchase Confirmation");
            builder.setMessage("Do you wish to confirm your purchase?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    newCredits = newCredits + loggedInUser.credits;
                    DatabaseReference reference = FirebaseDatabase.getInstance("https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
                    loggedInUser.setCredits(newCredits);
                    Toast toast = Toast.makeText(getApplication(), "purchase successful, credits added" ,Toast.LENGTH_SHORT);
                    toast.show();

                    addCartItemsToOrder();
                    recreate();
                    reference.child(loggedInUser.getUsername()).child("credits").setValue(newCredits);
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
        else {
            Toast toast = Toast.makeText(getApplication(), "no items in cart" ,Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void addCartItemsToOrder() {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("cart").child(loggedInUser.getUsername());
        DatabaseReference reference1 = FirebaseDatabase.getInstance("https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("purchases");
        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());
        String id = reference.push().getKey();
        OrderModel helperClass = new OrderModel(allCartItems, date, total_price[0], Long.valueOf(allCartItems.size()), id, (newCredits - oldCredits));
        reference1.child(loggedInUser.getUsername()).child(id).setValue(helperClass);
        reference.setValue(null);

        // show the details of the order
        Intent intent = new Intent (getApplication(), OrderDetails.class);
        intent.putExtra("order", helperClass);
        intent.putExtra("user", loggedInUser);
        startActivity(intent);
    }

    // function responsible for deleting items from cart
    private void onRecyclerItemSwipe() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainAdapter.deleteItem(viewHolder.getAdapterPosition());
                finish();
                startActivity(getIntent());
                Toast toast = Toast.makeText(getApplication(), "item was removed" ,Toast.LENGTH_SHORT);
                toast.show();

            }
        }).attachToRecyclerView(recyclerView);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                intent.putExtra("user", loggedInUser);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(getApplicationContext(), UserProfile.class);
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

    public void addCartItemsToArray() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("cart");
        DatabaseReference cartMoviesRef = rootRef.child(loggedInUser.getUsername());

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    MovieModel movie = new MovieModel(
                            ds.child("genre").getValue(String.class),ds.child("image").getValue(String.class),
                            ds.child("name").getValue(String.class), ds.child("price").getValue(Long.class),
                            ds.child("rating").getValue(String.class), ds.child("release").getValue(String.class),
                            ds.child("shortDesc").getValue(String.class), ds.child("runtime").getValue(String.class),
                            ds.child("writer").getValue(String.class), ds.child("director").getValue(String.class),
                            ds.child("cast").getValue(String.class));
                    total_price[0] = movie.getPrice() + total_price[0];
                    allCartItems.add(movie);
                    Log.d("MOVIE", String.valueOf(movie));
                }
                totalQuantity.setText(String.valueOf(allCartItems.size()));
                totalPrice.setText(String.valueOf(total_price[0]));

                if (allCartItems.size() != 0)
                newCredits = (int) (total_price[0] / allCartItems.size());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        cartMoviesRef.addListenerForSingleValueEvent(eventListener);
    }
}






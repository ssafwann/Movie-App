/*
    This class is accessed after a user clicks on the button in the user profile to see their order history
    It shows a recycler view with all the orders made by the user
 */
package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class OrderHistory extends AppCompatActivity {

    User loggedInUser;
    RecyclerView recyclerView;
    OrderAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_orderD);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLoggedUser();
        createLayout();
    }

    private void createLayout() {
        // RecyclerView code with firebase
        recyclerView = (RecyclerView) findViewById(R.id.order_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<OrderModel> options =
                new FirebaseRecyclerOptions.Builder<OrderModel>()
                        .setQuery(FirebaseDatabase.getInstance(
                                "https://movie-app-d9b4f-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("purchases").child(loggedInUser.getUsername()), OrderModel.class).build();
        mainAdapter = new OrderAdapter(options);
        recyclerView.setAdapter(mainAdapter);
        onRecyclerItemClick();
    }

    public void onRecyclerItemClick() {
        mainAdapter.setOnModelClickListener(orderModel -> {
            Intent intent = new Intent (getApplication(), OrderDetails.class);
            OrderModel order = new OrderModel(orderModel.getMovies(), orderModel.getPurchaseDate(), orderModel.getPrice(),
            orderModel.getQuantity(), orderModel.getOrderID(), orderModel.getCredits());
            intent.putExtra("order", order);
            intent.putExtra("user", loggedInUser);
            startActivity(intent);
        });
    }

    public void getLoggedUser() {
        Intent intent = getIntent();
        loggedInUser = (User) getIntent().getSerializableExtra("user");
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
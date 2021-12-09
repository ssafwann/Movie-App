/*
    This class is accessed after a user a user click on of the item in the orders recycler view in "OrderHistory" class
    It shows user the details of a specific order they clicked on
 */
package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    TextView id, date, amount, quantity, credits;
    User loggedinUser;
    OrderModel order;
    List<MovieModel> movies = new ArrayList<>();
    OrderDetailsAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar_orderDetailsD);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();
        intializeTextViews();
        showMovieData();

        movies = order.getMovies();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.order_details_rv);
        mainAdapter = new OrderDetailsAdapter(movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mainAdapter);

    }

    public void getData() {
        Intent intent = getIntent();
        order = (OrderModel) getIntent().getSerializableExtra("order");
        loggedinUser = (User) getIntent().getSerializableExtra("user");

    }

    public void intializeTextViews() {
        id = (TextView) findViewById(R.id.order_id_data);
        date = (TextView) findViewById(R.id.order_date_data);
        amount = (TextView) findViewById(R.id.order_price_data);
        quantity = (TextView) findViewById(R.id.order_item_data);
        credits = (TextView) findViewById(R.id.order_credits_data);
    }

    private void showMovieData() {
        String order_date = order.getPurchaseDate();
        String order_id = order.getOrderID();
        String order_total =String.valueOf(order.getPrice());
        String order_quantity =String.valueOf(order.getQuantity());
        String order_credits = String.valueOf(order.getCredits());

        id.setText(order_id);
        date.setText(order_date);
        amount.setText(order_total);
        quantity.setText(order_quantity);
        credits.setText(order_credits);
    }

}
/*
    The class is the recycler view adapter for the "OrderHistory" class
 */
package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OrderAdapter extends FirebaseRecyclerAdapter<OrderModel,OrderAdapter.myViewHolder> {

    public OrderAdapter(@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
    }

    public interface OnModelClickListener {
        void onClick(OrderModel orderModel);
    }

    private OnModelClickListener onModelClickListener;

    public void setOnModelClickListener(OnModelClickListener listener) {
        this.onModelClickListener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull OrderModel orderModel) {
        myViewHolder.date.setText(orderModel.getPurchaseDate());
        myViewHolder.price.setText(String.valueOf(orderModel.getPrice()));
        myViewHolder.itemCount.setText(String.valueOf(orderModel.getQuantity()));

        if (onModelClickListener != null) {
            myViewHolder.itemView.setOnClickListener(v -> {
                onModelClickListener.onClick(orderModel);
            });
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView date, price, itemCount;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.order_date);
            itemCount = (TextView) itemView.findViewById(R.id.order_num_data);
            price = (TextView) itemView.findViewById(R.id.order_total_price);
        }
    }
}

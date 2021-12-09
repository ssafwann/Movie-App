/*
    The class is the recycler view adapter for the "OrderDetails" class it shows all the movies the user purchased
 */
package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    List<MovieModel> movies = new ArrayList<>();

    public OrderDetailsAdapter(List<MovieModel> movies) {
        this.movies = movies;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.order_details_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieName.setText(movies.get(position).getName());
        holder.price.setText(String.valueOf(movies.get(position).getPrice()));

        Glide.with(holder.img.getContext()).load(movies.get(position).getImage()).placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView movieName, price;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            movieName = (TextView) itemView.findViewById(R.id.movie_name_text);
            price = (TextView) itemView.findViewById(R.id.price_text);
        }
    }
}


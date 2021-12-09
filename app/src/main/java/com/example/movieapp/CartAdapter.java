/*
    The class is the recycler view adapter for the "Cart" class
 */
package com.example.movieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CartAdapter extends FirebaseRecyclerAdapter<MovieModel,CartAdapter.myViewHolder> {

    public CartAdapter(@NonNull FirebaseRecyclerOptions<MovieModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull MovieModel movieModel) {
        myViewHolder.movieName.setText(movieModel.getName());
        myViewHolder.price.setText(String.valueOf(movieModel.getPrice()));

        Glide.with(myViewHolder.img.getContext()).load(movieModel.getImage()).placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(myViewHolder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new myViewHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView movieName, price;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            movieName = (TextView) itemView.findViewById(R.id.movie_name_text);
            price = (TextView) itemView.findViewById(R.id.price_text);
        }
    }
}

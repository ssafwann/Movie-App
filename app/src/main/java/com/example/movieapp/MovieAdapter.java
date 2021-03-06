/*
    The class is the recycler view adapter for the "HomePage" class
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

public class MovieAdapter extends FirebaseRecyclerAdapter<MovieModel,MovieAdapter.myViewHolder> {

    public MovieAdapter(@NonNull FirebaseRecyclerOptions<MovieModel> options) {
        super(options);
    }

    public interface OnModelClickListener {
        void onClick(MovieModel movieModel);
    }

    private OnModelClickListener onModelClickListener;

    public void setOnModelClickListener(OnModelClickListener listener) {
            this.onModelClickListener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull MovieModel movieModel) {
        myViewHolder.name.setText(movieModel.getName());
        myViewHolder.shortDesc.setText(movieModel.getShortDesc());
        myViewHolder.release.setText(movieModel.getRelease());

        Glide.with(myViewHolder.img.getContext()).load(movieModel.getImage()).placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(myViewHolder.img);

        if (onModelClickListener != null) {
            myViewHolder.itemView.setOnClickListener(v -> {
                onModelClickListener.onClick(movieModel);
            });
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,shortDesc,release;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            shortDesc = (TextView) itemView.findViewById(R.id.shortDesctext);
            release = (TextView) itemView.findViewById(R.id.releasetext);
        }
    }
}

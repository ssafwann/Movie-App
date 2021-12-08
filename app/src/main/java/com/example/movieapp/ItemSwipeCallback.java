package com.example.movieapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemSwipeCallback extends ItemTouchHelper.SimpleCallback {

    private Context mContext;
    private OnTouchListener mOnTouchListener;

    public ItemSwipeCallback(Context context, int dragDirs, int swipeDirs,  OnTouchListener onTouchListener) {
        super(dragDirs, swipeDirs);
        mContext = context;
        mOnTouchListener = onTouchListener;
    }

    public interface OnTouchListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mOnTouchListener.onSwiped(viewHolder, direction);
    }
}

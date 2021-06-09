package com.example.todolist.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapter.AdapterToDoList;


public class RecyclerItemClickListener extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTochHelperListener listener;

    public RecyclerItemClickListener(int dragDirs, int swipeDirs, RecyclerItemTochHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionStates){
        if (viewHolder != null){
            View foregroundView = ((AdapterToDoList.MyViewHolder) viewHolder).layoutABorrar;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwipe(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {

        View foregroundView = ((AdapterToDoList.MyViewHolder) viewHolder).layoutABorrar;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView,dX, dY,
                actionState,isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = ((AdapterToDoList.MyViewHolder) viewHolder).layoutABorrar;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                            float dY, int actionState, boolean isCurrentlyActive) {
        dX = (float) (dX * 0.3);
        View foregroundView = ((AdapterToDoList.MyViewHolder) viewHolder).layoutABorrar;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState,isCurrentlyActive);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTochHelperListener{
        void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
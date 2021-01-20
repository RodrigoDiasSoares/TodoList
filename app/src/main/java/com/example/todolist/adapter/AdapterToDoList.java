package com.example.todolist.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.model.ToDo;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AdapterToDoList extends RecyclerView.Adapter<AdapterToDoList.MyViewHolder> {
    private List<ToDo> toDoList;

    public AdapterToDoList(List<ToDo> list) {
        this.toDoList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_to_do_list, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ToDo toDo = toDoList.get(position);
        holder.itenList.setText(toDo.getToDo());
        Log.i("tarefaAdapter", toDo.getToDo());
    }

    @Override
    public int getItemCount() {
        return this.toDoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itenList;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itenList = itemView.findViewById(R.id.textViewAdapter);
        }
    }
}

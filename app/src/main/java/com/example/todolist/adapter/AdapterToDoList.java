package com.example.todolist.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.helper.ToDoDAO;
import com.example.todolist.model.ToDo;

import java.util.ArrayList;
import java.util.List;

public class AdapterToDoList extends RecyclerView.Adapter<AdapterToDoList.MyViewHolder> {
    private List<ToDo> toDoList;
    private List<ToDo> toDoListToDelete = new ArrayList<>();

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
        if(toDo.getStatus()){
            holder.checkBox.setBackgroundResource(R.drawable.check);

        }else{
            holder.checkBox.setBackgroundResource(R.drawable.square);
        }


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDo.setStatus(true);
                ToDoDAO dao = new ToDoDAO(v.getContext());

                if(toDoListToDelete.contains(toDoList.get(position))){
                    toDoListToDelete.remove(toDoList.get(position));
                    holder.checkBox.setBackgroundResource(R.drawable.square);
                    toDo.setStatus(false);
                    dao.atualizar(toDo);
                }else{
                    toDoListToDelete.add(toDoList.get(position));
                    holder.checkBox.setBackgroundResource(R.drawable.check);
                    toDo.setStatus(true);
                    dao.atualizar(toDo);
                }
            }
        });

        Log.i("tarefaAdapter", toDo.getToDo());
    }

    @Override
    public int getItemCount() {
        return this.toDoList.size();
    }

    public List<ToDo> allChecked(){
        return toDoList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itenList;
        Button checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itenList = itemView.findViewById(R.id.textViewAdapter);
            checkBox = itemView.findViewById(R.id.checkboxAdapter);
        }
    }
}

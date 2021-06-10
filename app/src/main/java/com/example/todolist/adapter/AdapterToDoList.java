package com.example.todolist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.todolist.R;
import com.example.todolist.activity.main.MainActivity;
import com.example.todolist.activity.main.MainViewModel;
import com.example.todolist.helper.ToDoDAO;
import com.example.todolist.model.ToDo;
import com.example.todolist.observer.Observador;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AdapterToDoList extends Adapter<AdapterToDoList.MyViewHolder> {
    private List<ToDo> toDoList;
    private MainViewModel main = new MainViewModel();
    private ArrayList<Observador> observadores = new ArrayList<>();
    private List<ToDo> toDoChecked = new ArrayList<>();



    public AdapterToDoList(List<ToDo> list) {
        adicionarObservador(main);
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
        holder.layoutDelete.setMinimumHeight(holder.layoutABorrar.getMinimumHeight());
        if(toDo.getStatus()){
            holder.checkBox.setBackgroundResource(R.drawable.ic_check);
            toDoChecked.add(toDo);
        }else{
            holder.checkBox.setBackgroundResource(R.drawable.ic_square);
            toDoChecked.remove(toDo);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoDAO dao = new ToDoDAO(v.getContext());

                if(toDoList.get(position).getStatus()){
                    holder.checkBox.setBackgroundResource(R.drawable.ic_square);
                    toDoList.get(position).setStatus(false);
                    dao.atualizar(toDoList.get(position));
                    toDoChecked.remove(toDoList.get(position));
                    atualizar(allChecked(), toDoList.size(), progress(toDoChecked));
                }else{
                    holder.checkBox.setBackgroundResource(R.drawable.ic_check);
                    toDoList.get(position).setStatus(true);
                    dao.atualizar(toDoList.get(position));
                    toDoChecked.add(toDoList.get(position));
                    atualizar(allChecked(), toDoList.size(), progress(toDoChecked));
                }
            }
        });



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(toDo);
            }
        });
        Log.i("tarefaAdapter", toDo.getToDo());
    }

    public List<ToDo> progress(List<ToDo> checked){
        return toDoChecked;
    }

    @Override
    public int getItemCount() {
        return this.toDoList.size();
    }

    public void editar(ToDo toDo){
        notificarObservadores(toDo);
    }

    public void delete(ToDo toDo){
        notificarObservadores(toDo.getId().intValue());
    }

    public void atualizar(boolean tarefasCumpridas,
                          int qtdTodo, List<ToDo> toDoChecked){
        notificarObservadores(tarefasCumpridas,qtdTodo, toDoChecked);
    }

    public void adicionarObservador(Observador observador){
        if(!observadores.contains(observador)){
            observadores.add(observador);
        }
    }

    public void removerObservador(Observador observador){
        observadores.remove(observador);
    }

    public void notificarObservadores(boolean tarefasCumpridas,
                                      int qtdTodo, List<ToDo> toDoChecked){
        for(Observador o: observadores){
            o.atualizar(tarefasCumpridas,qtdTodo,toDoChecked);
        }
    }
    public void notificarObservadores(int id){
        for(Observador o: observadores){
            o.deletar(id);
        }
    }
    public void notificarObservadores(ToDo toDo){
        for(Observador o: observadores){
            o.editTodo(toDo);
        }
    }

    public boolean allChecked(){

        if(toDoChecked.size() != 0 && toDoChecked.size() == toDoList.size()){
            return true;
        }else {
            return false;
        }
    }

    public void deleteItem(Context context){
        ToDoDAO dao = new ToDoDAO(context);
        for (int i = 0; i < toDoList.size(); i++){
            dao.deletar(toDoList.get(i));
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itenList;
        Button checkBox;
        Button delete;
        public RelativeLayout layoutABorrar;
        public RelativeLayout layoutDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itenList = itemView.findViewById(R.id.textViewAdapter);
            checkBox = itemView.findViewById(R.id.checkboxAdapter);
            layoutABorrar = itemView.findViewById(R.id.layoutABorrar);
            layoutDelete = itemView.findViewById(R.id.layoutDelete);
            delete = itemView.findViewById(R.id.btnDelete);
        }

    }
}

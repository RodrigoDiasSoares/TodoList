package com.example.todolist.activity.main;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.todolist.R;
import com.example.todolist.activity.addTarefas.AddTarefaActivity;
import com.example.todolist.activity.congratulation.CongratulationsActivity;
import com.example.todolist.adapter.AdapterToDoList;

import com.example.todolist.helper.RecyclerItemClickListener;
import com.example.todolist.helper.ToDoDAO;
import com.example.todolist.model.ToDo;
import com.example.todolist.observer.Observador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener.RecyclerItemTochHelperListener{
    private RecyclerView recyclerView;
    private AdapterToDoList adapter;
    private List<ToDo> toDoList = new ArrayList<>();
    private Dialog dialog;
    private TextView textViewProgress;
    private ProgressBar progressBar;
    int i = 0;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewToDoList);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        dialog = new Dialog(this);
        carregarLista();



        viewModel.getmChecked().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean allChecked) {
                if(allChecked){
                    congratulation();
                }
            }
        });

        viewModel.getmProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                progress(i);
            }
        });



        ItemTouchHelper.SimpleCallback simpleCallback =
                new RecyclerItemClickListener(0,ItemTouchHelper.LEFT, MainActivity.this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public final void carregarLista(){
        adapter = new AdapterToDoList(toDoList);
        adapter.adicionarObservador(viewModel);
        ToDoDAO toDoDAO = new ToDoDAO(this);
        toDoList = toDoDAO.listar();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        Log.e("INFO","Lista Carregada com sucesso");

    }

    public void congratulation(){
        adapter.deleteItem(this);
        startActivity(new Intent(this, CongratulationsActivity.class));
        finish();
    }

    public void progress(int qtdToDo){
            String valor = String.valueOf(qtdToDo);
            Double porcentagem = (100 / Double.parseDouble(valor));
            if (i != porcentagem.intValue() && i != 0){
                i += porcentagem.intValue();
                resultado(i);
            }
    }

    public void resultado(int porcentagem){
        dialog.setContentView(R.layout.test);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textViewProgress = dialog.findViewById(R.id.textProgress);
        progressBar = dialog.findViewById(R.id.progress_circular);
        textViewProgress.setText(String.valueOf(porcentagem));
        progressBar.setProgress(porcentagem);
        dialog.show();
    }

    @Override
    protected void onStart() {
        carregarLista();
        super.onStart();
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterToDoList.MyViewHolder){

        }
    }

}
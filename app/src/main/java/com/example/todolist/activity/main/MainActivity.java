package com.example.todolist.activity.main;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener.RecyclerItemTochHelperListener{
    private RecyclerView recyclerView;
    private AdapterToDoList adapter;
    private List<ToDo> toDoList = new ArrayList<>();
    private Dialog dialog;
    private TextView textViewProgress;
    private ProgressBar progressBar;
    private MainViewModel viewModel;
    private String[] appPermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CAMERA
    };
    public static final int CODIGO_PERMISSOES_REQUERIDAS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedindoPermissao();
        recyclerView = findViewById(R.id.recyclerViewToDoList);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
                resultado(i);
            }
        });

        viewModel.getmEditTodo().observe(this, new Observer<ToDo>() {
            @Override
            public void onChanged(ToDo toDo) {
                Intent intent = new Intent(getApplicationContext(), AddTarefaActivity.class);
                intent.putExtra("selectedToDo", toDo);
                startActivity(intent);
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

        if(pedindoPermissao()){
            Toast.makeText(this,"Nem todas as permissoes foram ativas",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Nem todas as permissoes foram ativas",Toast.LENGTH_SHORT).show();
        }

    }

    public final void carregarLista(){
        ToDoDAO toDoDAO = new ToDoDAO(this);
        adapter = new AdapterToDoList(toDoList);
        adapter.adicionarObservador(viewModel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        toDoList = toDoDAO.listar();
        Log.e("INFO","Lista Carregada com sucesso");
    }

    public void resultado(int porcentagem){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.test);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textViewProgress = dialog.findViewById(R.id.textProgress);
        progressBar = dialog.findViewById(R.id.progress_circular);
        textViewProgress.setText(String.valueOf(porcentagem));
        progressBar.setMax(viewModel.getmQtdToDo().getValue());
        progressBar.setProgress(porcentagem);
        dialog.show();
    }

    public void congratulation(){
        adapter.deleteItem(this);
        startActivity(new Intent(this, CongratulationsActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        carregarLista();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterToDoList.MyViewHolder){

        }
    }

    public boolean pedindoPermissao(){
        List<String> permissoes = new ArrayList<>();

        for(String permissao : appPermission){
            if(ContextCompat.checkSelfPermission(this,permissao) != PackageManager.PERMISSION_GRANTED){
                permissoes.add(permissao);
            }
        }

        if(!permissoes.isEmpty()){
            ActivityCompat.requestPermissions(this,
                    permissoes.toArray(new String[permissoes.size()]),CODIGO_PERMISSOES_REQUERIDAS);
            return false;
        }

        return true;
    }


}
package com.example.todolist.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.R;
import com.example.todolist.adapter.AdapterToDoList;
import com.example.todolist.helper.DbHelper;
import com.example.todolist.helper.RecyclerItemClickListener;
import com.example.todolist.helper.ToDoDAO;
import com.example.todolist.model.ToDo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private AdapterToDoList adapter;
    private List<ToDo> toDoList = new ArrayList<>();
    ToDoDAO toDoDAO;
    private boolean initCongratulation = false;
    public void Timer(){
        Timer timer = new Timer();
        Task task = new Task();

        timer.schedule(task, 1000, 1000);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewToDoList);
        toDoDAO = new ToDoDAO(getApplicationContext());

        Timer();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public final void carregarLista(){

        ToDoDAO toDoDAO = new ToDoDAO(this);
        toDoList = toDoDAO.listar();
        adapter = new AdapterToDoList(toDoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        Log.e("INFO","Lista Carregada com sucesso");

    }
    public void progress(){
        boolean check = false;
        List<ToDo> list = adapter.allChecked();
        for(int i = 0; i < list.size();i++){
            if (list.get(i).getStatus()){
                check = true;
            }else{
                check = false;
            }
        }
        if (check == true){
            initCongratulation = true;
            startActivity(new Intent(this, CongratulationsActivity.class));
            for (int i = 0; i < list.size(); i++){
                toDoDAO.deletar(list.get(i));
            }
            finish();
        }
    }


    @Override
    protected void onStart() {
        carregarLista();

        super.onStart();
    }

    class Task extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(initCongratulation == false) {
                        progress();
                    }
                }
            });
        }
    }
}
package com.example.todolist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.helper.ToDoDAO;
import com.example.todolist.model.ToDo;
import com.google.android.material.textfield.TextInputEditText;

public class AddTarefaActivity extends AppCompatActivity {

    private TextInputEditText textToDo;
    private ToDo editToDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);
        textToDo = findViewById(R.id.textToDo);

        editToDo = (ToDo) getIntent().getSerializableExtra("selectedToDo");

        if (editToDo != null){
            textToDo.setText(editToDo.getToDo());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_to_do, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar :
                
                ToDoDAO toDoDAO = new ToDoDAO(getApplicationContext());
                if(editToDo != null){
                    String nameToDo = textToDo.getText().toString();
                    if(!nameToDo.isEmpty()){
                        ToDo toDo = new ToDo();
                        toDo.setToDo(nameToDo);
                        toDo.setId(editToDo.getId());
                        if(toDoDAO.atualizar(toDo)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao atualizar a Tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao atualizar a Tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    String nameToDo = textToDo.getText().toString();
                    if(!nameToDo.isEmpty()){
                        ToDo toDo = new ToDo();
                        toDo.setToDo(nameToDo);
                        if (toDoDAO.salvar(toDo)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao salvar a Tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao salvar a Tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
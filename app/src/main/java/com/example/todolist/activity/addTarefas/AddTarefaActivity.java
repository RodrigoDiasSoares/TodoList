package com.example.todolist.activity.addTarefas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.helper.ToDoDAO;
import com.example.todolist.model.ToDo;
import com.google.android.material.textfield.TextInputEditText;

public class AddTarefaActivity extends AppCompatActivity {
    private Button btnSave;
    private TextInputEditText textToDo;
    private ToDo editToDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);
        textToDo = findViewById(R.id.textToDo);
        btnSave = findViewById(R.id.saveButton);

        editToDo = (ToDo) getIntent().getSerializableExtra("selectedToDo");

        if (editToDo != null){
            textToDo.setText(editToDo.getToDo());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        toDo.setStatus(false);
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
            }
        });

    }
}
package com.example.todolist.observer;

import com.example.todolist.model.ToDo;

import java.util.List;

public interface Observador {
    void atualizar(boolean tarefaCumprida, int qtdToDo, List<ToDo> toDoChecked);
    void deletar(int id);
    void editTodo(ToDo toDo);
}

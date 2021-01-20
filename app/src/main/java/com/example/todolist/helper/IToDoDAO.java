package com.example.todolist.helper;

import com.example.todolist.model.ToDo;

import java.util.List;

public interface IToDoDAO {
    public boolean salvar(ToDo toDo);
    public  boolean atualizar(ToDo toDo);
    public  boolean deletar(ToDo toDo);
    public List<ToDo> listar();
}

package com.example.todolist.model;

import java.io.Serializable;

public class ToDo implements Serializable {
    private Long id;
    private String toDo;



    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

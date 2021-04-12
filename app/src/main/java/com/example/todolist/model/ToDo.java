package com.example.todolist.model;

import android.widget.CheckBox;

import java.io.Serializable;

public class ToDo implements Serializable {
    private Long id;
    private String toDo;
    private boolean status;

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int mStatus(){
        return getStatus() ? 1 : 0;
    }
}

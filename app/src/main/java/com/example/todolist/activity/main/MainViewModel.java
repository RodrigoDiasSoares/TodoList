package com.example.todolist.activity.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.R;
import com.example.todolist.activity.addTarefas.AddTarefaActivity;
import com.example.todolist.model.ToDo;
import com.example.todolist.observer.Observador;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel implements Observador {
    private MutableLiveData<Boolean> mChecked= new MutableLiveData<>();
    private MutableLiveData<Integer> mProgress = new MutableLiveData<>();
    private MutableLiveData<Integer> mQtdToDo = new MutableLiveData<>();
    private MutableLiveData<ToDo> mEditTodo = new MutableLiveData<>();
    private MutableLiveData<Integer> mDeleteToDo = new MutableLiveData<>();
    private List<ToDo> toDoChecked = new ArrayList<>();

    public MutableLiveData<Boolean> getmChecked() {
        return mChecked;
    }

    public void setmChecked(boolean allChecked) {
        mChecked.setValue(allChecked);
    }

    public MutableLiveData<Integer> getmProgress() {
        return mProgress;
    }

    public void setmProgress(int progress) {
        mProgress.setValue(progress);
    }

    public MutableLiveData<Integer> getmQtdToDo() {
        return mQtdToDo;
    }

    public void setmQtdToDo(int mQtdToDo) {
        this.mQtdToDo.setValue(mQtdToDo);
    }

    public MutableLiveData<ToDo> getmEditTodo() {
        return mEditTodo;
    }

    public void setmEditTodo(ToDo mEditTodo) {
        this.mEditTodo.setValue(mEditTodo);
    }

    public MutableLiveData<Integer> getmDeleteToDo() {
        return mDeleteToDo;
    }

    public void setmDeleteToDo(MutableLiveData<Integer> mDeleteToDo) {
        this.mDeleteToDo = mDeleteToDo;
    }

    @Override
    public void atualizar(boolean tarefaCumprida, int qtdToDo, List<ToDo> toDoChecked) {
        if(tarefaCumprida == true){
            setmChecked(true);
        }else{
            setmQtdToDo(qtdToDo);
            this.toDoChecked = toDoChecked;
            setmProgress(this.toDoChecked.size());
        }
    }

    @Override
    public void deletar(int id) {

    }

    @Override
    public void editTodo(ToDo toDo) {
        setmEditTodo(toDo);
    }
}

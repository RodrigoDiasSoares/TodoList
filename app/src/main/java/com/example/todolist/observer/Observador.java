package com.example.todolist.observer;

import androidx.lifecycle.MutableLiveData;

public interface Observador {

    void atualizar(boolean tarefaCumprida, int qtdToDo);
}

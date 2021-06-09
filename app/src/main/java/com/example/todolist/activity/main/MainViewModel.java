package com.example.todolist.activity.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.observer.Observador;

public class MainViewModel extends ViewModel implements Observador {
    private MutableLiveData<Boolean> mChecked= new MutableLiveData<>();
    private MutableLiveData<Integer> mProgress = new MutableLiveData<>();

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

    @Override
    public void atualizar(boolean tarefaCumprida,  int qtdToDo) {
        if(tarefaCumprida == true){
            setmChecked(tarefaCumprida);
        }else{
            setmProgress(qtdToDo);
        }
    }
}

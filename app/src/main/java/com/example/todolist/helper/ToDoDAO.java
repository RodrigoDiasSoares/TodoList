package com.example.todolist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.todolist.model.ToDo;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class ToDoDAO implements IToDoDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public ToDoDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(ToDo toDo) {
        ContentValues cv = new ContentValues();
        cv.put("nome",toDo.getToDo());
        cv.put("status",toDo.mStatus());
        try{
            escreve.insert(DbHelper.TABLE_TODO,null, cv);

            Log.i("INFO","Sucesso ao Salvar");
        }catch (Exception e){
            Log.e("INFO","Erro ao Salvar"+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(ToDo toDo) {
        ContentValues cv = new ContentValues();
        cv.put("nome",toDo.getToDo());
        cv.put("status",toDo.mStatus());
        try{
            String[] args = {toDo.getId().toString()};
            escreve.update(DbHelper.TABLE_TODO,cv,"id=?",args);
            Log.e("INFO","Tarefa atualizada com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar"+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(ToDo toDo) {
        try{
            String[] args = {toDo.getId().toString()};
            escreve.delete(DbHelper.TABLE_TODO,"id=?",args);
            Log.e("INFO","Tarefa atualizada com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao atualizar"+e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<ToDo> listar() {
        List<ToDo> toDoList = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.TABLE_TODO + " ;";
        Cursor cursor = le.rawQuery(sql,null);
        while (cursor.moveToNext()){
            ToDo toDo = new ToDo();
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nameToDo = cursor.getString(cursor.getColumnIndex("nome"));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            toDo.setId(id);
            toDo.setToDo(nameToDo);
            if(status !=0){
                toDo.setStatus(true);
            }else{
                toDo.setStatus(false);
            }
            toDoList.add(toDo);
            Log.i("ToDoDao", toDo.getToDo() );
        }

        return toDoList;
    }
}

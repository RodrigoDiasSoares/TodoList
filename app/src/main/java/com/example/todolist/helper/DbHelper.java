package com.example.todolist.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TODO";
    public static String TABLE_TODO = "ToDo";

    public DbHelper(@Nullable Context context ) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TODO +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL," +
                "status BOOLEAN NOT NULL CHECK (status IN (0, 1)))";
        try{
            db.execSQL(sql);
            Log.i("INFO DB","SUCESSO ao criar a tablela");
        }catch (Exception e){
            Log.i("INFO DB","Erro ao criar a tablela"+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

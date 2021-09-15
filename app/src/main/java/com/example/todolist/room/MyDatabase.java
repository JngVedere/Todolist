package com.example.todolist.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Todoitem.class})
public abstract class MyDatabase extends RoomDatabase {
    abstract public TodoDao todoDao();

    private static MyDatabase myDatabase;

    //실제 데이터베이스와 연결
    public static MyDatabase getInstance(Context context){
        if (myDatabase == null){
            myDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "myDatabase.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return myDatabase;
    }
}

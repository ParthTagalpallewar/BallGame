package com.example.balltry1java.data.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.balltry1java.data.room.dao.UserDao;
import com.example.balltry1java.data.room.entity.UserModel;

@Database(entities = { UserModel.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getNoteDao();

    private static AppDatabase noteDB;

    public static AppDatabase getInstance(Context context) {
        if (null == noteDB) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "userDatabse")
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        noteDB = null;
    }

}
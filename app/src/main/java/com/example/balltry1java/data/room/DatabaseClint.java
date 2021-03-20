package com.example.balltry1java.data.room;


import android.content.Context;

import androidx.room.Room;

public class DatabaseClint {

    private Context mCtx;
    private static DatabaseClint mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClint(Context mCtx) {
        this.mCtx = mCtx;

        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "MyToDos").build();
    }

    public static synchronized DatabaseClint getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClint(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
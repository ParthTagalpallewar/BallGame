package com.example.balltry1java.data.room.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.balltry1java.data.room.entity.UserModel;

import java.util.List;


@Dao
public interface UserDao {


    @Query("SELECT * FROM usermodel WHERE name = :name AND pass = :password")
    UserModel login(String name,String password);

    @Insert
    long insert(UserModel task);


    @Update
    void update(UserModel task);

    @Query("SELECT * FROM usermodel ORDER BY score DESC LIMIT 3")
    List<UserModel> getTopPlayers();
}
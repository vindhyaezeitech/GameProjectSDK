package com.today.gamesdk.shabdamsdk.db;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    Single<List<Task>> getAll();

    @Insert
    void insert(Task task);

    @Query("DELETE FROM task")
    void deleteAllCategory();


}

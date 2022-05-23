package com.mirea.zarin.mireaproject.practice8.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhotosDao
{
    @Query("SELECT * FROM photos")
    List<Photos> getAll();

    @Query("SELECT * FROM photos WHERE id = :id")
    Photos getById(long id);

    @Query("DELETE FROM photos")
    void deleteAll();

    @Insert
    void insert(Photos photo);

    @Update
    void update(Photos photo);

    @Delete
    void delete(Photos photo);
}

package com.sggyu.diet_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDAO {
    @Query("SELECT * FROM food")
    List<Food> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Food... foods);

    @Delete
    void delete(Food food);
}
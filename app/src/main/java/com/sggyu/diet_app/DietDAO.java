package com.sggyu.diet_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DietDAO {
    @Query("SELECT * FROM diet")
    List<Diet> getAll();

    @Query("SELECT * FROM diet WHERE date LIKE :str || '%'")
    List<Diet> getByDate(String str);

    @Query("SELECT count(*) FROM diet")
    int getCnt();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Diet... diets);

    @Delete
    void delete(Diet diet);
}
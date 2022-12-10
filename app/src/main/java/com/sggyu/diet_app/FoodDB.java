package com.sggyu.diet_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Food.class,Diet.class}, version = 3)
public abstract class FoodDB extends RoomDatabase {
    public abstract FoodDAO foodDao();
    public abstract DietDAO dietDAO();
    private static FoodDB database;
    private static String DATABASE_NAME = "food";
    public synchronized static FoodDB getInstance(Context context)
    {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), FoodDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
package com.sggyu.diet_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey
    public int fid;

    @ColumnInfo(name = "kcal")
    public String kcal;

    @ColumnInfo(name = "name")
    public String name;

    Food(int id,String cal, String fname){
        fid=id;
        kcal=cal;
        name=fname;
    }
    Food(){

    }
}
package com.sggyu.diet_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diet {
    @PrimaryKey
    public int did;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "num")
    public int num;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "photo")
    public String photo;

    @ColumnInfo(name = "eval")
    public String eval;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    Diet(int id, String name, int num, String date, String photo, String eval, String place, double latitude, double longitude){
        this.did = id;
        this.name = name;
        this.num = num;
        this.date = date;
        this.photo = photo;
        this.eval = eval;
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    Diet(){

    }
}

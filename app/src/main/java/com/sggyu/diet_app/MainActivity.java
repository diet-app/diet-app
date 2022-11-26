package com.sggyu.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create foodDB
        FoodDB db = FoodDB.getInstance(this);
        FoodDAO foodDao = db.foodDao();
        // read from food asset
        try {
            List<Food> foodList = readFromAssets("food.txt");
            for(Food food :foodList){
                foodDao.insertAll(food);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<Food> readFromAssets(String filename) throws Exception {
        List<Food> foodList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
        String[] line;
        String tmp;
        while((tmp = reader.readLine()) != null){
            line = tmp.split("\t");
            Food food = new Food(Integer.parseInt(line[0]),line[1],line[2]);
            foodList.add(food);
        }
        reader.close();
        return foodList;
    }
    public void enterDiet(View view){
        Intent intent = new Intent(this, EnterDiet.class);
        startActivity(intent);
    }

}
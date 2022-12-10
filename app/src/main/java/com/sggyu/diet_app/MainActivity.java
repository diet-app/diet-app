package com.sggyu.diet_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Optional;
import java.util.zip.Inflater;


public class MainActivity extends Activity {

    public CalendarView calendarView;
    public ListView listView;
    public TextView dietTextView;
    public TextView totalKcalTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        dietTextView = findViewById(R.id.diettextView);
        listView = findViewById(R.id.listView1);
        totalKcalTextView = findViewById(R.id.totaltextView);


        //캘린더에서 날짜 눌렀을때 식단 listView 띄우기
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                dietTextView.setText(String.format("  %d년 %d월 %d일", year, month + 1, dayOfMonth));

                checkDayArr(year, month, dayOfMonth);
            }
        });

        Calendar calendar = Calendar.getInstance();
        dietTextView.setText(String.format("  %d년 %d월 %d일  ", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
        checkDayArr(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

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
    // 날짜마다 식단 확인해서 리스트로 변경
    public void checkDayArr(int year, int month, int day){

        String date;
        date = year + "/" + (month+1) + "/" + day;

        FoodDB db = FoodDB.getInstance(this);
        FoodDAO foodDAO = db.foodDao();

        DietDAO dietDao = db.dietDAO();
        List<Diet> dietInfo = dietDao.getByDate(date);


        double totalKcal = 0;

        List<String> stringList = new ArrayList<>();
        for(int i=0; i<dietInfo.size(); i++){
            stringList.add("식단" + (i+1));
            Diet curDiet = dietInfo.get(i);
            List<Food> FoodInfo = foodDAO.getByName(curDiet.name);
            Food curFood = FoodInfo.get(0);
            totalKcal += Double.parseDouble(curFood.kcal)*curDiet.num;
        }

        totalKcalTextView.setText("총 칼로리 : " + totalKcal);


        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.main_listview, stringList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomDialog dialog = new CustomDialog(MainActivity.this);
                dialog.setOwnerActivity(MainActivity.this);
                dialog.show();
                int i = Long.valueOf(Optional.ofNullable(id).orElse(0L)).intValue();

                try{
                    dialog.setContent(i, db, date);
                }catch (Exception e) {
                    Toast.makeText(MainActivity.this, "이미지 사진 없음", Toast.LENGTH_SHORT).show();
                }
            }
        });
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


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
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Optional;
import java.util.zip.Inflater;


public class MainActivity extends Activity {

    public CalendarView calendarView;
    public ListView listView;
    public TextView dietTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        dietTextView = findViewById(R.id.diettextView);
        listView = findViewById(R.id.listView1);

        dietTextView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);

        //캘린더에서 날짜 눌렀을때 식단 listView 띄우기
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                dietTextView.setVisibility(View.VISIBLE);
                dietTextView.setText(String.format("  %d년 %d월 %d일", year, month + 1, dayOfMonth));

                checkDayArr(year, month, dayOfMonth);

                listView.setVisibility(View.VISIBLE);

            }
        });

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
        String[] arr;

        date = Integer.toString(year) + Integer.toString(month+1) + Integer.toString(day);

        if (date.equals("20221128")){
            //@Query("SELECT COUNT(*) FROM food where date = %s")
            //arr
            arr = new String[]{"식단1", "식단2"};
        }
        else{
            arr =new String[]{""};
        }
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.main_listview, arr);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                View layout = inflater.inflate(R.layout.diet_popup,(ViewGroup) findViewById(R.id.popup));
                AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
                int i = Long.valueOf(Optional.ofNullable(id).orElse(0L)).intValue();
                //PopupDiet.setTitleText(i);
                aDialog.setTitle(String.format("식단%d",i+1));
                aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅
                aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog ad = aDialog.create();

                ad.show();
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


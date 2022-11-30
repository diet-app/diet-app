package com.sggyu.diet_app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public TextView titleText;
    public TextView dietPhotoContent;
    public TextView dietNameContent;
    public TextView dietNumContent;
    public TextView dietEvalContent;
    public TextView dietDateContent;
    public TextView dietPlaceContent;
    public TextView dietCalContent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_popup);

        titleText = findViewById(R.id.TitleText);
        dietPhotoContent = findViewById(R.id.dietPhotoContent);
        dietNameContent = findViewById(R.id.dietNameContent);
        dietNumContent = findViewById(R.id.dietNumContent);
        dietEvalContent = findViewById(R.id.dietEvalContent);
        dietDateContent = findViewById(R.id.dietDateContent);
        dietPlaceContent =  findViewById(R.id.dietPlaceContent);
        dietCalContent = findViewById(R.id.dietCalContent);
    }

    public void setContent(int id, FoodDB db, String dateString){

        titleText.setText(String.format("식단%d",id+1));

        DietDAO dietDao = db.dietDAO();
        List<Diet> dietInfo = dietDao.getByDate(dateString);

        FoodDAO foodDAO = db.foodDao();
        //List<Food> FoodInfo = foodDAO.getByName();
        //Food curFood = FoodInfo.get(0);

        //diet DB에서 리스트id로 데이터를 가져와 popup창에 세팅
//        Diet curDiet = dietInfo.get(id);
//
//        dietPhotoContent.setText(curDiet.photo);
//        dietNameContent.setText(curDiet.name);
//        String foodNum = Integer.toString(curDiet.num);
//        dietNumContent.setText(foodNum);
//        dietEvalContent.setText(curDiet.eval);
//        dietDateContent.setText(curDiet.date);
//        dietPlaceContent.setText(curDiet.place);

        //dietCalContent.setText(curFood.kcal);

        //dietPhotoContent.setText(R.drawable.foodPhotoExample);
        dietNameContent.setText("양파");
        dietNumContent.setText("1개");
        dietEvalContent.setText("매움");
        dietDateContent.setText("2022/11/22일 오후 6시 30분");
        dietPlaceContent.setText("서울 중구 충무로2길 32 제1호");
        dietCalContent.setText("40kcal");




    }

}

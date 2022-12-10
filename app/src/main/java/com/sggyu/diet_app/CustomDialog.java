package com.sggyu.diet_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public ImageView dietImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_popup);

        titleText = findViewById(R.id.TitleText);
        dietNameContent = findViewById(R.id.dietNameContent);
        dietNumContent = findViewById(R.id.dietNumContent);
        dietEvalContent = findViewById(R.id.dietEvalContent);
        dietDateContent = findViewById(R.id.dietDateContent);
        dietPlaceContent =  findViewById(R.id.dietPlaceContent);
        dietCalContent = findViewById(R.id.dietCalContent);
        dietImageView = findViewById(R.id.dietImageView) ;
    }

    public void setContent(int id, FoodDB db, String dateString) throws IOException {

        titleText.setText(String.format("식단%d",id+1));

        DietDAO dietDao = db.dietDAO();
        List<Diet> dietInfo = dietDao.getByDate(dateString);

        //diet DB에서 리스트id로 데이터를 가져와 popup창에 세팅
        Diet curDiet = dietInfo.get(id);

        //dietPhotoContent.setText(curDiet.photo);
        dietNameContent.setText(curDiet.name);
        String foodNum = Integer.toString(curDiet.num);
        dietNumContent.setText(foodNum);
        dietEvalContent.setText(curDiet.eval);
        dietDateContent.setText(curDiet.date);
        dietPlaceContent.setText(curDiet.place);

        FoodDAO foodDAO = db.foodDao();
        List<Food> FoodInfo = foodDAO.getByName(curDiet.name);
        Food curFood = FoodInfo.get(0);

        double kcal = Double.parseDouble(curFood.kcal)*curDiet.num;
        dietCalContent.setText(kcal+"kcal");

        Context context = this.getOwnerActivity().getApplicationContext();
        File tempFile = new File(context.getCacheDir(), curDiet.photo);
        FileInputStream in = new FileInputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
        Bitmap bm = BitmapFactory.decodeStream(in) ;

        // 만들어진 Bitmap 객체를 이미지뷰에 표시.
        dietImageView.setImageBitmap(bm) ;
        in.close();


    }

}

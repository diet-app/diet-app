package com.sggyu.diet_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.Optional;


public class PopupDiet extends Activity {

    //public static TextView dietPopupTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.diet_popup);
        //dietPopupTxtView = findViewById(R.id.dietpopuptxt);

    }
//    public static void setTitleText(int id){
//
//        dietPopupTxtView.setText(String.format("식단%d", id+1));
//
//    }
    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }


}

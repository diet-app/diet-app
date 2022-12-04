package com.sggyu.diet_app;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EnterDiet2 extends AppCompatActivity {
    TimePicker timePicker;
    FoodDB db = FoodDB.getInstance(this);
    DietDAO dietDao = db.dietDAO();
    String filename = String.valueOf(dietDao.getCnt()+1);
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            // Handle the returned Uri

            ContentResolver resolver = getContentResolver();
            InputStream instream;
            try {
                instream = resolver.openInputStream(uri);
                Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
//                    imageView.setImageBitmap(imgBitmap);
//                    선택한 이미지 이미지뷰에 셋
                instream.close();   // 스트림 닫아주기
                saveBitmapToJpeg(imgBitmap);
                Log.d("Uri: ", getCacheDir() + "/" + uri.getEncodedPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_diet2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String kcal = intent.getStringExtra("kcal");
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText(kcal);
        textView4.setText(name);
        timePicker = findViewById(R.id.timePicker);
    }
    public void savePhoto(View view){
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        Registers a photo picker activity launcher in single-select mode.
        mGetContent.launch("image/*");
    }
    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getCacheDir(), filename+".png");    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
            Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void enterClick(View view){
        List<Diet> dietInfo = dietDao.getAll();
        Log.d("Time : ",String.valueOf(timePicker.getHour())+":"+String.valueOf(timePicker.getMinute()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
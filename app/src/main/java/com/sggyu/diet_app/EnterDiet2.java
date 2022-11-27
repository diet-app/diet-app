package com.sggyu.diet_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EnterDiet2 extends AppCompatActivity {

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
    }

}
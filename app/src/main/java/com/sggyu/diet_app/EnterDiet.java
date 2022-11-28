package com.sggyu.diet_app;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EnterDiet extends AppCompatActivity {
    SearchView searchView;
//    Button btAdd, btReset;
    RecyclerView recyclerView;
    List<Food> dataList = new ArrayList<>();
    FoodDB database;
    FoodAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_diet);

        searchView = findViewById(R.id.searchView);
//        btAdd = findViewById(R.id.bt_add);
//        btReset = findViewById(R.id.bt_reset);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        recyclerView = findViewById(R.id.recycler_view);

        database = FoodDB.getInstance(this);

        dataList = database.foodDao().getAll();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FoodAdapter(EnterDiet.this, dataList);
//        adapter.setOnItemClickListener(new FoodAdapter.onItemClickListener(){
//            @Override
//            public void onItemClicked(int position, String data) {
//                Intent intent = new Intent(this.context, EnterDiet2.class);
//                startActivity(intent);
//            }
//        });
        recyclerView.setAdapter(adapter);
        searchView.setIconifiedByDefault(false);
    }
}
package com.sggyu.diet_app;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EnterDiet2 extends AppCompatActivity implements OnMapReadyCallback{
    Context context = EnterDiet2.this;
    TimePicker timePicker;
    FoodDB db = FoodDB.getInstance(this);
    DietDAO dietDao = db.dietDAO();
    String filename = String.valueOf(dietDao.getCnt()+1);
    EditText location;
    List<Address> addressList = new ArrayList<>();
    double lat=0, lon=0;
    GoogleMap googleMap;
    String name;
    EditText numView,evalView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_diet2);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        numView = findViewById(R.id.editTextNumber2);
        evalView = findViewById(R.id.editTextTextMultiLine);
        String kcal = intent.getStringExtra("kcal");
        TextView textView4 = findViewById(R.id.textView4);
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText(kcal);
        textView4.setText(name);
        timePicker = findViewById(R.id.timePicker);
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.KOREA);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // 장소 검색
        location = findViewById(R.id.location);
        location.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String searchData = textView.getText().toString();
                if (searchData.isEmpty()) {
                    // 토스트 메세지를 띄우고, 창 내용을 비운다
                    Toast.makeText(context, "정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    textView.clearFocus();
                    textView.setFocusable(false);
                    textView.setFocusableInTouchMode(true);
                    textView.setFocusable(true);

                    return true;
                }
                switch (i) {
                    // Search 버튼일경우
                    case EditorInfo.IME_ACTION_SEARCH:
                        break;
                    // Enter 버튼일경우
                    default:
//                        new Thread(() -> {
                            try {
                                addressList = geocoder.getFromLocationName(searchData, 1);
                                if (addressList.size() == 0) {
                                    Toast.makeText(context, "올바른 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Address address = addressList.get(0);
                                    lat = address.getLatitude();
                                    lon = address.getLongitude();
                                    LatLng latLng = new LatLng(lat,lon);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                        }).start();
                        return false;
                }
                return false;
            }
        });
    }

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

                uri = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    });

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
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String currentDate = format.format(current);
        String currentTime = timePicker.getHour()+":"+timePicker.getMinute();
        Log.d("Time : ",currentDate+" "+currentTime);
        String eval = evalView.getText().toString();
        name = getIntent().getStringExtra("name");
        Diet myDiet = new Diet(dietDao.getCnt()+1,name,Integer.parseInt(numView.getText().toString()),currentDate+" "+currentTime,filename+".png",eval,location.getText().toString(),lat,lon);
        dietDao.insertAll(myDiet);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

//        this.googleMap.setOnCameraIdleListener(this);
//        this.googleMap.setOnCameraMoveStartedListener(this);
//        this.googleMap.setOnCameraMoveListener(this);
//        this.googleMap.setOnCameraMoveCanceledListener(this);
        // [START_EXCLUDE silent]
        // We will provide our own zoom controls.
//        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
//        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        // [END_EXCLUDE]
        // Show Sydney
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.53, 127.02), 5.0f));
    }
}
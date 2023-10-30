package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Viewitem extends AppCompatActivity {

    TextView tv;
    String resultData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewitem);


        tv = findViewById(R.id.itemid);
        Intent intent = getIntent();
        resultData = intent.getStringExtra("result_data");

        tv.setText(resultData);
    }
}
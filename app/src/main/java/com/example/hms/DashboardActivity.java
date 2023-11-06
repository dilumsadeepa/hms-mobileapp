package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hms.model.Users;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name_key", "name");


        String userName = name;

        TextView textView = findViewById(R.id.uname);
        textView.setText(userName);

        ImageView combtn = findViewById(R.id.complaintbtn);
        combtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users.setIndex_no(Users.getIndex_no());
                Users.setId(Users.getId());
                Intent intent = new Intent(DashboardActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });
    }
}
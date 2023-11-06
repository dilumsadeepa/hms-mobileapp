package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        String userName = Users.getName(); // Retrieve the name from the Users class

        TextView textView = findViewById(R.id.uname);
        textView.setText(userName);

        ImageView combtn = findViewById(R.id.complaintbtn);
        combtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });
    }
}
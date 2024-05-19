package com.example.b7intentbegin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn_child = findViewById(R.id.btn_child);
        Button btn_bundle = findViewById(R.id.btn_bundle);
        Button btn_bundle_update = findViewById(R.id.btn_bundle_update);

        btn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity("Child"); } });

        btn_bundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity("BundleTest"); } });

        btn_bundle_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity("BundleUpdate"); } });

    }
    public void startActivity(String activityName) {
        try {
            // Lấy lớp Activity mục tiêu dựa trên tên được cung cấp
            Class targetActivity = Class.forName(getPackageName() + "." + activityName);
            // Tạo một Intent mới với lớp Activity mục tiêu
            Intent myIntent = new Intent(MainActivity.this, targetActivity);
            // Khởi động hoạt động
            startActivity(myIntent);
        } catch (ClassNotFoundException e) {
            // Xử lý trường hợp lớp Activity không được tìm thấy
            e.printStackTrace();
        }
    }
}
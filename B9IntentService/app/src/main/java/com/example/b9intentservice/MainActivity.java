package com.example.b9intentservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnPlay, btnStop;
    private Boolean flag = true;
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

        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khai báo để khởi động service
                Intent startItent = new Intent(MainActivity.this, MyService.class);
                startService(startItent);
                if (flag) {
                    // nhạc chưa chạy thì giờ cho chạy và thay bằng nút pause
                    btnPlay.setImageResource(R.drawable.pause);
                    flag = false;
                }
                else {
                    // nhạc ko chạy thì là nút Play
                    btnPlay.setImageResource(R.drawable.play);
                    flag = true;
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nhạc đang chạy thì cho dừng và cho về mặc định
                Intent stopItent = new Intent(MainActivity.this, MyService.class);
                stopService(stopItent);
                btnPlay.setImageResource(R.drawable.play);
                flag = true;
            }
        });
    }
}
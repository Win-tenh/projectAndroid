package com.example.b7intentbegin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class Result extends AppCompatActivity {

    private TextView result_tv;
    private Button btn_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result_tv = findViewById(R.id.result_tv);
        btn_result = findViewById(R.id.btn_result);

        Intent yourIntent = getIntent();
        Bundle yourBundle = yourIntent.getBundleExtra("myBackage");

        int a = yourBundle.getInt("numa");
        int b = yourBundle.getInt("numb");
        String result = "";
        if (a==0 && b==0) result = "Vô nghiệm";
        else if (a==0 && b!=0) result = "Vô số nghiệm";
        else {
            DecimalFormat dcf = new DecimalFormat("0.##");
            result = dcf.format(-b*1.0/(a*1.0));
        }
        result_tv.setText(result);

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
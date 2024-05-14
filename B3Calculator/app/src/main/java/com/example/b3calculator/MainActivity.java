package com.example.b3calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    private TextView result_tv;
    private EditText a_ed, b_ed;

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
        // get references to the widgets
        a_ed = findViewById(R.id.a_ed);
        b_ed = findViewById(R.id.b_ed);
        result_tv = findViewById(R.id.result_ed);
        Button plus_btn = findViewById(R.id.btncong);
        Button minus_btn = findViewById(R.id.btntru);
        Button times_btn = findViewById(R.id.btnnhan);
        Button div_btn = findViewById(R.id.btnchia);

        // set the listeners
        plus_btn.setOnClickListener(this);
        minus_btn.setOnClickListener(this);
        times_btn.setOnClickListener(this);
        div_btn.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        String a_string = a_ed.getText().toString();
        String b_string = b_ed.getText().toString();
        float result = 0;

        float a = Float.parseFloat("0"+a_string);
        float b = Float.parseFloat("0"+b_string);

        // check id button and calculator
        if (v.getId() == R.id.btncong) {
            a_string = "A+B = ";
            result = a+b;
        } else if (v.getId() == R.id.btntru) {
            a_string = "A-B = ";
            result = a-b;
        } else if (v.getId() == R.id.btnnhan) {
            a_string = "A*B = ";
            result = a*b;
        } else if (v.getId() == R.id.btnchia) {
            if (b == 0) {
                result_tv.setText("B phải khác 0");
                return;
            }
            a_string = "A/B = ";
            result = a/b;
        }

        // display result
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedResult = df.format(result);
        result_tv.setText(a_string + formattedResult);
    }

}
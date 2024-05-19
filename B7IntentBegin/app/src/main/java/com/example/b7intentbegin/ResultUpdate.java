package com.example.b7intentbegin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultUpdate extends AppCompatActivity implements View.OnClickListener {

    private Button btn_sum, btn_sub;
    private EditText edt_a, edt_b;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_sum = findViewById(R.id.btn_sum);
        btn_sub = findViewById(R.id.btn_sub);
        edt_a = findViewById(R.id.edt_a);
        edt_b = findViewById(R.id.edt_b);

        resultIntent = getIntent();
        Bundle resultBundle = resultIntent.getBundleExtra("myBackage");
        edt_a.setText(resultBundle.getInt("numa")+"");
        edt_b.setText(resultBundle.getInt("numb")+"");

        btn_sum.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int a = Integer.parseInt(edt_a.getText().toString()+"");
        int b = Integer.parseInt(edt_b.getText().toString()+"");
        int result = 0;
        int btn_chk = 0;
        if (v.getId() == R.id.btn_sum) {
            result = a + b;
            btn_chk = 33;
        } else if (v.getId() == R.id.btn_sub) {
            result = a - b;
            btn_chk = 34;
        }
        // if btn_chk is true, result is sum, else result is sub
        resultIntent.putExtra("result", result);
        setResult(btn_chk, resultIntent);
        finish();
    }
}
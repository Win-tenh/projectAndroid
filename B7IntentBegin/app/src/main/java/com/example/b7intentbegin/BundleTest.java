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

public class BundleTest extends AppCompatActivity {

    private EditText a_et, b_et;
    private Button btn_bundle, btn_toMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bundle_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        a_et = findViewById(R.id.a_et);
        b_et = findViewById(R.id.b_et);
        btn_bundle = findViewById(R.id.btn_bundle);
        btn_toMain = findViewById(R.id.btn_toMain);

        btn_bundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(BundleTest.this, Result.class);
                Bundle bundle = new Bundle();
                // lấy giá trị từ ET
                int a = Integer.parseInt(a_et.getText().toString()+"");
                int b = Integer.parseInt(b_et.getText().toString()+"");
                // lưu 2 giá trị vào key trong bundle
                bundle.putInt("numa", a);
                bundle.putInt("numb", b);
                // gắn bundle vào key
                myIntent.putExtra("myBackage", bundle);
                startActivity(myIntent);
            }
        });
        btn_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
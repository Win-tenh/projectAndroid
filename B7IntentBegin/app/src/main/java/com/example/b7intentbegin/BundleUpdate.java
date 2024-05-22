package com.example.b7intentbegin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BundleUpdate extends AppCompatActivity {

    private EditText a_et, b_et, result_et;
    private Button btn_toResult, btn_toMain;
    private Intent myIntent;
    private ActivityResultLauncher ARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    int resultCode = o.getResultCode();
                    if (o.getData() != null) {
                        myIntent = o.getData();
                        int result = myIntent.getIntExtra("result", 0);
                        if (resultCode == 33)
                            result_et.setText("Tổng a và b là: " + result);
                        else if (resultCode == 34)
                            result_et.setText("Hiệu a và b là: " + result);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bundle_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        a_et = findViewById(R.id.a_et);
        b_et = findViewById(R.id.b_et);
        result_et = findViewById(R.id.result_et);
        btn_toResult = findViewById(R.id.btn_toResult);
        btn_toMain = findViewById(R.id.btn_toMain);

        btn_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_toResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(BundleUpdate.this, ResultUpdate.class);
                Bundle bundle = new Bundle();
                // lấy giá trị từ ET
                int a = Integer.parseInt(a_et.getText().toString()+"");
                int b = Integer.parseInt(b_et.getText().toString()+"");
                // lưu 2 giá trị vào key trong bundle
                bundle.putInt("numa", a);
                bundle.putInt("numb", b);
                // gắn bundle vào key
                myIntent.putExtra("myBackage", bundle);
                // khởi động intent và chờ kết quả về
                ARL.launch(myIntent);
            }
        });
    }
}
package com.example.b8smsandcall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendSMSActivity extends AppCompatActivity {

    private Button btn_back;
    private ImageButton btn_send;
    private EditText et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_smsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_back = findViewById(R.id.btn_sms_back);
        btn_send = findViewById(R.id.btn_sms);
        et_number = findViewById(R.id.edt_sms);

        btn_send.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(
                    SendSMSActivity.this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) // kiểm tra quyền đã đc cấp hay chưa
            {
                ActivityCompat.requestPermissions(
                        SendSMSActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1); // yêu cầu cấp quyền, requestcode để nhận biết kết quả trong phương thức callback onRequestPermissionsResult.
                return; // dừng các thao tác tiếp theo
            }
            String number = et_number.getText().toString();
            if (number.isEmpty()) {
                et_number.setError("Số điện thoại không được để trống");
                return;
            } else {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + number));
                intent.putExtra("sms_body", "Hello bạn nhỏ"); // nội dung sms
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(v -> finish());

    }
}
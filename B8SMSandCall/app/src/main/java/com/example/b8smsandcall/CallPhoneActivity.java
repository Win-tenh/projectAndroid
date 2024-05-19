package com.example.b8smsandcall;

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

public class CallPhoneActivity extends AppCompatActivity {

    private Button btn_back;
    private ImageButton btn_call;
    private EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ánh xạ các view trong layout
        btn_back = findViewById(R.id.btn_phone_back);
        btn_call = findViewById(R.id.btn_call);
        et_phone = findViewById(R.id.edt_phone_number);

        // xử lý sự kiện khi nhấn nút call
        btn_call.setOnClickListener(v -> {
            // cấp quyền gọi điện cho app
            if (ActivityCompat.checkSelfPermission(
                    CallPhoneActivity.this,
                    android.Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) // kiểm tra quyền đã đc cấp hay chưa
            {
                ActivityCompat.requestPermissions(
                        CallPhoneActivity.this,
                        new String[]{android.Manifest.permission.CALL_PHONE},
                        1); // yêu cầu cấp quyền, requestcode để nhận biết kết quả trong phương thức callback onRequestPermissionsResult.
                return; // dừng các thao tác tiếp theo
            }

            // lấy số điện thoại từ EditText
            String phone = et_phone.getText().toString();
            // kiểm tra số điện thoại có hợp lệ hay không
            if (phone.isEmpty() || phone.length() != 10) {
                et_phone.setError("Số điện thoại không hợp lệ!");
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL); // mở ứng dụng di động
                intent.setData(Uri.parse("tel:" + phone)); // gán số điện thoại cho intent
                startActivity(intent);
            }
        });

        // quay trở lại màn hình chính
        btn_back.setOnClickListener(v -> {
            finish();
        });

    }
}
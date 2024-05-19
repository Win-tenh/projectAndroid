package com.example.b8smsandcall;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn_phone, btn_sms, btn_gmail;
    private Intent mainIntent;
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

        btn_phone = findViewById(R.id.btn_call_phone);
        btn_sms = findViewById(R.id.btn_send_sms);
        btn_gmail = findViewById(R.id.btn_send_gmail);

        btn_phone.setOnClickListener(v -> {
            mainIntent = new Intent(MainActivity.this, CallPhoneActivity.class);
            startActivity(mainIntent);
        });

        btn_sms.setOnClickListener(v -> {
            mainIntent = new Intent(MainActivity.this, SendSMSActivity.class);
            startActivity(mainIntent);
        });

        btn_gmail.setOnClickListener(v -> {
            Intent gmailIntent = new Intent(Intent.ACTION_SENDTO);
            gmailIntent.setData(Uri.parse("mailto:")); // chỉ có ứng dụng email mới xử lý việc này
            gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nqtfacebookanhxtanh@gmail.com"}); // nhập địa chỉ gmail
            gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Thư đóng góp ý kiến cho App đọc truyện"); // nhập tiêu đề gmail
//            gmailIntent.putExtra(Intent.EXTRA_TEXT, "Body"); // nhập nội dung gmail

            try {
                // Kiểm tra xem có ứng dụng email nào có thể xử lý intent này hay không
                if ( gmailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(gmailIntent, "Send email using:")); // mở ứng dụng email cho người dùng
                } else {
                    // Hiển thị thông báo cho người dùng
                    Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
            } catch (ActivityNotFoundException e) {
                // Xử lý ngoại lệ nếu không có ứng dụng email nào có thể xử lý intent này
                Toast.makeText(this, "No email application found.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
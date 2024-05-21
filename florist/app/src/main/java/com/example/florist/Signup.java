package com.example.florist;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thiết lập layout cho Activity từ file XML.
        setContentView(R.layout.activity_signup);

        // Lấy Intent được gửi đến Activity này.
        Intent intent = getIntent();
        // Lấy Bundle chứa dữ liệu từ Intent.
        Bundle bundle = intent.getExtras();

        // Trích xuất email từ Bundle, được gửi từ Activity trước.
        email = bundle.getString("email");

        // Ánh xạ EditText cho nhập email từ layout và thiết lập email đã nhận được.
        editText = findViewById(R.id.sign_email);
        editText.setText(email);

        // Gọi phương thức ConnectDB() để kết nối với cơ sở dữ liệu.
        ConnectDB();

        // Gọi phương thức createEvent() để thiết lập sự kiện cho các thành phần giao diện.
        createEvent();
    }

    String email;
    String password;
    String name;
    TextView textView;
    EditText editText;
    void createEvent() {
        // Tìm nút đăng ký trong layout và thiết lập một OnClickListener.
        Button button = (Button) findViewById(R.id.signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy email, mật khẩu, và tên từ các EditText.
                editText = (EditText) findViewById(R.id.sign_email);
                email = editText.getText().toString();
                editText = (EditText) findViewById(R.id.sign_password);
                password = editText.getText().toString();
                editText = (EditText) findViewById(R.id.sign_name);
                name = editText.getText().toString();

                // Kiểm tra các điều kiện nhập liệu cơ bản.
                boolean isOK = true;
                if(email.isEmpty()){
                    isOK = false;
                    textView = findViewById(R.id.checkemail);
                    textView.setText("Bạn chưa nhập email");
                }
                if(password.isEmpty()){
                    isOK = false;
                    textView = findViewById(R.id.checkpassword);
                    textView.setText("Bạn chưa nhập mật khẩu");
                }
                if(name.isEmpty()){
                    name = "Vô Danh";
                }
                // Nếu có lỗi nhập liệu, hàm sẽ kết thúc tại đây.
                if(!isOK){
                    return;
                }

                // Truy vấn cơ sở dữ liệu để kiểm tra email đã tồn tại hay chưa.
                String sql = "select * from Account where Email='" + email + "'";
                Cursor cursor = db.rawQuery(sql, null);
                cursor.moveToNext();
                try {
                    // Nếu có thể lấy được dữ liệu, email đã tồn tại.
                    cursor.getString(0);
                } catch(Exception e) {
                    // Nếu không, thêm tài khoản mới vào cơ sở dữ liệu.
                    sql = "insert into Account values('" + email + "','" + password + "','" + name + "')";
                    db.execSQL(sql);
                    Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                    return;
                }
                // Hiển thị thông báo nếu email đã tồn tại.
                textView = findViewById(R.id.checkemail);
                textView.setText("Email này đã tồn tại");
            }
        });
    }

    SQLiteDatabase db=null;
    void ConnectDB(){
        // Mở hoặc tạo db nếu chưa có
        db=openOrCreateDatabase("florist.db", MODE_PRIVATE, null);
        // Tạo table Account nếu chưa có
        String sql = "create table if not exists Account(Email char(50) primary key, Password char(50), Name char(50))";
        db.execSQL(sql);
    }
}
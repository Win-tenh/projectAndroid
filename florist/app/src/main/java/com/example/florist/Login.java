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

public class Login extends AppCompatActivity {

    @Override
    // Phương thức được gọi khi Activity được tạo lần đầu tiên
    protected void onCreate(Bundle savedInstanceState) {

        // Gọi phương thức onCreate của lớp cha
        super.onCreate(savedInstanceState);

        // Đặt layout cho Activity bằng file R.layout.activity_login
        setContentView(R.layout.activity_login);

        // Kết nối với cơ sở dữ liệu
        ConnectDB();

        // Thiết lập trình nghe sự kiện cho các thành phần UI
        setEvent();
    }

    Button button;
    TextView textview;
    EditText edittext;
    String email="";
    String password="";
    SQLiteDatabase db = null;
    void setEvent(){
        // Thiết lập sự kiện cho nút đăng nhập.
        button = (Button) findViewById(R.id.BT_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy email và mật khẩu từ người dùng.
                getemail();
                getpassword();
                // Tạo câu lệnh SQL để kiểm tra email và mật khẩu có tồn tại trong cơ sở dữ liệu hay không.
                String sql = "select * from account where Email='"+email+"' and Password='"+password+"'";
                // Thực thi câu lệnh SQL.
                Cursor cursor = db.rawQuery(sql, null);
                try{
                    // Di chuyển đến bản ghi đầu tiên, nếu không có bản ghi nào thì bắt lỗi.
                    cursor.moveToNext();
                    cursor.getString(0);
                }
                catch(Exception e){
                    // Nếu không tìm thấy bản ghi, hiển thị thông báo lỗi và trở về.
                    Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không khớp", Toast.LENGTH_LONG).show();
                    cursor.close();
                    return;
                }
                // Nếu tìm thấy bản ghi, hiển thị thông báo đăng nhập thành công.
                Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                // Chuyển đến trang Homepage và truyền email qua Bundle.
                Intent intent = new Intent(Login.this, Homepage.class);
                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                intent.putExtras(bundle);
                startActivity(intent); // Bắt đầu Activity mới.
            }
        });

        // Thiết lập sự kiện cho nút đăng ký.
        button = (Button) findViewById(R.id.BT_signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy email từ người dùng.
                getemail();
                // Chuyển đến trang đăng ký và truyền email qua Bundle.
                Intent intent = new Intent(Login.this, Signup.class);
                Bundle bundle = new Bundle();
                // Đặt email vào Bundle để truyền qua Activity đăng ký.
                bundle.putString("email", email);
                intent.putExtras(bundle);
                startActivity(intent); // Bắt đầu Activity mới.
            }
        });
    }

    void ConnectDB(){
        // Mở hoặc tạo db nếu chưa có
        db=openOrCreateDatabase("florist.db", MODE_PRIVATE, null);
        //tạo table nếu chưa có
        String sql = "create table if not exists Account(Email char(50) primary key, Password char(50), Name char(50))";
        db.execSQL(sql);
    }
    void getemail(){
        // Lấy email
        edittext = (EditText) findViewById(R.id.ET_email);
        email = edittext.getText().toString();
    }
    void getpassword(){
        // Lấy password
        edittext = (EditText) findViewById(R.id.ET_password);
        password = edittext.getText().toString();
    }

}
package com.example.florist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Checkout extends AppCompatActivity {
    EditText et_namepay,et_phonepay,et_locationpay;
    SQLiteDatabase db;
    Button bt_pay,btn_tiep;
    String email;
    int total;
    int id,price,imgflower,quantity;
    String nameflower;
    TextView tv_totalpay,tv_getemail;
    String idflower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thiết lập layout từ file XML.
        setContentView(R.layout.activity_checkout);

        // Lấy Intent được gửi từ Activity trước.
        Intent intent = getIntent();
        // Lấy Bundle từ Intent để trích xuất dữ liệu.
        Bundle bundle = intent.getExtras();

        // Trích xuất email từ Bundle.
        email = bundle.getString("email");
        // Trích xuất tổng giá trị đơn hàng từ Bundle.
        total = bundle.getInt("total");

        // Gọi phương thức ánh xạ các view từ layout XML.
        anhXa();
        // Gọi phương thức để kết nối với db.
        connectDB();
        // Thiết lập sự kiện click cho nút thanh toán.
        setClickPay();
        // Thiết lập sự kiện click cho nút tiếp tục mua sắm.
        setClickTieptuc();
    }

    //Click nút tiếp tục
    private void setClickTieptuc() {
        // Thiết lập sự kiện click cho nút btn_tiep.
        btn_tiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một Intent mới để chuyển từ CheckoutActivity sang Homepage.
                Intent intent = new Intent(Checkout.this, Homepage.class);

                // Tạo một Bundle mới để chứa dữ liệu cần truyền.
                Bundle bundle = new Bundle();
                // Đặt email vào bundle với key là "email".
                bundle.putString("email", email);
                // Đính kèm bundle vào Intent.
                intent.putExtras(bundle);

                // Bắt đầu Homepage Activity và truyền Bundle qua Intent.
                startActivity(intent);
            }
        });
    }

    //Click pay
    private void setClickPay() {
        // Thiết lập sự kiện click cho nút thanh toán.
        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin nhập từ các EditText, bao gồm tên, số điện thoại và địa chỉ, sau đó loại bỏ khoảng trắng thừa.
                String name = et_namepay.getText().toString().trim();
                String phone = et_phonepay.getText().toString().trim();
                String location = et_locationpay.getText().toString().trim();
                // Chuyển đổi số điện thoại từ String sang Integer. Lưu ý: có thể gây ra ngoại lệ nếu số điện thoại chứa ký tự không phải số.
                int phone1 = Integer.parseInt(phone);
                // Khởi tạo đối tượng Random để tạo ID ngẫu nhiên cho đơn hàng.
                Random random = new Random();
                id = random.nextInt();
                // Kiểm tra thông tin nhập vào. Nếu thông tin không đầy đủ hoặc số điện thoại không hợp lệ (dài hơn 11 chữ số), hiển thị thông báo lỗi.
                if(name.length() <= 0 || location.length() <= 0 || phone.length() <= 0 || phone.length() > 11){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập lại thông tin", Toast.LENGTH_LONG).show();
                }else{
                    // Nếu thông tin hợp lệ, tiến hành lặp qua danh sách giỏ hàng để lưu từng mục vào cơ sở dữ liệu.
                    for(int i = 0; i < Homepage.mListCart.size(); i++){
                        // Lấy thông tin từng sản phẩm trong giỏ hàng.
                        idflower = Homepage.mListCart.get(i).getIdflower();
                        nameflower = Homepage.mListCart.get(i).getNameflower();
                        price = (int) Homepage.mListCart.get(i).getPrice();
                        imgflower = Homepage.mListCart.get(i).getImgflower();
                        quantity = Homepage.mListCart.get(i).getQuantity();
                        // Tạo câu lệnh SQL để chèn thông tin sản phẩm vào bảng DetailOrder của cơ sở dữ liệu.
                        String sql1 = "Insert into DetailOrder values(" + id + ",'" + nameflower + "'," + price + "," + quantity + "," + imgflower + ",'" + email + "','" + name + "'," + phone1 + ",'" + location + "')";
                        // Thực thi câu lệnh SQL để lưu thông tin vào cơ sở dữ liệu.
                        db.execSQL(sql1);
                    }
                    // Sau khi lưu thành công tất cả sản phẩm trong giỏ hàng vào cơ sở dữ liệu, hiển thị thông báo đặt hàng thành công.
                    Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void connectDB() {
        // Mở hoặc tạo mới cơ sở dữ liệu SQLite có tên là "florist.db".
        // Nếu cơ sở dữ liệu này chưa tồn tại, nó sẽ được tạo mới.
        // MODE_PRIVATE đảm bảo rằng cơ sở dữ liệu này chỉ có thể được truy cập bởi ứng dụng gọi nó.
        db = openOrCreateDatabase("florist.db", MODE_PRIVATE, null);
    }



    private void anhXa() {
        // Ánh xạ EditText cho nhập địa chỉ thanh toán.
        et_locationpay = findViewById(R.id.et_locationpay);
        // Ánh xạ EditText cho nhập tên người thanh toán.
        et_namepay = findViewById(R.id.et_namepay);
        // Ánh xạ EditText cho nhập số điện thoại người thanh toán.
        et_phonepay = findViewById(R.id.et_phonepay);
        // Ánh xạ Button cho thực hiện thanh toán.
        bt_pay = findViewById(R.id.bt_pay);
        // Ánh xạ TextView hiển thị tổng số tiền cần thanh toán.
        tv_totalpay = findViewById(R.id.tv_totalpay);
        // Cập nhật TextView với tổng số tiền được truyền từ Activity trước.
        tv_totalpay.setText("" + total);
        // Ánh xạ Button cho tiếp tục mua sắm, quay lại màn hình trước.
        btn_tiep = findViewById(R.id.btn_tiep);
    }

}
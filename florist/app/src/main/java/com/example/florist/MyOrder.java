package com.example.florist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.florist.Adapter.OrderAdapter;
import com.example.florist.Model.Order;

import java.util.ArrayList;

public class MyOrder extends AppCompatActivity {
    ImageView img_order;
    TextView tv_tensp, tv_quantitysp, tv_giasp, tv_dc, tv_sdt;
    SQLiteDatabase db;
    RecyclerView rcv_order;
    String email;
    ArrayList<Order> mListOrder;
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập giao diện người dùng cho Activity này từ layout XML.
        setContentView(R.layout.activity_my_order);

        // Lấy Intent đã gửi đến Activity này.
        Intent intent = getIntent();
        // Lấy Bundle chứa dữ liệu từ Intent.
        Bundle bundle = intent.getExtras();

        // Trích xuất email từ Bundle, được gửi từ Activity trước.
        email = bundle.getString("email");

        // Gọi phương thức anhXa() để ánh xạ và khởi tạo các view từ layout.
        anhXa();

        // Gọi phương thức connectDB() để thiết lập kết nối với cơ sở dữ liệu.
        connectDB();

        // Khởi tạo danh sách đơn hàng.
        mListOrder = new ArrayList<>();

        // Gọi phương thức displayDataOrder() để lấy dữ liệu đơn hàng từ cơ sở dữ liệu.
        mListOrder = displayDataOrder();

        // Hiển thị dữ liệu đơn hàng lên giao diện người dùng.
        setDataOrder();
    }

    private void setDataOrder() {
        // Tạo một instance mới của OrderAdapter, truyền vào context, email, và danh sách đơn hàng.
        orderAdapter = new OrderAdapter(MyOrder.this, email, mListOrder);

        // Tạo một LinearLayoutManager mới để quản lý layout của các item trong RecyclerView.
        // Thiết lập này cho RecyclerView hiển thị các item theo chiều ngang và không đảo ngược danh sách.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyOrder.this, RecyclerView.HORIZONTAL, false);

        // Áp dụng LinearLayoutManager cho rcv_order, RecyclerView hiển thị danh sách đơn hàng.
        rcv_order.setLayoutManager(linearLayoutManager);

        // Thiết lập adapter cho RecyclerView để hiển thị dữ liệu từ OrderAdapter.
        rcv_order.setAdapter(orderAdapter);
    }

    private ArrayList<Order> displayDataOrder() {
        // Câu truy vấn SQL để lấy tất cả thông tin đơn hàng liên quan đến email cụ thể.
        String sql1 = "Select * from DetailOrder where Email='" + email + "'";
        // Thực thi câu truy vấn.
        Cursor cursor = db.rawQuery(sql1, null);
        // Khởi tạo danh sách để lưu trữ các đối tượng Order.
        ArrayList<Order> mListOrder = new ArrayList<>();
        // Di chuyển con trỏ đến bản ghi đầu tiên.
        if (cursor.moveToFirst()) {
            do {
                // Tạo mới một đối tượng Order.
                Order order = new Order();
                // Lấy dữ liệu từ con trỏ cursor và đặt vào đối tượng Order tương ứng.
                String id = cursor.getString(0); // Giả sử id không được sử dụng trong Order.
                String name = cursor.getString(1);
                int price = cursor.getInt(2);
                int quan = cursor.getInt(3);
                int img = cursor.getInt(4); // Giả sử đây là ID hình ảnh hoặc tên tệp.
                String location = cursor.getString(8);
                String namecus = cursor.getString(6);
                int phone = cursor.getInt(7);
                // Đặt các giá trị cho đối tượng Order.
                order.setNameflower(name);
                order.setImg(img);
                order.setQuantity(quan);
                order.setPrice(price);
                order.setNamecus(namecus);
                order.setLocation(location);
                order.setPhone(phone);
                // Thêm đối tượng Order vào danh sách.
                mListOrder.add(order);
            } while (cursor.moveToNext()); // Di chuyển đến bản ghi tiếp theo.
        }
        cursor.close(); // Đóng con trỏ khi không sử dụng nữa.
        return mListOrder; // Trả về danh sách các đơn hàng.
    }

    // Mở or tạo db nếu chưa có
    private void connectDB() {
        db = openOrCreateDatabase("florist.db", MODE_PRIVATE, null);
    }

    private void anhXa() {
        // Ánh xạ ImageView được sử dụng để hiển thị hình ảnh của đơn hàng.
        img_order = findViewById(R.id.img_order);

        // Ánh xạ TextView để hiển thị tên sản phẩm trong đơn hàng.
        tv_tensp = findViewById(R.id.tv_tensp);

        // Ánh xạ TextView để hiển thị số lượng sản phẩm.
        tv_quantitysp = findViewById(R.id.tv_quantitysp);

        // Ánh xạ TextView để hiển thị giá của sản phẩm.
        tv_giasp = findViewById(R.id.tv_giasp);

        // Ánh xạ TextView để hiển thị địa chỉ giao hàng.
        tv_dc = findViewById(R.id.tv_dc);

        // Ánh xạ TextView để hiển thị số điện thoại liên hệ.
        tv_sdt = findViewById(R.id.tv_sdt);

        // Ánh xạ RecyclerView được sử dụng để hiển thị danh sách các đơn hàng.
        rcv_order = findViewById(R.id.rcv_order);
    }
}
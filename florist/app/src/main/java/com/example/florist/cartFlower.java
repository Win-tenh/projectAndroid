package com.example.florist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.florist.Adapter.CartAdapter;

public class cartFlower extends AppCompatActivity {
    ListView lv_cart;
    String email;
    TextView tv_thongbao;
    static TextView tv_total;
    Button bt_thanhtoan,bt_tieptuc;
    Toolbar tb_cart;
    CartAdapter cartAdapter;

    @Override
    //Được gọi khi activity được tạo lần đầu tiên
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Thiết lập layout cho activity từ file XML.
        setContentView(R.layout.activity_cart_flower);

        // Lấy Intent gửi đến activity này và trích xuất Bundle chứa dữ liệu.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Lấy email từ Bundle và lưu vào biến instance 'email'.
        email = bundle.getString("email");

        // Gọi các phương thức khởi tạo và cấu hình.
        anhXa(); // Ánh xạ các view từ XML và khởi tạo adapter cho ListView.
        ActionToolBar(); // Cấu hình ActionBar/ToolBar.
        CheckData(); // Kiểm tra dữ liệu trong giỏ hàng và cập nhật UI tương ứng.
        setData(); // Tính toán và hiển thị tổng giá trị đơn hàng.
        deleteCart(); // Thiết lập sự kiện nhấn và giữ để xóa sản phẩm khỏi giỏ hàng.
        setClickTieptuc(); // Thiết lập sự kiện click cho nút "Tiếp tục mua hàng".
        setClickThanhToan(); // Thiết lập sự kiện click cho nút "Thanh toán".
    }
    //evt khi click tiếp tục
    private void setClickTieptuc() {
        bt_tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            //evt listener khi click vô
            public void onClick(View view) {
                // Khi nút được nhấp, tạo một Intent mới để chuyển từ Activity hiện tại (cartFlower) sang Activity Homepage.
                Intent intent = new Intent(cartFlower.this, Homepage.class);

                // Tạo một Bundle mới để chứa dữ liệu cần truyền giữa các Activity.
                Bundle bundle = new Bundle();
                // Đặt email vào bundle. Biến "email" được giả định là đã được khai báo và có giá trị ở một nơi nào đó trong code.
                bundle.putString("email", email);
                // Đính kèm bundle vào Intent.
                intent.putExtras(bundle);

                // Bắt đầu Activity mới (Homepage) và truyền bundle qua Intent.
                startActivity(intent);
            }
        });
    }
    //evt click nút thanh toán
    private void setClickThanhToan() {
        // Thiết lập sự kiện click cho nút "bt_thanhtoan".
        bt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra giỏ hàng có sản phẩm không
                if (Homepage.mListCart.size() > 0) {
                    // Lấy tổng giá trị đơn hàng từ TextView, bỏ đi 4 ký tự cuối (đơn vị tiền tệ), và chuyển thành số nguyên.
                    total = Integer.parseInt(tv_total.getText().toString().substring(0, tv_total.getText().length() - 4));

                    // Tạo một Intent mới để chuyển từ activity cartFlower sang activity Checkout.
                    Intent intent = new Intent(cartFlower.this, Checkout.class);

                    // Tạo một Bundle mới và thêm email và tổng giá trị đơn hàng vào Bundle.
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email); // Đặt email.
                    bundle.putInt("total", total); // Đặt tổng giá trị đơn hàng.

                    // Đính kèm Bundle vào Intent.
                    intent.putExtras(bundle);

                    // Bắt đầu activity Checkout, truyền qua dữ liệu.
                    startActivity(intent);
                } else {
                    // Nếu giỏ hàng trống, hiển thị thông báo Toast cho người dùng.
                    Toast.makeText(getApplicationContext(), "Giỏ hàng trống, không thể thanh toán được", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void deleteCart() {
        // Thiết lập sự kiện nhấn và giữ lâu trên một mục trong ListView lv_cart.
        lv_cart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Tạo một AlertDialog để xác nhận việc xóa mục.
                AlertDialog.Builder builder = new AlertDialog.Builder(cartFlower.this);
                builder.setTitle("Xác nhận xóa hoa ra khỏi giỏ hàng"); // Tiêu đề của cửa sổ xác nhận.
                builder.setMessage("Xóa"); // Nội dung thông báo.

                // Định nghĩa hành động khi nhấp vào nút "Đồng ý".
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Kiểm tra xem danh sách giỏ hàng có trống không.
                        if(Homepage.mListCart.size() <= 0){
                            // Nếu trống, hiển thị thông báo.
                            tv_thongbao.setVisibility(View.VISIBLE);
                        }else{
                            // Nếu không trống, xóa mục ở vị trí được chọn.
                            Homepage.mListCart.remove(position);
                            // Cập nhật adapter để phản ánh thay đổi trên giao diện.
                            cartAdapter.notifyDataSetChanged();
                            // Cập nhật tổng giá trị đơn hàng sau khi một mục được xóa.
                            setData();
                            // Kiểm tra lại sau khi xóa, nếu danh sách giờ đây trống, hiển thị thông báo.
                            if(Homepage.mListCart.size() <= 0){
                                tv_thongbao.setVisibility(View.VISIBLE);
                            }else{
                                // Nếu còn mục, ẩn thông báo và cập nhật giao diện.
                                tv_thongbao.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                setData();
                            }
                        }
                    }
                });

                // Định nghĩa hành động khi nhấp vào nút "Không".
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Người dùng chọn không xóa, chỉ cập nhật lại giao diện mà không thay đổi dữ liệu.
                        cartAdapter.notifyDataSetChanged();
                        setData();
                    }
                });

                // Hiển thị AlertDialog.
                builder.show();
                // Trả về true để báo hiệu sự kiện nhấn và giữ đã được xử lý.
                return true;
            }
        });
    }

    static int total;
    public static void setData() {
        // Khởi tạo tổng giá trị đơn hàng là 0.
        total = 0;
        // Duyệt qua từng mục trong giỏ hàng.
        for(int i = 0; i < Homepage.mListCart.size(); i++){
            // Cộng dồn giá trị của từng mục vào tổng giá trị đơn hàng.
            total += Homepage.mListCart.get(i).getPrice();
        }
        // Cập nhật TextView hiển thị tổng giá trị đơn hàng, thêm "vnd" làm đơn vị tiền tệ.
        tv_total.setText(total + " vnd");
    }


    private void CheckData() {
        // Kiểm tra nếu danh sách giỏ hàng trống (kích thước <= 0).
        if(Homepage.mListCart.size() <= 0){
            // Cập nhật lại adapter cho ListView để phản ánh sự thay đổi dữ liệu (nếu có).
            cartAdapter.notifyDataSetChanged();
            // Hiển thị TextView thông báo giỏ hàng trống.
            tv_thongbao.setVisibility(View.VISIBLE);
            // Ẩn ListView hiển thị danh sách các mục trong giỏ hàng.
            lv_cart.setVisibility(View.INVISIBLE);
        } else {
            // Nếu giỏ hàng không trống, cũng cập nhật lại adapter.
            cartAdapter.notifyDataSetChanged();
            // Ẩn TextView thông báo giỏ hàng trống.
            tv_thongbao.setVisibility(View.INVISIBLE);
            // Hiển thị ListView.
            lv_cart.setVisibility(View.VISIBLE);
        }
    }


    private void ActionToolBar() {
        // Đặt tb_cart làm ActionBar cho activity.
        setSupportActionBar(tb_cart);

        // Kích hoạt nút Up (mũi tên quay lại) trên ActionBar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Thiết lập sự kiện click cho nút Navigation trên ToolBar.
        tb_cart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kết thúc và đóng activity hiện tại, quay lại activity trước đó trong stack.
                finish();
            }
        });
    }


    private void anhXa() {
        // Ánh xạ ListView hiển thị danh sách các mục trong giỏ hàng.
        lv_cart = findViewById(R.id.lv_cart);

        // Ánh xạ TextView dùng để hiển thị thông báo khi giỏ hàng trống.
        tv_thongbao = findViewById(R.id.tv_thongbao);

        // Ánh xạ TextView hiển thị tổng giá trị của giỏ hàng.
        tv_total = findViewById(R.id.tv_total);

        // Ánh xạ Button dùng để tiến hành thanh toán.
        bt_thanhtoan = findViewById(R.id.bt_thanhtoan);

        // Ánh xạ Button cho phép người dùng tiếp tục mua sắm, quay lại trang chính.
        bt_tieptuc = findViewById(R.id.bt_tieptuc);

        // Ánh xạ Toolbar được sử dụng như một phần của giao diện người dùng.
        tb_cart = findViewById(R.id.tb_cart);

        // Khởi tạo CartAdapter với context là activity hiện tại và dữ liệu từ mListCart.
        cartAdapter = new CartAdapter(cartFlower.this, Homepage.mListCart);

        // Thiết lập cartAdapter cho lv_cart để hiển thị danh sách các mục trong giỏ hàng.
        lv_cart.setAdapter(cartAdapter);
    }

}
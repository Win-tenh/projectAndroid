package com.example.florist;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.florist.Model.Cart;
import com.example.florist.Model.Flower;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {
    public  static ArrayList<Cart> mListCart;
    String flowerid;
    int page=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Lấy dữ liệu email được truyền qua Intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        email = bundle.getString("email");

        // Khởi tạo danh sách giỏ hàng (nếu chưa có)
        if (mListCart != null) {
            // Do nothing, danh sách giỏ hàng đã được khởi tạo
        } else {
            mListCart = new ArrayList<>();
            // Khởi tạo danh sách giỏ hàng rỗng
        }

        // Thiết lập ViewPager2
        ViewPager2 vp2 = findViewById(R.id.vp2_title);
        vp2.setAdapter(new View2Apdapter(this));

        // Mở kết nối database
        db = openOrCreateDatabase("florist.db", MODE_PRIVATE, null);

        // Lấy dữ liệu hoa từ database
        getDataFlower();

        // Thiết lập các sự kiện
        setEvent();
    }

    ArrayList<Flower> flowers = new ArrayList<Flower>();
    String email = "";
    SQLiteDatabase db = null;

    void getDataFlower() {
        // Tạo bảng Flower nếu chưa tồn tại
        String sql = "Create table if not exists Flower(idflower char(50) primary key, nameflower char(50), category char(50), price int, color char(50), imgflower int, quantity int)";
        db.execSQL(sql);

        // Lấy dữ liệu tất cả các loại hoa
        sql = "select * from Flower";
        selectflower(sql);
    }


    boolean selectflower(String sql) {
        // Khởi tạo lại trang (dùng cho phân trang, có thể bỏ qua nếu không cần)
        page = 0;

        // Thực hiện truy vấn lấy dữ liệu hoa
        Cursor cursor = db.rawQuery(sql, null);

        try {
            // Khởi tạo danh sách hoa
            flowers = new ArrayList<>();

            // Duyệt qua kết quả truy vấn
            while (!cursor.isLast()) {
                // Lấy dữ liệu từng dòng
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                int price = cursor.getInt(3);
                String color = cursor.getString(4);
                int imgid = cursor.getInt(5);
                int quantity = cursor.getInt(6);

                // Tạo đối tượng Flower
                Flower fl = new Flower(id, name, category, price, color, imgid, quantity);

                // Thêm đối tượng Flower vào danh sách
                flowers.add(fl);
            }

            // Kiểm tra danh sách hoa rỗng
            if (flowers.size() == 0) {
                return false;
            }

            // Gọi hàm xử lý hiển thị dữ liệu hoa
            LoadContent();

        } catch (Exception e) {
            // Xử lý ngoại lệ
            NoContent();
            return false;
        }
        return true;
    }
    void setEvent() {
        // Lấy Edit Text để tìm kiếm
        EditText editText = findViewById(R.id.ET_SearchFlower);

        // Gán sự kiện lắng nghe key cho Edit Text
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                // Bắt sự kiện Enter
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && i == KeyEvent.KEYCODE_ENTER) {
                    // Xóa trang hiện tại (nếu có phân trang)
                    page = 0;

                    // Lấy từ khóa tìm kiếm
                    String tukhoa = editText.getText().toString();

                    // Tạo câu truy vấn tìm kiếm theo tên hoa
                    String sql = "select * from Flower where nameflower like '%" + tukhoa + "%'";

                    // Thực hiện tìm kiếm với câu truy vấn mới
                    return selectflower(sql);
                }
                return false;
            }
        });

        // Lấy ImageButton giỏ hàng
        imgBT_cartHome = findViewById(R.id.imgBT_cartHome);

        // Gán sự kiện click cho ImageButton giỏ hàng
        imgBT_cartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để chuyển sang trang giỏ hàng
                Intent intent = new Intent(Homepage.this, cartFlower.class);

                // Tạo Bundle để truyền email sang trang giỏ hàng
                Bundle bundle = new Bundle();
                bundle.putString("email", email);

                // Gán Bundle cho Intent
                intent.putExtras(bundle);

                // Chuyển sang trang giỏ hàng
                startActivity(intent);
            }
        });

        // Gán sự kiện click cho nút "Cây"
        ImageButton imgcCay = findViewById(R.id.imgBT_cCay);
        imgcCay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = " Select * from Flower where category like '%Cây%'";
                selectflower(sql);
            }
        });

        // Gán sự kiện click cho nút "Tất cả"
        ImageButton imgcAll = findViewById(R.id.imgBT_cAll);
        imgcAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = " Select * from Flower";
                selectflower(sql);
            }
        });

        // Gán sự kiện click cho nút "Hoa"
        ImageButton imgcHoa = findViewById(R.id.imgBT_cHoa);
        imgcHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = " Select * from Flower where category like '%Hoa%'";
                selectflower(sql);
            }
        });

        // Gán sự kiện click cho nút "Chậu"
        ImageButton imgcChau = findViewById(R.id.imgBT_cChau);
        imgcChau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = " Select * from Flower where category like '%Chậu%'";
                selectflower(sql);
            }
        });

        // Lấy Button quay lại trang
        Button button = findViewById(R.id.backpage);

        // Gán sự kiện click cho Button quay lại trang
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra nếu chưa đến trang đầu
                if (page > 0) {
                    // Quay lại trang trước
                    page--;
                    LoadContent();
                } else {
                    // Hiển thị thông báo nếu đang ở trang đầu
                    Toast.makeText(Homepage.this, "Số trang đã không thể thấp hơn được nữa", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Lấy Button sang trang tiếp theo
        button = findViewById(R.id.nextpage);

        // Gán sự kiện click cho Button sang trang tiếp theo
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hiển thị tối đa 10 mặt hàng trên 1 trang. Trang đầu là 0(hiển thị trên ứng dụng là 1)
                if (page < flowers.size() / 10) {
                    page++;
                    LoadContent();
                } else {
                    Toast.makeText(Homepage.this, "Số trang đã không thể cao hơn được nữa", Toast.LENGTH_LONG).show();
                }
            }
        });
// Gán sự kiện click cho nút "Đến trang"
        button = findViewById(R.id.gotopage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy EditText nhập số trang
                EditText et = findViewById(R.id.inputpage);

                // Chuyển đổi nội dung EditText thành số trang
                int p = Integer.parseInt(et.getText().toString()) - 1;

                // Kiểm tra tính hợp lệ của số trang nhập vào
                if (p <= flowers.size() / 10 && p >= 0) {
                    // Cập nhật trang hiện tại
                    page = p;

                    // Thực hiện cập nhật nội dung hiển thị
                    LoadContent();
                } else {
                    // Hiển thị thông báo lỗi nếu số trang không hợp lệ
                    Toast.makeText(Homepage.this, "???", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    ImageView imgv;
    TextView textname;
    TextView textprice;
    Button btnaddcart;
    RatingBar rbar;
    LinearLayout lay;
    ImageButton imgBT_cartHome;
    LinearLayout NoContent;
    TableLayout HaveContent;
    void NoContent(){
        // Lấy View "NoContent"
        NoContent = findViewById(R.id.NoContent);

        // Thiết lập hiển thị của "NoContent" thành "VISIBLE"
        NoContent.setVisibility(View.VISIBLE);

        // Lấy View "HaveContent"
        HaveContent = findViewById(R.id.HaveContent);

        // Thiết lập hiển thị của "HaveContent" thành "GONE"
        HaveContent.setVisibility(View.GONE);
    }

    void LoadContent() {

        // Ẩn khung "No Content" nếu không có nội dung
        LinearLayout NoContent = findViewById(R.id.NoContent);
        NoContent.setVisibility(View.GONE);

        // Hiển thị khung "Have Content" nếu có nội dung
        LinearLayout HaveContent = findViewById(R.id.HaveContent);
        HaveContent.setVisibility(View.VISIBLE);

        // Cập nhật số trang trong EditText
        EditText et = findViewById(R.id.inputpage);
        et.setText("" + (page + 1));

        // Thiết lập giao diện cho từng thông tin của một sản phẩm là hoa

        // Lấy tham chiếu đến ImageView hiển thị hình ảnh hoa thứ 1
        imgv = findViewById(R.id.imgFlower1);

        // Lấy tham chiếu đến TextView hiển thị tên hoa thứ 1
        textname = findViewById(R.id.TV_name1);

        // Lấy tham chiếu đến TextView hiển thị giá hoa thứ 1
        textprice = findViewById(R.id.TV_pricef1);

        // Lấy tham chiếu đến Button thêm hoa thứ 1 vào giỏ hàng
        btnaddcart = findViewById(R.id.BT_add1);

        // Lấy tham chiếu đến RatingBar hiển thị đánh giá hoa thứ 1
        rbar = findViewById(R.id.rating1);

        // Lấy tham chiếu đến Layout chứa các thông tin hoa thứ 1
        lay = findViewById(R.id.lay1);

        // Gọi hàm Design để thiết lập giao diện cho mục hoa thứ 1,
        Design(1);

        // Mấy hình còn lại tương tự nhé
        imgv = findViewById(R.id.imgFlower2);
        textname = findViewById(R.id.TV_name2);
        textprice = findViewById(R.id.TV_pricef2);
        btnaddcart = findViewById(R.id.BT_add2);
        rbar = findViewById(R.id.rating2);
        lay = findViewById(R.id.lay2);
        Design(2);
        imgv = findViewById(R.id.imgFlower3);
        textname = findViewById(R.id.TV_name3);
        textprice = findViewById(R.id.TV_pricef3);
        btnaddcart = findViewById(R.id.BT_add3);
        rbar = findViewById(R.id.rating3);
        lay = findViewById(R.id.lay3);
        Design(3);
        imgv = findViewById(R.id.imgFlower4);
        textname = findViewById(R.id.TV_name4);
        textprice = findViewById(R.id.TV_pricef4);
        btnaddcart = findViewById(R.id.BT_add4);
        rbar = findViewById(R.id.rating4);
        lay = findViewById(R.id.lay4);
        Design(4);
        imgv = findViewById(R.id.imgFlower5);
        textname = findViewById(R.id.TV_name5);
        textprice = findViewById(R.id.TV_pricef5);
        btnaddcart = findViewById(R.id.BT_add5);
        rbar = findViewById(R.id.rating5);
        lay = findViewById(R.id.lay5);
        Design(5);
        imgv = findViewById(R.id.imgFlower6);
        textname = findViewById(R.id.TV_name6);
        textprice = findViewById(R.id.TV_pricef6);
        btnaddcart = findViewById(R.id.BT_add6);
        rbar = findViewById(R.id.rating6);
        lay = findViewById(R.id.lay6);
        Design(6);
        imgv = findViewById(R.id.imgFlower7);
        textname = findViewById(R.id.TV_name7);
        textprice = findViewById(R.id.TV_pricef7);
        btnaddcart = findViewById(R.id.BT_add7);
        rbar = findViewById(R.id.rating7);
        lay = findViewById(R.id.lay7);
        Design(7);
        imgv = findViewById(R.id.imgFlower8);
        textname = findViewById(R.id.TV_name8);
        textprice = findViewById(R.id.TV_pricef8);
        btnaddcart = findViewById(R.id.BT_add8);
        rbar = findViewById(R.id.rating8);
        lay = findViewById(R.id.lay8);
        Design(8);
        imgv = findViewById(R.id.imgFlower9);
        textname = findViewById(R.id.TV_name9);
        textprice = findViewById(R.id.TV_pricef9);
        btnaddcart = findViewById(R.id.BT_add9);
        rbar = findViewById(R.id.rating9);
        lay = findViewById(R.id.lay9);
        Design(9);
        imgv = findViewById(R.id.imgFlower10);
        textname = findViewById(R.id.TV_name10);
        textprice = findViewById(R.id.TV_pricef10);
        btnaddcart = findViewById(R.id.BT_add10);
        rbar = findViewById(R.id.rating10);
        lay = findViewById(R.id.lay10);
        Design(10);
    }

    // Thiết lập giao diện cho một mục hoa
    void Design(int position){
        // Tính toán index của phần tử trong danh sách flowers dựa vào vị trí và trang
        int index = page*10+position-1;

        // Kiểm tra danh sách flowers có trống hay không
        if(flowers.size()==0){

        }

        // Kiểm tra xem index có vượt quá kích thước của danh sách flowers không
        if(index>=flowers.size()){
            // Nếu index vượt quá, ẩn layout của mục hoa (không hiển thị)
            lay.setVisibility(View.GONE);
            return;
        }

        // Hiển thị layout của mục hoa (nếu trước đó bị ẩn)
        lay.setVisibility(View.VISIBLE);

        // Thiết lập hình ảnh cho ImageView imgv
        imgv.setImageResource(flowers.get(index).getImgid());

        // Thiết lập tên hoa cho TextView textname
        textname.setText(flowers.get(index).getFlowername());

        // Thiết lập giá hoa cho TextView textprice
        textprice.setText("Giá: " + flowers.get(index).getPrice() + " đ");

        // Thiết lập sự kiện click cho nút "Thêm vào giỏ hàng" (btnaddcart)
        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                switch (view.getId()){
                    // Nếu nút được click có ID R.id.BT_add1
                    case R.id.BT_add1:{
                        flowerid = flowers.get(page*10+0).getFlowerid();
                        break;
                    }
                    // Tương tự mấy cái dưới nhé
                    case R.id.BT_add2:{
                        flowerid = flowers.get(page*10+1).getFlowerid();
                        break;
                    }
                    case R.id.BT_add3:{
                        flowerid = flowers.get(page*10+2).getFlowerid();
                        break;
                    }
                    case R.id.BT_add4:{
                        flowerid = flowers.get(page*10+3).getFlowerid();
                        break;
                    }
                    case R.id.BT_add5:{
                        flowerid = flowers.get(page*10+4).getFlowerid();
                        break;
                    }
                    case R.id.BT_add6:{
                        flowerid = flowers.get(page*10+5).getFlowerid();
                        break;
                    }
                    case R.id.BT_add7:{
                        flowerid = flowers.get(page*10+6).getFlowerid();
                        break;
                    }
                    case R.id.BT_add8:{
                        flowerid = flowers.get(page*10+7).getFlowerid();
                        break;
                    }
                    case R.id.BT_add9:{
                        flowerid = flowers.get(page*10+8).getFlowerid();
                        break;
                    }
                    case R.id.BT_add10:{
                        flowerid = flowers.get(page*10+9).getFlowerid();
                        break;
                    }
                }
                // Tạo Intent để chuyển sang trang chi tiết sản phẩm (FlowerDetail)
                Intent intent = new Intent(Homepage.this, FlowerDetail.class);

                // Tạo Bundle để lưu trữ dữ liệu cần truyền sang trang chi tiết
                Bundle bundle = new Bundle();

                // Thêm email của người dùng vào Bundle với key "email"
                bundle.putString("email", email);

                // Thêm ID của sản phẩm hoa được chọn vào Bundle với key "flowerid"
                bundle.putString("flowerid", flowerid);

                // Đính kèm Bundle với Intent
                intent.putExtras(bundle);

                // Khởi chạy trang chi tiết sản phẩm với dữ liệu được truyền qua Intent
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện click cho ImageView imgv (click vào hình ảnh)
        // Tương tự như trên
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.imgFlower1:{
                        flowerid = flowers.get(page*10+0).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower2:{
                        flowerid = flowers.get(page*10+1).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower3:{
                        flowerid = flowers.get(page*10+2).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower4:{
                        flowerid = flowers.get(page*10+3).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower5:{
                        flowerid = flowers.get(page*10+4).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower6:{
                        flowerid = flowers.get(page*10+5).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower7:{
                        flowerid = flowers.get(page*10+6).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower8:{
                        flowerid = flowers.get(page*10+7).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower9:{
                        flowerid = flowers.get(page*10+8).getFlowerid();
                        break;
                    }
                    case R.id.imgFlower10:{
                        flowerid = flowers.get(page*10+9).getFlowerid();
                        break;
                    }
                }
                Intent intent = new Intent(Homepage.this, FlowerDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("flowerid", flowerid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    // Lớp View2Adapter dùng để quản lý và hiển thị các fragment trong ViewPager2
    class View2Apdapter extends FragmentStateAdapter {
        // Constructor dùng cho các phiên bản Android mới hơn (hỗ trợ Lifecycle)
        public View2Apdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        // Constructor dùng cho các phiên bản Android cũ hơn
        public View2Apdapter(@NonNull FragmentActivity fragmentactivity) {
            super(fragmentactivity);
        }
        // Phương thức này được gọi để tạo ra một fragment dựa vào vị trí của nó
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            int imgid; // Biến lưu trữ ID của ảnh drawable
            // Chọn ảnh drawable dựa vào vị trí
            switch (position){
                case 1:{
                    imgid = R.drawable.banner2;
                    break;
                }
                case 2:{
                    imgid = R.drawable.banner3;
                    break;
                }
                default:{
                    imgid = R.drawable.banner1;
                    break;
                }
            }
            // Tạo và trả về một fragment home_slide với ID ảnh đã chọn
            return new home_slide(imgid);
        }

        // Trả về số lượng fragment cần hiển thị
        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
package com.example.florist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.florist.Adapter.FlowerAdapter;
import com.example.florist.Adapter.VoteAdapter;
import com.example.florist.Model.Cart;
import com.example.florist.Model.Flower;
import com.example.florist.Model.Vote;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FlowerDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String email1="";
    int page;
    ImageButton imgBT_cart;
    private int mSelectId;
    private  ArrayList<Vote> mListVote;
    private ArrayList<Flower> mListFlower;
    TextView tv_name,tv_price,tv_quantity,tv_namesptt,tv_pricesptt,
            tv_category,tv_color;
    Button bt_addtocart,bt_vote;
    ImageView img_chitiet,img_sptt;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar,tb_cart;
    RecyclerView rcv_vote,rcv_flower;
    VoteAdapter voteAdapter;
    FlowerAdapter flowerAdapter;
    RatingBar rating1,rating;
    EditText et_namevote,et_contentvote;
    String flowerid;
    @Override
// Hàm onCreate được gọi khi activity được tạo lần đầu tiên
    protected void onCreate(Bundle savedInstanceState) {

        // Gọi phương thức onCreate của lớp cha
        super.onCreate(savedInstanceState);

        // Thiết lập layout cho activity này
        setContentView(R.layout.activity_flower_detail);

        // Gọi phương thức anhXa để ánh xạ view
        anhXa();

        // Gọi phương thức ConnectDB để kết nối đến database
        ConnectDB();

        // Lấy dữ liệu được truyền từ activity khác
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Lấy flowerid, email từ Bundle và lưu vào biến flowerid và email1
        String flowerid = bundle.getString("flowerid");
        String email1 = bundle.getString("email");

        // Khởi tạo danh sách bình chọn rỗng
        ArrayList<Vote> mListVote = new ArrayList<>();

        // Hiển thị dữ liệu bình chọn và thêm vào danh sách mListVote
        mListVote = displayDataVote();

        // Tải dữ liệu chi tiết sản phẩm
        loadDataChitietSP();

        // Khởi tạo danh sách hoa rỗng
        ArrayList<Flower> mListFlower = new ArrayList<>();

        // Hiển thị dữ liệu hoa và thêm vào danh sách mListFlower
        mListFlower = displayDataFlower();

        // Hiển thị dữ liệu bình chọn
        displayDataVote();

        // Cài đặt dữ liệu bình chọn
        setDataVote();

        // Cài đặt sự kiện click cho nút bình chọn
        setClickVote();

        // Cài đặt dữ liệu hoa
        setDataFlower();

        // Cài đặt sự kiện click cho nút thêm vào giỏ hàng
        setClickAddtoCart();

        // Cài đặt sự kiện click cho nút hình ảnh giỏ hàng
        setClickCartImgButton();
    }



    String email;
    // Hàm setClickCartImgButton thiết lập hành động click cho nút hình ảnh giỏ hàng
    private void setClickCartImgButton() {

        // Cài đặt sự kiện click cho nút imgBT_cart
        imgBT_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Khi nút được click, thực hiện các bước sau:

                // Tạo một Intent mới để khởi chạy activity cartFlower
                Intent intent = new Intent(FlowerDetail.this, cartFlower.class);

                // Tạo một Bundle để chứa dữ liệu truyền cho activity tiếp theo
                Bundle bundle = new Bundle();

                // Thêm email vào Bundle với key là "email"
                bundle.putString("email", email1);

                // Thêm Bundle vào Intent
                intent.putExtras(bundle);

                // Khởi chạy activity cartFlower
                startActivity(intent);
            }
        });
    }

    // Hàm setClickAddtoCart thiết lập hành động click cho nút thêm vào giỏ hàng
    private void setClickAddtoCart() {

        // Cài đặt sự kiện click cho nút bt_addtocart
        bt_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Khi nút được click, thực hiện các bước sau:

                // Kiểm tra xem giỏ hàng đã có sản phẩm nào chưa
                if (Homepage.mListCart.size() > 0) {

                    // Nếu giỏ hàng đã có sản phẩm, thực hiện:
                    int sl = 1; // Số lượng sản phẩm thêm vào
                    boolean exit = false; // Biến kiểm tra sản phẩm đã tồn tại chưa

                    // Vòng lặp để kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                    for (int i = 0; i < Homepage.mListCart.size(); i++) {
                        if (Homepage.mListCart.get(i).getIdflower() == idflower) {
                            // Nếu sản phẩm đã tồn tại:
                            // - Tăng số lượng lên 1
                            Homepage.mListCart.get(i).setQuantity(Homepage.mListCart.get(i).getQuantity() + sl);
                            // - Giới hạn số lượng tối đa là 20
                            if (Homepage.mListCart.get(i).getQuantity() >= 20) {
                                Homepage.mListCart.get(i).setQuantity(20);
                            }
                            // - Cập nhật tổng giá tiền
                            Homepage.mListCart.get(i).setPrice(price * Homepage.mListCart.get(i).getQuantity());
                            // - Đánh dấu là đã tìm thấy sản phẩm trong giỏ hàng
                            exit = true;
                        }
                    }

                    // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới sản phẩm vào giỏ hàng
                    if (exit == false) {
                        int giamoi = sl * price;
                        Homepage.mListCart.add(new Cart(idflower, nameflower, giamoi, imgflower, sl));
                    }

                } else {
                    // Nếu giỏ hàng còn trống, thêm sản phẩm mới vào giỏ hàng
                    int sl = 1;
                    int giamoi = sl * price;
                    Homepage.mListCart.add(new Cart(idflower, nameflower, giamoi, imgflower, sl));
                }

                // Hiển thị thông báo thành công
                Toast.makeText(getApplicationContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Hàm setDataFlower thiết lập dữ liệu hoa cho RecyclerView
    private void setDataFlower() {

        // Khởi tạo FlowerAdapter
        flowerAdapter = new FlowerAdapter(FlowerDetail.this, mListFlower, email1);
        // - FlowerDetail.this: Context của activity hiện tại
        // - mListFlower: Danh sách chứa dữ liệu hoa
        // - email1: Địa chỉ email của người dùng (có thể được sử dụng trong adapter)

        // Khởi tạo LayoutManager cho RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FlowerDetail.this, RecyclerView.HORIZONTAL, false);
        // - LinearLayoutManager: Kiểu quản lý mặc định, xếp các item theo chiều dọc (VERTICAL)
        // - RecyclerView.HORIZONTAL: Canh chỉnh item hiển thị theo chiều ngang
        // - false: Không đảo ngược thứ tự item

        // Thiết lập LayoutManager cho RecyclerView
        rcv_flower.setLayoutManager(linearLayoutManager);

        // Thiết lập adapter cho RecyclerView
        rcv_flower.setAdapter(flowerAdapter);
    }

    // Hàm displayDataFlower lấy dữ liệu hoa theo danh mục và trừ sản phẩm hiện tại
    private ArrayList<Flower> displayDataFlower() {

        // 1. Lấy danh mục từ TextView
        String category = tv_category.getText().toString();

        // 2. Tạo câu truy vấn SQL
        String sql = "Select * from Flower where category like '%" + category + "%' except select * from Flower where idflower='" + flowerid + "'";
        // - "Select * from Flower where category like '%" + category + "%'":
        //   Chọn tất cả các bản ghi trong bảng Flower where category chứa chuỗi 'category'
        // - "except select * from Flower where idflower='" + flowerid + "'":
        //   Loại trừ các bản ghi có idflower bằng 'flowerid' khỏi kết quả truy vấn trước

        // Thực thi truy vấn và lấy dữ liệu
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Flower> mListFlower = new ArrayList<>();
        // Kiểm tra nếu có kết quả
        if (cursor.moveToFirst()) {
            do {
                // Duyệt qua từng dòng dữ liệu và tạo object Flower
                Flower flower = new Flower();
                String idflower1 = cursor.getString(0);
                String nameflower1 = cursor.getString(1);
                int price1 = cursor.getInt(3);
                int imgflower1 = cursor.getInt(5);

                // Gán giá trị cho object Flower
                flower.setFlowername(nameflower1);
                flower.setFlowerid(idflower1);
                flower.setPrice(price1);
                flower.setImgid(imgflower1);

                // Thêm object Flower vào danh sách
                mListFlower.add(flower);

            } while (cursor.moveToNext());
        }

        // Đóng Cursor
        cursor.close();

        // Trả về danh sách dữ liệu hoa
        return mListFlower;
    }

    // Hàm setClickVote xử lý sự kiện click cho nút bình chọn
    private void setClickVote() {

        // Lắng nghe sự kiện click cho nút bt_vote
        bt_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy thông tin người dùng từ các trường nhập
                String name = et_namevote.getText().toString().trim(); // Cắt bỏ khoảng trắng
                String content = et_contentvote.getText().toString().trim(); // Cắt bỏ khoảng trắng
                float ratingvote = rating.getRating(); // Lấy giá trị xếp hạng

                // Kiểm tra tính hợp lệ của thông tin
                if (name == null||content == null||ratingvote == 0) {
                    // Hiển thị thông báo lỗi
                    Toast.makeText(FlowerDetail.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    // Tạo câu lệnh SQL để thêm bình chọn vào database
                    String sql = "Insert into Vote(email,content,numstar,idflower) values ('" + name + "','" + content + "'," + ratingvote + ",'" + flowerid + "')";

                    // Thực thi câu lệnh SQL
                    db.execSQL(sql);

                    // Cập nhật giao diện sau khi thêm bình chọn thành công
                    displayDataVote(); // Làm mới danh sách bình chọn
                    setDataVote(); // Cập nhật tóm tắt bình chọn hoặc hiển thị trên màn hình

                    // Hiển thị thông báo thành công
                    Toast.makeText(FlowerDetail.this, "Đánh giá thành công", Toast.LENGTH_LONG).show();

                    // Xóa thông tin người dùng đã nhập
                    et_namevote.setText("");
                    et_contentvote.setText("");
                }
            }
        });
    }

    // Hàm lấy và hiển thị 5 bình chọn mới nhất cho hoa có ID được chỉ định
    private ArrayList<Vote> displayDataVote() {

        // Câu lệnh SQL để lấy tất cả các cột từ bảng Vote
        // Lọc theo idflower được truyền vào
        // Giới hạn kết quả 5 bản ghi mới nhất
        String sql = "Select * from Vote where idflower='" + flowerid + "' limit 5";

        // Thực thi câu lệnh SQL và lưu kết quả vào Cursor
        Cursor cursor = db.rawQuery(sql, null);

        // Tạo ArrayList để lưu trữ các đối tượng Vote
        ArrayList<Vote> mListVote = new ArrayList<>();

        // Kiểm tra nếu có kết quả
        if (cursor.moveToFirst()) {
            // Lặp qua từng bản ghi trong Cursor
            do {
                // Tạo một đối tượng Vote mới
                Vote vote = new Vote();

                // Lấy email từ bản ghi hiện tại
                String email = cursor.getString(0);

                // Lấy nội dung từ bản ghi hiện tại
                String content = cursor.getString(1);

                // Lấy số sao từ bản ghi hiện tại
                float ratingvote = cursor.getFloat(2);

                // Gán email cho đối tượng Vote
                vote.setEmail(email);

                // Gán nội dung cho đối tượng Vote
                vote.setContent(content);

                // Gán số sao cho đối tượng Vote
                vote.setNumStar(ratingvote);

                // Thêm đối tượng Vote vào ArrayList
                mListVote.add(vote);

                // Tiếp tục lặp cho đến khi hết bản ghi
            } while (cursor.moveToNext());
        }

        // Đóng Cursor để giải phóng tài nguyên
        cursor.close();

        // Trả về ArrayList chứa các đối tượng Vote
        return mListVote;
    }

    // Hàm thiết lập dữ liệu và adapter cho RecyclerView để hiển thị danh sách bình chọn
    private void setDataVote() {

        // Tạo adapter để kết nối dữ liệu với các view trong RecyclerView
        voteAdapter = new VoteAdapter(mListVote, FlowerDetail.this);

        // Cài đặt layout manager để bố trí các item theo chiều ngang
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FlowerDetail.this, RecyclerView.HORIZONTAL, false);
        rcv_vote.setLayoutManager(linearLayoutManager);

        // Gán adapter cho RecyclerView để hiển thị danh sách bình chọn
        rcv_vote.setAdapter(voteAdapter);
    }

    // Hàm ánh xạ các view trong layout
    private void anhXa() {

        // Ánh xạ image hiển thị chi tiết hoa
        img_chitiet = findViewById(R.id.img_chitiet);

        // Ánh xạ textview hiển thị tên hoa
        tv_name = findViewById(R.id.tv_name);

        // Ánh xạ textview hiển thị giá hoa
        tv_price = findViewById(R.id.tv_price);

        // Ánh xạ button thêm vào giỏ hàng
        bt_addtocart = findViewById(R.id.bt_addtocart);

        // Ánh xạ navigation view
        navigationView = findViewById(R.id.navigationview);

        // Ánh xạ textview hiển thị số lượng
        tv_quantity = findViewById(R.id.tv_quantity);

        // Ánh xạ recyclerview hiển thị bình chọn
        rcv_vote = findViewById(R.id.rcv_vote);

        // Ánh xạ rating bar 1 (có thể không dùng)
        rating1 = findViewById(R.id.rating1);

        // Ánh xạ rating bar để đánh giá
        rating = findViewById(R.id.rating);

        // Ánh xạ edittext nhập tên người bình chọn
        et_namevote = findViewById(R.id.et_namevote);

        // Ánh xạ edittext nhập nội dung bình chọn
        et_contentvote = findViewById(R.id.et_contentvote);

        // Ánh xạ button bình chọn
        bt_vote = findViewById(R.id.bt_vote);

        // Ánh xạ textview hiển thị tên sản phẩm gợi ý
        tv_namesptt = findViewById(R.id.tv_namesptt);

        // Ánh xjména sản phẩm gợi ý
        tv_pricesptt = findViewById(R.id.tv_pricesptt);

        // Ánh xạ image sản phẩm gợi ý
        img_sptt = findViewById(R.id.img_sptt);

        // Ánh xạ recyclerview hiển thị các sản phẩm khác
        rcv_flower = findViewById(R.id.rcv_flower);

        // Ánh xạ textview hiển thị loại hoa
        tv_category = findViewById(R.id.tv_category);

        // Ánh xạ textview hiển thị màu sắc
        tv_color = findViewById(R.id.tv_color);

        // Ánh xạ image button giỏ hàng
        imgBT_cart = findViewById(R.id.imgBT_cart);

        // Ánh xạ DrawerLayout
        drawerLayout = findViewById(R.id.drawerlayout);

        // Ánh xạ Toolbar
        toolbar = findViewById(R.id.toolbar);

        // Thiết lập Toolbar là action bar
        setSupportActionBar(toolbar);

        // Khởi tạo ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Thêm listener cho DrawerLayout
        drawerLayout.addDrawerListener(toggle);

        // Đồng bộ trạng thái của toggle
        toggle.syncState();

        // Thiết lập listener cho navigation view
        navigationView.setNavigationItemSelectedListener(this);
    }

    SQLiteDatabase db = null;
    void ConnectDB(){
        db=openOrCreateDatabase("florist.db", MODE_PRIVATE, null);
    }

    String idflower,nameflower,catagory,color,content;
    int price,quantity,imgflower;
    float ratingvote;
// Hàm lấy dữ liệu chi tiết sản phẩm (hoa)

    private void loadDataChitietSP() {

        // Tạo câu lệnh SQL để lấy thông tin hoa theo ID
        String sql = "Select * from Flower where idflower= '" + flowerid + "'";

        // Thực thi câu lệnh SQL và lưu kết quả vào Cursor
        Cursor cursor = db.rawQuery(sql, null);

        // Di chuyển đến bản ghi đầu tiên (nếu có)
        cursor.moveToFirst();

        // Duyệt qua các bản ghi kết quả (chỉ có 1 bản ghi vì lọc theo ID)
        while (!cursor.isAfterLast()) {

            // Lấy thông tin từ bản ghi hiện tại
            idflower = cursor.getString(0);
            nameflower = cursor.getString(1);
            catagory = cursor.getString(2);
            price = cursor.getInt(3);
            color = cursor.getString(4);
            imgflower = cursor.getInt(5);
            quantity = cursor.getInt(6);

            // Cập nhật hiển thị giá (thêm đơn vị tiền)
            tv_price.append(" " + price + " vnđ");

            // Cập nhật hiển thị tên hoa
            tv_name.append(nameflower);

            // Cập nhật hiển thị số lượng
            tv_quantity.append(" " + quantity);

            // Cập nhật textview loại hoa
            tv_category.setText(catagory);

            // Cập nhật textview màu sắc
            tv_color.setText(color);

            // Lấy ID ảnh từ database và đặt ảnh cho imageview
            img_chitiet.setImageResource(imgflower);

            // Di chuyển đến bản ghi tiếp theo (chỉ thực hiện 1 lần vì chỉ có 1 bản ghi)
            cursor.moveToNext();
        }

        // Đóng Cursor để giải phóng tài nguyên
        cursor.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Khởi tạo biến Intent để lưu trữ ý định bắt đầu một hoạt động mới (ban đầu là null)
        Intent intent = null;

        // Đánh dấu mục được nhấp trong NavigationView là đã chọn (làm nổi bật lựa chọn bằng hình ảnh)
        item.setChecked(true);

        // Lưu trữ ID của mục được nhấp để sử dụng sau này
        int selectedItemId = item.getItemId();

        // Xử lý lựa chọn dựa trên ID của mục được nhấp
        if (selectedItemId == R.id.nav_home) {
            // Xử lý khi click vào mục Home
            // Đóng Navigation Drawer
            drawerLayout.closeDrawer(GravityCompat.START);

            // Tạo một Intent mới để chuyển hướng đến activity Homepage
            intent = new Intent(FlowerDetail.this, Homepage.class);

            // Tạo một Bundle để truyền dữ liệu đến activity Homepage
            Bundle bundle = new Bundle();
            bundle.putString("email", email1);

            // Thêm Bundle chứa dữ liệu vào Intent
            intent.putExtras(bundle);

            // Bắt đầu hoạt động Homepage
            startActivity(intent);

            // Cho biết sự kiện đã được xử lý thành công
            return true;
        } else if (selectedItemId == R.id.nav_cart) {
            // **Xử lý khi click vào mục Giỏ hàng
            // Đóng Navigation Drawer
            drawerLayout.closeDrawer(GravityCompat.START);

            // Tạo một Intent mới để chuyển hướng đến activity CartFlower
            intent = new Intent(FlowerDetail.this, cartFlower.class);

            // Tạo một Bundle để truyền dữ liệu đến activity CartFlower
            Bundle bundle = new Bundle();
            bundle.putString("email", email1);

            // Thêm Bundle chứa dữ liệu vào Intent
            intent.putExtras(bundle);

            // Bắt đầu hoạt động CartFlower
            startActivity(intent);

            // Cho biết sự kiện đã được xử lý thành công
            return true;
        } else if (selectedItemId == R.id.nav_order) {
            // Xử lý khi click vào mục Lịch sử đơn hàng
            // Đóng Navigation Drawer
            drawerLayout.closeDrawer(GravityCompat.START);

            // Tạo một Intent mới để chuyển hướng đến activity MyOrder
            intent = new Intent(FlowerDetail.this, MyOrder.class);

            // Tạo một Bundle để truyền dữ liệu đến activity MyOrder
            Bundle bundle = new Bundle();
            bundle.putString("email", email1);

            // Thêm Bundle chứa dữ liệu vào Intent
            intent.putExtras(bundle);

            // Bắt đầu hoạt động MyOrder
            startActivity(intent);

            // Cho biết sự kiện đã được xử lý thành công
            return true;
        }
        return true;
    }



}
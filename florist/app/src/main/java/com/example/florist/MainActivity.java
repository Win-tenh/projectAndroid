package com.example.florist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {
    Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tạo một Intent mới để chuyển từ MainActivity sang Login Activity.
        Intent intent = new Intent(MainActivity.this, Login.class);

        // Chạy hàm ở dưới
        FirstTime();

        // Bắt đầu Login Activity.
        startActivity(intent);

        // Kết thúc và đóng MainActivity
        finish();
    }
    void FirstTime(){

        // Tạo hoặc mở db
        SQLiteDatabase db = openOrCreateDatabase("florist.db", MODE_PRIVATE, null);
        try{

            // Tạo bảng rồi thêm data vào
            db.execSQL("create table if not exists FirstUse(first int unique)");
            db.execSQL("insert into FirstUse values(1)");
            //đã có = lỗi -> không insert nữa
            String sql = "Create table if not exists Flower(idflower char(50) primary key, nameflower char(50), category char(50),price int,color char(50),imgflower int,quantity int)";
            db.execSQL(sql);
            sql = " insert into Flower values" +
                    "('tr01', 'Bàng Sin mini', 'Cây', 95000, 'Xanh', "+R.drawable.bangsin+", 40)," +
                    "('p02', 'Bàng chúc ngủ ngon', 'Cây, Chậu', 180000, 'Xanh', "+ R.drawable.bangzz +", 95)," +
                    "('tr03', 'Lưỡi hổ', 'Cây', 105000, 'Xanh', "+R.drawable.luoiho+", 54)," +
                    "('p01', 'Lưỡi hổ happy birthday', 'Cây, Chậu', 125000, 'Xanh', "+R.drawable.luoihohppd+", 87)," +
                    "('fl01', 'Moon River', 'Hoa', 205000, 'Xanh', "+R.drawable.moonriver+", 45)," +
                    "('fl02', 'Pinky', 'Hoa', 99000, 'Hồng', "+R.drawable.pinky+", 227)," +
                    "('p1', 'Sen đá cá voi', 'Hoa, Chậu', 150000, 'Xanh', "+R.drawable.sebdacavoi+", 54)," +
                    "('tr05', 'Trầu bà sữa', 'Cây', 80000, 'Xanh', "+R.drawable.traubasua+", 65)," +
                    "('fl04', 'Fallforyou', 'Hoa', 92000, 'Xanh', "+R.drawable.fallforyou+", 879)," +
                    "('fl05', 'Bó hoa mẫu số 1', 'Hoa', 100000, 'Đỏ', "+R.drawable.hoa1+", 56)," +
                    "('fl06', 'Bó hoa mẫu số 2', 'Hoa', 100000, 'Trắng', "+R.drawable.hoa2+", 61)," +
                    "('fl07', 'Bó hoa mẫu số 3', 'Hoa', 100000, 'Đỏ', "+R.drawable.hoa3+", 321)," +
                    "('fl08', 'Bó hoa mẫu số 4', 'Hoa', 100000, 'Đỏ', "+R.drawable.hoa4+", 14)," +
                    "('fl09', 'Bó hoa mẫu số 5', 'Hoa', 100000, 'Đỏ', "+R.drawable.hoa5+", 456)," +
                    "('fl10', 'Bó hoa mẫu số 6', 'Hoa', 100000, 'Đỏ', "+R.drawable.hoa6+", 45)," +
                    "('fl11', 'Bó hoa mẫu số 7', 'Hoa', 100000, 'Hồng', "+R.drawable.hoa7+", 78)," +
                    "('fl12', 'Bó hoa mẫu số 8', 'Hoa', 100000, 'Trắng', "+R.drawable.hoa8+", 52)," +
                    "('fl13', 'Bó hoa mẫu số 9', 'Hoa', 100000, 'Xanh, Trắng', "+R.drawable.hoa1_date+", 14)," +
                    "('fl14', 'Bó hoa mẫu số 10', 'Hoa', 100000, 'Hồng', "+R.drawable.hoa2_date+", 49)," +
                    "('fl15', 'Bó hoa mẫu số 11', 'Hoa', 100000, 'Hồng', "+R.drawable.hoa3_date+", 65)," +
                    "('fl16', 'Bó hoa mẫu số 12', 'Hoa', 100000, 'Hồng', "+R.drawable.hoa4_date+", 98)," +
                    "('fl17', 'Hoa lan Parade', 'Hoa', 156000, 'Xanh', "+R.drawable.hoalanparade+", 47)," +
                    "('fl18', 'Bó hoa mẫu số 14', 'Hoa', 100000, 'Xanh', "+R.drawable.hoahong1+", 62)," +
                    "('fl19', 'Bó hoa mẫu số 15', 'Hoa', 100000, 'Xanh', "+R.drawable.hoa_tinhyeu+", 55)," +
                    "('p2', 'Sen đá hươu cao cổ', 'Hoa, Chậu', 180000, 'Xanh', "+R.drawable.chau1+", 75)," +
                    "('p3', 'Sen đá cún nằm ngủ', 'Hoa, Chậu', 200000, 'Xanh', "+R.drawable.chaucay+", 36)," +
                    "('p4', 'Sen đá happy birthday', 'Hoa, Chậu', 175000, 'Xanh', "+R.drawable.tree3+", 44)," +
                    "('p5', 'Sen đá voi', 'Hoa, Chậu', 175000, 'Xanh', "+R.drawable.chau2+", 17)," +
                    "('fl20', 'Lavierose', 'Hoa', 150000, 'Hồng', "+R.drawable.lavierose+", 60)";
            db.execSQL(sql);
            String sql1="create table if not exists Vote(email char(50),content char(100),numstar float,idflower char(50),foreign key (idflower)references Flower(idflower))";
            db.execSQL(sql1);
            String sql2 = "create table if not exists Flower(idflower char(50) primary key, nameflower char(50), category char(50),price int,color char(50),imgflower int,quantity int)";
            db.execSQL(sql2);
            String sql3="Create table if not exists DetailOrder(idorder int, nameflower char(50), " +
                    "price int, quantity int, imgflower int,Email char(50),namecus char(50),phone int(11),location char(50))";
            db.execSQL(sql3);
        }
        catch (Exception e){
            return;
        }
    }
}
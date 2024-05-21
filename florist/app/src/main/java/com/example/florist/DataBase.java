package com.example.florist;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DataBase extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tạo hoặc mở db tên florist
        db = openOrCreateDatabase("florist.db",MODE_PRIVATE,null);
        // Tạo table Account nếu chưa có
        String sql = "create table if not exists Account(Email char(50) primary key, Password char(50), Name char(50))";
        // Chạy
        db.execSQL(sql);
        // Tạo table Vote nếu chưa có
        String sql1="create table if not exists Vote(email char(50),content char(100),numstar float,idflower char(50),foreign key (idflower)references Flower(idflower))";
        // Chạy
        db.execSQL(sql1);
        // Tạo table Flower nếu chưa có
        String sql2 = "create table if not exists Flower(idflower char(50) primary key, nameflower char(50), category char(50),price int,color char(50),imgflower int,quantity int)";
        // Chạy
        db.execSQL(sql2);
        // Tạo table DetailOrder nếu chưa có
        String sql3="Create table if not exists DetailOrder(idorder int primary key, nameflower char(50), " +
                "price int, quantity int, imgflower int,Email char(50),namecus char(50),phone int(11),location char(50))";
        // Chạy
        db.execSQL(sql3);
        // Thêm data vô mấy cái bảng trên
        db.execSQL("Insert into Flower values('flower1','First Date','Hoa',300000,'yellow and white',"+R.drawable.hoa1_date+",100)"
                + ",('flower2','Carla','Date',550000,'Hoa',"+R.drawable.hoa2_date+",100)"
                +",('flower3','La Vie En Rose','Hoa',500000,'pink and violet',"+R.drawable.hoa3_date+",100)"
                +",('flower4','Violet Lover','Hoa',700000,'pink and violet',"+R.drawable.hoa4_date+",100)");
        db.execSQL("Insert into Flower values('flower5_date','BearFlower','Hoa',300000,'red',"+R.drawable.hoa1+",100)"
                + ",('flower6','WhiteRose','Date',550000,'Hoa',"+R.drawable.hoa2+",100)"
                +",('flower7','Gorerous','Hoa',500000,'red',"+R.drawable.hoa3+",100)"
                +",('flower8','Valentine','Hoa',700000,'red',"+R.drawable.hoa4+",100)");
        db.execSQL("Insert into Flower values('flower9_date','BlackRed','Hoa',300000,'red',"+R.drawable.hoa5+",100)"
                + ",('flower10','Carlaaaaa','Hoa',550000,'red',"+R.drawable.hoa6+",100)"
                +",('flower11','En Rose','Hoa',500000,'pink and violet',"+R.drawable.hoa7+",100)"
                +",('flower12','Graduatee reosd','Hoa',700000,'white and yellow',"+R.drawable.hoa8+",100)");
        db.execSQL("Insert into Flower values('tree1','Chậu Bàng Sin','Cây',300000,'green',"+R.drawable.bangsin+",100)"
                + ",('tree2','Chậu Sen Đá','Cây',550000,'green',"+R.drawable.tree2+",100)"
                +",('tree3','Chậu lưỡi hổ','Cây',500000,'green',"+R.drawable.luoiho+",100)"
                +",('tree4','Graduatee reosd','Cây',700000,'green',"+R.drawable.tree3+",100)");
        db.execSQL("Insert into Flower values('tree1','Chậu Bàng Sin','Cây',300000,'green',"+R.drawable.bangsin+",100)"
                + ",('tree2','Chậu Sen Đá','Cây',550000,'green',"+R.drawable.tree2+",100)"
                +",('tree3','Chậu lưỡi hổ','Cây',500000,'green',"+R.drawable.luoiho+",100)"
                +",('tree4','Birthday tree','Cây',700000,'green',"+R.drawable.tree3+",100)");
        db.execSQL("Insert into Flower values('tree1','Chậu hưu cao cổ','Chậu',300000,'green',"+R.drawable.chau1+",100)"
                + ",('tree2','Chậu Pit','Chậu',550000,'blue',"+R.drawable.chaucay+",100)"
                +",('tree3','Chậu cá voi','Chậu',500000,'yellow',"+R.drawable.sebdacavoi+",100)"
                +",('tree4','Chậu voi','Chậu',700000,'green',"+R.drawable.chau2+",100)");
    }
}
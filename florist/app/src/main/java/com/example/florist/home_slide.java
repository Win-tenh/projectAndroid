package com.example.florist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class home_slide extends Fragment {
    private int imgid;
    public home_slide(int imgid) {
        this.imgid = imgid;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Khởi tạo biến root để lưu trữ giao diện được tạo từ layout
        View root = inflater.inflate(R.layout.fragment_home_slide, container, false);

        // Tìm kiếm ImageView trong giao diện đã được tạo
        ImageView imgview = root.findViewById(R.id.imgview);

        // Gán hình ảnh cho ImageView dựa trên ID được lưu trữ trong biến imgid
        imgview.setImageResource(imgid);

        // Cập nhật biến imgid (tùy chọn, lý do không rõ ràng dựa trên đoạn mã này)
        imgid = R.id.imgview;

        // Trả về giao diện (root) của fragment
        return root;
    }

}
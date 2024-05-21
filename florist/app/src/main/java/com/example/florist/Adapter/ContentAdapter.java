package com.example.florist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ContentInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.florist.FlowerDetail;
import com.example.florist.R;
import com.example.florist.Model.Flower;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {
    String email;
    ArrayList<Flower> flowers;
    Context context;
    public ContentAdapter(ArrayList<Flower> flowers, String email, Context context) {
        this.flowers = flowers;
        this.context = context;
        this.email = email;
    }

    @NonNull
    @Override
    public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng LayoutInflater để tạo một View mới từ XML layout (trong trường hợp này là fragment_load_content_home.xml).
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Tạo View mới mà không tự động gắn vào ViewGroup gốc (parent) ngay lập tức (tham số attachToRoot là false).
        View itemview = layoutInflater.inflate(R.layout.fragment_load_content_home, parent, false);

        // Trả về một instance mới của ContentHolder, được khởi tạo với View mới tạo từ XML layout.
        return new ContentHolder(itemview);
    }


    int index;
    @Override
    public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
        // Lưu vị trí hiện tại vào biến index.
        index = position;
        // Gán hình ảnh, tên, và giá cho view thứ nhất trong holder.
        holder.imgv.setImageResource(flowers.get(index * 2).getImgid());
        holder.tvname.setText(flowers.get(index * 2).getFlowername());
        holder.tvprice.setText(String.valueOf(flowers.get(index * 2).getPrice()) + "đ");

        // Thiết lập sự kiện click cho hình ảnh view thứ nhất.
        holder.imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Intent để mở Activity chi tiết sản phẩm.
                Intent intent = new Intent(context, FlowerDetail.class);
                Bundle bundle = new Bundle();
                // Đính kèm thông tin sản phẩm vào Bundle.
                bundle.putString("flowerid", flowers.get(index * 2).getFlowerid());
                bundle.putString("flowername", flowers.get(index * 2).getFlowername());
                bundle.putString("email", email);
                intent.putExtras(bundle);
                // Thiết lập cờ để Activity mới được mở trong một task mới.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Bắt đầu Activity mới.
                context.startActivity(intent);
            }
        });

        // Kiểm tra nếu là phần tử cuối cùng và không có cặp, ẩn view thứ hai.
        if (position * 2 + 1 == flowers.size()) {
            holder.lay2.setVisibility(View.INVISIBLE);
            return;
        }

        // Gán hình ảnh, tên, và giá cho view thứ hai trong holder (nếu có).
        holder.imgv2.setImageResource(flowers.get(index * 2 + 1).getImgid());
        holder.tvname2.setText(flowers.get(index * 2 + 1).getFlowername());
        holder.tvprice2.setText(String.valueOf(flowers.get(index * 2 + 1).getPrice()) + "đ");

        // Thiết lập sự kiện click cho hình ảnh view thứ hai.
        holder.imgv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tương tự như view thứ nhất, tạo Intent và mở Activity chi tiết.
                Intent intent = new Intent(context, FlowerDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("flowerid", flowers.get(index * 2 + 1).getFlowerid());
                bundle.putString("flowername", flowers.get(index * 2 + 1).getFlowername());
                bundle.putString("email", email);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    // Lấy số lượng sản phẩm
    public int getItemCount() {
        return flowers.size()/2 + flowers.size()%2;
    }

    class ContentHolder extends RecyclerView.ViewHolder{
        TextView tvname;
        TextView tvprice;
        ImageView imgv;
        RatingBar ratingb;
        TextView tvname2;
        TextView tvprice2;
        ImageView imgv2;
        RatingBar ratingb2;
        LinearLayout lay2;

        public ContentHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ TextView hiển thị tên của sản phẩm thứ nhất.
            tvname = itemView.findViewById(R.id.TV_name1);
            // Ánh xạ TextView hiển thị giá của sản phẩm thứ nhất.
            tvprice = itemView.findViewById(R.id.TV_pricef1);
            // Ánh xạ ImageView hiển thị hình ảnh của sản phẩm thứ nhất.
            imgv = itemView.findViewById(R.id.imgFlower1);
            // Ánh xạ RatingBar hiển thị đánh giá của sản phẩm thứ nhất.
            ratingb = itemView.findViewById(R.id.rating1);

            // Ánh xạ TextView hiển thị tên của sản phẩm thứ hai.
            tvname2 = itemView.findViewById(R.id.TV_name2);
            // Ánh xạ TextView hiển thị giá của sản phẩm thứ hai.
            tvprice2 = itemView.findViewById(R.id.TV_pricef2);
            // Ánh xạ ImageView hiển thị hình ảnh của sản phẩm thứ hai.
            imgv2 = itemView.findViewById(R.id.imgFlower2);
            // Ánh xạ RatingBar hiển thị đánh giá của sản phẩm thứ hai.
            ratingb2 = itemView.findViewById(R.id.rating2);

            // Ánh xạ ViewGroup chứa view của sản phẩm thứ hai.
            lay2 = itemView.findViewById(R.id.lay2);
        }
    }

}


package com.example.florist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.florist.FlowerDetail;
import com.example.florist.R;
import com.example.florist.Model.Flower;

import java.util.ArrayList;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.ViewHolder> {
    private Context context;
    String email;
    private ArrayList<Flower> mListFlower;
    public FlowerAdapter(Context context, ArrayList<Flower> mListFlower,String email) {
        this.context = context;
        this.mListFlower = mListFlower;
        this.email=email;
    }


    @NonNull
    @Override
    public FlowerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flower,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FlowerAdapter.ViewHolder holder, int position) {
        // Lấy đối tượng Flower từ danh sách dựa trên vị trí.
        Flower Flower = mListFlower.get(position);
        // Lấy tên, giá, và ID hình ảnh từ đối tượng Flower.
        String nameflower = Flower.getFlowername();
        float price = Flower.getPrice();
        int imgflower = Flower.getImgid();

        // Thiết lập tên và giá cho các TextView trong ViewHolder.
        holder.tv_namesptt.setText(nameflower);
        holder.tv_pricesptt.setText(price + " vnđ");
        // Thiết lập hình ảnh cho ImageView trong ViewHolder.
        holder.img_sptt.setImageResource(imgflower);

        // Thiết lập sự kiện click cho ImageView.
        holder.img_sptt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một Intent mới để mở FlowerDetail Activity.
                Intent intent = new Intent(context, FlowerDetail.class);
                // Tạo và đặt Bundle để chứa dữ liệu gửi qua Intent.
                Bundle bundle = new Bundle();
                bundle.putString("flowerid", mListFlower.get(position).getFlowerid()); // Đặt ID của hoa.
                bundle.putString("email", email); // Đặt email (nếu cần).
                // Đính kèm Bundle vào Intent.
                intent.putExtras(bundle);
                // Thiết lập cờ để mở Activity trong một task mới.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Bắt đầu Activity mới.
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListFlower.size();
    }

    // Lớp ViewHolder kế thừa từ RecyclerView.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view bên trong mỗi item của RecyclerView.
        private TextView tv_namesptt, tv_pricesptt;
        private ImageView img_sptt;

        // Constructor nhận vào một View, đại diện cho itemView của RecyclerView.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view từ itemView.
            tv_namesptt = itemView.findViewById(R.id.tv_namesptt);
            img_sptt = itemView.findViewById(R.id.img_sptt);
            tv_pricesptt = itemView.findViewById(R.id.tv_pricesptt);
        }
    }

}


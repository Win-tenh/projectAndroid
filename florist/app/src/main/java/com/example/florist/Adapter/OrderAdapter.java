package com.example.florist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.florist.R;
import com.example.florist.Model.Flower;
import com.example.florist.Model.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    String email;
    private ArrayList<Order> mListOrder;

    public OrderAdapter(Context context, String email, ArrayList<Order> mListOrder) {
        this.context = context;
        this.email = email;
        this.mListOrder = mListOrder;
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        // Lấy đối tượng Order tại vị trí hiện tại trong danh sách.
        Order order = mListOrder.get(position);

        // Lấy dữ liệu từ đối tượng Order và lưu vào các biến tạm.
        int img = order.getImg(); // ID hình ảnh.
        String dc = order.getLocation(); // Địa chỉ.
        String name = order.getNameflower(); // Tên hoa.
        int quan = order.getQuantity(); // Số lượng.
        int price = order.getPrice(); // Giá.
        int sdt = order.getPhone(); // Số điện thoại.
        String namecus = order.getNamecus(); // Tên khách hàng.

        // Thiết lập dữ liệu cho các view trong ViewHolder dựa trên dữ liệu từ đối tượng Order.
        holder.img_order.setImageResource(img); // Thiết lập hình ảnh hoa.
        holder.tv_quantitysp.setText("" + quan); // Thiết lập số lượng.
        holder.tv_tensp.setText(name); // Thiết lập tên hoa.
        holder.tv_giasp.setText("" + price); // Thiết lập giá.
        holder.tv_dc.setText(dc); // Thiết lập địa chỉ.
        holder.tv_sdt.setText("" + sdt); // Thiết lập số điện thoại.
        holder.tv_namecus.setText(namecus); // Thiết lập tên khách hàng.
    }

    // Lấy số lượng sp
    @Override
    public int getItemCount() {
        return mListOrder.size();
    }

    // Lớp ViewHolder mở rộng từ RecyclerView.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các biến tham chiếu đến các view trong layout của mỗi item
        ImageView img_order;
        TextView tv_tensp, tv_quantitysp, tv_giasp, tv_dc, tv_sdt, tv_namecus;

        // Constructor nhận vào một View, tức là itemView của RecyclerView
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view trong layout vào các biến tương ứng
            img_order = itemView.findViewById(R.id.img_order); // ImageView hiển thị hình ảnh đơn hàng
            tv_dc = itemView.findViewById(R.id.tv_dc); // TextView hiển thị địa chỉ
            tv_tensp = itemView.findViewById(R.id.tv_tensp); // TextView hiển thị tên sản phẩm
            tv_quantitysp = itemView.findViewById(R.id.tv_quantitysp); // TextView hiển thị số lượng sản phẩm
            tv_giasp = itemView.findViewById(R.id.tv_giasp); // TextView hiển thị giá sản phẩm
            tv_sdt = itemView.findViewById(R.id.tv_sdt); // TextView hiển thị số điện thoại
            tv_namecus = itemView.findViewById(R.id.tv_namecus); // TextView hiển thị tên khách hàng
        }
    }

}

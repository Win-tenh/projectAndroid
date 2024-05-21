package com.example.florist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.florist.Homepage;
import com.example.florist.R;
import com.example.florist.cartFlower;
import com.example.florist.Model.Cart;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrayCart;

    public CartAdapter(Context context, ArrayList<Cart> arrayCart) {
        this.context = context;
        this.arrayCart = arrayCart;
    }

    @Override
    public int getCount() {
        return arrayCart.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        public TextView tv_namecart,tv_pricecart;
        public ImageView img_cart;
        public Button btn_minus,btn_values,btn_plus;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        // Kiểm tra xem View có đang tái sử dụng không.
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Tạo mới View từ layout XML.
            view = inflater.inflate(R.layout.listorder, null);
            // Ánh xạ các view thành phần trong layout vào ViewHolder.
            viewHolder.tv_namecart = view.findViewById(R.id.tv_namecart);
            viewHolder.tv_pricecart = view.findViewById(R.id.tv_pricecart);
            viewHolder.img_cart = view.findViewById(R.id.img_cart);
            viewHolder.btn_minus = view.findViewById(R.id.btn_minus);
            viewHolder.btn_values = view.findViewById(R.id.btn_values);
            viewHolder.btn_plus = view.findViewById(R.id.btn_plus);
            // Lưu trữ ViewHolder trong view.
            view.setTag(viewHolder);
        } else {
            // Nếu view đã tồn tại, tái sử dụng nó.
            viewHolder = (ViewHolder) view.getTag();
        }

        // Lấy dữ liệu cho mục được chọn.
        Cart cart = (Cart) getItem(i);
        // Thiết lập dữ liệu cho các view thành phần.
        viewHolder.tv_namecart.setText(cart.getNameflower());
        viewHolder.tv_pricecart.setText((int) cart.getPrice() + " vnd");
        viewHolder.img_cart.setImageResource(cart.getImgflower());
        viewHolder.btn_values.setText(cart.getQuantity() + "");

        // Cập nhật giao diện dựa trên số lượng sản phẩm.
        int sl = Integer.parseInt(viewHolder.btn_values.getText().toString());
        if (sl >= 10) {
            viewHolder.btn_plus.setVisibility(View.INVISIBLE);
            viewHolder.btn_minus.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewHolder.btn_plus.setVisibility(View.VISIBLE);
            viewHolder.btn_minus.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewHolder.btn_plus.setVisibility(View.VISIBLE);
            viewHolder.btn_minus.setVisibility(View.VISIBLE);
        }
// Tạo một tham chiếu cuối cùng đến viewHolder để sử dụng trong sự kiện click.
        ViewHolder finalViewHolder = viewHolder;

// Thiết lập sự kiện click cho nút tăng số lượng.
        viewHolder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tăng số lượng sản phẩm lên 1.
                int slmoi = Integer.parseInt(finalViewHolder.btn_values.getText().toString()) + 1;
                // Lấy số lượng hiện tại và giá hiện tại của sản phẩm từ danh sách giỏ hàng.
                int slhientai = Homepage.mListCart.get(i).getQuantity();
                int giaht = (int) Homepage.mListCart.get(i).getPrice();

                // Cập nhật số lượng mới cho sản phẩm trong giỏ hàng.
                Homepage.mListCart.get(i).setQuantity(slmoi);

                // Tính giá mới dựa trên số lượng mới.
                int giamoi = (giaht * slmoi) / slhientai;
                Homepage.mListCart.get(i).setPrice(giamoi);

                // Cập nhật giá mới lên giao diện.
                finalViewHolder.tv_pricecart.setText(giamoi + " vnd");

                // Gọi phương thức để cập nhật lại tổng số tiền hoặc thông tin giỏ hàng khác trên giao diện chính.
                cartFlower.setData();

                // Cập nhật giao diện: ẩn nút tăng nếu số lượng lớn hơn 9, hiển thị cả hai nút nếu không.
                if (slmoi > 9) {
                    finalViewHolder.btn_plus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btn_minus.setVisibility(View.VISIBLE);
                } else {
                    finalViewHolder.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_minus.setVisibility(View.VISIBLE);
                }

                // Cập nhật lại số lượng hiển thị trên nút.
                finalViewHolder.btn_values.setText(String.valueOf(slmoi));
            }
        });

        // Tương tự như trên
        ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nhưng ở đây số lượng giảm 1
                int slmoi=Integer.parseInt(finalViewHolder1.btn_values.getText().toString())-1;
                int slhientai= Homepage.mListCart.get(i).getQuantity();
                int giaht= (int) Homepage.mListCart.get(i).getPrice();
                Homepage.mListCart.get(i).setQuantity(slmoi);

                int giamoi=(giaht*slmoi)/slhientai;
                Homepage.mListCart.get(i).setPrice(giamoi);
                finalViewHolder.tv_pricecart.setText(giamoi+ " vnd");
                cartFlower.setData();
                if(slmoi<2){
                    finalViewHolder.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_minus.setVisibility(View.INVISIBLE);
                }else{
                    finalViewHolder.btn_plus.setVisibility(View.VISIBLE);
                    finalViewHolder.btn_minus.setVisibility(View.VISIBLE);
                }
                finalViewHolder.btn_values.setText(String.valueOf(slmoi));
            }
        });
        return view;
    }
}
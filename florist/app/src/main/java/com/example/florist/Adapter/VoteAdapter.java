package com.example.florist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.florist.R;
import com.example.florist.Model.Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.ViewHolder>{
    private ArrayList<Vote> mListVote;
    private Context context;
    public VoteAdapter(ArrayList<Vote>mListVote,Context context){
        this.mListVote=mListVote;
        this.context=context;
    }
    @NonNull
    @Override
    public VoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vote,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteAdapter.ViewHolder holder, int position) {
        // Lấy đối tượng Vote tại vị trí hiện tại trong danh sách.
        Vote vote = mListVote.get(position);
        // Lấy thông tin cần thiết từ đối tượng Vote.
        String name = vote.getEmail(); // Email của người dùng, sử dụng làm tên.
        String content = vote.getContent(); // Nội dung của đánh giá hoặc bình luận.
        float rating1 = vote.getNumStar(); // Số sao đánh giá.

        // Cập nhật thông tin lên giao diện người dùng thông qua ViewHolder.
        holder.tv_username.setText(name); // Cập nhật tên (email) người dùng.
        holder.tv_content.setText(content); // Cập nhật nội dung đánh giá.
        holder.rating1.setRating(rating1); // Cập nhật số sao đánh giá.
    }


    @Override
    public int getItemCount() {
        // Trả về kích thước của danh sách các đối tượng Vote.
        return mListVote.size();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Biến để tham chiếu đến các view trong layout của mỗi item.
        private TextView tv_username, tv_content;
        private RatingBar rating1;

        // Constructor nhận một View, đây là layout cho mỗi item.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view từ itemView.
            tv_username = itemView.findViewById(R.id.tv_username); // TextView hiển thị tên người dùng.
            tv_content = itemView.findViewById(R.id.tv_content); // TextView hiển thị nội dung đánh giá.
            rating1 = itemView.findViewById(R.id.rating1); // RatingBar hiển thị số sao đánh giá.
        }
    }

}
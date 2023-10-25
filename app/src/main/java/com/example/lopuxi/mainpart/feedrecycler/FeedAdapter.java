package com.example.lopuxi.mainpart.feedrecycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lopuxi.R;
import com.example.lopuxi.databinding.FeedItemBinding;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    FeedItemBinding binding;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView imageRecycler;
        private TextView text;
        private TextView commentsNumber;
        private ImageView comments_img;

        public ViewHolder(FeedItemBinding binding){
            super(binding.getRoot());
            imageRecycler = binding.imageRecycler;
            text = binding.text;
            commentsNumber = binding.commentsNumber;
            comments_img = binding.commentsImg;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FeedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText("Я фанат bones, Данил Миляев.");
        holder.imageRecycler.setAdapter(new HorizontalImageAdapter());
        holder.imageRecycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.commentsNumber.setText(String.valueOf(position+1));
        holder.comments_img.setImageResource(R.drawable.ic_baseline_comment_24);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
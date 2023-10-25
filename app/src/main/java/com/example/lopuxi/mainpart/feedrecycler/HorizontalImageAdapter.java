package com.example.lopuxi.mainpart.feedrecycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lopuxi.R;
import com.example.lopuxi.databinding.FragmentHorizontalImageItemBinding;

public class HorizontalImageAdapter extends RecyclerView.Adapter<HorizontalImageAdapter.ViewHolder> {

    FragmentHorizontalImageItemBinding binding;

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageInHorizontalRCV;

        public ViewHolder(FragmentHorizontalImageItemBinding binding){
            super(binding.getRoot());
            imageInHorizontalRCV = binding.imageInHorizontalRcv;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = FragmentHorizontalImageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageInHorizontalRCV.setImageResource(R.drawable.papanyama);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
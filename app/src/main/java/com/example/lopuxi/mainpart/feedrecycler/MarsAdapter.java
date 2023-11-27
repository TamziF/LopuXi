package com.example.lopuxi.mainpart.feedrecycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lopuxi.R;
import com.example.lopuxi.databinding.MarsItemBinding;
import com.example.lopuxi.network.MarsPhoto;
import com.squareup.picasso.Picasso;

public class MarsAdapter extends ListAdapter<MarsPhoto, MarsAdapter.MarsViewHolder> {

    MarsItemBinding binding;

    public MarsAdapter(@NonNull DiffUtil.ItemCallback<MarsPhoto> diffCallback) {
        super(diffCallback);
    }

    public class MarsViewHolder extends RecyclerView.ViewHolder{

        private ImageView mars_image;

        public MarsViewHolder(MarsItemBinding binding){
            super(binding.getRoot());
            mars_image = binding.marsImage;
        }
    }

    @NonNull
    @Override
    public MarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = MarsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MarsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarsViewHolder holder, int position) {
        Picasso.get().load(getItem(position).img_src).placeholder(R.drawable.ic_baseline_image_24).into(holder.mars_image);
    }
}
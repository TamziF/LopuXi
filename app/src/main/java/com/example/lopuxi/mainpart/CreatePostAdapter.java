package com.example.lopuxi.mainpart;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lopuxi.R;
import com.example.lopuxi.databinding.ImageCreateItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CreatePostAdapter extends RecyclerView.Adapter<CreatePostAdapter.ViewHolder> {

    ImageCreateItemBinding binding;

    ArrayList<Uri> list;

    Context context;

    CreatePostAdapter(ArrayList<Uri> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ImageCreateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).placeholder(R.drawable.ic_baseline_image_24).into(holder.image);
        //Picasso.get().load(list.get(position)).placeholder(R.drawable.ic_baseline_image_24).into(holder.image);

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public ImageView deleteImage;

        public ViewHolder(ImageCreateItemBinding binding) {
            super(binding.getRoot());

            image = binding.image;
            deleteImage = binding.deleteImage;
        }
    }

    public void updateList(Uri uri){
        list.add(uri);
        notifyItemInserted(list.size()-1);
    }

}

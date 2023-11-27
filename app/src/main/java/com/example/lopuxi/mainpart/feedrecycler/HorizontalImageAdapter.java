package com.example.lopuxi.mainpart.feedrecycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lopuxi.R;
import com.example.lopuxi.databinding.FragmentHorizontalImageItemBinding;
import com.example.lopuxi.network.App;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HorizontalImageAdapter extends RecyclerView.Adapter<HorizontalImageAdapter.ViewHolder> {

    FragmentHorizontalImageItemBinding binding;

    ArrayList<String> photos;

    Context context;

    HorizontalImageAdapter(ArrayList<String> uri, Context context) {
        photos = uri;
        this.context = context;
    }

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
        /*
        If you are using Glide v4.0.0-RC1 then you need to use RequestOptions to add the placeholder, error image and other option. Here is an working example
*/
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.error);



        Glide.with(context).load(App.BASE_URL + "files/" + photos.get(position)).apply(options).into(holder.imageInHorizontalRCV);
        //Picasso.get().load(App.BASE_URL + "files/" + photos.get(position)).placeholder(R.drawable.ic_baseline_image_24).error(R.drawable.image).into(holder.imageInHorizontalRCV);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}
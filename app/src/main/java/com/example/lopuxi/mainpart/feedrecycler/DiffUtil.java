package com.example.lopuxi.mainpart.feedrecycler;

import androidx.annotation.NonNull;

import com.example.lopuxi.network.Post;

import java.util.Objects;

public class DiffUtil extends androidx.recyclerview.widget.DiffUtil.ItemCallback<Post> {
    @Override
    public boolean areItemsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
        return Objects.equals(oldItem.id, newItem.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Post oldItem, @NonNull Post newItem) {
        return Objects.equals(oldItem.text, newItem.text) && Objects.equals(oldItem.time, newItem.time) && Objects.equals(oldItem.author, newItem.author);
    }
    /*@Override
    public boolean areItemsTheSame(@NonNull MarsPhoto oldItem, @NonNull MarsPhoto newItem) {
        return oldItem.id == newItem.id;
    }

    @Override
    public boolean areContentsTheSame(@NonNull MarsPhoto oldItem, @NonNull MarsPhoto newItem) {
        return Objects.equals(oldItem.img_src, newItem.img_src);
    }*/
}

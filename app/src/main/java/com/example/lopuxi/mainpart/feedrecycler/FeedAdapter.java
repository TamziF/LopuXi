package com.example.lopuxi.mainpart.feedrecycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lopuxi.CallBack;
import com.example.lopuxi.databinding.PostItemBinding;
import com.example.lopuxi.network.Post;

import java.text.SimpleDateFormat;

/*public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

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
}*/

public class FeedAdapter extends ListAdapter<Post, FeedAdapter.PostViewHolder> {

    PostItemBinding binding;

    CallBack callBack;

    Context context;

    public FeedAdapter(@NonNull DiffUtil.ItemCallback<Post> diffCallback, CallBack callBack, Context context) {
        super(diffCallback);
        this.callBack = callBack;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = PostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.username.setText(getItem(position).author);
        String time = new SimpleDateFormat("HH:mm dd.MMM").format(getItem(position).time);
        holder.time.setText(time);
        holder.text.setText(getItem(position).text);

        if (position>2) Log.d("FeedPic", getItem(2).photos.get(0));
        callBack.onItemBind(getCurrentList().size() - position);
        /*holder.username.setText("Данил Миляев");
        holder.time.setText("Сегодня");
        holder.text.setText("Я фанат, люблю флудить, снимать кружочки, я крашусь");*/

        holder.imagesRecycler.setVisibility(View.VISIBLE);
        if(getItem(position).photos.size() != 0) {
            holder.imagesRecycler.setAdapter(new HorizontalImageAdapter(getItem(position).photos, context));
            holder.imagesRecycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
        else {
            holder.imagesRecycler.setVisibility(View.GONE);
        }
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView time;
        private RecyclerView imagesRecycler;
        private TextView text;

        PostViewHolder(PostItemBinding binding){
            super(binding.getRoot());

            username = binding.userNameTv;
            time = binding.postTimeTv;
            imagesRecycler = binding.imagesRecycler;
            text = binding.postText;
        }

    }

}
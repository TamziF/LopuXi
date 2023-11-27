package com.example.lopuxi.mainpart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lopuxi.ApiStatus;
import com.example.lopuxi.CallBack;
import com.example.lopuxi.databinding.FragmentFeedBinding;
import com.example.lopuxi.mainpart.feedrecycler.DiffUtil;
import com.example.lopuxi.mainpart.feedrecycler.FeedAdapter;
import com.example.lopuxi.mainpart.feedrecycler.MarsAdapter;
import com.example.lopuxi.network.App;
import com.example.lopuxi.network.MarsPhoto;
import com.example.lopuxi.network.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Feed extends Fragment {

    FragmentFeedBinding binding;
    MainViewModel mainViewModel;

    ArrayList<Post> arrayList = new ArrayList<>();

    CallBack callBack = new CallBack() {
        @Override
        public void onItemBind(int a) {
            Log.v("Feed", a + "");
            if (a == 5 && mainViewModel.feedStatus.getValue() != ApiStatus.LOADING) {
                mainViewModel.getFeed();
            }
        }
    };

    private RecyclerView recyclerView;
    private FeedAdapter adapter;
    //private MarsAdapter marsAdapter = new MarsAdapter(new DiffUtil());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFeedBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*arrayList.add(new Post("Данил Миляев", 1, "Я фанат Bones, люблю флудить, снимать кружочки, я крашусь"));
        arrayList.add(new Post("Данил Миляев", 1, "Я фанат Bones, люблю флудить, снимать кружочки, я крашусь"));
        arrayList.add(new Post("Данил Миляев", 1, "Я фанат Bones, люблю флудить, снимать кружочки, я крашусь"));

        adapter.submitList(arrayList);*/

        adapter = new FeedAdapter(new DiffUtil(), callBack, requireContext());

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);


        mainViewModel.getFeed();
        mainViewModel.feedStatus.observe(getViewLifecycleOwner(), new Observer<ApiStatus>() {
            @Override
            public void onChanged(ApiStatus apiStatus) {
                if (apiStatus == ApiStatus.DONE) {
                    Log.v("Feed", mainViewModel.posts.size() + " Size");
                    adapter.submitList(mainViewModel.posts);
                    adapter.notifyItemRangeInserted(adapter.getItemCount() - adapter.getItemCount() % 5, adapter.getItemCount());
                    //marsAdapter.submitList(mainViewModel.marsPhotos);
                    //recyclerView.setAdapter(marsAdapter);
                } else if (apiStatus == ApiStatus.ERROR) {
                    Toast.makeText(requireContext(), "надо тренироваться", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainViewModel.setEndGetFeed(false);
        mainViewModel.requestNumber = 0;
        mainViewModel.posts = new ArrayList<>();
    }
}
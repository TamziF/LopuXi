package com.example.lopuxi.mainpart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lopuxi.ApiStatus;
import com.example.lopuxi.R;
import com.example.lopuxi.database.DAO;
import com.example.lopuxi.database.Database;
import com.example.lopuxi.database.PostEntity;
import com.example.lopuxi.databinding.FragmentCreatePostBinding;
import com.example.lopuxi.network.App;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreatePost extends Fragment {

    FragmentCreatePostBinding binding;
    private Uri selectedImageUri;
    //private ArrayList<Uri> listUri = new ArrayList<Uri>();

    MainViewModel mainViewModel;

    CreatePostAdapter adapter;
    RecyclerView recyclerView;

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            if (result.getData() != null) {
                                //TODO мб убрать
                                binding.tempImage.setVisibility(View.GONE);

                                selectedImageUri = result.getData().getData();
                                //listUri.add(result.getData().getData());
                                adapter.updateList(result.getData().getData());
                                //Glide.with(requireContext()).load(selectedImageUri).into(binding.loadedImage);
                            }
                        }
                    }
            );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        Log.v("Feed", mainViewModel.posts.size() + "sizeVM");
        adapter = new CreatePostAdapter(new ArrayList<>(), requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.loadedImage;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        mainViewModel.postStatus.observe(getViewLifecycleOwner(), new Observer<ApiStatus>() {
            @Override
            public void onChanged(ApiStatus apiStatus) {
                if(apiStatus == ApiStatus.DONE){
                    binding.statusImage.setVisibility(View.GONE);
                    binding.postButton.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
                }
                else if(apiStatus == ApiStatus.ERROR){
                    binding.statusImage.setVisibility(View.GONE);
                    binding.postButton.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mainViewModel.tokens.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Log.v("TESTOSTERON", "User: " + App.username);
                if(strings.size() == adapter.list.size()){
                    Toast.makeText(requireContext(), "Creating post", Toast.LENGTH_SHORT).show();
                    mainViewModel.createPost(App.username, binding.descriptionEt.getText().toString());
                }
            }
        });

        /*mainViewModel.apiStatus.observe(getViewLifecycleOwner(), new Observer<ApiStatus>() {
            @Override
            public void onChanged(ApiStatus apiStatus) {
                if(apiStatus == ApiStatus.LOADING)
                    binding.loadedImage.setImageResource(R.drawable.papanyama);
                else if(apiStatus == ApiStatus.DONE)
                    Picasso.get().load(mainViewModel.marsPhotos.get(0).img_src).placeholder(R.drawable.papanyama).into(binding.loadedImage);
                else if(apiStatus == ApiStatus.ERROR)
                    binding.loadedImage.setImageResource(R.drawable.ic_baseline_image_24);
            }
        });*/

        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.list.size() < 5)
                    openImagePicker();
            }
        });

        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.postButton.setVisibility(View.INVISIBLE);
                binding.statusImage.setVisibility(View.VISIBLE);

                if(adapter.list.size() == 0){
                    Toast.makeText(requireContext(), "Creating post", Toast.LENGTH_SHORT).show();
                    mainViewModel.createPost(App.username, binding.descriptionEt.getText().toString());
                }
                else{
                    Toast.makeText(requireContext(), "Uploading photos", Toast.LENGTH_SHORT).show();
                    for(int i = 0; i < adapter.list.size(); i++){
                        mainViewModel.uploadImage(getRealPathFromUri(requireContext(), adapter.list.get(i)));
                    }
                }

                //dao.add(new PostEntity(selectedImageUri.toString(), binding.descriptionEt.getText().toString())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private static String getRealPathFromUri(Context context, Uri uri) {
        String filePath = "";
        if (context != null && uri != null) {
            Cursor cursor = null;
            try {
                String[] projection = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(columnIndex);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return filePath;
    }
}
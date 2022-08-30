package com.example.instapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
//        String[] postNames=new String[Post.posts.length];
//        for (int i=0;i<postNames.length;i++){
//            postNames[i]=Post.posts[i].getName();
//        }
//
//        int[] postImages=new int[Post.posts.length];
//        for (int i=0;i<postImages.length;i++){
//            postImages[i]=Post.posts[i].getImageResourceID();
//        }
//        CaptionedImagesAdapter adapter =new CaptionedImagesAdapter(postNames, postImages);
//        RecyclerView res = view.findViewById(R.id.post_recycler);

//        LinearLayoutManager mylayoutmanager = new LinearLayoutManager(view.getContext());
//        res.setLayoutManager(mylayoutmanager);
//        res.setAdapter(adapter);
        return view;
    }
}

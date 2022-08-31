package com.example.instapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instapp.Post;
import com.example.instapp.R;
import com.example.instapp.adapters.CaptionedImagesAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        List<Post> posts = new ArrayList<>();
        Post p =new Post("hadi",R.drawable.hadi,"this is my first post",R.drawable.nature);
        posts.add(p);
        posts.add(p);
        posts.add(p);
        posts.add(p);
        posts.add(p);
        CaptionedImagesAdapter adapter =new CaptionedImagesAdapter(posts);
        RecyclerView res = view.findViewById(R.id.post_recycler);

        LinearLayoutManager mylayoutmanager = new LinearLayoutManager(view.getContext());
        res.setLayoutManager(mylayoutmanager);
        res.setAdapter(adapter);
        return view;
    }
}

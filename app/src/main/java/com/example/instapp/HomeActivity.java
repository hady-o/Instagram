package com.example.instapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {


    public View onCreate(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView postRecycler=(RecyclerView) inflater
                .inflate(R.layout.activity_home,container,false);

        String[] postNames=new String[Post.posts.length];
        for (int i=0;i<postNames.length;i++){
            postNames[i]=Post.posts[i].getName();
        }

        int[] postImages=new int[Post.posts.length];
        for (int i=0;i<postImages.length;i++){
            postImages[i]=Post.posts[i].getImageResourceID();
        }

        CaptionedImagesAdapter adapter=new CaptionedImagesAdapter(postNames,postImages);
        postRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        postRecycler.setLayoutManager(layoutManager);
        return postRecycler;
    }
}
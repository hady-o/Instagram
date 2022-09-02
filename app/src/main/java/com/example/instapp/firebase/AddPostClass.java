package com.example.instapp.firebase;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instapp.Home2Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class AddPostClass {



    public static void addPost(ProgressBar progressBar, Activity activity,String userId, String userName, String userPhoto, String post, String postImage ) {
        progressBar.setVisibility(View.VISIBLE);
        DocumentReference mydef = FirebaseFirestore.getInstance().document("myPosts/"+userId);
        Map<String, Object> tripdata = new HashMap<>();
        tripdata.put("userId", userId);
        tripdata.put("userName", userName);
        tripdata.put("userPhoto", userPhoto);
        tripdata.put("post", post);
        tripdata.put("postImage", postImage);
        Timestamp timestamp =new Timestamp(System.currentTimeMillis());
        tripdata.put("date", timestamp);

        mydef.collection("data").add(tripdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressBar.setVisibility(View.GONE);
                Intent intent =new Intent(activity, Home2Activity.class);
                activity.startActivity(intent);
            }
        });
    }

    public static void addFollowersPost(Activity activity,String userId, String userName, String userPhoto, String post, String postImage ) {
        DocumentReference mydef = FirebaseFirestore.getInstance().document("followersPost/"+userId);
        Map<String, Object> tripdata = new HashMap<>();
        tripdata.put("userId", userId);
        tripdata.put("userName", userName);
        tripdata.put("userPhoto", userPhoto);
        tripdata.put("post", post);
        tripdata.put("postImage", postImage);

        mydef.collection("data")
                .add(tripdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(activity, "done", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

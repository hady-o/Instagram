package com.example.instapp.firebase;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instapp.Home2Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddFollowClass {
    public static void addFollow(Activity activity, String userId) {

        DocumentReference mydef = FirebaseFirestore.getInstance().document("follows/"+userId);
        Map<String, Object> tripdata = new HashMap<>();
        tripdata.put("userId", userId);
        mydef.collection("data")
                .add(tripdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(activity, "done", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

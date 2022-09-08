package com.example.instapp.firebase;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.instapp.Home2Activity;
import com.example.instapp.classes.CurrentUserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class AddPostClass {


    public static void addPost(ProgressBar progressBar, Activity activity, String userId, String userName, String userEmail, String userPhoto, String post, String postImage) {
        progressBar.setVisibility(View.VISIBLE);
        DocumentReference mydef = FirebaseFirestore.getInstance().document("myPosts/" + userId);
        Map<String, Object> tripdata = new HashMap<>();
        tripdata.put("userId", userId);
        tripdata.put("userName", userName);
        tripdata.put("userEmail", userEmail);
        tripdata.put("userPhoto", userPhoto);
        tripdata.put("post", post);
        tripdata.put("postImage", postImage);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tripdata.put("date", timestamp);

        mydef.collection("data").add(tripdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                addTimeLine(task.getResult().getId());
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(activity, Home2Activity.class);
                activity.startActivity(intent);
            }
        });
    }

    public static void addTimeLine(String Id) {
//        DocumentReference mydef = FirebaseFirestore.getInstance().document("timeLine/"+ CurrentUserClass.currentUser.getUid());
//        Map<String, Object> tripdata = new HashMap<>();
//        tripdata.put("userId", CurrentUserClass.currentUser.getUid());
//        tripdata.put("docId", Id);


      //  mydef.set(tripdata);
        FirebaseFirestore.getInstance().document("follower/" + CurrentUserClass.currentUser.getUid())
                .collection("data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference mydef = FirebaseFirestore.getInstance().document("timeLine/" + document.getString("userId"));
                                Map<String, Object> tripdata = new HashMap<>();
                                tripdata.put("userId", CurrentUserClass.currentUser.getUid());
                                tripdata.put("docId", Id);
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                tripdata.put("date", timestamp);
                                mydef.collection("data").add(tripdata);
                            }
                        }
                    }
                });

    }
    public static void editPosts(String name, String uri) {
        DocumentReference mydef = FirebaseFirestore.getInstance().document("myPosts/" + CurrentUserClass.currentUser.getUid());
        Map<String, Object> tripdata = new HashMap<>();
        tripdata.put("userName", name);
        if(uri!="")
        {
            tripdata.put("userPhoto", uri);
        }
        mydef.collection("data").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot document : value)
                {
                    mydef.collection("data").document(document.getId()).update(tripdata);
                }
            }
        });



    }

}

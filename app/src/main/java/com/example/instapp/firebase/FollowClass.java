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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FollowClass {
    public static void addFollow(Activity activity, String userId) {

        DocumentReference mydef = FirebaseFirestore.getInstance().document("following/"+ CurrentUserClass.currentUser.getUid());
        Map<String, Object> tripdata = new HashMap<>();
        tripdata.put("userId", userId);
        mydef.collection("data")
                .add(tripdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(activity, "done", Toast.LENGTH_SHORT).show();
                    }
                });

        mydef = FirebaseFirestore.getInstance().document("follower/"+ userId);
        Map<String, Object> data = new HashMap<>();
        data.put("userId", CurrentUserClass.currentUser.getUid());
        mydef.collection("data")
                .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(activity, "done", Toast.LENGTH_SHORT).show();
                    }
                });


    }




    public static void deleteFollow(Activity activity, String docId,String userId) {

        FirebaseFirestore.getInstance().document("following/"+ CurrentUserClass.currentUser.getUid())
                .collection("data").document(docId).delete();

        FirebaseFirestore.getInstance().document("follower/"+ userId)
                .collection("data").whereEqualTo("userId",CurrentUserClass.currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        FirebaseFirestore.getInstance().document("follower/"+ userId)
                                .collection("data").document(task.getResult().getDocuments().get(0).getId()).delete();
                    }
                });


    }




}

package com.example.instapp.firebase;

import com.example.instapp.classes.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddUserToHistory {
    public static void addUserToHistory(String id,User user)
    {
        DocumentReference mydef = FirebaseFirestore.getInstance().document("history/"+id);
        Map<String, Object> userdata = new HashMap<>();
        userdata.put("name", user.getName());
        userdata.put("id", user.getId());
        userdata.put("email", user.getEmail());
        userdata.put("uri", user.getPhoto());
        mydef.collection("data").add(userdata);
    }
}

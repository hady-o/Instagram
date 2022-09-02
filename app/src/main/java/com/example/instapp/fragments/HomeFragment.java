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

import com.example.instapp.classes.Post;
import com.example.instapp.R;
import com.example.instapp.adapters.CaptionedImagesAdapter;
import com.example.instapp.firebase.CurrentUserClass;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseFirestore dbe;
    CaptionedImagesAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        List<Post> posts = new ArrayList<>();
//
        adapter =new CaptionedImagesAdapter(posts);
        dbe = FirebaseFirestore.getInstance();

        dbe.document("myPosts/"+ CurrentUserClass.currentUser.getUid()).
                collection("data").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        posts.clear();
                        for (QueryDocumentSnapshot document : value ) {
                                String userName = document.getString("userName");
                                String userImage = document.getString("userPhoto");
                                String post = document.getString("post");
                                String postImage = document.getString("postImage");
                                String formatedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(document.getDate("date"));

                                Post p = new Post(userName,userImage,post,postImage,formatedDate);
                                posts.add(p);

                            }
                        adapter.notifyDataSetChanged();
                        }

                });


        RecyclerView res = view.findViewById(R.id.post_recycler);

        LinearLayoutManager mylayoutmanager = new LinearLayoutManager(view.getContext());
        res.setLayoutManager(mylayoutmanager);
        res.setAdapter(adapter);
        return view;
    }
}

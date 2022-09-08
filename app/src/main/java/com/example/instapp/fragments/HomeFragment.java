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
import com.example.instapp.classes.CurrentUserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

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
        adapter =new CaptionedImagesAdapter(posts);
        dbe = FirebaseFirestore.getInstance();

        dbe.document("timeLine/"+ CurrentUserClass.currentUser.getUid()).
                collection("data").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        posts.clear();
                        for (QueryDocumentSnapshot document : value ) {

                       dbe.document("myPosts/" + document.getString("userId"))
                                    .collection("data").document(document.getString("docId")).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            String userId = value.getString("userId");
                                            String userName = value.getString("userName");
                                            String userEmail = value.getString("userEmail");
                                            String userImage = value.getString("userPhoto");
                                            String post = value.getString("post");
                                            String postImage = value.getString("postImage");
                                            String formatedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(value.getDate("date"));

                                            Post p = new Post(userId, userName,userEmail, userImage, post, postImage, formatedDate);
                                            posts.add(p);
                                            adapter.notifyDataSetChanged();
                                        }

                                    });



                            adapter.notifyDataSetChanged();

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

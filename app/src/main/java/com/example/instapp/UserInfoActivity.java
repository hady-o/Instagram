package com.example.instapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instapp.adapters.CaptionedImagesAdapter;
import com.example.instapp.classes.CurrentUserClass;
import com.example.instapp.classes.Post;
import com.example.instapp.databinding.ActivityUserInfoBinding;
import com.example.instapp.firebase.FollowClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {
    ActivityUserInfoBinding binding;
    FirebaseFirestore dbe;
    CaptionedImagesAdapter adapter;
    String docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseFirestore.getInstance().document("myPosts/"+CurrentUserClass.currentFriend.getId())
                .collection("data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String s =String.valueOf(task.getResult().getDocuments().size());
                        binding.postsNum.setText(s);
                    }
                });

        FirebaseFirestore.getInstance().document("follower/"+CurrentUserClass.currentFriend.getId())
                .collection("data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String s =String.valueOf(task.getResult().getDocuments().size());
                        binding.followersNum.setText(s);
                    }
                });

        FirebaseFirestore.getInstance().document("following/"+CurrentUserClass.currentFriend.getId())
                .collection("data").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String s =String.valueOf(task.getResult().getDocuments().size());
                        binding.following.setText(s);
                    }
                });



        binding.textView8.setText(CurrentUserClass.currentFriend.getName());
        binding.backBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Home2Activity.class);
                startActivity(intent);
                finish();
            }
        });
        if(CurrentUserClass.currentFriend.getId().equals(CurrentUserClass.currentUser.getUid()))
        {
            binding.followButtonId.setEnabled(false);
            binding.followButtonId.setVisibility(View.GONE);
            binding.editProfileButtonId.setVisibility(View.VISIBLE);
            binding.editProfileButtonId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getApplicationContext(),EditProfileActivity.class);
                    startActivity(intent);
                }
            });
        }
        CurrentUserClass.follow =true;
        DocumentReference mydef = FirebaseFirestore.getInstance().document("following/"+ CurrentUserClass.currentUser.getUid());
        Object id = mydef.collection("data").whereEqualTo("userId",CurrentUserClass.currentFriend.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    try {

                        task.getResult().getDocuments().get(0).toString();
                        docId = task.getResult().getDocuments().get(0).getId().toString();
                        //inding.followButtonId.setText("Un Follow");
                    }catch (Exception e)
                    {
                        CurrentUserClass.follow =false;
                        binding.followButtonId.setText(R.string.follow);
                    }



                }
            }
        });






        binding.followButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.followButtonId.setEnabled(false);
                if(CurrentUserClass.follow==false) {
                    FollowClass.addFollow(UserInfoActivity.this, CurrentUserClass.currentFriend.getId());
                    binding.followButtonId.setText("Un Follow");
                    CurrentUserClass.follow =true;

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                else
                {
                    FollowClass.deleteFollow(UserInfoActivity.this,docId,CurrentUserClass.currentFriend.getId()
                    );
                    binding.followButtonId.setText(R.string.follow);
                    CurrentUserClass.follow =false;
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });

        List<Post> posts = new ArrayList<>();
        adapter =new CaptionedImagesAdapter(posts);
        dbe = FirebaseFirestore.getInstance();

        dbe.document("myPosts/"+ CurrentUserClass.currentFriend.getId()).
                collection("data").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        posts.clear();
                        for (QueryDocumentSnapshot document : value ) {
                            String userId = document.getString("userId");
                            String userName = document.getString("userName");
                            String userEmail = document.getString("userEmail");
                            String userImage = document.getString("userPhoto");
                            String post = document.getString("post");
                            String postImage = document.getString("postImage");
                            String formatedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(document.getDate("date"));

                            Post p = new Post(userId, userName,userEmail,userImage,post,postImage,formatedDate);
                            posts.add(p);

                        }
                        adapter.notifyDataSetChanged();
                    }

                });

        Glide.with(getApplicationContext())
                .load(CurrentUserClass.currentFriend.getPhoto())
                .into(binding.circularImageView);
        binding.emailId.setText(CurrentUserClass.currentFriend.getEmail());
        binding.nameId.setText(CurrentUserClass.currentFriend.getName());

        RecyclerView res = binding.postRecycler;

        LinearLayoutManager mylayoutmanager = new LinearLayoutManager(getApplicationContext());
        res.setLayoutManager(mylayoutmanager);
        res.setAdapter(adapter);
       // binding.postsNum.setText(adapter.getItemCount());
    }
}
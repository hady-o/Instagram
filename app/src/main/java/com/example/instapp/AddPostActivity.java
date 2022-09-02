package com.example.instapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instapp.classes.BackButton;
import com.example.instapp.databinding.ActivityAddPostBinding;
import com.example.instapp.firebase.AddPostClass;
import com.example.instapp.firebase.CurrentUserClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddPostActivity extends AppCompatActivity {
    ActivityAddPostBinding binding;

    static final int chose_image =101;
    Uri image_uri;
    String profileimageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvName.setText(CurrentUserClass.currentUser.getDisplayName());
        if (CurrentUserClass.currentUser.getPhotoUrl()!=null)
        {
            Glide.with(this)
                    .load(CurrentUserClass.currentUser.getPhotoUrl())
                    .into(binding.circularImageView);
        }


            binding.backBtnId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BackButton.back(AddPostActivity.this ,Home2Activity.class);
                }
            });
        binding.logInButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_images();
                binding.postImage.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = binding.editTextPost.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                binding.editTextPost.setLayoutParams(params);

            }
        });

        binding.addPostBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image_uri!=null)
                    uploadphoto();
                else {
                    String u = "";

                    if (CurrentUserClass.currentUser.getPhotoUrl() != null) {
                        u = CurrentUserClass.currentUser.getPhotoUrl().toString();
                    }
                    AddPostClass.addPost(binding.progressBar3, AddPostActivity.this, CurrentUserClass.currentUser.getUid(), CurrentUserClass.currentUser.getDisplayName(), u, binding.editTextPost.getText().toString(), "");
                }

            }
        });
    }

    void show_images()
    {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "select product photo"), chose_image) ;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==chose_image &&resultCode==RESULT_OK &&data!=null&&data.getData()!=null)
        {
            image_uri=data.getData();
            try {

                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),image_uri);
                binding.postImage.setImageBitmap(bitmap);
                binding.postImage.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    void uploadphoto()
    {
        binding.progressBar3.setVisibility(View.VISIBLE);
        binding.addPostBtnId.setEnabled(false);
        binding.logInButtonId.setEnabled(false);
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
        if (image_uri!=null)
        {
            mStorageRef.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Photo has been uploaded successfully", Toast.LENGTH_LONG).show();
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            profileimageurl=uri.toString();
                            binding.progressBar3.setVisibility(View.GONE);
                            binding.addPostBtnId.setEnabled(true);
                            binding.logInButtonId.setEnabled(true);
                            String userUri="";
                            String postUri="";

                            if(CurrentUserClass.currentUser.getPhotoUrl()!=null)
                            {
                                userUri =CurrentUserClass.currentUser.getPhotoUrl().toString();
                            }
                            if(image_uri!=null)
                            {
                                postUri=image_uri.toString();
                            }
                            AddPostClass.addPost(binding.progressBar3, AddPostActivity.this, CurrentUserClass.currentUser.getUid(), CurrentUserClass.currentUser.getDisplayName(), userUri,binding.editTextPost.getText().toString(),postUri);

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }
}
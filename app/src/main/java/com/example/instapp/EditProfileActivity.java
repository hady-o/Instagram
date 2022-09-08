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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.instapp.classes.CurrentUserClass;
import com.example.instapp.databinding.ActivityEditProfileBinding;
import com.example.instapp.firebase.AddPostClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;

    static final int chose_image =101;
    Uri image_uri;
    String profileimageurl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.editTextEmail.setEnabled(false);
        binding.editTextname.setText(CurrentUserClass.currentUser.getDisplayName());
        binding.editTextEmail.setText(CurrentUserClass.currentUser.getEmail());
        Glide.with(this)
                .load(CurrentUserClass.currentUser.getPhotoUrl())
                .into(binding.imageView5);

        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_images();
            }
        });

        binding.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_images();
            }
        });

        binding.backBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),UserInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.SaveBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.editTextname.getText().toString().equals(CurrentUserClass.currentUser.getDisplayName()) && image_uri==null)
                {
                    Intent intent =new Intent(getApplicationContext(),UserInfoActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if(image_uri!=null)
                    {
                        uploadphoto();
                    }
                    else
                    {
                        updateuserinformation();
                    }
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
                binding.imageView5.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void uploadphoto()
    {

        binding.progressBar3.setVisibility(View.VISIBLE);
        binding.imageView5.setEnabled(false);
        binding.SaveBtnId.setEnabled(false);
        binding.textView6.setEnabled(false);
        binding.editTextname.setEnabled(false);

        binding.backBtnId.setEnabled(false);
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
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            profileimageurl=uri.toString();
                            binding.progressBar3.setVisibility(View.GONE);
                            binding.imageView5.setEnabled(true);
                            binding.SaveBtnId.setEnabled(true);
                            binding.textView6.setEnabled(true);
                            binding.editTextname.setEnabled(true);
                            binding.backBtnId.setEnabled(true);
                            updateuserinformation();

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
    void updateuserinformation()
    {
        AddPostClass.editPosts(binding.editTextname.getText().toString(),profileimageurl);
        binding.progressBar3.setVisibility(View.VISIBLE);
        binding.imageView5.setEnabled(false);
        binding.SaveBtnId.setEnabled(false);
        binding.textView6.setEnabled(false);
        binding.editTextname.setEnabled(false);
        binding.editTextEmail.setEnabled(false);
        binding.backBtnId.setEnabled(false);
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null)
        {
            if(profileimageurl!="")
            {
                DocumentReference mydef = FirebaseFirestore.getInstance().document("users/"+CurrentUserClass.currentUser.getUid());
                Map<String, Object> userdata = new HashMap<>();
                userdata.put("name",binding.editTextname.getText().toString());
                userdata.put("uri", profileimageurl);
                mydef.update(userdata);
                UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse( profileimageurl))
                        .setDisplayName(binding.editTextname.getText().toString())
                        .build();
                user.updateProfile(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        CurrentUserClass.currentUser =user;
                        Intent intent =new Intent(getApplicationContext(),UserInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            else
            {
                DocumentReference mydef = FirebaseFirestore.getInstance().document("users/"+CurrentUserClass.currentUser.getUid());
                Map<String, Object> userdata = new HashMap<>();
                userdata.put("name",binding.editTextname.getText().toString());

                mydef.update(userdata);
                UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(binding.editTextname.getText().toString())
                    .build();
                user.updateProfile(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        CurrentUserClass.currentUser =user;
                        Intent intent =new Intent(getApplicationContext(),UserInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }


        }


        binding.progressBar3.setVisibility(View.GONE);
        binding.imageView5.setEnabled(true);
        binding.SaveBtnId.setEnabled(true);
        binding.textView6.setEnabled(true);
        binding.editTextname.setEnabled(true);
        binding.editTextEmail.setEnabled(true);
        binding.backBtnId.setEnabled(true);
    }
}
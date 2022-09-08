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

import com.example.instapp.databinding.ActivitySignUp2Binding;
import com.example.instapp.classes.CurrentUserClass;
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

public class SignUp2Activity extends AppCompatActivity {

    ActivitySignUp2Binding binding;

    static final int chose_image =101;
    Uri image_uri;
    String profileimageurl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.savePhotoButtonId.setEnabled(false);

        binding.progressBar3.setVisibility(View.GONE);
        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_images();
            }
        });

        binding.savePhotoButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateuserinformation();
            }
        });

        binding.skipPhotoButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                CurrentUserClass.currentUser =user;
                DocumentReference mydef = FirebaseFirestore.getInstance().document("users/"+user.getUid());
                Map<String, Object> userdata = new HashMap<>();
                userdata.put("name", user.getDisplayName());
                userdata.put("id", user.getUid());
                userdata.put("email", user.getEmail());
                userdata.put("uri", "");
                mydef.set(userdata);
                Intent intent =new Intent(getApplicationContext(),Home2Activity.class);
                startActivity(intent);
                finish();
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
                uploadphoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    void uploadphoto()
    {
        binding.progressBar3.setVisibility(View.VISIBLE);
        binding.imageView5.setEnabled(false);
        binding.savePhotoButtonId.setEnabled(false);
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
                            FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                            profileimageurl=uri.toString();
                            binding.progressBar3.setVisibility(View.GONE);
                            binding.imageView5.setEnabled(true);
                            binding.savePhotoButtonId.setEnabled(true);
                            Toast.makeText(getApplicationContext(), "User information has been updated successfully", Toast.LENGTH_LONG).show();
                            DocumentReference mydef = FirebaseFirestore.getInstance().document("users/"+user.getUid());
                            Map<String, Object> userdata = new HashMap<>();
                            userdata.put("name", user.getDisplayName());
                            userdata.put("id", user.getUid());
                            userdata.put("email", user.getEmail());
                            userdata.put("uri", profileimageurl);
                            mydef.set(userdata);
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
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        //binding.progressBar3.setVisibility(View.VISIBLE);
        if (user !=null)
        {
            Toast.makeText(this, "done2", Toast.LENGTH_SHORT).show();
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse( profileimageurl))
                    .build()
                    ;
            Toast.makeText(this, "done3", Toast.LENGTH_SHORT).show();

            user.updateProfile(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            CurrentUserClass.currentUser =user;
                            //set user data in fireStore


//
                            binding.progressBar3.setVisibility(View.GONE);


                            Intent intent =new Intent(getApplicationContext(),Home2Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    })



            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "fall", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
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
import com.example.instapp.firebase.CurrentUserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignUp2Activity extends AppCompatActivity {

    ActivitySignUp2Binding binding;

    static final int chose_image =101;
    Uri image_uri;
    String profileimageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
                            profileimageurl=uri.toString();
                            binding.progressBar3.setVisibility(View.GONE);
                            binding.imageView5.setEnabled(true);
                            binding.savePhotoButtonId.setEnabled(true);
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
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        //binding.progressBar3.setVisibility(View.VISIBLE);
        if (FirebaseAuth.getInstance().getCurrentUser() !=null)
        {
            Toast.makeText(this, "done2", Toast.LENGTH_SHORT).show();
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse( profileimageurl))
                    .build()
                    ;
            Toast.makeText(this, "done3", Toast.LENGTH_SHORT).show();

            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

//                    FirebaseFirestore dbe = FirebaseFirestore.getInstance();
//                    dbe.document("sampledata/users/data/"+SignInClass.me.getDoc_id()).update("uri",profileimageurl.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                        }
//                    });
                    binding.progressBar3.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "User information has been updated successfully", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(),Home2Activity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "fall", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
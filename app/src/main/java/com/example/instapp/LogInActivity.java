package com.example.instapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instapp.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signUpButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
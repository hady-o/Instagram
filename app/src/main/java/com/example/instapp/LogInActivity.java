package com.example.instapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instapp.databinding.ActivityLogInBinding;
import com.example.instapp.firebase.SignInClass;

public class LogInActivity extends AppCompatActivity {
    ActivityLogInBinding binding ;
    SignInClass signInClass;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding =ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signInClass= new SignInClass(this, binding.progressBar3);
        binding.signUpButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.logInButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInClass.signIn(binding.editTextLogin, binding.editTextPassword);

            }
        });
    }
}
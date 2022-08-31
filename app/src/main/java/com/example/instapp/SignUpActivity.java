package com.example.instapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.instapp.databinding.ActivitySignUpBinding;
import com.example.instapp.firebase.SignInClass;
import com.example.instapp.firebase.SignUpClass;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    SignUpClass signUpClass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signUpClass =new SignUpClass(this,binding.progressBar);

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        binding.signUpButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.signUpButtonId.setEnabled(false);
                binding.loginButtonId.setEnabled(false);
                if(signUpClass.signUp(binding.editTextname,binding.editTextEmail,binding.editTextPassword))
                {
                    binding.signUpButtonId.setEnabled(true);
                    binding.loginButtonId.setEnabled(true);


                }
                else
                {
                    binding.signUpButtonId.setEnabled(true);
                    binding.loginButtonId.setEnabled(true);
                }
               
            }
        });
        binding.loginButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}
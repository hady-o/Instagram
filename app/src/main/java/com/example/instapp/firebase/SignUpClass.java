package com.example.instapp.firebase;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instapp.SignUp2Activity;
import com.example.instapp.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class SignUpClass {
    //Global
    Activity activity;
    FirebaseAuth mAuth;
    Boolean up;
    ProgressBar progressBar;
    SignInClass signInClass;
    public SignUpClass(Activity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
        signInClass =new SignInClass(activity,progressBar);
        mAuth=FirebaseAuth.getInstance();
        up=false;
    }//end Constructor

    public boolean signUp(EditText name, EditText email, EditText password) {
        progressBar.setVisibility(View.VISIBLE);
        String userName = name.getText().toString();
        String userEmail = email.getText().toString().trim();

        String userPassword = password.getText().toString().trim();


        if (userPassword.isEmpty() || userName.isEmpty() || userEmail.isEmpty() ) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(activity, "You must fill all filds ... try again ", Toast.LENGTH_SHORT).show();
            return false;
//        } else if (!userPassword.equals(userPasswordConfirmation)) {
//            Toast.makeText(activity, "your confirmation password is incorrect", Toast.LENGTH_LONG).show();
//            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            progressBar.setVisibility(View.GONE);
            email.setError("Email is invalid");
            email.requestFocus();

            return false;
        } else {

            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                up = true;
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    //set user Desplay name
                                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userName)
                                            .build();
                                    user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Intent intent =new Intent(activity, SignUp2Activity.class);
                                            //signInClass.signIn(email,password);
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(activity, "you have signed up successfully", Toast.LENGTH_SHORT).show();
                                            activity.startActivity(intent);
                                            activity.finish();
                                        }
                                    });
                                }




                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(activity, "this Email is already exist", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                // If sign in fails, display a message to the user.
                                up = false;
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

            return up;
        }
    }
}

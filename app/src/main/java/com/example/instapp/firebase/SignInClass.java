package com.example.instapp.firebase;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.instapp.Home2Activity;
import com.example.instapp.classes.CurrentUserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInClass {
    //Global
    Activity activity;
    Boolean up;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    public static FirebaseUser currentUser;

    public SignInClass(Activity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar=progressBar;
        up=false;
        mAuth=FirebaseAuth.getInstance();
    }//end

    public boolean signIn(EditText email, EditText password) {
        progressBar.setVisibility(View.VISIBLE);
        String userEmail = new String(String.valueOf(email.getText()));
        String userPassword = new String(String.valueOf(password.getText()));
        boolean found = false;
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(activity, "please enter the whole data", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        } else {
            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                up=true;
                                CurrentUserClass.currentUser =mAuth.getCurrentUser();
                                FirebaseFirestore db =FirebaseFirestore.getInstance();
                                db.collection("sampledata/users/data").whereEqualTo("id",CurrentUserClass.currentUser.getUid())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
//                                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                                       if(document.getString("email").equals(userEmail))
//                                                       {
//                                                           me=new User(document.getString("name"),document.getString("email"),
//                                                                   document.getString("id"),document.getString("phone"),
//                                                                   true,document.getId(),document.getString("uri"),false,
//                                                                   document.getString("token"));
//                                                           StatusClass.stasus(true);
//                                                           Intent intent =new Intent(activity, FriendsActivity.class);
//                                                           activity.startActivity(intent);
//                                                           break;
//                                                       }
//                                                    }
                                                    progressBar.setVisibility(View.GONE);
                                                    Intent intent =new Intent(activity, Home2Activity.class);
                                                    activity.startActivity(intent);
                                                    activity.finish();
                                                    Toast.makeText(activity, "signed in", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user.
                                Toast.makeText(activity, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
       return up;
    }
}

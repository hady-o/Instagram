//package com.example.instapp.firebase;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//public class SignInClass {
//    //Global
//    Activity activity;
//    Boolean up;
//    FirebaseAuth mAuth;
//    public static FirebaseUser currentUser;
//    public static User me;
//
//    public SignInClass(Activity activity) {
//        this.activity = activity;
//        up=false;
//        mAuth=FirebaseAuth.getInstance();
//    }//end
//
//    public boolean signIn(EditText email, EditText password) {
//        String userEmail = new String(String.valueOf(email.getText()));
//        String userPassword = new String(String.valueOf(password.getText()));
//        boolean found = false;
//        if (userEmail.isEmpty() || userPassword.isEmpty()) {
//            Toast.makeText(activity, "pleas enter date", Toast.LENGTH_SHORT).show();
//        } else {
//            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
//                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                up=true;
//                                currentUser =mAuth.getCurrentUser();
//                                FirebaseFirestore db =FirebaseFirestore.getInstance();
//                                db.collection("sampledata/users/data")
//                                        .get()
//                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                if (task.isSuccessful()) {
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
//                                                }
//                                            }
//                                        });
//
//                            }
//                            else {
//                                // If sign in fails, display a message to the user.
//                                Toast.makeText(activity, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//       return up;
//    }
//}

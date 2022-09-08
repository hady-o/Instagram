package com.example.instapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.instapp.Home2Activity;
import com.example.instapp.LogInActivity;
import com.example.instapp.MainActivity;
import com.example.instapp.R;
import com.example.instapp.UserInfoActivity;
import com.example.instapp.classes.CurrentUserClass;
import com.example.instapp.classes.Theme;
import com.example.instapp.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_setting, container, false);

        RadioGroup radioGroup;
        RadioButton en, ar;

        Map<String, Object> themeData = new HashMap<>();
        TextView profile = view.findViewById(R.id.profileBtn);
        TextView logOut = view.findViewById(R.id.logOutBtn);
        radioGroup =view.findViewById(R.id.radio);
        en =view.findViewById(R.id.enRadio);
        ar = view.findViewById(R.id.arRadio);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.arRadio)
                {

                }else
                {

                }
            }
        });

        Switch theme = view.findViewById(R.id.switch1);
        if(Theme.dark)
        {
            theme.setChecked(true);
        }
        else
        {
            theme.setChecked(false);
        }
        TextView language = view.findViewById(R.id.languageBtn);


        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theme.isChecked())
                {

                    themeData.put("theme", "dark");
                    FirebaseFirestore.getInstance().document("theme/"+CurrentUserClass.currentUser.getUid()).set(themeData);
                    Theme.dark=true;
                }
                else
                {
                    themeData.put("theme", "light");
                    FirebaseFirestore.getInstance().document("theme/"+CurrentUserClass.currentUser.getUid()).set(themeData);
                    Theme.dark=false;
                }
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUserClass.currentFriend = new User(CurrentUserClass.currentUser.getUid(),CurrentUserClass.currentUser.getDisplayName()
                ,CurrentUserClass.currentUser.getEmail(),CurrentUserClass.currentUser.getPhotoUrl().toString());
                Intent intent =new Intent(view.getContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(view.getContext(), LogInActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}

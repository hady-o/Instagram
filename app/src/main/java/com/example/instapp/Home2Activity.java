package com.example.instapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.instapp.adapters.TabLayoutAdapter;
import com.example.instapp.classes.Theme;
import com.example.instapp.databinding.ActivityHome2Binding;
import com.example.instapp.fragments.HomeFragment;
import com.example.instapp.fragments.LoveFragment;
import com.example.instapp.fragments.SearchFragment;
import com.example.instapp.fragments.SettingFragment;

public class Home2Activity extends AppCompatActivity {
    ActivityHome2Binding binding ;
    TabLayoutAdapter tabLayoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        tabLayoutAdapter =new TabLayoutAdapter(getSupportFragmentManager());
        tabLayoutAdapter.addFragment(new HomeFragment(),"");
        tabLayoutAdapter.addFragment(new SearchFragment(),"");
        tabLayoutAdapter.addFragment(new LoveFragment(),"");
        tabLayoutAdapter.addFragment(new SettingFragment(),"");
        binding.addPostBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),AddPostActivity.class);
                startActivity(intent);
            }
        });

        binding.viewPager.setAdapter(tabLayoutAdapter);
        binding.myTab.setupWithViewPager(binding.viewPager);
        binding.myTab.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        binding.myTab.getTabAt(1).setIcon(R.drawable.ic_baseline_search_24);
        binding.myTab.getTabAt(2).setIcon(R.drawable.ic_baseline_favorite_24);
        binding.myTab.getTabAt(3).setIcon(R.drawable.ic_baseline_settings_24);



        //binding.myTab.getTabAt(0).setCustomView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Theme.setTheme();
    }
}
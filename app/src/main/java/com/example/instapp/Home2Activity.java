package com.example.instapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.instapp.databinding.ActivityHome2Binding;

public class Home2Activity extends AppCompatActivity {
    ActivityHome2Binding binding ;
    TabLayoutAdapter tabLayoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tabLayoutAdapter =new TabLayoutAdapter(getSupportFragmentManager());
        tabLayoutAdapter.addFragment(new HomeFragment(),"");
        tabLayoutAdapter.addFragment(new SearchFragment(),"");
        tabLayoutAdapter.addFragment(new LoveFragment(),"");
        tabLayoutAdapter.addFragment(new SettingFragment(),"");

        binding.viewPager.setAdapter(tabLayoutAdapter);
        binding.myTab.setupWithViewPager(binding.viewPager);
        binding.myTab.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        binding.myTab.getTabAt(1).setIcon(R.drawable.ic_baseline_search_24);
        binding.myTab.getTabAt(2).setIcon(R.drawable.ic_baseline_favorite_24);
        binding.myTab.getTabAt(3).setIcon(R.drawable.ic_baseline_settings_24);



        //binding.myTab.getTabAt(0).setCustomView();
    }
}
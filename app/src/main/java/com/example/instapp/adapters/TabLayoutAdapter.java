package com.example.instapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentsList =new ArrayList<>();
    private final List<String> fragmentsTitleList =new ArrayList<>();
    public TabLayoutAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment,String title)
    {
        fragmentsList.add(fragment);
        fragmentsTitleList.add(title);
    }//end AddFragment


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }//end getItem

    @Override
    public int getCount() {
        return fragmentsList.size();
    }//end get count

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitleList.get(position);
    }
}

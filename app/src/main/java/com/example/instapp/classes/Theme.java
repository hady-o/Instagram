package com.example.instapp.classes;

import androidx.appcompat.app.AppCompatDelegate;

public class Theme {
    public static boolean dark =false;

    public static void setTheme()
    {
        if(dark)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}

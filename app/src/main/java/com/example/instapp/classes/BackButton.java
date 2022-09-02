package com.example.instapp.classes;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BackButton {
    public static void back(Activity from, Class to)
    {
        Intent intent =new Intent(from,  to);
        from.startActivity(intent);
        from.finish();

    }
}

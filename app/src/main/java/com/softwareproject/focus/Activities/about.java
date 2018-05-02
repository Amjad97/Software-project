package com.softwareproject.focus.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softwareproject.focus.R;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

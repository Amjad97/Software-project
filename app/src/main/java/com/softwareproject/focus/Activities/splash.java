package com.softwareproject.focus.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.softwareproject.focus.R;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);
        View splash_icon = findViewById(R.id.splash_icon);
        View splash_text = findViewById(R.id.splash_text);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        splash_icon.startAnimation(animation);
        splash_text.startAnimation(animation);

        Thread timer= new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2500);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                }catch (Exception e){}
            }
        };
        timer.start();

    }


}

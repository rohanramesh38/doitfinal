package com.example.rohan.doitfinal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    TextView t1,t2,t3;
    ImageView imlogo;
    Animation frombottom1,frombottom2,frombottom3,fromtop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        t1=findViewById(R.id.one);
        t2=findViewById(R.id.stop);
        t3=findViewById(R.id.torc);
        imlogo=findViewById(R.id.imgLogo);

frombottom1= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        frombottom2= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        frombottom3= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);


       frombottom1.setDuration(1000);
        t1.setAnimation(frombottom1);
        frombottom2.setDuration(2000);
        t2.setAnimation(frombottom2);
        frombottom3.setDuration(3000);
        t3.setAnimation(frombottom3);
        imlogo.setAnimation(fromtop);



        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);


                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
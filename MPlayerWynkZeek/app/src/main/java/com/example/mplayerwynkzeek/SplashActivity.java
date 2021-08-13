package com.example.mplayerwynkzeek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    LinearLayout L1,L2;
    TextView tv,textView;

    Animation DownToTop,Fade;

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()==android.R.id.home){
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        L1=(LinearLayout)findViewById(R.id.l1);
        L2=(LinearLayout)findViewById(R.id.l2);

        tv=(TextView)findViewById(R.id.tag);
        textView=(TextView)findViewById(R.id.app_title);

//        getSupportActionBar().setTitle("WynkZeek");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Handle-Regular.ttf");
//        textView.setTypeface(type);

        DownToTop= AnimationUtils.loadAnimation(this,R.anim.downtotop);
        Fade=AnimationUtils.loadAnimation(this, R.anim.fade);

        L2.setAnimation(DownToTop);
        tv.setAnimation(Fade);

        Intent i=new Intent(SplashActivity.this, MainActivity.class);

        Thread thread=new Thread() {
            @Override
            public void run() {
                try {
                    // to stay on splash screen for 5 seconds
                    sleep(5000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
    }
}
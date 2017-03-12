package org.temoteam.holodilnik.Main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import org.temoteam.holodilnik.R;

public class SplashActivity extends AppCompatActivity implements View.OnTouchListener,
        SpringListener {

    private final static double TENSION = 400;
    private final static double DAMPER = 30; // friction


    ImageView logo;
    SpringSystem springSystem;
    Spring spring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = (ImageView) findViewById(R.id.imageView);
        logo.setOnTouchListener(this);

        springSystem = SpringSystem.create();
        spring = springSystem.createSpring();
        spring.addListener(this);
        spring.setSpringConfig(new SpringConfig(TENSION, DAMPER));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Thread closeActivity = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                } catch (Exception e) {
                    e.getLocalizedMessage();
                }
            }
        });

        closeActivity.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // v.performClick();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                spring.setEndValue(1f);
                return true;
            case MotionEvent.ACTION_UP:
                spring.setEndValue(0f);
                return true;
        }
        v.performClick();
        return false;
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        float value = (float) spring.getCurrentValue();
        float scale = 1f - (value * 0.5f);
        logo.setScaleX(scale);
        logo.setScaleY(scale);
    }

    @Override
    public void onSpringAtRest(Spring spring) {

    }

    @Override
    public void onSpringActivate(Spring spring) {

    }

    @Override
    public void onSpringEndStateChange(Spring spring) {

    }

}

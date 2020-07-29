package com.example.coll_tab;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements VolleyCallback{
    private final int time = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // wake server
        API.getParentsRequest(MainActivity.this);

        // Assign Variable
        ImageView aniView = (ImageView)findViewById(R.id.imageView);

        // animate
        ObjectAnimator mover = ObjectAnimator.ofFloat(aniView, "rotation", 400f,0f);
        mover.setDuration(3000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(aniView, "alpha", 0f, 1f);
        fadeIn.setDuration(3000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeIn).with(mover);
        animatorSet.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(time);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(String err) {

    }
}

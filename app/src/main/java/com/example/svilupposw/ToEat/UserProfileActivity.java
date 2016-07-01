package com.example.svilupposw.ToEat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton homeButton = (ImageButton) findViewById(R.id.home);
        ImageButton userButton = (ImageButton) findViewById(R.id.user);
        ImageButton logoutButton = (ImageButton) findViewById(R.id.logout);
        userButton.setEnabled(false);
        userButton.setColorFilter(Color.parseColor("#B7B2B0"), PorterDuff.Mode.MULTIPLY);

        // Creating timer Color Backgorund
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        changeColor();
                    }
                });
            }

            ;
        }, 0, 4500);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListLocalActivity.class);
                startActivity(intent);
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
                startActivity(intent);
            }
        });

        TextView nameText = (TextView) findViewById(R.id.nameText);
        TextView emailText = (TextView) findViewById(R.id.emailText);
        //TextView ageText = (TextView) findViewById(R.id.ageText);

        if (nameText != null) {
            nameText.setText(MyApplication.getName());
        }
        if (emailText != null) {
            emailText.setText(MyApplication.getMail());
        }
        /*if (ageText != null) {
            ageText.setText(MyApplication.getAge());
        }*/
    }

    public void changeColor(){

        // Get Elements
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final AppBarLayout actionBarXX = (AppBarLayout) findViewById(R.id.actionBarXX);

        // Set colors
        int newColor = ColorGenerator.getColor();

        // Get old color
        int currentColor = ( (ColorDrawable) toolbar.getBackground() ).getColor();

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, newColor);
        colorAnimation.setDuration(4000); // milliseconds
        colorAnimation.setInterpolator(new AccelerateInterpolator(1.0F));
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                container.setBackgroundColor((int) animator.getAnimatedValue());
                toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                actionBarXX.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

}

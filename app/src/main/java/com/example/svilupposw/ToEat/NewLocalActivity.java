package com.example.svilupposw.ToEat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Timer;
import java.util.TimerTask;

public class NewLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton homeButton = (ImageButton) findViewById(R.id.home);
        ImageButton userButton = (ImageButton) findViewById(R.id.user);
        ImageButton logoutButton = (ImageButton) findViewById(R.id.logout);

        // Creating timer Color Backgorund
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread( new Runnable() {
                    public void run() {
                        changeColor();
                    }
                });
            };
        },0, 4500 );

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nametxt = (EditText) findViewById(R.id.name);
                String name = nametxt.getText().toString();

                EditText addresstxt = (EditText) findViewById(R.id.address);
                String address = addresstxt.getText().toString();

                EditText typetxt = (EditText) findViewById(R.id.type);
                String type = typetxt.getText().toString();

                EditText moneytxt = (EditText) findViewById(R.id.money);
                String money = moneytxt.getText().toString();

                EditText contacttxt = (EditText) findViewById(R.id.contact);
                String contact = contacttxt.getText().toString();

                EditText hourstxt = (EditText) findViewById(R.id.hours);
                String hours = hourstxt.getText().toString();

                if (name.equals("") || address.equals("")|| type.equals("")|| money.equals("")|| contact.equals("")|| hours.equals("")) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.fillFields), Toast.LENGTH_LONG).show();
                }
                else {
                    Firebase newLocal = MyApplication.getMyFirebaseRef().child("local");
                    Firebase newLocalRef = newLocal.push();
                    Local item = new Local(name, address, type, money, contact, hours, MyApplication.getMyUid());
                    item.setId(newLocalRef.getKey());
                    newLocalRef.setValue(item);

                    Intent intent = new Intent(getApplicationContext(), ListLocalActivity.class);

                    startActivity(intent);
                }

            }
        });

    }

    public void changeColor(){

        // Get Elements
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ScrollView container = (ScrollView) findViewById(R.id.container);
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

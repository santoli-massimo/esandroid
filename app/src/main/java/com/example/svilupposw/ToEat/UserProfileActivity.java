package com.example.svilupposw.ToEat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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

}

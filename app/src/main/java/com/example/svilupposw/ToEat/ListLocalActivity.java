package com.example.svilupposw.ToEat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ListLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_local);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton homeButton = (ImageButton) findViewById(R.id.home);
        ImageButton userButton = (ImageButton) findViewById(R.id.user);
        ImageButton logoutButton = (ImageButton) findViewById(R.id.logout);
        homeButton.setEnabled(false);
        homeButton.setColorFilter(Color.parseColor("#B7B2B0"), PorterDuff.Mode.MULTIPLY);

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

        final String userName = MyApplication.getName();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewLocalActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<Local> list = new ArrayList<>();
        final MyLocalAdapter myLocalAdapter = new MyLocalAdapter(getApplicationContext());
        final ListView listView = (ListView) findViewById(R.id.listLocal);
        if (listView != null) {
            listView.setAdapter(myLocalAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Local selectedFromList = (Local) (listView.getItemAtPosition(position));
                    Intent intent = new Intent(getApplicationContext(), LocalDetailActivity.class);

                    intent.putExtra("localName", (String) selectedFromList.getName());
                    intent.putExtra("localAddress", (String) selectedFromList.getAddress());
                    intent.putExtra("localType", (String) selectedFromList.getType());
                    intent.putExtra("localMoney", (String) selectedFromList.getMoney());
                    intent.putExtra("localContact", (String) selectedFromList.getContact());
                    intent.putExtra("localHours", (String) selectedFromList.getHours());
                    intent.putExtra("localId", (String) selectedFromList.getId());
                    intent.putExtra("userName",userName);

                    startActivity(intent);
                }
            });

            MyApplication.getMyFirebaseRef().child("local").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.i("onChildAdded", dataSnapshot.getKey());

                    Local newLocal = dataSnapshot.getValue(Local.class);
                    myLocalAdapter.addItem(newLocal);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.i("onChildChanged", dataSnapshot.getKey());
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.i("onChildRemoved", dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    Log.i("onChildMoved", "dataSnapshot.getKey()");
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.i("onCancelled", firebaseError.getMessage());

                }
            });
        }
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
        colorAnimation.setInterpolator( new AccelerateInterpolator(1.0F) );
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

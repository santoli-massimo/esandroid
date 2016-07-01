package com.example.svilupposw.ToEat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class LoginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_login_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText mail = (EditText) findViewById(R.id.textMail);
        final EditText pwd = (EditText) findViewById(R.id.textPsw);
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase ref = MyApplication.getMyFirebaseRef();
        Log.i("fab", "ok");
                ref.authWithPassword(mail.getText().toString(), pwd.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                        MyApplication.setMyUid(authData.getUid());
                        MyApplication.setMail(authData.getProviderData().get("email").toString());
                        Log.i("onAuthenticated", "ok");
                        MyApplication.getUserRefByKey(authData.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                MyApplication.setName((String) dataSnapshot.child("displayName").getValue());

                                Intent intent = new Intent(getApplicationContext(), ListLocalActivity.class);
                                String userName = ((String)dataSnapshot.child("displayName").getValue());

                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.welcome)+" "+ userName, Toast.LENGTH_LONG).show();

                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                Log.i("Login", "###########################à Firebase Error");

                            }
                        });

                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {

                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.InvalidLogin),
                                Toast.LENGTH_LONG).show();
                        Log.i("Login", firebaseError.getMessage());
                    }

                });
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationMainActivity.class);
                startActivity(intent);
            }

        });
    }

    public void changeColor(){

        // Get Elements
        final Button loginButton = (Button) findViewById(R.id.buttonRegister);
        final TextView email = (TextView) findViewById(R.id.textMail);
        final TextView password = (TextView) findViewById(R.id.textPsw);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final AppBarLayout actionBarXX = (AppBarLayout) findViewById(R.id.actionBarXX);

        final LinearLayout container = (LinearLayout) findViewById(R.id.container);

        // Set colors
        int color = ColorGenerator.getColor();
        int currentColor = loginButton.getCurrentTextColor();

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, color);
        colorAnimation.setDuration(4000); // milliseconds
        colorAnimation.setInterpolator( new AccelerateInterpolator(1.0F) );
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                container.setBackgroundColor((int) animator.getAnimatedValue());
                loginButton.setTextColor((int) animator.getAnimatedValue());
                toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                actionBarXX.setBackgroundColor((int) animator.getAnimatedValue());

//                toolbar.setBackgroundColor((int) animator.getAnimatedValue());

            }

        });
        colorAnimation.start();
    }

}

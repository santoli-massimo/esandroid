package com.example.svilupposw.ToEat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class AddReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton homeButton = (ImageButton) findViewById(R.id.home);
        ImageButton userButton = (ImageButton) findViewById(R.id.user);
        ImageButton logoutButton = (ImageButton) findViewById(R.id.logout);

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

        final Bundle recdData = getIntent().getExtras();
        final String userName = recdData.getString("userName");

        final String localName = recdData.getString("localName");
        TextView localNameText = (TextView) findViewById(R.id.localName);
        localNameText.setText(localName);

        final EditText comment_Text = (EditText) findViewById(R.id.comment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String comment = comment_Text.getText().toString();
                RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                String rating = String.valueOf(ratingBar.getRating());

                if (comment.equals("") || rating.equals("")) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.fillFields), Toast.LENGTH_LONG).show();
                }
                else {
                    Firebase newReview = MyApplication.getMyFirebaseRef().child("review");
                    Firebase newReviewRef = newReview.push();

                    Review item = new Review(comment, rating, MyApplication.getMyUid(), recdData.getString("localId"),userName);
                    item.setId(newReviewRef.getKey());
                    newReviewRef.setValue(item);

                    Intent intent = new Intent(getApplicationContext(), LocalDetailActivity.class);
                    intent.putExtra("localName", localName);
                    intent.putExtra("localAddress", recdData.getString("localAddress"));
                    intent.putExtra("localType", recdData.getString("localType"));
                    intent.putExtra("localMoney", recdData.getString("localMoney"));
                    intent.putExtra("localContact", recdData.getString("localContact"));
                    intent.putExtra("localHours", recdData.getString("localHours"));
                    intent.putExtra("localId", recdData.getString("localId"));
                    intent.putExtra("userName",userName);

                    startActivity(intent);

                }

            }
        });
    }

}

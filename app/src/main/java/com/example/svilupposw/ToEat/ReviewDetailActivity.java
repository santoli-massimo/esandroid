package com.example.svilupposw.ToEat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_detail);

        String activityTitle = getApplicationContext().getResources().getString(R.string.title_activity_review_detail);
        TextView activityTitleText = (TextView) findViewById(R.id.activityTitle);
        activityTitleText.setText(activityTitle);

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

        final String localName = recdData.getString("localName");
        TextView localNameText = (TextView) findViewById(R.id.localName);
        localNameText.setText(localName);

        final String localType = recdData.getString("localType");
        TextView localTypeText = (TextView) findViewById(R.id.localType);
        localTypeText.setText(localType);

        final String author = recdData.getString("reviewAuthor");
        TextView localAuthorText = (TextView) findViewById(R.id.reviewAuthor);
        localAuthorText.setText(author);

        final String comment = recdData.getString("reviewComment");
        TextView localCommentText = (TextView) findViewById(R.id.reviewComment);
        localCommentText.setText(comment);

        final String rating = recdData.getString("reviewRating");
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.parseFloat(rating));

    }
}

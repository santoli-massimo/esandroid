package com.example.svilupposw.ToEat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public class LocalDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_detail);

        String activityTitle = getApplicationContext().getResources().getString(R.string.title_activity_local_detail);
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

        final String localAddress = recdData.getString("localAddress");
        TextView localAddressText = (TextView) findViewById(R.id.localAddress);
        localAddressText.setText(localAddress);

        final String localType = recdData.getString("localType");
        TextView localTypeText = (TextView) findViewById(R.id.localType);
        localTypeText.setText(localType);

        final String localMoney = recdData.getString("localMoney");
        TextView localMoneyText = (TextView) findViewById(R.id.localMoney);
        localMoneyText.setText(localMoney + "€");

        final String localContact = recdData.getString("localContact");
        TextView localContactText = (TextView) findViewById(R.id.localContact);
        localContactText.setText(localContact);

        final String localHours = recdData.getString("localHours");
        TextView localHoursText = (TextView) findViewById(R.id.localHours);
        localHoursText.setText(localHours);

        Button addReview = (Button) findViewById(R.id.addReview);
        addReview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddReviewActivity.class);
                intent.putExtras(recdData);
                startActivity(intent);
            }

        });


        final MyReviewAdapter myReviewAdapter = new MyReviewAdapter(getApplicationContext(),recdData.getString("localId"));
        final ListView listView = (ListView) findViewById(R.id.listLocalReview);

        if (listView != null) {
            listView.setAdapter(myReviewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Review selectedFromList = (Review) (listView.getItemAtPosition(position));
                    Intent intent = new Intent(getApplicationContext(), ReviewDetailActivity.class);

                    intent.putExtra("localName", localName);
                    intent.putExtra("localType", localType);
                    intent.putExtra("reviewComment", (String) selectedFromList.getComment());
                    intent.putExtra("reviewAuthor", (String) selectedFromList.getUserName());
                    intent.putExtra("reviewRating", (String) selectedFromList.getRating());

                    startActivity(intent);
                }
            });

            MyApplication.getMyFirebaseRef().child("review").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.i("onChildAdded", dataSnapshot.getKey());

                    Review newReview = dataSnapshot.getValue(Review.class);
                    myReviewAdapter.addItem(newReview);
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
}

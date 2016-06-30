package com.example.svilupposw.ToEat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

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

}

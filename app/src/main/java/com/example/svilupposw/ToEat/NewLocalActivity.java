package com.example.svilupposw.ToEat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.client.Firebase;

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

}

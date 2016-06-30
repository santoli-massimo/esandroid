package com.example.svilupposw.ToEat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class RegistrationMainActivity extends AppCompatActivity {

    private EditText email_editText;
    private EditText password_editText;
    private EditText name_editText;
    private EditText age_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        email_editText = (EditText) findViewById(R.id.email);
        password_editText = (EditText) findViewById(R.id.password);
        name_editText = (EditText) findViewById(R.id.nickname);
        age_editText = (EditText) findViewById(R.id.age);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email_editText.setError(null);
                password_editText.setError(null);

                final String email = email_editText.getText().toString();
                final String password = password_editText.getText().toString();
                final String name = name_editText.getText().toString();
                final int age;
                if (age_editText.getText().toString().equals(""))
                {
                    age=0;
                    age_editText.setError(getString(R.string.error_invalid_age));
                }
                else
                {age = Integer.parseInt(age_editText.getText().toString());}

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(password) || !(password.length() > 4)) {
                    password_editText.setError(getString(R.string.error_invalid_password));
                    focusView = password_editText;
                    cancel = true;
                }

                if (TextUtils.isEmpty(email) || !email.contains("@")) {
                    email_editText.setError(getString(R.string.error_invalid_email));
                    focusView = email_editText;
                    cancel = true;
                }

                if (TextUtils.isEmpty(name)) {
                    name_editText.setError(getString(R.string.error_invalid_name));
                    focusView = name_editText;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    Log.i("registration", "ok");
                    MyApplication.getMyFirebaseRef().createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            System.out.println("Successfully created user account with uid: " + result.get("uid"));
                            MyApplication.setMyUid((String) result.get("uid"));
                            MyApplication.setMail(email);


                            MyApplication.getMyFirebaseRef().authWithPassword(email, password, new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                                    // Authentication just completed successfully :)
                                    Map<String, String> map = new HashMap<>();
                                    map.put("provider", authData.getProvider());
                                    if (authData.getProviderData().containsKey("displayName")) {
                                        map.put("displayName", authData.getProviderData().get("displayName").toString());
                                    } else {
                                        map.put("displayName", name);
                                    }
                                    if (authData.getProviderData().containsKey("displayAge")) {
                                        map.put("displayAge", authData.getProviderData().get("displayAge").toString());
                                    } else {
                                        map.put("displayAge", new Integer(age).toString());
                                    }
                                    MyApplication.getMyFirebaseRef().child("users").child(authData.getUid()).setValue(map, new Firebase.CompletionListener() {
                                        @Override
                                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                            MyApplication.setName(name);
                                            Intent intent = new Intent(getApplicationContext(), ListLocalActivity.class);

                                            intent.putExtra("userName",name);

                                            Toast.makeText(getApplicationContext(), getString(R.string.welcomeToast)+" " + name, Toast.LENGTH_LONG).show();

                                            startActivity(intent);
                                        }
                                    });
                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    // there was an error
                                    Log.e("Login", "error");
                                    Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // there was an error
                            Log.e("creation", "error");
                            Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

}

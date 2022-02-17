package com.example.courserafirstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    public final static String USER_KEY = "USER_KEY";
    private TextView mUsername;
    private TextView mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mUsername = findViewById(R.id.profileUsername);
        mPassword = findViewById(R.id.profilePassword);

        Bundle bundle = getIntent().getExtras();
        User user = (User)bundle.get(USER_KEY);
        mUsername.setText(user.getUsername());
        mPassword.setText(user.getPassword());

    }
}
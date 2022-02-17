package com.example.courserafirstproject;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AuthActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mButtonLogin;
    private Button mButtonRegister;

    private final View.OnClickListener mOnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {
                showMessage(R.string.auth_succeed);
                Intent startProfileIntent = new Intent(AuthActivity.this, ProfileActivity.class);
                startProfileIntent.putExtra(ProfileActivity.USER_KEY, new User(mUsername.getText().toString(), mPassword.getText().toString()));
                startActivity(startProfileIntent);
            } else {
                showMessage(R.string.auth_input_error);
            }
        }
    };

    private final View.OnClickListener mOnRegisterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(AuthActivity.this, "Раздел в разработке!", Toast.LENGTH_LONG).show();
        }
    };

    private void showMessage(@StringRes int message) {
        Toast.makeText(AuthActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mUsername.getText()) && Patterns.EMAIL_ADDRESS.matcher(mUsername.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_auth);

        mUsername = findViewById(R.id.editLogin);
        mPassword = findViewById(R.id.editPassword);
        mButtonLogin = findViewById(R.id.buttonLogin);
        mButtonRegister = findViewById(R.id.buttonRegister);

        mButtonLogin.setOnClickListener(mOnLoginClick);
        mButtonRegister.setOnClickListener(mOnRegisterClick);
    }
}
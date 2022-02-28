package com.example.courserafirstproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AuthFragment extends Fragment {

    private EditText mUsername;
    private EditText mPassword;
    private Button mButtonLogin;
    private Button mButtonRegister;

    private SharedPreferenceHelper mSharedPreferenceHelper;

    private final View.OnClickListener mOnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid() && isUserRegistered()) {
                showMessage(R.string.auth_succeed);
                Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                startProfileIntent.putExtra(ProfileActivity.USER_KEY, new User(mUsername.getText().toString(), mPassword.getText().toString()));
                startActivity(startProfileIntent);
            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    private final View.OnClickListener mOnRegisterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName())
                    .commit();
        }
    };

    private void showMessage(@StringRes int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isUserRegistered() {
        String inputUsername = mUsername.getText().toString();
        String inputPassword = mPassword.getText().toString();
        List<User> users = mSharedPreferenceHelper.getUsers();
        for (User u : users) {
            if (inputUsername.equalsIgnoreCase(u.getUsername())) {
                if (inputPassword.equals(u.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mUsername.getText()) && Patterns.EMAIL_ADDRESS.matcher(mUsername.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();
        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mSharedPreferenceHelper = new SharedPreferenceHelper(getActivity());

        mUsername = v.findViewById(R.id.editLogin);
        mPassword = v.findViewById(R.id.editPassword);
        mButtonLogin = v.findViewById(R.id.buttonLogin);
        mButtonRegister = v.findViewById(R.id.buttonRegister);

        mButtonLogin.setOnClickListener(mOnLoginClick);
        mButtonRegister.setOnClickListener(mOnRegisterClick);
        return v;
    }
}
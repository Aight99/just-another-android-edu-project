package com.example.courserafirstproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RegistrationFragment extends Fragment {
    private EditText mUsername;
    private EditText mPassword;
    private EditText mPasswordRepeat;
    private Button mButtonRegister;

    private SharedPreferenceHelper mSharedPreferenceHelper;

    public static RegistrationFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final View.OnClickListener mOnRegisterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInputCorrect()) {
                if (mSharedPreferenceHelper.addNewUser(new User(mUsername.getText().toString(), mPassword.getText().toString()))) {
                    showMessage(R.string.registration_succeed);
                    getParentFragmentManager().popBackStack();
                } else {
                    showMessage(R.string.email_already_exist);
                }
            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    private void showMessage(@StringRes int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean isInputCorrect() {
        return isEmailCorrect() && isPasswordsCorrect();
    }

    private boolean isEmailCorrect() {
        return !TextUtils.isEmpty(mUsername.getText()) && Patterns.EMAIL_ADDRESS.matcher(mUsername.getText()).matches();
    }

    private boolean isPasswordsCorrect() {
        return !TextUtils.isEmpty(mPassword.getText())
                && !TextUtils.isEmpty(mPasswordRepeat.getText())
                && (mPasswordRepeat.getText().toString().equals(mPassword.getText().toString()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fr_register, container, false);

        mSharedPreferenceHelper = new SharedPreferenceHelper(getActivity());

        mUsername = v.findViewById(R.id.registrationLogin);
        mPassword = v.findViewById(R.id.registrationPassword);
        mPasswordRepeat = v.findViewById(R.id.registrationPasswordRepeat);
        mButtonRegister = v.findViewById(R.id.buttonRegister);

        mButtonRegister.setOnClickListener(mOnRegisterClick);
        return v;
    }
}

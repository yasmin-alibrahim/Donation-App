package com.example.alsharhan99.activities.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.alsharhan99.R;
import com.example.alsharhan99.activities.login.LoginActivity;
import com.example.alsharhan99.activities.signup.SignupActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnSignUp;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViews();
        handleClicks();
    }

    private void findViews() {
        btnSignUp = findViewById(R.id.signup_button);
        btnLogin = findViewById(R.id.login_button);
    }

    private void handleClicks() {
        btnSignUp.setOnClickListener(view -> startSignUp());

        btnLogin.setOnClickListener(view -> startLogin());
    }

    private void startSignUp() {
        startActivity(new Intent(this, SignupActivity.class));
    }

    private void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
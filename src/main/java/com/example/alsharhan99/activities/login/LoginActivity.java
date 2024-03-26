package com.example.alsharhan99.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alsharhan99.activities.main.MainActivity;
import com.example.alsharhan99.R;
import com.example.alsharhan99.activities.signup.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edEmail;
    private EditText edPassword;
    private Button btnLogin;

    private TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initData();
    }

    private void initData() {
        findViews();
        handleClicks();
    }

    private void findViews() {
        edEmail = findViewById(R.id.login_email);
        edPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
    }

    private void handleClicks() {
        btnLogin.setOnClickListener(view -> {
            handleLogin();
        });
        signupRedirectText.setOnClickListener(view -> {
            handleRegister();
        });
    }

    private void handleLogin() {
        validateInput();
    }

    private void validateInput() {
        if (edEmail.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()) {
            showToast("Email/Password required ...");
        } else {
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            login(email, password);
        }
    }

    private void login(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login success
                        showToast("Login successful");
                        proceedToMain();
                    } else {
                        // If sign in fails, display a message to the user.
                        showToast("Login failed: " + task.getException().getMessage());
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void proceedToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void handleRegister() {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }


}
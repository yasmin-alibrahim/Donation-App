package com.example.alsharhan99.activities.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alsharhan99.R;
import com.example.alsharhan99.activities.login.LoginActivity;
import com.example.alsharhan99.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private EditText edName;
    private EditText edEmail;
    private EditText edPhone;
    private EditText edPassword;
    private Button btnSign;

    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        initData();
    }


    private void initData() {
        findViews();
        handleClicks();
    }

    private void findViews() {
        edEmail = findViewById(R.id.signup_email);
        edPassword = findViewById(R.id.signup_password);
        edName = findViewById(R.id.signup_name);
        edPhone = findViewById(R.id.signup_phone);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        btnSign = findViewById(R.id.signup_button);
    }

    private void handleClicks() {
        btnSign.setOnClickListener(view -> {
            handleRegister();
        });
        loginRedirectText.setOnClickListener(view -> {
            proceedLogin();
        });
    }

    private void handleRegister() {
        validateInput();
    }

    private void validateInput() {
        if (edName.getText().toString().isEmpty() || edPhone.getText().toString().isEmpty() || edEmail.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()) {
            showToast("All Inputs required ...");
        } else {
            String name = edName.getText().toString();
            String phone = edPhone.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            signUp(name, email, phone, password);
            showToast("Processing please wait ...");
        }
    }

    private void signUp(String name, String email, String phoneNumber, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            // Save additional user data to Firestore
                            saveUserDataToFirestore(uid, name, email, phoneNumber);
                            // You can navigate to another activity, for example.
                        } else {
                            showToast("Sign up failed: " + task.getException().getMessage());
                        }
                    }
                });
    }


    private void saveUserDataToFirestore(String uid, String name, String email, String phoneNumber) {
        // Access a Cloud Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user object with the provided data
        User user = new User(name, email, phoneNumber);
        // Add a new document with a generated ID
        db.collection("users")
                .document(uid)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    showToast("User data saved ...");
                    proceedLogin();
                })
                .addOnFailureListener(e -> showToast("Unable save user data ... " + e.getLocalizedMessage()));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void proceedLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
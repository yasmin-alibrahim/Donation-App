package com.example.alsharhan99.activities.payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alsharhan99.R;
import com.example.alsharhan99.activities.main.MainActivity;
import com.example.alsharhan99.model.MoneyDonation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentActivity extends AppCompatActivity {

    private EditText edCardNumber, edCardName, edCvv, edExp;
    private Button payButton;

    private String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        findViews();
        handleClick();
    }

    private void findViews() {
        edCardNumber = findViewById(R.id.card_number);
        edCardName = findViewById(R.id.name_on_card);
        edCvv = findViewById(R.id.cvv_number);
        edExp = findViewById(R.id.expiration_date);
        payButton = findViewById(R.id.pay_button);
        Intent intent = new Intent();
        amount = intent.getStringExtra("amount");

    }

    private void handleClick() {
        payButton.setOnClickListener(view -> {
            String nameOnCard = edCardName.getText().toString();
            String cardNumber = edCardNumber.getText().toString();
            String cvv = edCvv.getText().toString();
            String expiryDate = edExp.getText().toString();
            if (!nameOnCard.isEmpty() && !cardNumber.isEmpty() && !cvv.isEmpty() && !expiryDate.isEmpty()) {
                MoneyDonation moneyDonation = new MoneyDonation(amount, nameOnCard, cardNumber, cvv, expiryDate);
                savePaymentDetails(moneyDonation);
            } else {
                showMessage("All details required ...");
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void savePaymentDetails(MoneyDonation moneyDonation) {
        showMessage("Processing please wait ...");
        // Access a Cloud Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user object with the provided data
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Add a new document with a generated ID
        db.collection("payment_details")
                .document(uid)
                .set(moneyDonation)
                .addOnSuccessListener(aVoid -> {
                    showMessage("Payment details saved successfully");
                    goBack();
                })
                .addOnFailureListener(e -> showMessage("Unable save payment details ... " + e.getLocalizedMessage()));

    }

    private void goBack() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

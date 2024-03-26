package com.example.alsharhan99.activities.details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alsharhan99.R;
import com.example.alsharhan99.activities.main.MainActivity;
import com.example.alsharhan99.model.DonateFood;
import com.example.alsharhan99.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivity extends AppCompatActivity {


    private EditText edPickUp, edFoodItems, edDescriptions, edPreferredTime, edPickUpDay;

    private CheckBox agreeTerms;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        findViews();
        handleClick();
    }


    private void findViews() {
        edPickUp = findViewById(R.id.edittext1);
        edFoodItems = findViewById(R.id.edittext02);
        edDescriptions = findViewById(R.id.description);
        edPreferredTime = findViewById(R.id.edittext03);
        edPickUpDay = findViewById(R.id.edittext4);
        btnSubmit = findViewById(R.id.submitdonation_button);
        agreeTerms = findViewById(R.id.checkBox);
    }

    private void handleClick() {
        btnSubmit.setOnClickListener(view -> {
            if (agreeTerms.isChecked()) {
                validateInputs();
            } else {
                showMessage("Please accept terms and conditions ..");
            }
        });
    }

    private void validateInputs() {
        String pickup = edPickUp.getText().toString();
        String foodItem = edFoodItems.getText().toString();
        String description = edDescriptions.getText().toString();
        String preferredTime = edPreferredTime.getText().toString();
        String pickUpDay = edPickUpDay.getText().toString();
        if (!pickup.isEmpty() && !foodItem.isEmpty() && !description.isEmpty() && !preferredTime.isEmpty() && !pickUpDay.isEmpty()) {
            DonateFood donateFood = new DonateFood(pickup, foodItem, description, preferredTime, pickUpDay);
            saveFoodDonation(donateFood);
        } else {
            showMessage("All inputs required ...");
        }


    }

    private void saveFoodDonation(DonateFood donateFood) {

        showMessage("Processing please wait ...");
        // Access a Cloud Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user object with the provided data
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Add a new document with a generated ID
        db.collection("food_details")
                .document(uid)
                .set(donateFood)
                .addOnSuccessListener(aVoid -> {
                    showMessage("Food details saved successfully");
                    goBack();
                })
                .addOnFailureListener(e -> showMessage("Unable save food details ... " + e.getLocalizedMessage()));


    }


    private void goBack() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
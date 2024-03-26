package com.example.alsharhan99.fragments.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alsharhan99.R;
import com.example.alsharhan99.databinding.ProfileBinding;
import com.example.alsharhan99.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private View view;

    private EditText edName, edPhone;

    private Button btnUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile, container, false);
        // You can perform any additional setup or UI initialization here
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        handleClicks();
        fetchUserDataFromFirestore();
    }

    private void findViews() {
        edName = view.findViewById(R.id.user_name);
        edPhone = view.findViewById(R.id.user_phone);
        btnUpdate = view.findViewById(R.id.save_button);
    }


    private void handleClicks() {
        btnUpdate.setOnClickListener(view -> validateInputs());
    }

    private void fetchUserDataFromFirestore() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Convert the document snapshot to a User object (or use the data directly)
                        User user = documentSnapshot.toObject(User.class);

                        // Access the data
                        String name = user.getName();
                        String phoneNumber = user.getPhoneNumber();

                        // Now you can use the retrieved data as needed
                        updateUI(name, phoneNumber);
                    } else {
                        // Handle the case where the document doesn't exist
                        showMessage("User document does not exist");
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the failure
                    showMessage("Failed to fetch user data: ");
                });
    }

    private void updateUI(String name, String phoneNumber) {
        edName.setText(name);
        edPhone.setText(phoneNumber);
    }

    private void validateInputs() {
        String username = edName.getText().toString();
        String userPhone = edPhone.getText().toString();

        if (!username.isEmpty() && !userPhone.isEmpty()) {
            updateProfile(username, userPhone);
        } else {
            showMessage("All inputs required ....");
        }
    }

    private void updatePhoneNumberAndName(String uid, String newName, String newPhoneNumber) {
        showMessage("Processing please wait ...");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new map with the updated data
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", newName);
        updatedData.put("phoneNumber", newPhoneNumber);

        // Update the document in Firestore
        db.collection("users")
                .document(uid)
                .update(updatedData)
                .addOnSuccessListener(aVoid -> {
                    // Document successfully updated
                    showMessage("Profile updated successfully.. ");
                })
                .addOnFailureListener(e -> {
                    // Handle the failure
                    showMessage("Failed to update profile information: " + e.getMessage());
                });
    }

    private void updateProfile(String newName, String newPhoneNumber) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName) // Set the name here
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Profile updated successfully
                        updatePhoneNumberAndName(user.getUid(), newName, newPhoneNumber);
                    } else {
                        // If updating fails, handle the error
                        showMessage("Profile update failed");
                    }
                });

    }

    private void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
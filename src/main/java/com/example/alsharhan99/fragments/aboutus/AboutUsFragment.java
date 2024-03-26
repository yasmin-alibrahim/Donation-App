package com.example.alsharhan99.fragments.aboutus;

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
import com.example.alsharhan99.databinding.AboutusBinding;
import com.example.alsharhan99.databinding.DetailsBinding;
import com.example.alsharhan99.model.ContactUs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AboutUsFragment extends Fragment {

    private View view;

    private EditText editTextMessage;
    private Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.aboutus, container, false);
        // You can perform any additional setup or UI initialization here
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        handleClick();
    }

    private void findViews() {
        editTextMessage = view.findViewById(R.id.editTextMessage);
        btnSubmit = view.findViewById(R.id.button1);
    }

    private void handleClick() {
        btnSubmit.setOnClickListener(view -> validateInputs());
    }

    private void validateInputs() {
        String message = editTextMessage.getText().toString();
        if (!message.isEmpty()) {
            saveMessage(new ContactUs(message));
        } else {
            showMessage("All inputs required ...");
        }
    }

    private void saveMessage(ContactUs contactUs) {
        showMessage("Processing please wait ...");
        // Access a Cloud Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user object with the provided data
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Add a new document with a generated ID
        db.collection("about_us")
                .document(uid)
                .set(contactUs)
                .addOnSuccessListener(aVoid -> {
                    showMessage("Message saved successfully");
                    editTextMessage.setText("");
                })
                .addOnFailureListener(e -> showMessage("Unable save message ... " + e.getLocalizedMessage()));

    }

    private void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
package com.example.alsharhan99.fragments.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.alsharhan99.R;
import com.example.alsharhan99.activities.payment.PaymentActivity;

public class DonationpriceFragment extends Fragment {

    private View view;

    private RadioGroup  radioGroup;
    private Button btnContinue;
    private EditText edAmount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.donation_price, container, false);
        // You can perform any additional setup or UI initialization here
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        handleClicks();
    }


    private void findViews(){
        radioGroup = view.findViewById(R.id.amountRadioGroup1);
        btnContinue = view.findViewById(R.id.continue_button);
        edAmount = view.findViewById(R.id.edittext33);
    }

    private void handleClicks(){
// Set a listener for the RadioGroup
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // This method will be called when a radio button is selected or unselected
            // checkedId is the RadioButton ID that is now checked
            RadioButton checkedRadioButton = view.findViewById(checkedId);
            if (checkedRadioButton != null) {
                String text = checkedRadioButton.getText().toString();
                edAmount.setText(text);
            }
        });

        btnContinue.setOnClickListener(view -> {
            if(!edAmount.getText().toString().isEmpty()){
                startPaymentActivity();
            }else{
                showMessage("Amount required ..");
            }
        });

    }

    private void startPaymentActivity(){
        startActivity(new Intent(requireContext(), PaymentActivity.class).putExtra("amount",edAmount.getText().toString()));
    }

    private void showMessage(String message){
        Toast.makeText(requireContext(), message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

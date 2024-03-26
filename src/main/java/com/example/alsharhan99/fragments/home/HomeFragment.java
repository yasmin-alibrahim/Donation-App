package com.example.alsharhan99.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alsharhan99.R;
import com.example.alsharhan99.activities.details.DetailsActivity;
import com.example.alsharhan99.fragments.donation.DonationpriceFragment;

public class HomeFragment extends Fragment {


    private Button btnDonate;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home, container, false);
        // You can perform any additional setup or UI initialization here
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        handleClick();
    }

    private void findViews(){
        btnDonate = view.findViewById(R.id.button3);
    }

    private void handleClick(){
        btnDonate.setOnClickListener(view -> {
            startAmountActivity();
        });
    }

    private void startAmountActivity(){
        startActivity(new Intent(requireContext(), DetailsActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
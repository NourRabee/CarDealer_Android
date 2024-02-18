package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DealerActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "db", null, 1);
        HashMap<String, String> dealers = dataBaseHelper.getAdminSuperAdminUsers();

        AutoCompleteTextView carDealers = (AutoCompleteTextView) findViewById(R.id.dealerName);
        Button next = (Button) findViewById(R.id.next);

        final ArrayAdapter<String> dealerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);

        ArrayList<String> dealersNames = new ArrayList<>(dealers.values());
        dealersNames.add(0, "All");
        dealerAdapter.addAll(dealersNames);
        carDealers.setAdapter(dealerAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (carDealers.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Select Dealer", Toast.LENGTH_SHORT).show();
                } else {
                    SelectedDealer.getInstance().dealer = getEmailFromFullName(dealers, carDealers.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private String getEmailFromFullName(HashMap<String, String> userMap, String fullName) {
        for (Map.Entry<String, String> entry : userMap.entrySet()) {
            if (entry.getValue().equals(fullName)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
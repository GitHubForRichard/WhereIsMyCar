package com.example.richa.buttonclickapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    private Spinner garageSpinner;
    private Spinner searchSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeUI();
    }

    private void initializeUI() {
        garageSpinner = findViewById(R.id.spinner_garage);
        searchSpinner = findViewById(R.id.spinner_search);

        //Initializing string array for garage spinner
        String[] garages = new String[]{
                "South Garage",
                "North Garage",
                "West Garage"
        };

        //Initializing string array for search spinner
        String[] searchOption = new String[]{
                "Search By Car Model",
                "Search By Plate"
        };

        //Initializing ArrayAdapter for garages
        ArrayAdapter<String> mySpinnerAdpt =
                new ArrayAdapter<>(this, R.layout.spinner_layout, garages);
        garageSpinner.setAdapter(mySpinnerAdpt);

        //Initializing ArrayAdapter for search option
        ArrayAdapter<String> mySpinnerAdpt2 =
                new ArrayAdapter<>(this, R.layout.spinner_layout, searchOption);
        searchSpinner.setAdapter(mySpinnerAdpt2);
    }
}
package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner garageSpinner;
    private Spinner searchSpinner;
    private Button searchButton;
    private Button uploadImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeUI();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.button_search) {
            startActivity(new Intent(this, SearchByLicenseActivity.class));
        }
        else if(i == R.id.button_upload_image){
            startActivity(new Intent(this, UploadLicensePlateActivity.class));
        }
    }
    private void initializeUI() {
        garageSpinner = findViewById(R.id.spinner_garage);
        searchSpinner = findViewById(R.id.spinner_search);
        searchButton = findViewById(R.id.button_search);
        uploadImageButton = findViewById(R.id.button_upload_image);
        searchButton.setOnClickListener(this);
        uploadImageButton.setOnClickListener(this);

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
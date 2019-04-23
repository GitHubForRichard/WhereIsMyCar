package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements
        View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private Spinner garageSpinner;
    private Spinner searchSpinner;
    private ImageButton buttonAccount;
    private ImageButton buttonSearch;
    private ImageButton buttonMaps;
    private Button buttonSubmit;
    private Class activityToOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeUI();
    }

    private void initializeUI() {
        garageSpinner = findViewById(R.id.spinner_garage);
        searchSpinner = findViewById(R.id.spinner_search);

        buttonAccount = findViewById(R.id.imgButton_account);
        buttonAccount.setImageResource(R.drawable.account_icon);

        buttonSearch = findViewById(R.id.imgButton_search);
        buttonSearch.setImageResource(R.drawable.search_icon);

        buttonMaps = findViewById(R.id.imgButton_maps);
        buttonMaps.setImageResource(R.drawable.google_icon);

        buttonAccount.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonMaps.setOnClickListener(this);

        // Setup submit button to launch different activity
        buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);

////////////////////////////////////////////////////////////////////////////////////////////////////

        // Link different activity based on spinner's content
        searchSpinner.setOnItemSelectedListener(this);

        // Spinner Dropdown elements
        List<String> garage_categories = new ArrayList<String>();
        garage_categories.add("Which garage did you park?");
        garage_categories.add("South Garage");
        garage_categories.add("North Garage");
        garage_categories.add("West Garage");

        List<String> method_categories = new ArrayList<String>();
        method_categories.add("How would you like to search?");
        method_categories.add("Search By Plate Number");
        method_categories.add("Search By Car Model");

        // Create spinner's adapter
        ArrayAdapter<String> garageAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, garage_categories);
        ArrayAdapter<String> methodAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, method_categories);
        // Dropdown layout style - list view with radio button
        garageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Attaching adapter to spinner
        garageSpinner.setAdapter(garageAdapter);
        searchSpinner.setAdapter(methodAdapter);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        // Account page button
        if (i == R.id.imgButton_account) {
            // Checking if there is a user logging in already
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                finish();
                startActivity(new Intent(this, AccountActivity.class));
            } else {
                startActivity(new Intent(this, ProfileActivity.class));
            }
        }
        // Search button
        else if (i == R.id.imgButton_search) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        }
        // Google Maps button
        else if (i == R.id.imgButton_maps) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }
        // Submit button
        else if (i == R.id.button_submit) {
            if (activityToOpen != null) {
                startActivity(new Intent(v.getContext(), activityToOpen));
            }
        }
    }

    // Set up different activity based on spinner's selection: plate or car model
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item (Toast)
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();

        if (item.equals("Search By Plate Number")) {
            activityToOpen = GetPlateNumberActivity.class;
        } else if (item.equals("Search By Car Model")) {
            activityToOpen = GetCarModelActivity.class;
        } else
            activityToOpen = null;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        activityToOpen = null;
    }

}

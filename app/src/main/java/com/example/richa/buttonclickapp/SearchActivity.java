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
    private Class activityToOpen;

    private Button btnSubmit;

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

//setup submit button to launch different activity
        btnSubmit = findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityToOpen != null) {
                    Intent intent = new Intent(view.getContext(), activityToOpen);
                    startActivity(intent);
                }
            }
        });


//////////////////////////////////////////////////////////////////////////////////
//linkdifferentactivitybasedonspinnercontent
        searchSpinner.setOnItemSelectedListener(this);

//SpinnerDropdownelements
        List<String> garage_categories = new ArrayList<String>();
        garage_categories.add("Which garage did you park?");
        garage_categories.add("South Garage");
        garage_categories.add("North Garage");
        garage_categories.add("West Garage");

        List<String> method_categories = new ArrayList<String>();
        method_categories.add("How would you like to search? ");
        method_categories.add("Search By Plate Number");
        method_categories.add("Search By Car Model");


//Creatingadapterforspinner
        ArrayAdapter<String> garageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, garage_categories);
        ArrayAdapter<String> methodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, method_categories);
//Dropdownlayoutstyle-listviewwithradiobutton
        garageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//attachingdataadaptertospinner
        garageSpinner.setAdapter(garageAdapter);
        searchSpinner.setAdapter(methodAdapter);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

//Buttonforaccountpage
        if (i == R.id.imgButton_account) {
            Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
            startActivity(intent);
        }
//Buttonforsearchimplementation
        else if (i == R.id.imgButton_search) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }
//ButtonforGoogleMaps
        else if (i == R.id.imgButton_maps) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }

    }

    //set up different activity based on spinner content:plate or car model
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//Onselectingaspinneritem
        String item = parent.getItemAtPosition(position).toString();

//Showing selected spinneritem
        Toast.makeText(parent.getContext(), "Selected:" + item, Toast.LENGTH_LONG).show();

        if (item.equals("Search By Plate Number")) {
            activityToOpen = GetPlateNumberActivity.class;
        } else if (item.equals("Search By Car Model")) {
            activityToOpen = GetCarModelActivity.class;
        } else
            activityToOpen = null;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonAccount;
    private ImageButton buttonSearch;
    private ImageButton buttonMaps;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize UI components, and its functionality
        initializeUI();
    }

    private void initializeUI() {
        buttonAccount = findViewById(R.id.imgButton_account);
        buttonSearch = findViewById(R.id.imgButton_search);
        buttonMaps = findViewById(R.id.imgButton_maps);
        buttonAccount.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonMaps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        // Button for account page
        if (i == R.id.imgButton_account) {

            // checking if there is a user logging in already
            if (firebaseAuth.getCurrentUser() == null) {
                finish();
                startActivity(new Intent(this, AccountActivity.class));
            }
            else {
                startActivity(new Intent(this, ProfileActivity.class));
            }

        }
        // Button for search implementation
        else if (i == R.id.imgButton_search) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }
        /**
         * Todo: Implement Google Maps functionality (Assign: Jackbui96)
         */
        // Button for Google Maps
        else if (i == R.id.imgButton_maps) {

        }
    }
}
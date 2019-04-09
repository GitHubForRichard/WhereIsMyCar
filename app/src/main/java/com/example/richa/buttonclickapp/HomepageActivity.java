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
    FirebaseAuth.AuthStateListener mAuthListener;
    //checking if user is logged in
    boolean loggedIn = FirebaseAuth.getInstance().getCurrentUser() != null;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Initialize UI components, and its functionality
        initializeUI();

    }

    private void initializeUI() {
        buttonAccount = findViewById(R.id.imgButton_account);
        buttonAccount.setImageResource(R.drawable.account_icon);

        buttonSearch = findViewById(R.id.imgButton_search);
        buttonSearch.setImageResource(R.drawable.search_icon);

        buttonMaps = findViewById(R.id.imgButton_maps);
        buttonMaps.setImageResource(R.drawable.google_icon);
        buttonAccount.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonMaps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        // account buttom displays profile page if user is logged in
        if (loggedIn == true) {
            if (i == R.id.imgButton_account) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
            // Button for search implementation
            else if (i == R.id.imgButton_search) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
            // Button for Google Maps
            else if (i == R.id.imgButton_maps) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        } else {//account button display sign in/sign up page for guest user
            if (i == R.id.imgButton_account) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
            // Button for search implementation
            else if (i == R.id.imgButton_search) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
            // Button for Google Maps
            else if (i == R.id.imgButton_maps) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        }
    }

}
package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class HomepageActivity extends AppCompatActivity implements
        View.OnClickListener {

    private ImageView backgroundImage;
    private ImageButton buttonAccount;
    private ImageButton buttonSearch;
    private ImageButton buttonMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Initialize UI components, and its functionality
        initializeUI();
    }

    private void initializeUI() {

        Resources res = getResources();
        Drawable sjsuPortrait = res.getDrawable(R.drawable.sjsu_background);
        Drawable sjsuLandscape = res.getDrawable(R.drawable.sjsu_background_landscape);
        backgroundImage = findViewById(R.id.image_background);

        buttonAccount = findViewById(R.id.imgButton_account);
        buttonAccount.setImageResource(R.drawable.account_icon);

        buttonSearch = findViewById(R.id.imgButton_search);
        buttonSearch.setImageResource(R.drawable.search_icon);

        buttonMaps = findViewById(R.id.imgButton_maps);
        buttonMaps.setImageResource(R.drawable.google_icon);
        buttonAccount.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonMaps.setOnClickListener(this);

        /**
         * Change background image based on screen's rotation.
         */
        WindowManager window = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        int num = display.getRotation();
        if (num == 0) {
            backgroundImage.setImageDrawable(sjsuPortrait);
        } else if (num == 1 || num == 3) {
            backgroundImage.setImageDrawable(sjsuLandscape);
        } else {
            backgroundImage.setImageDrawable(sjsuPortrait);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        // Account button will jump to the profile page if user is logged in
        if (i == R.id.imgButton_account) {

            // Checking if there is a user logging in already
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                finish();
                startActivity(new Intent(this, AccountActivity.class));
            } else {
                startActivity(new Intent(this, ProfileActivity.class));
            }

        }
        // Button for search implementation
        else if (i == R.id.imgButton_search) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        }
        // Button for Google Maps
        else if (i == R.id.imgButton_maps) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }
    }
}
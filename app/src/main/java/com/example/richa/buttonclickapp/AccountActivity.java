package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private Button buttonSignUp;
    private Button buttonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize UI components, and its functionality
        initializeUI();

    }

    private void initializeUI() {
        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignUp = findViewById(R.id.button_sign_up);
        buttonHelp = findViewById(R.id.button_help);
        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
        buttonHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        //Button for sign in
        if (i == R.id.button_sign_in) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        // Button for sign up
        else if (i == R.id.button_sign_up) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }

        // Button for help
        else if (i == R.id.button_help) {
            startActivity(new Intent(getApplicationContext(), HelpActivity.class));
        }
    }
}

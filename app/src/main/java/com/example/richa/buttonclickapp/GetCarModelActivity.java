package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GetCarModelActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_car_model);
        initializeUI();
    }

    private void initializeUI() {
        btnSubmit = findViewById(R.id.button_submit);

        btnSubmit.setOnClickListener(this);
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_submit) {
            Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
            startActivity(intent);
        }
    }
}

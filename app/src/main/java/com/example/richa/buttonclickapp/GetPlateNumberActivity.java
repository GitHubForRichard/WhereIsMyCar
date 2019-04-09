package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetPlateNumberActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSubmit;
    private Button btnClear;
    private EditText etPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_plate_number);

        initializeUI();
    }

    private void initializeUI() {
        btnSubmit = findViewById(R.id.button_submit);
        btnClear = findViewById(R.id.button_clear);
        etPlate = findViewById(R.id.edit_plate);

        btnSubmit.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_submit) {

            Intent intent = new Intent(getApplicationContext(), DisplayResultActivity.class);
            startActivity(intent);
        }
        if (i == R.id.button_clear) {
            etPlate.setText("");
        }
    }
}
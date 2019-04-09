package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateCarInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etLicensePlate;
    private EditText etBrand;
    private EditText etYear;
    private EditText etColor;
    private Button btnSubmit;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    //
    private Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_info);

        initializeUI();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    public void saveCarInfo(EditText licensePlate, EditText model, EditText year, EditText color) {
        String licensePlateStr = licensePlate.getText().toString().trim();
        String brandStr = model.getText().toString().trim().toLowerCase();
        String colorStr = color.getText().toString().trim().toLowerCase();
        int yearInt = Integer.parseInt(year.getText().toString());

        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        // Update users' car information
        if (licensePlateStr.length() > 0) {
            databaseReference.
                    child("users").
                    child(currUser.getUid()).
                    child("licensePlate").
                    setValue(licensePlateStr);
        }

        if (brandStr.length() > 0) {
            databaseReference.
                    child("users").
                    child(currUser.getUid()).
                    child("brand").
                    setValue(brandStr);
        }

        if (year.getText().toString().length() > 0 && yearInt > 0) {
            databaseReference.
                    child("users").
                    child(currUser.getUid()).
                    child("year").
                    setValue(yearInt);
        }

        if (colorStr.length() > 0) {
            databaseReference.
                    child("users").
                    child(currUser.getUid()).
                    child("color").
                    setValue(colorStr);
        }

        Log.d("TAG", "Successfully added Car Info............................");
    }

    private void initializeUI() {
        etLicensePlate = findViewById(R.id.edit_license_plate);
        etBrand = findViewById(R.id.edit_brand);
        etYear = findViewById(R.id.edit_Year);
        etColor = findViewById(R.id.edit_color);
        btnSubmit = findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(this);
        btnCancel = findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_submit) {
            saveCarInfo(etLicensePlate, etBrand, etYear, etColor);
            Toast.makeText(getApplicationContext(), "Saved Car Info Successfully", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        }
        // use cancel button to go back to main page
        if (i == R.id.button_cancel) {
            Intent intent = new Intent(this, HomepageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


}

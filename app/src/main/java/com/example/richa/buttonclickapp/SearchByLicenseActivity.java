package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.richa.buttonclickapp.Object.LicensePlateInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchByLicenseActivity extends AppCompatActivity {

    private EditText etLicensePlate;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_by_license);

        //initializeUI();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    // Go through the database to find the car image that matches users' input license plate
    public void searchCar(View v) {
        // Get the license plate input
        final String searchPlate = etLicensePlate.getText().toString().trim();


        // Go through the list and find the cars license plate that contain the searchPlate
        databaseReference.child("cars")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String eachPlateText;

                        ArrayList<LicensePlateInfo> licensePlateList = new ArrayList<>();

                        // Go through each child under "cars" parents
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if (licensePlateList.size() > 3) {
                                break;
                            }

                            LicensePlateInfo licensePlateInfo = snapshot.getValue(LicensePlateInfo.class);
                            eachPlateText = licensePlateInfo.getLicensePlateText();
                            System.out.println(eachPlateText);
                            if (eachPlateText.contains(searchPlate)) {
                                Log.d("TAG", "The URL for matching Text is: " + licensePlateInfo.getPhotoUrl());
                                licensePlateList.add(licensePlateInfo);
                            }
                        }
                        Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST", licensePlateList);
                        intent.putExtra("BUNDLE", args);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    //private void initializeUI() {
    //    etLicensePlate = findViewById(R.id.edit_license_plate);
    //}
}

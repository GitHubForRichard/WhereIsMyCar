package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.richa.buttonclickapp.Object.LicensePlateInfo;
import com.example.richa.buttonclickapp.Object.SearchHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class GetPlateNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPlate;
    private Button btnFindMyCar;
    private Button btnSearchHistory;
    private Button btnSubmit;
    private Button btnClear;
    private Button btnUploadImage;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_plate_number);

        initializeUI();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initializeUI() {

        etPlate = findViewById(R.id.edit_plate);
        btnFindMyCar = findViewById(R.id.button_find_my_car);
        btnSearchHistory = findViewById(R.id.button_search_history);
        btnSubmit = findViewById(R.id.button_submit);
        btnClear = findViewById(R.id.button_clear);
        btnUploadImage = findViewById(R.id.button_upload_img);

        btnFindMyCar.setOnClickListener(this);
        btnSearchHistory.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            btnFindMyCar.setVisibility(View.GONE);
            btnSearchHistory.setVisibility(View.GONE);
        } else {
            btnFindMyCar.setVisibility(View.VISIBLE);
            btnSearchHistory.setVisibility(View.VISIBLE);
        }

        etPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 7) {
                    Toast.makeText(getApplicationContext(), "Only 7 characters are allowed to be entered", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    private void findMyCar() {
        if (firebaseAuth.getUid() == null) {
            startActivity(new Intent(this, AccountActivity.class));
            Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
            return;
        } else {
            String userId = firebaseAuth.getUid();
            databaseReference.child("users").child(userId).child("licensePlate").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
                    String licensePlate = snapshot.getValue().toString();
                    etPlate.setText(licensePlate);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    // Go through the database to find the car image that matches users' input license plate
    private void searchCar() {
        // Get the license plate input
        final String searchPlate = etPlate.getText().toString().trim();

        // save user's search result to search collection on Firebase
        if (firebaseAuth.getUid() != null) {
            String userId = firebaseAuth.getUid();
            SearchHistory searchHistory = new SearchHistory(userId, searchPlate);
            databaseReference.child("searches").child(UUID.randomUUID().toString()).setValue(searchHistory);
        }

        if (searchPlate.length() < 5) {
            Toast.makeText(this, "We needs at least five characters to search", Toast.LENGTH_LONG).show();
            return;
        }


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

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.button_find_my_car) {
            findMyCar();
        } else if (i == R.id.button_search_history) {
            startActivity(new Intent(getApplicationContext(), SearchHistoryActivity.class));
        } else if (i == R.id.button_submit) {
            searchCar();
        } else if (i == R.id.button_clear) {
            etPlate.setText("");
        } else if (i == R.id.button_upload_img) {
            startActivity(new Intent(getApplicationContext(), UploadLicensePlateActivity.class));
        }
    }
}
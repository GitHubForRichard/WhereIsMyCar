package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textLicensePlate;
    private TextView textBrand;
    private TextView textYear;
    private TextView textColor;

    private Button buttonLogout;
    private Button buttonUpdate;//CarInfor
    private DatabaseReference databaseReference;

    //
    private Button buttonSearchHistory;
    private Button buttonUpdateAccount;
    private Button buttonUpdateCarInfor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initializeUI();

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();
/*
        databaseReference.child("users").child(userId).child("licensePlate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.getValue() == null) {
                    textLicensePlate.setText("License Plate: to be Updated");
                }
                else {
                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
                    String licensePlate = snapshot.getValue().toString();
                    textLicensePlate.setText("License Plate: " + licensePlate);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        databaseReference.child("users").child(userId).child("brand").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    textBrand.setText("Brand: to be Updated");
                }
                else {
                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
                    String brand = snapshot.getValue().toString();
                    textBrand.setText("Brand: " + brand);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        databaseReference.child("users").child(userId).child("year").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    textBrand.setText("Year: to be Updated");
                }
                else {
                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
                    String year = snapshot.getValue().toString();
                    textYear.setText("Year: " + year);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        databaseReference.child("users").child(userId).child("color").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    textColor.setText("Color: to be Updated");
                }
                else {
                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
                    String color = snapshot.getValue().toString();
                    textColor.setText("Color: " + color);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
*/
    }
    private void initializeUI() {
        textLicensePlate = findViewById(R.id.text_license_plate);
        textBrand = findViewById(R.id.text_brand);
        textYear = findViewById(R.id.text_year);
        textColor = findViewById(R.id.text_color);
        buttonLogout = findViewById(R.id.button_log_out);
        buttonUpdateCarInfor = findViewById(R.id.button_CarInfor);
        buttonLogout.setOnClickListener(this);
        buttonUpdateCarInfor.setOnClickListener(this);
        //
        buttonUpdateAccount = findViewById(R.id.button_updateAccount);
        buttonUpdateAccount.setOnClickListener(this);
        buttonSearchHistory = findViewById(R.id.button_searchHistory);
        buttonSearchHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_log_out) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else if (i == R.id.button_CarInfor) {

            startActivity(new Intent(getApplicationContext(), UpdateCarInfoActivity.class));
        }
        //
        else if (i == R.id.button_updateAccount) {
            Intent intent = new Intent(getApplicationContext(), UserAccountActivity.class);
            startActivity(intent);
        } else if (i == R.id.button_searchHistory) {
            Intent intent = new Intent(getApplicationContext(), SearchHistoryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    //when click back arrow, go to main page instead previous page
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, HomepageActivity.class);
        startActivity(intent);
        finish();
    }
    }

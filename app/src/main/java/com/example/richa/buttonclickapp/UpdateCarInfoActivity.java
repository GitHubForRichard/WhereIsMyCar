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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateCarInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etLicensePlate;
//    private EditText etBrand;
//    private EditText etYear;
//    private EditText etColor;
    private Button btnSubmit;
    private Button btnCancel;

    private String licensePlateInitStr;
//    private String brandInitStr;
//    private String colorInitStr;
//    private int yearInitStr;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_info);

        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        initializeUI();


        setInitLicensePlate(userId);
//        setInitBrand(userId);
//        setInitYear(userId);
//        setInitColor(userId);
    }


    public void saveCarInfo(EditText licensePlate) {
        String licensePlateStr = licensePlate.getText().toString().trim();
//        String brandStr = model.getText().toString().trim().toLowerCase();
//        String colorStr = color.getText().toString().trim().toLowerCase();
//        int yearInt = Integer.parseInt(year.getText().toString());

        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        // Update users' car information
        if (licensePlateStr.length() > 0) {
            databaseReference.
                    child("users").
                    child(currUser.getUid()).
                    child("licensePlate").
                    setValue(licensePlateStr);
        }

//        if (brandStr.length() > 0) {
//            databaseReference.
//                    child("users").
//                    child(currUser.getUid()).
//                    child("brand").
//                    setValue(brandStr);
//        }
//
//        if (year.getText().toString().length() > 0 && yearInt > 0) {
//            databaseReference.
//                    child("users").
//                    child(currUser.getUid()).
//                    child("year").
//                    setValue(yearInt);
//        }
//
//        if (colorStr.length() > 0) {
//            databaseReference.
//                    child("users").
//                    child(currUser.getUid()).
//                    child("color").
//                    setValue(colorStr);
//        }

        Log.d("TAG", "Successfully added Car Info............................");
    }

    private void initializeUI() {
        etLicensePlate = findViewById(R.id.edit_license_plate);
//        etBrand = findViewById(R.id.edit_brand);
//        etYear = findViewById(R.id.edit_Year);
//        etColor = findViewById(R.id.edit_color);

        etLicensePlate.setText(licensePlateInitStr);


        btnSubmit = findViewById(R.id.button_submit);
        btnCancel = findViewById(R.id.button_cancel);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_submit) {
            saveCarInfo(etLicensePlate);
            Toast.makeText(getApplicationContext(), "Saved Car Info Successfully", Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, HomepageActivity.class));
        }

        else if(i == R.id.button_cancel){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }

    public void setInitLicensePlate(String userId)
    {
        databaseReference.child("users").child(userId).child("licensePlate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
                    String licensePlate = snapshot.getValue().toString();
                    etLicensePlate.setText(licensePlate);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

//    public void setInitBrand(String userId)
//    {
//        databaseReference.child("users").child(userId).child("brand").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if(snapshot.getValue() != null) {
//                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
//                    String brand = snapshot.getValue().toString();
//                    etBrand.setText(brand);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
//
//    public void setInitColor(String userId)
//    {
//        databaseReference.child("users").child(userId).child("color").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if(snapshot.getValue() != null) {
//                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
//                    String color = snapshot.getValue().toString();
//                    etColor.setText(color);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
//
//    public void setInitYear(String userId)
//    {
//        databaseReference.child("users").child(userId).child("year").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if(snapshot.getValue() != null) {
//                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
//                    String year = snapshot.getValue().toString();
//                    etYear.setText(year);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
}

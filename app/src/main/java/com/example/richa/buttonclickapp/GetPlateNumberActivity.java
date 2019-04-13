package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.richa.buttonclickapp.Object.SearchHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class GetPlateNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFindMyCar;
    private Button btnSearchHistory;
    private Button btnSubmit;
    private Button btnClear;
    private EditText etPlate;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_plate_number);

        initializeUI();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initializeUI() {
        btnFindMyCar = findViewById(R.id.button_find_my_car);
        btnSearchHistory = findViewById(R.id.button_search_history);
        btnSubmit = findViewById(R.id.button_submit);
        btnClear = findViewById(R.id.button_clear);
        etPlate = findViewById(R.id.edit_plate);

        btnFindMyCar.setOnClickListener(this);
        btnSearchHistory.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnClear.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.button_find_my_car) {
            findMyCar();
        } else if (i == R.id.button_search_history) {
            startActivity(new Intent(getApplicationContext(), SearchHistory.class));
        } else if (i == R.id.button_submit) {
            startActivity(new Intent(getApplicationContext(), DisplayResultActivity.class));
        } else if (i == R.id.button_clear) {
            etPlate.setText("");
        }
    }
}
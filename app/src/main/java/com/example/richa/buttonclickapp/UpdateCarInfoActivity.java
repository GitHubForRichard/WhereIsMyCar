package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateCarInfoActivity extends AppCompatActivity {



    private EditText editTextLicensePlate;
    private EditText editTextModel;
    private EditText editTextYear;
    private EditText editTextColor;
    private Button buttonSubmit;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_info);

        editTextLicensePlate = (EditText) findViewById(R.id.editTextLicensePlate);
        editTextModel = (EditText) findViewById(R.id.editTextModel);
        editTextYear = (EditText) findViewById(R.id.editTextYear);
        editTextColor = (EditText) findViewById(R.id.editTextColor);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        View.OnClickListener saveCarListener = new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if(v == buttonSubmit)
                {
                    saveCarInfo(editTextLicensePlate, editTextModel, editTextYear, editTextColor);
                    Toast.makeText(getApplicationContext(),"Saved Car Info Successfully", Toast.LENGTH_LONG);
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                }
            }
        };

        buttonSubmit.setOnClickListener(saveCarListener);

    }


    public void saveCarInfo(EditText licensePlate, EditText model, EditText year, EditText color)
    {
        String licensePlateStr = licensePlate.getText().toString().trim();
        String modelStr = model.getText().toString().trim();
        String colorStr = color.getText().toString().trim();

        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        // Update users' car information
        if(licensePlateStr.length() > 0)
        {
            databaseReference.child("users").child(currUser.getUid()).child("licensePlate").setValue(licensePlateStr);
        }

        if(modelStr.length() > 0)
        {
            databaseReference.child("users").child(currUser.getUid()).child("model").setValue(modelStr);
        }

        if(year.getText().toString().trim().length() > 0)
        {
            int yearInt = Integer.parseInt(year.getText().toString());
            databaseReference.child("users").child(currUser.getUid()).child("year").setValue(yearInt);
        }

        if(colorStr.length() > 0)
        {
            databaseReference.child("users").child(currUser.getUid()).child("color").setValue(licensePlateStr);
        }

        Log.d("TAG", "Successfully added Car Info............................");
    }
}

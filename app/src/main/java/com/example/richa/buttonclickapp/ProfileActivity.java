package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    private TextView textViewLicensePlate;
    private Button buttonLogout;
    private Button buttonUpdate;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewLicensePlate = (TextView) findViewById(R.id.textViewLicensePlate);
        buttonLogout = (Button) findViewById(R.id.Logout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdateProfile);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        databaseReference.child("users").child(userId).child("licensePlate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("---------------" + snapshot.getValue() + "-------------------");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        View.OnClickListener thisOnClickListener = new View.OnClickListener(){
          public void onClick(View v)
          {
              if(v == buttonLogout)
              {
                  firebaseAuth.signOut();
                  finish();
                  startActivity(new Intent(getApplicationContext(), LoginActivity.class));
              }

              if(v == buttonUpdate)
              {
                  finish();
                  startActivity(new Intent(getApplicationContext(), UpdateCarInfoActivity.class));
              }
          }
        };

        buttonLogout.setOnClickListener(thisOnClickListener);
        buttonUpdate.setOnClickListener(thisOnClickListener);

    }
}

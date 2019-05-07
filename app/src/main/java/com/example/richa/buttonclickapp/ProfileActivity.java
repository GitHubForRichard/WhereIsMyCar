package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.richa.buttonclickapp.Object.UserAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textEmail;
    private TextView textLicensePlate;
    private TextView textFirstname;
    private TextView textLastname;

    private Button buttonUpdateUserInfo;
    private Button buttonLogout;

//    private ImageView imageViewUpdateUserInfo;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        initializeUI();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userId = user.getUid();

        databaseReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getValue() == null) {
                    textLicensePlate.setText("License Plate: to be Updated");
                }
                else {
                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
                    UserAccount user = snapshot.getValue(UserAccount.class);
                    textEmail.setText(user.getEmail());
                    textLicensePlate.setText(user.getLicensePlate());
                    textFirstname.setText(user.getFirstname());
                    textLastname.setText(user.getLastname());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void initializeUI() {

        textEmail = findViewById(R.id.text_email);
        textLicensePlate = findViewById(R.id.text_license_plate);
        textFirstname = findViewById(R.id.text_first_name);
        textLastname = findViewById(R.id.text_last_name);

        buttonUpdateUserInfo = findViewById(R.id.button_update_user_info);
//        imageViewUpdateUserInfo = findViewById(R.id.imageView_update_user_info);
        buttonLogout = findViewById(R.id.button_log_out);

        buttonUpdateUserInfo.setOnClickListener(this);
//        imageViewUpdateUserInfo.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        /*
        // Users logged in with Gmail account should not be able to change their account info
        for (UserInfo user : firebaseAuth.getCurrentUser().getProviderData()) {
            if (user.getProviderId().equals("google.com")) {
                buttonUpdateCarInfo.setVisibility(View.GONE);
            }
        }
        */
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_log_out) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        } else if (i == R.id.button_update_user_info) {
            finish();
            startActivity(new Intent(getApplicationContext(), UpdateUserInfoActivity.class));
        }
//        else if (i == R.id.imageView_update_user_info) {
//            finish();
//            startActivity(new Intent(getApplicationContext(), UpdateUserInfoActivity.class));
//        }
    }
}
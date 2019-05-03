package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editFirstname;
    private EditText editLastname;
    private EditText editLicensePlate;
    private Button buttonEditName;
    private Button buttonEditLicensePlate;
    private Button buttonResetPassword;
    private Button buttonSkip;

    private TextView textViewForgotPassword;
    private View forgotPasswordHorizontalLine;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference();

        initializeUI();
    }

    private void editName() {
        final DatabaseReference nameRef = ref.child("users").child(uid);
        if (editLastname.getText().toString().length() != 0) {
            nameRef.child("lastname").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    nameRef.child("lastname").setValue(editLastname.getText().toString());
                    Toast.makeText(UpdateUserInfoActivity.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (editFirstname.getText().toString().length() != 0) {
            nameRef.child("firstname").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    nameRef.child("firstname").setValue(editFirstname.getText().toString());
                    Toast.makeText(UpdateUserInfoActivity.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void editLicensePlate() {
        if (editLicensePlate.getText().toString().length() != 0) {
            ref.child("users").child(uid).child("licensePlate").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ref.child("users").child(uid).child("licensePlate").setValue(editLicensePlate.getText().toString());
                    Toast.makeText(UpdateUserInfoActivity.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

//    private void resetPassword() {
//
//    }

    private void initializeUI() {
        editLastname = findViewById(R.id.edit_lastname_input);
        editFirstname = findViewById(R.id.edit_firstname_input);
        editLicensePlate = findViewById(R.id.edit_license_plate_input);
        buttonEditName = findViewById(R.id.button_edit_name);
        buttonEditLicensePlate = findViewById(R.id.button_edit_license_plate);
        buttonResetPassword = findViewById(R.id.button_reset_password);
        buttonSkip = findViewById(R.id.button_skip);
        textViewForgotPassword = findViewById(R.id.text_forgot_password);
        forgotPasswordHorizontalLine = findViewById(R.id.view3);

        // user logged in from Google cannot update password in our platform
        for (UserInfo user : firebaseAuth.getCurrentUser().getProviderData()) {
            if (user.getProviderId().equals("google.com")) {
                buttonResetPassword.setVisibility(View.GONE);
                textViewForgotPassword.setVisibility(View.GONE);
                forgotPasswordHorizontalLine.setVisibility(View.GONE);
            }
        }

        buttonEditName.setOnClickListener(this);
        buttonEditLicensePlate.setOnClickListener(this);
        buttonResetPassword.setOnClickListener(this);
        buttonSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_edit_name) editName();
        if (i == R.id.button_edit_license_plate) editLicensePlate();
        if (i == R.id.button_reset_password) {
            startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
        }
        if (i == R.id.button_skip) {
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        }
    }
}

package com.example.richa.buttonclickapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richa.buttonclickapp.Object.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmit;
    private Button btnCancel;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    private TextView textViewPasswordNotMatch;
    private TextView textViewLoginReminder;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUI();
        initializeFirebase();
    }

    private void initializeUI() {
        progressDialog = new ProgressDialog(this);
        btnSubmit = findViewById(R.id.button_submit);
        btnCancel = findViewById(R.id.button_cancel);
        etEmail = findViewById(R.id.edit_email);
        etPassword = findViewById(R.id.edit_password);
        etConfirmPassword = findViewById(R.id.edit_confirm_password);
        textViewPasswordNotMatch = findViewById(R.id.textView_password_not_match);
        textViewLoginReminder = findViewById(R.id.textView_login_reminder);

        textViewPasswordNotMatch.setVisibility(View.GONE);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        textViewLoginReminder.setOnClickListener(this);

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                System.out.println("Checking Password ad confirmed password .................");

                if (password.length() > 0) {
                    if (!confirmPassword.equals(password)) {
                        // give an error that password and confirm password not match
                        System.out.println("Passwords do not match!");
                        textViewPasswordNotMatch.setVisibility(View.VISIBLE);
                    } else {
                        textViewPasswordNotMatch.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    private void initializeFirebase() {

        // Initialize Firebase auth and object
        firebaseAuth = FirebaseAuth.getInstance();

        // Check if there is user currently logging in
        if (firebaseAuth.getCurrentUser() != null) {
            // if the user has already logged in'
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            // If the email field is empty
            Toast.makeText(this,"Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            // If the password field is empty
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 5) {
            Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // If validation is ok
        // We will show a progress bar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If task is completed
                        if (task.isSuccessful()) {

                            // user is successfully registered and logged in
                            // we will start the profile activity here
                            // right now lets display a toast only

                            // save user info in Firebase database
                            String email = etEmail.getText().toString().trim();
                            UserAccount userInfo = new UserAccount(email, "", "", "");
                            FirebaseUser currUser = firebaseAuth.getCurrentUser();
                            databaseReference.child("users").child(currUser.getUid()).setValue(userInfo);

                            Log.d("TAG", "Successfully added User............................");

                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), UpdateUserInfoActivity.class));
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Could not Register, Please Try Again...", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        // Button for register user
        if (i == R.id.button_submit) {
            registerUser();
        } else if (i == R.id.button_cancel) {
            finish();
            startActivity(new Intent(this, HomepageActivity.class));
        } else if (i == R.id.textView_login_reminder) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}

package com.example.richa.buttonclickapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity{

    private Button buttonSubmit;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiailze firebase auth and object
        firebaseAuth = FirebaseAuth.getInstance();

        // check if there is user currently logging in
        if(firebaseAuth.getCurrentUser() != null)
        {
            // if the user has already logged in'
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();



        progressDialog = new ProgressDialog(this);

        buttonSubmit = findViewById(R.id.button_submit);
        editTextEmail = findViewById(R.id.edit_email);
        editTextPassword = findViewById(R.id.edit_password);

//        textViewLogin = (TextView) findViewById(R.id.Login);

        View.OnClickListener ouronClickListner = new View.OnClickListener() {
            public void onClick(View v)
            {
                if (v == buttonSubmit)
                {
                    registerUser();
                }

//                if(v == textViewLogin)
//                {
//                    // will open login activity here
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                }
            }
        };

        buttonSubmit.setOnClickListener(ouronClickListner);
//        textViewLogin.setOnClickListener(ouronClickListner);

    }

    public void registerUser()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            // email is empty
            Toast.makeText(this,"Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty((password)))
        {
            // password is empty
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        // if validation is ok
        // We will show a progress bar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // will be run after the task is completed
                if(task.isSuccessful())
                {
                    // user is successfully registered and logged in
                    // we will start the profile activity here
                    // right now lets display a toast only

                    // save user info in firebase database
                    String email = editTextEmail.getText().toString().trim();
                    UserInfo userInfo = new UserInfo(email, "", "", "", 1900);
                    FirebaseUser currUser = firebaseAuth.getCurrentUser();
                    databaseReference.child("users").child(currUser.getUid()).setValue(userInfo);

                    Log.d("TAG", "Successfully added User............................");

                    progressDialog.dismiss();
                    finish();
//                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    startActivity(new Intent(getApplicationContext(), UpdateCarInfoActivity.class));
//                    Toast.makeText(MainActivity.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Could not Register, Please Try Again...", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

}

package com.example.richa.buttonclickapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    private EditText etEmail;
    private Button btnUpdatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        intitalizeUI();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_send_email) {
            sendResetPasswordForm();
        }
    }

    public void intitalizeUI() {
        etEmail = findViewById(R.id.et_email);
        btnUpdatePassword = findViewById(R.id.button_send_email);
        progressDialog = new ProgressDialog(this);

        btnUpdatePassword.setOnClickListener(this);

    }

    public void sendResetPasswordForm() {

//            progressDialog.setMessage("Updating password...");
//            progressDialog.show();

        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        // get the password string from the textfield
        String emailStr = etEmail.getText().toString().trim();

        firebaseAuth.sendPasswordResetEmail(emailStr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                        }
                    }
                });


    }


}

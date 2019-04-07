package com.example.richa.buttonclickapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUpdateEmail;
    private EditText etUpdatePassword;
    private Button btnUpdateEmail;
    private Button btnUpdatePassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        intitalizeUI();


    }


    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if(id == R.id.button_update_email){

            // update email for user if the update email field is not empty
            if(!etUpdateEmail.getText().toString().trim().equals("")) {
                updateEmail();
            }
        }
        else if(id == R.id.button_update_password){
            updatePassword();
        }
    }

    public void intitalizeUI()
    {
        etUpdateEmail = findViewById(R.id.editText_update_email);
        etUpdatePassword = findViewById(R.id.editText_update_password);
        btnUpdateEmail = findViewById(R.id.button_update_email);
        btnUpdatePassword = findViewById(R.id.button_update_password);
        progressDialog = new ProgressDialog(this);

        btnUpdateEmail.setOnClickListener(this);
        btnUpdatePassword.setOnClickListener(this);
    }

    public void updateEmail()
    {
//        progressDialog.setMessage("Updateing email...");
//        progressDialog.show();

        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        // get the email string from the textfield
        String updateEmailstr = etUpdateEmail.getText().toString().trim();

        // update email fro current user in profile database side for storage purpose
        databaseReference.child("users").child(currUser.getUid()).child("email").setValue(updateEmailstr);

        // update email for current user in authentication side for logged in purpose
        currUser.updateEmail(updateEmailstr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        Log.d("TAG", "Doing the task.....................");
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "The email address has been updated!", Toast.LENGTH_LONG).show();
                            Log.d("TAG", "User email address updated.");
//                            progressDialog.dismiss();
                        }

                        else
                        {
                            Log.d("TAG", "Update Failed...");
                        }
                    }
                });
    }

    public void updatePassword()
    {
        progressDialog.setMessage("Updateing password...");
        progressDialog.show();

        FirebaseUser currUser = firebaseAuth.getCurrentUser();

        // get the password string from the textfield
        String updatePasswordstr = etUpdatePassword.getText().toString().trim();

        // update email for current user in authentication side for logged in purpose
        currUser.updatePassword(updatePasswordstr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "The password has been updated!", Toast.LENGTH_LONG).show();
                            Log.d("TAG", "User password updated.");
                            progressDialog.dismiss();
                        }
                    }
                });
    }

}

package com.example.richa.buttonclickapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class UserAccountActivity extends AppCompatActivity implements
        DialogName.DialogNameListener,
        DialogPassword.DialogPasswordListener,
        DialogMail.DialogMailListener,
        View.OnClickListener {

    private TextView tvLastnameInput;
    private TextView tvFirstnameInput;
    private TextView tvEmailInput;
    private TextView tvPasswordInput;
    private Button buttonEditName;
    private Button buttonEditEmail;
    private Button buttonEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        initializeUI();
    }

    private void initializeUI() {
        tvLastnameInput = findViewById(R.id.text_lastname_input);
        tvFirstnameInput = findViewById(R.id.text_firstname_input);
        tvEmailInput = findViewById(R.id.text_email_input);
        tvPasswordInput = findViewById(R.id.text_password_input);
        buttonEditName = findViewById(R.id.button_edit_name);
        buttonEditEmail = findViewById(R.id.button_edit_email);
        buttonEditPassword = findViewById(R.id.button_edit_password);
        buttonEditName.setOnClickListener(this);
        buttonEditEmail.setOnClickListener(this);
        buttonEditPassword.setOnClickListener(this);
    }

    @Override
    public void applyTexts(String lastname, String firstname) {
        tvLastnameInput.setText(lastname);
        tvFirstnameInput.setText(firstname);

    }

    @Override
    public void updatepassword(String password) {
        tvPasswordInput.setText(password);
    }

    @Override
    public void applyTexts(String emailaddress) {
        tvEmailInput.setText(emailaddress);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_edit_name) {
            DialogName nameDialog = new DialogName();
            nameDialog.show(getSupportFragmentManager(), "dialog name");
        } else if (i == R.id.button_edit_email) {
            DialogMail mailDialog = new DialogMail();
            mailDialog.show(getSupportFragmentManager(), "dialog mail");
        } else if (i == R.id.button_edit_password) {
            DialogPassword passwordDialog = new DialogPassword();
            passwordDialog.show(getSupportFragmentManager(), "dialog password");
        }
    }
}
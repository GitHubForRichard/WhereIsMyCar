package com.example.richa.buttonclickapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class UserAccountActivity extends AppCompatActivity implements DialogName.DialogNameListener, DialogPassword.DialogPasswordListener,
        DialogMail.DialogMailListener {

    private Button buttonEditname;
    private Button buttonEditmail;
    private Button buttonEditpassword;
    private TextView textViewLastname;
    private TextView textViewFirstname;
    private TextView textViewEmailaddress;
    private TextView textViewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraccount);

        textViewLastname = (TextView) findViewById(R.id.textView_lastname);
        textViewFirstname = (TextView) findViewById(R.id.textView_firstname);
        buttonEditname = (Button) findViewById(R.id.button_edit_name);
        textViewEmailaddress = (TextView) findViewById(R.id.textView_emailaddress);
        buttonEditmail = (Button) findViewById(R.id.button_edit_mail);
        textViewPassword = (TextView) findViewById(R.id.textView_password);
        buttonEditpassword = (Button) findViewById(R.id.button_edit_password);


        buttonEditname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }

            private void openDialog() {
                DialogName nameDialog = new DialogName();
                nameDialog.show(getSupportFragmentManager(), "dialog name");
            }


        });

        buttonEditmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openDialog();
            }

            private void openDialog() {
                DialogMail mailDialog = new DialogMail();
                mailDialog.show(getSupportFragmentManager(), "dialog mail");
            }

        });


        buttonEditpassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openDialog();
            }

            private void openDialog() {
                DialogPassword passwordDialog = new DialogPassword();
                passwordDialog.show(getSupportFragmentManager(), "dialog password");
            }

        });


    }


    @Override
    public void applyTexts(String lastname, String firstname) {
        textViewLastname.setText(lastname);
        textViewFirstname.setText(firstname);

    }


    @Override
    public void updatepassword(String password) {
        textViewPassword.setText(password);
    }

    @Override
    public void applyTexts(String emailaddress) {
        textViewEmailaddress.setText(emailaddress);
    }


}
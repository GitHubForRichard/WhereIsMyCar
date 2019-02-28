package com.example.richa.buttonclickapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends AppCompatActivity {

    private Button buttonSignIn;
    private Button buttonSignUp;
    private Button buttonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonHelp = (Button) findViewById(R.id.buttonHelp);



        View.OnClickListener accountClickListener = new View.OnClickListener(){
          @Override
          public void onClick(View v)
          {
              if(v == buttonSignIn)
              {
                  startActivity(new Intent(getApplicationContext(), LoginActivity.class));
              }

              else if(v == buttonSignUp)
              {
                  startActivity(new Intent(getApplicationContext(), MainActivity.class));
              }

              else if(v == buttonHelp)
              {
                  // go to help page....
              }
          }
        };

        buttonSignIn.setOnClickListener(accountClickListener);
        buttonSignUp.setOnClickListener(accountClickListener);
        buttonHelp.setOnClickListener(accountClickListener);



    }
}

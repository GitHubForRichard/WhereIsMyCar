package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomepageActivity extends AppCompatActivity {

    private ImageButton buttonAccount;
    private ImageButton buttonSearch;
    private ImageButton buttonMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        buttonAccount = (ImageButton) findViewById(R.id.button_Account);
        buttonSearch = (ImageButton) findViewById(R.id.button_Search);
        buttonMap = (ImageButton) findViewById(R.id.button_Map);


        View.OnClickListener buttomBarListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v == buttonAccount) {
                    Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(intent);
                }

                else if(v == buttonSearch)
                {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                }

                else if(v == buttonMap)
                {
                    // go to map page...
                }
            }
        };

        buttonAccount.setOnClickListener(buttomBarListener);
        buttonSearch.setOnClickListener(buttomBarListener);
        buttonMap.setOnClickListener(buttomBarListener);

//        buttonAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getApplicationContext(),AccountActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}

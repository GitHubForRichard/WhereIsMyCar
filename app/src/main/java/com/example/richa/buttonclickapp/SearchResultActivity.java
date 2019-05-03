package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.richa.buttonclickapp.Object.LicensePlateInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView textFloor;
    private TextView textLocation;
    private TextView textGarage;

    private ArrayList<LicensePlateInfo> licensePlateList;
    private ArrayList<Bitmap> bitmapList;

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        initializeUI();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        licensePlateList = (ArrayList<LicensePlateInfo>) args.getSerializable("ARRAYLIST");
        bitmapList = new ArrayList<>();

        // When there is no matching license plate with given input
        if (licensePlateList.size() == 0) {
            textFloor.setText("Sorry.\nThere is no matching license plate");
        } else {
            // Convert matching license plate photo url to bitmap and add to the list
            for (int i = 0; i < licensePlateList.size(); i++) {
                Log.d("TAG", "Photo Url: " + licensePlateList.get(i).getPhotoUrl());
                Bitmap eachBitMap = getBitmapFromURL(licensePlateList.get(i).getPhotoUrl());
                bitmapList.add(eachBitMap);
                textFloor.setText(
                        "Your car is located at the " +
                                licensePlateList.get(i).getFloor() + " floor");
                textLocation.setText("In spot " +
                        licensePlateList.get(i).getLocation());
                textGarage.setText("At the " + licensePlateList.get(i).getGarage());
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 300);
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.setMargins(16, 8, 16, 0);

            // Display license plate images that match with given input
            for (int j = 0; j < bitmapList.size(); j++) {
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmapList.get(j));
                imageView.setLayoutParams(layoutParams);
                linearLayout.addView(imageView);
            }
        }
    }

    public void initializeUI() {
        linearLayout = findViewById(R.id.linearLayout);
        textFloor = findViewById(R.id.text_floor);
        textLocation = findViewById(R.id.text_location);
        textGarage = findViewById(R.id.text_garage);
    }
}

package com.example.richa.buttonclickapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

//    private ConstraintLayout constraintLayout;

//    private ImageView imageViewSearchResult1;
//    private ImageView imageViewSearchResult2;
//    private ImageView imageViewSearchResult3;
    private TextView textViewNoResult;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        initializeUI();

        ArrayList<String> licensePlateList = getIntent().getStringArrayListExtra("SEARCH_RESULT");
        ArrayList<ImageView> searchResultImageList = new ArrayList<>();
        ArrayList<Bitmap> bitmapList = new ArrayList<>();

        // when there is no matching license plate with given input
        if(licensePlateList.size() == 0) {
            textViewNoResult.setText("Sorry.\nThere is no matching license plate");
        }
        else {
            // convert matching license plate photo url to bitmap and add to the list
            for (int i = 0; i < licensePlateList.size(); i++) {
                Log.d("TAG", "Photo Url: " + licensePlateList.get(i));
                Bitmap eachBitMap = getBitmapFromURL(licensePlateList.get(i));
                bitmapList.add(eachBitMap);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1000,800);
            layoutParams.setMargins(200,0,200,0);

            // display license plate images that match with given input
            for (int j = 0; j < bitmapList.size(); j++) {
//                searchResultImageList.get(j).setImageBitmap(bitmapList.get(j));
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmapList.get(j));
                imageView.setLayoutParams(layoutParams);
                linearLayout.addView(imageView);
            }
        }

    }

    public void initializeUI()
    {

        linearLayout = findViewById(R.id.linearLayout);

//        constraintLayout = findViewById(R.id.ConstraintLayout);

//        imageViewSearchResult1 = findViewById(R.id.image_view_search_result1);
//        imageViewSearchResult2 = findViewById(R.id.image_view_search_result2);
//        imageViewSearchResult3 = findViewById(R.id.image_view_search_result3);
//
//        textViewNoResult = findViewById(R.id.text_view_no_result);


    }

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
}

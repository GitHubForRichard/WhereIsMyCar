package com.example.richa.buttonclickapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.richa.buttonclickapp.Object.LicensePlateInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private ImageView imageViewSearchResult;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        imageViewSearchResult = findViewById(R.id.search_result_image_view);

        ArrayList<String> licensePlateList = getIntent().getStringArrayListExtra("SEARCH_RESULT");
        ArrayList<Bitmap> bitmapList = new ArrayList<>();

        for(int i = 0; i < licensePlateList.size(); i++)
        {
            Log.d("TAG", "Photo Url: " + licensePlateList.get(i));
            Bitmap eachBitMap = getBitmapFromURL(licensePlateList.get(i));
            bitmapList.add(eachBitMap);
        }

        for(int j = 0; j < bitmapList.size(); j++)
        {
            imageViewSearchResult.setImageBitmap(bitmapList.get(j));
        }

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

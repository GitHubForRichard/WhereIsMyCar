package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.richa.buttonclickapp.Object.LicensePlateInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchByLicenseActivity extends AppCompatActivity {

    private EditText etLicensePlate;

    private DatabaseReference databaseReference;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_license);

        initializeUI();

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    // go through the database to find the car image that matches users' input license plate
    public void searchCar(View v) {
        // get the license plate users enter
        final String searchPlate = etLicensePlate.getText().toString().trim();

//        if(searchPlate.length() < 5)
//        {
//            Toast.makeText(this, "We needs at least five characters to search", Toast.LENGTH_LONG).show();
//            return;
//        }

        // go through the list and find the cars license plate that contain the searchPlate
        databaseReference.child("cars")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String eachPlateText;

                        ArrayList<LicensePlateInfo> licensePlateList = new ArrayList<>();

                        // go through each child under "cars" parents
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if (licensePlateList.size() > 3) {
                                break;
                            }

                            LicensePlateInfo licensePlateInfo = snapshot.getValue(LicensePlateInfo.class);
                            eachPlateText = licensePlateInfo.getLicensePlateText();
                            System.out.println(eachPlateText);
                            if (eachPlateText.contains(searchPlate)) {
                                Log.d("TAG", "The URL for matching Text is: " + licensePlateInfo.getPhotoUrl());
                                licensePlateList.add(licensePlateInfo);
                            }
                        }
                        Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST", licensePlateList);
                        intent.putExtra("BUNDLE", args);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    /*
    public void detect() {
        bitmap = getBitmapFromURL("https://sdashirts.com/wp-content/uploads/2016/09/SDA-License-Frame-for-car-plates-Adventists-Seventh-is-still-Gods-Sabbath.jpg");
        if(bitmap == null)
        {
            Toast.makeText(getApplicationContext(), "Bitmap is null", Toast.LENGTH_LONG).show();
        }
        else
        {
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);

            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();
            Task<FirebaseVisionText> result =
                    detector.processImage(firebaseVisionImage)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                    // Task completed successfully
                                    // ...
                                    process_text(firebaseVisionText);
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            // Task failed with an exception
                                            // ...
                                            e.printStackTrace();
                                        }
                                    });
        }
    }

    private void process_text(FirebaseVisionText firebaseVisionText)
    {
        List<FirebaseVisionText.TextBlock> blocks = firebaseVisionText.getTextBlocks();
        if(blocks.size() == 0)
        {
            Toast.makeText(getApplicationContext(), "No text detected", Toast.LENGTH_LONG).show();
        }
        else
        {
            for(FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks())
            {
                String text = block.getText();
                System.out.println("--------" + text + "---------");
                //textView.append("\n" + text);
            }
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
    */

    private void initializeUI() {
        etLicensePlate = findViewById(R.id.edit_license_plate);
    }

}

package com.example.richa.buttonclickapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richa.buttonclickapp.Object.LicensePlateInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner garageSpinner;
    private Spinner searchSpinner;
    private Button searchButton;
    private Button uploadImageButton;

    private TextView textView;
    private EditText searchPlateEditText;

    private Bitmap bitmap;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeUI();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_search) {
            searchCar();
        }

        else if(i == R.id.button_upload_image){
            startActivity(new Intent(this, UploadLicensePlateActivity.class));
        }
    }
    private void initializeUI() {
        garageSpinner = findViewById(R.id.spinner_garage);
        searchSpinner = findViewById(R.id.spinner_search);
        searchButton = findViewById(R.id.button_search);
        searchPlateEditText = findViewById(R.id.edit_text_search_plate);
        uploadImageButton = findViewById(R.id.button_upload_image);
        searchButton.setOnClickListener(this);
        uploadImageButton.setOnClickListener(this);

        textView = findViewById(R.id.textView);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Initializing string array for garage spinner
        String[] garages = new String[]{
                "South Garage",
                "North Garage",
                "West Garage"
        };

        //Initializing string array for search spinner
        String[] searchOption = new String[]{
                "Search By Car Model",
                "Search By Plate"
        };

        //Initializing ArrayAdapter for garages
        ArrayAdapter<String> mySpinnerAdpt =
                new ArrayAdapter<>(this, R.layout.spinner_layout, garages);
        garageSpinner.setAdapter(mySpinnerAdpt);

        //Initializing ArrayAdapter for search option
        ArrayAdapter<String> mySpinnerAdpt2 =
                new ArrayAdapter<>(this, R.layout.spinner_layout, searchOption);
        searchSpinner.setAdapter(mySpinnerAdpt2);


    }

    // go through the database to find the car image that matches users' input license plate
    public void searchCar()
    {
        // get the license plate users enter
        final String searchPlate = searchPlateEditText.getText().toString().trim();

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

                        ArrayList<String> licensePlateList  = new ArrayList<>();

                        // go through each child under "cars" parents
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if(licensePlateList.size() > 3) {
                                break;
                            }

                            LicensePlateInfo licensePlateInfo = snapshot.getValue(LicensePlateInfo.class);
                            eachPlateText = licensePlateInfo.licensePlateText;
                            System.out.println(eachPlateText);
                            if(eachPlateText.contains(searchPlate)){
                                Log.d("TAG", "The URL for matching Text is: " + licensePlateInfo.photoUrl);
                                licensePlateList.add(licensePlateInfo.photoUrl);
                            }
                        }
                        Intent intent = new Intent(getBaseContext(), SearchResultActivity.class);
                        intent.putExtra("SEARCH_RESULT", licensePlateList);
                        startActivity(intent);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void detect()
    {

//        databaseReference.child("cars").child("photoUrl").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if(snapshot.getValue() == null) {
//                    textView.setText("Bitmap is null");
//                }
//                else {
//                    System.out.println("---------------" + snapshot.getValue() + "-------------------");
//                    String color = snapshot.getValue().toString();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

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
//            textView.setText("");
            for(FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks())
            {
                String text = block.getText();
                System.out.println("--------" + text + "---------");
                textView.append("\n" + text);
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




//    public void pick_Image(View v)
//    {
//        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(i,1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1 && resultCode == RESULT_OK)
//        {
//            Uri uri = data.getData();
//            try
//            {
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                imageView.setImageBitmap(bitmap);
//            }
//            catch(IOException e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
}
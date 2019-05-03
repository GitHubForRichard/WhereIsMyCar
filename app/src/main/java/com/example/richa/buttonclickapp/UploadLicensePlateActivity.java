package com.example.richa.buttonclickapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richa.buttonclickapp.Object.LicensePlateInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UploadLicensePlateActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE = 1;
    private static final int IMAGE_RECOGNITION = 2;

    private ImageView ivResult;
    private TextView tvResult;
    private EditText etFloor;
    private EditText etLocation;
    private Spinner garageSpinner;

    private int floor;
    private String location;
    private String garage;

    private LicensePlateInfo licensePlateInfo
            = new LicensePlateInfo("", "", 0, "", "");

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private Uri uri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_license_plate);

        // Initialize UI components, and its functionality.
        initializeUI();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    // Detect text on license plate image.
    public void detect() {
        if (bitmap == null) {
            Toast.makeText(getApplicationContext(), "Bitmap is null", Toast.LENGTH_LONG).show();
        } else {

            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();
            Task<FirebaseVisionText> result =
                    detector.processImage(firebaseVisionImage)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                    // Task completed successfully
                                    process_text(firebaseVisionText);
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            // Task failed with an exception
                                            e.printStackTrace();
                                        }
                                    });
        }
    }

    // Process text to output the final string that the image could contain.
    private void process_text(FirebaseVisionText firebaseVisionText) {
        floor = Integer.parseInt(etFloor.getText().toString());
        location = etLocation.getText().toString().trim();
        tvResult.setText("");
        String licensePlateText = "";

        for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
            for (FirebaseVisionText.Line line : block.getLines()) {
                Log.d("Testing line", line.getText());
                String text = line.getText();
                tvResult.append(text);
                licensePlateText += text;
            }
        }

        licensePlateInfo.setLicensePlateText(licensePlateText);
        licensePlateInfo.setFloor(floor);
        licensePlateInfo.setLocation(location);
        licensePlateInfo.setGarage(garage);

        Log.d("TAGS", "License Plate Text Detected:" + licensePlateText);
        databaseReference.child("cars").child(UUID.randomUUID().toString()).setValue(licensePlateInfo);
        Log.d("TAG", "Successfully added the uploaded car info............................");
    }

    // Allow the photo selection from Android camera.
    public void pick_Image(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    // Perform cropping the letters and numbers from license plate. (Manual)
    private void performCrop() {
        // Call the standard crop action intent (some user device may not support)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        // Indicate image type and Uri
        cropIntent.setDataAndType(uri, "image/*");
        // Set crop properties
        cropIntent.putExtra("crop", "true");
        // Retrieve data on return
        cropIntent.putExtra("return-data", true);
        // Start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Done with picking image -> perform cropping.
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            uri = data.getData();
            performCrop();
        }

        // After cropping, get returned data to image recognition.
        if (requestCode == IMAGE_RECOGNITION && resultCode == RESULT_OK) {
            // Get returned data
            Bundle extras = data.getExtras();
            // Get  cropped bitmap
            bitmap = extras.getParcelable("data");
            ivResult.setImageBitmap(bitmap);
        }
    }

    // Upload Image to Firebase Storage
    public void uploadImage(View v) {
        if (uri != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Place the image to firebase storage
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Call detect function to get the text within its method
                            detect();

                            // Get the download url from the image that is just uploaded
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    System.out.println("Download Url: " + downloadUrl);
                                    licensePlateInfo.setPhotoUrl(downloadUrl);
                                }
                            });

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private void initializeUI() {

        List<String> garage_categories = new ArrayList<>();
        garage_categories.add("Which garage did you park?");
        garage_categories.add("South Garage");
        garage_categories.add("North Garage");
        garage_categories.add("West Garage");

        ivResult = findViewById(R.id.image_view_search_result);
        tvResult = findViewById(R.id.text_result);
        etFloor = findViewById(R.id.edit_floor);
        etLocation = findViewById(R.id.edit_location);
        garageSpinner = findViewById(R.id.spinner_garage);

        ArrayAdapter<String> garageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, garage_categories);
        garageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        garageSpinner.setAdapter(garageAdapter);

        garageSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//Onselectingaspinneritem
        String item = parent.getItemAtPosition(position).toString();

//Showing selected spinneritem
        Toast.makeText(parent.getContext(), "Selected:" + item, Toast.LENGTH_LONG).show();

        if (item.equals("South Garage")) {
            garage = "South Garage";
        } else if (item.equals("North Garage")) {
            garage = "North Garage";
        } else if (item.equals("West Garage")) {
            garage = "West Garage";
        } else
            garage = null;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

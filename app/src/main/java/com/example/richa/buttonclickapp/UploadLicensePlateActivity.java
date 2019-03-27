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
import android.widget.ImageView;
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

import java.util.List;
import java.util.UUID;

public class UploadLicensePlateActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    LicensePlateInfo licensePlateInfo = new LicensePlateInfo("","");

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    Uri uri;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_license_plate);

        imageView = findViewById(R.id.image_view_search_result);
        textView = findViewById(R.id.textView6);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    // detect the text on the license plate image
    public void detect()
    {
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

    // process the text to output the final string that the image could contain
    private void process_text(FirebaseVisionText firebaseVisionText)
    {
        List<FirebaseVisionText.TextBlock> blocks = firebaseVisionText.getTextBlocks();

            textView.setText("");
            String licensePlateText = "";
            for(FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks())
            {
                for (FirebaseVisionText.Line line : block.getLines()) {
                    Log.d("Testing line", line.getText());
                    String text = line.getText();
                    textView.append(text);
                    licensePlateText += text;
                }
            }

            licensePlateInfo.setLicensePlateText(licensePlateText);

            Log.d("TAGS", "License Plate Text Detected:" + licensePlateText);

            databaseReference.child("cars").child(UUID.randomUUID().toString()).setValue(licensePlateInfo);

            Log.d("TAG", "Successfully added the uploaded car info............................");

        }


    // allow the photo selection from Android camera
    public void pick_Image(View v)
    {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            uri = data.getData();
            performCrop();
            //bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            //get the returned data
            Bundle extras = data.getExtras();
//get the cropped bitmap
            bitmap = extras.getParcelable("data");
            //uri = data.getData();


            //bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), thePic);
            imageView.setImageBitmap(bitmap);


        }
    }

    private void performCrop() {
        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(uri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        //cropIntent.putExtra("aspectX", 1);
        //cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        ////retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }

    // upload Image to Firebase Storage
    public void uploadImage(View v) {

        if(uri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // place the image to firebase storage
            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // call detect function to get the text within its method
                            detect();

                            // get the download url from the image that is just uploaded
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
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}

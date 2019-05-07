package com.example.richa.buttonclickapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richa.buttonclickapp.Object.UserAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {




    private Button buttonLogin;
    private Button btnCancel;
    private SignInButton buttonGoogleLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static int RC_SIGN_IN = 1;
    private static final String TAG = "LOGIN_ACTIVITY";
    private TextView textViewSignup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    //////////////////////////////////////////////////////////////
    private ImageButton buttonAccount;
    private ImageButton buttonSearch;
    private ImageButton buttonMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() != null) {
            // if the user has already logged in'
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        editTextEmail = findViewById(R.id.edit_email);
        editTextPassword = findViewById(R.id.edit_password);
        buttonLogin = findViewById(R.id.button_login);
        btnCancel = findViewById(R.id.button_cancel);
//        textViewSignup = (TextView) findViewById(R.id.TextViewSignup);

        buttonGoogleLogin = findViewById(R.id.sign_in_button);

        progressDialog = new ProgressDialog(this);

        View.OnClickListener myOnClickListener = new View.OnClickListener(){
            public void onClick(View v) {
                if(v == buttonLogin) {
                    userLogin();
                }

//                if(v == textViewSignup)
//                {
//                    finish();
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                }

                if (v == buttonGoogleLogin) {
                    googleLogin();
                }

                if (v == btnCancel) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                }
            }
        };

        buttonLogin.setOnClickListener(myOnClickListener);
//        textViewSignup.setOnClickListener(myOnClickListener);
        buttonGoogleLogin.setOnClickListener(myOnClickListener);
        btnCancel.setOnClickListener(myOnClickListener);

        initializeUI();
    }

    //////////////////////////////////////////////////////////////////////////////
    private void initializeUI() {
        buttonAccount = findViewById(R.id.imgButton_account);
        buttonAccount.setImageResource(R.drawable.account_icon);

        buttonSearch = findViewById(R.id.imgButton_search);
        buttonSearch.setImageResource(R.drawable.search_icon);

        buttonMaps = findViewById(R.id.imgButton_maps);
        buttonMaps.setImageResource(R.drawable.google_icon);

        textViewSignup = findViewById(R.id.textView_sign_up_reminder);

        buttonAccount.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonMaps.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onClick(View v) {
        int i = v.getId();
        // Button for account page
        if (i == R.id.imgButton_account)
            startActivity(new Intent(getApplicationContext(), AccountActivity.class));
            // Button for search implementation
        else if (i == R.id.imgButton_search)
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            // Button for Google Maps
        else if (i == R.id.imgButton_maps)
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        else if (i == R.id.textView_sign_up_reminder) {
            finish();
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void googleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        progressDialog.setMessage("Logging in with Google ...");
        progressDialog.show();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // check if the logged in user is a new user
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            // save google users' info in firebase database
                            // if the user is first time logging in
                            if (isNewUser) {
                                UserAccount userInfo = new UserAccount(user.getEmail(), "", "", "");
                                FirebaseUser currUser = firebaseAuth.getCurrentUser();
                                databaseReference.child("users").child(currUser.getUid()).setValue(userInfo);
                                Log.d("TAG", "This is a new user from Google, Successfully added User............................");
                            }

                            finish();

                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void userLogin()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            // email is empty
            Toast.makeText(this,"Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty((password)))
        {
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful())
                                {
                                    // start the profile activity
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                }
                                else
                                {
                                    // If sign in fails, display a message to the user.
                                    // Username or password false, display and an error
                                    // add a pop up dialog to show users entering wrong email and password
                                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);

                                    dlgAlert.setMessage("Invalid email or password");
                                    dlgAlert.setTitle("Login Failed");
                                    dlgAlert.setPositiveButton("Try Again", null);
                                    dlgAlert.setCancelable(true);
                                    dlgAlert.create().show();

                                    dlgAlert.setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            });

//                                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
    }
}
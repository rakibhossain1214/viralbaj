package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private FirebaseAuth mAuth;
    FirebaseUser mUser;

    //    CallbackManager mCallbackManager;
    SignInButton signInButton;

    String fbid, mobileno, email, gender, birthday, agerange, imageurl, balance, uid, name, location;

    TextView tvDate, tvStep;
    EditText etMobile;
    Button btnContinue;
    LinearLayout signupSteps;
    private static final String[] paths = {"male", "female"};

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    Context context = this;
    Button btnFetch;
    TextView userLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    String latittude, longitude;


    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = "na";
        uid = "na";
        fbid = "na";
        mobileno = "na";
        gender = "male";
        birthday = "na";
        imageurl = "na";
        location = "na";
        agerange = "na";
        email = "na";
        balance = "0";
        latittude = "0";
        longitude = "0";

        btnFetch = findViewById(R.id.fetch_location);
        userLocation = findViewById(R.id.user_location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchLocation();

            }
        });

        fetchLocation();

        //etEmail = (EditText) findViewById(R.id.et_email);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        tvDate = (TextView) findViewById(R.id.date_textview);
        tvStep = (TextView) findViewById(R.id.tv_step);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        signupSteps = (LinearLayout) findViewById(R.id.signup_steps);

        findViewById(R.id.btn_bday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SpinnerDatePickerDialogBuilder()
                        .context(SignupActivity.this)
                        .callback(SignupActivity.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(2017, 0, 1)
                        .maxDate(2020, 0, 1)
                        .minDate(1950, 0, 1)
                        .build()
                        .show();
            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.spinner_gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the second item gets selected
                        gender = "male";
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        gender = "female";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                //email = etEmail.getText().toString().trim();
                mobileno = etMobile.getText().toString().trim();
                birthday = tvDate.getText().toString().trim();
                location = userLocation.getText().toString().trim();

                if(mobileno.isEmpty() || mobileno.length() < 11){
                    etMobile.setError("Enter a valid mobile");
                    etMobile.requestFocus();
                    return;
                }

//                if (email.isEmpty() || !email.matches(emailPattern))
//                {
//                    etEmail.setError("Enter a valid email");
//                    etEmail.requestFocus();
//                    return;
//                }

                if (birthday.isEmpty())
                {
                    tvDate.setError("Please pick a birthday");
                    tvDate.requestFocus();
                    return;
                }

                if(location.isEmpty()){
                    userLocation.setError("User location cannot be empty");
                    userLocation.requestFocus();
                    return;
                }

                Toast.makeText(SignupActivity.this, "Step - 1 completed!", Toast.LENGTH_SHORT).show();
                tvStep.setText("Step - 2");
                signupSteps.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);
            }
        });

        mAuth = FirebaseAuth.getInstance();

//        mCallbackManager = CallbackManager.Factory.create();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();
//        loginButton.setPermissions(Arrays.asList( "public_profile",
//                "email"));
//
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//
//                SharedPreferences.Editor editor = getSharedPreferences("signinorup", MODE_PRIVATE).edit();
//                editor.putBoolean("olduser", true);
//                editor.apply();
//
//                getFbInfo();
////                Toast.makeText(SignupActivity.this,"Login success",Toast.LENGTH_LONG).show();
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Toast.makeText(SignupActivity.this,"Cancel",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//                Toast.makeText(SignupActivity.this,exception.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });

    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            mUser = mAuth.getCurrentUser();
                            if(mUser!=null){
                                imageurl = mUser.getPhotoUrl().toString();
                                uid = mUser.getUid();
                                name = mUser.getDisplayName();
                                fbid = mUser.getUid();
                                email = mUser.getEmail();
                                createUser();
                            }

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
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
                //Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    //location

    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(SignupActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignupActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to acess this feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(SignupActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(SignupActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Double latittude1 = location.getLatitude();
                                Double longitude1 = location.getLongitude();

                                latittude = String.valueOf(latittude1);
                                longitude = String.valueOf(longitude1);

                                getAddress(latittude1, longitude1);
                            }
                        }
                    });

        }

    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());

                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();

                userLocation.setText(city +", "+ country);
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //abc
            }else{

            }
        }
    }

    //location

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        //birthday = dayOfMonth+"/"+monthOfYear+"/"+year;
        tvDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

        int age = Calendar.getInstance().get(Calendar.YEAR) - year;
        agerange = String.valueOf(age);

//        dateTextView.setText(simpleDateFormat.format(calendar.getTime()));

        Toast.makeText(this, "age range: "+agerange, Toast.LENGTH_SHORT).show();
    }



//    private void getFbInfo() {
//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        try {
//                            fbid = object.getString("id");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,first_name,last_name"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
//        request.setParameters(parameters);
//        request.executeAsync();
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(SignupActivity.this, "Authentication successful! Please wait...",
//                                    Toast.LENGTH_SHORT).show();
//                            mUser = mAuth.getCurrentUser();
//                            if(mUser!=null){
//                                imageurl = mUser.getPhotoUrl().toString();
//                                uid = mUser.getUid();
//                                name = mUser.getDisplayName();
//                                createUser();
//                            }
//                        } else {
//                            Toast.makeText(SignupActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//    }

    void createUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        ref.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users users = dataSnapshot.getValue(users.class);
                if(users==null){
                    loadData();
                }
                finish();
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void loadData(){

        try{
            users users = new users(fbid, mobileno, email, gender, birthday, location, agerange, imageurl, balance, uid, name , latittude, longitude);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("users");
            ref.child(mUser.getUid()).setValue(users);

            finish();
            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }
}

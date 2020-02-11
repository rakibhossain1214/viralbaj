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
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class UpdateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private FirebaseAuth mAuth;
    FirebaseUser mUser;

    String fbid, mobileno, email, gender, birthday, agerange, imageurl, balance, uid, name, location;

    TextView tvDate, tvStep;
    EditText etMobile;
    Button btnUpdate;
    private static final String[] paths = {"male", "female"};

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    Context context = this;
    Button btnFetch;
    TextView userLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    String latittude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = getIntent().getStringExtra("name");
        uid =  getIntent().getStringExtra("uid");
        fbid =  getIntent().getStringExtra("fbid");
        mobileno =  getIntent().getStringExtra("mobileno");
        gender =  getIntent().getStringExtra("gender");
        birthday = getIntent().getStringExtra("birthday");
        imageurl =  getIntent().getStringExtra("imageurl");
        location =  getIntent().getStringExtra("location");
        agerange =  getIntent().getStringExtra("agerange");
        email =  getIntent().getStringExtra("email");
        balance =  getIntent().getStringExtra("balance");
        latittude =  getIntent().getStringExtra("latittude");
        longitude =  getIntent().getStringExtra("longitude");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnFetch = findViewById(R.id.fetch_location);
        userLocation = findViewById(R.id.user_location);
        userLocation.setText(location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchLocation();

            }
        });

//        etEmail = (EditText) findViewById(R.id.et_email);
//        etEmail.setText(email);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etMobile.setText(mobileno);
        tvDate = (TextView) findViewById(R.id.date_textview);
        tvDate.setText(birthday);
        tvStep = (TextView) findViewById(R.id.tv_step);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        findViewById(R.id.btn_bday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SpinnerDatePickerDialogBuilder()
                        .context(UpdateActivity.this)
                        .callback(UpdateActivity.this)
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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//                email = etEmail.getText().toString().trim();
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

                updateData();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(UpdateActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateActivity.this,
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
                                ActivityCompat.requestPermissions(UpdateActivity.this,
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
                ActivityCompat.requestPermissions(UpdateActivity.this,
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
        int monthFix = monthOfYear+1;
        tvDate.setText(dayOfMonth+"/"+monthFix+"/"+year);

        int age = Calendar.getInstance().get(Calendar.YEAR) - year;
        agerange = String.valueOf(age);

//        dateTextView.setText(simpleDateFormat.format(calendar.getTime()));

        Toast.makeText(this, "age range: "+agerange, Toast.LENGTH_SHORT).show();
    }


    void updateData(){

        users users = new users(fbid, mobileno, email, gender, birthday, location, agerange, imageurl, balance, uid, name, latittude, longitude);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        ref.child(mUser.getUid()).setValue(users);

        finish();

        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);

    }

}

package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectModel;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SocialboostActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener,
        OnMapReadyCallback {

    private static final LatLng SYDNEY = new LatLng(23.8103, 90.4125);
    private static final double DEFAULT_RADIUS_METERS = 10000;
    private static final double RADIUS_OF_EARTH_METERS = 6371009;

    private static final int MAX_WIDTH_PX = 50;

    private GoogleMap mMap;

    private List<DraggableCircle> mCircles = new ArrayList<>(1);

    private int mStrokeColorArgb;

    private SeekBar radiusSeekbar;
    public double mRadiusMeters;
    private Circle mCircle;

    TextView tvRadiusLength, tvConvertToTk, tvMin, tvMax;
    String minage = "10" , maxage = "100";
    CrystalRangeSeekbar rangeSeekbar;
    EditText etDollar, etLocations, etAdUrl;
    String locations;
    String boosttype;
    String gendertype;
    String boosturl;
    String latlng;
    String radiuslength;
    Button btnContinue;

    Spinner spinnerType, genderType;
    private static final String[] paths = {"Facebook", "Instagram", "Youtube"};
    private static final String[] paths1 = {"any", "male", "female"};


    TextView totalCost;
    EditText etPromoCode;
    ownerinfo owner;
    String ownerid, ownername, owneremail, ownermobileno;
    boolean codeAvailable;
    double discountAmount;
    EditText trxId;
    EditText buyerAccount1;
    EditText buyerAccount2;
    private static final String[] pathsAccType = {"Bkash", "Rocket"};
    Button confirm;
    Button cancel;
    EditText serviceIdea;
    int total;

    String boostid, booststatus, boostduration, boostcategory, boostownerid,  boostownername,
            boosttime,  boostdate,
            buyingaccounttype, buyingaccountnumber, buyingtrxid, boostcost, durationreached;

    FirebaseUser mUser;

    Spinner spinnerExpert;
    private static final String[] pathsExpert = {"yes", "no"};
    String expertAccepted;


    private class DraggableCircle {
        private final Marker mCenterMarker;
//        private final Marker mRadiusMarker;



        public DraggableCircle(LatLng center, double radiusMeters) {
            mRadiusMeters = radiusMeters;
            mCenterMarker = mMap.addMarker(new MarkerOptions()
                    .position(center)
                    .draggable(true));
//            mRadiusMarker = mMap.addMarker(new MarkerOptions()
//                    .position(toRadiusLatLng(center, radiusMeters))
//                    .draggable(true)
//                    .icon(BitmapDescriptorFactory.defaultMarker(
//                            BitmapDescriptorFactory.HUE_AZURE)));
            mCircle = mMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(radiusMeters)
                    .strokeWidth(MAX_WIDTH_PX / 3)
                    .strokeColor(mStrokeColorArgb)
                    .fillColor(mStrokeColorArgb)
                    .clickable(true)
            );
        }

        public boolean onMarkerMoved(Marker marker) {
            if (marker.equals(mCenterMarker)) {
                mCircle.setCenter(marker.getPosition());
                latlng = String.valueOf(marker.getPosition());
//                mRadiusMarker.setPosition(toRadiusLatLng(marker.getPosition(), mRadiusMeters));
                return true;
            }
//            if (marker.equals(mRadiusMarker)) {
//                mRadiusMeters =
//                        toRadiusMeters(mCenterMarker.getPosition(), mRadiusMarker.getPosition());
//                mCircle.setRadius(mRadiusMeters);
//                return true;
//            }
            return false;
        }


    }

    /** Generate LatLng of radius marker */
    private static LatLng toRadiusLatLng(LatLng center, double radiusMeters) {
        double radiusAngle = Math.toDegrees(radiusMeters / RADIUS_OF_EARTH_METERS) /
                Math.cos(Math.toRadians(center.latitude));
        return new LatLng(center.latitude, center.longitude + radiusAngle);
    }

    private static double toRadiusMeters(LatLng center, LatLng radius) {
        float[] result = new float[1];
        Location.distanceBetween(center.latitude, center.longitude,
                radius.latitude, radius.longitude, result);
        return result[0];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialboost);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        etDollar = (EditText) findViewById(R.id.et_dollar);
        etDollar.addTextChangedListener(textWatcher1);
        etAdUrl = (EditText) findViewById(R.id.ad_url);
        btnContinue = (Button) findViewById(R.id.btn_continue);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etDollar.getText().toString().equals("")){
                    if(!(Integer.parseInt(etDollar.getText().toString())<10)){
                        buyingInstruction();
                    }else{
                        Toast.makeText(SocialboostActivity.this, "Dollar amount is less then USD.10!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SocialboostActivity.this, "Please write down a dollar amount!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        etLocations = (EditText) findViewById(R.id.et_locations);

        spinnerType = (Spinner) findViewById(R.id.spinner_type);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        boosttype = "facebook";
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        boosttype = "youtube";
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        boosttype = "instagram";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        genderType = (Spinner) findViewById(R.id.spinner_gender);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths1);

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderType.setAdapter(adapterGender);
        genderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        gendertype = "any";
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        gendertype = "male";
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        gendertype = "female";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerExpert = (Spinner) findViewById(R.id.spinner_expert);
        ArrayAdapter<String> adapterExpert = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,pathsExpert);

        adapterExpert.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpert.setAdapter(adapterExpert);
        spinnerExpert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        expertAccepted = "yes";
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        expertAccepted = "no";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        radiusSeekbar = (SeekBar) findViewById(R.id.radius_seekbar);
        radiusSeekbar.setMax(1000); //100000
        radiusSeekbar.setProgress(100); //10000

        radiusSeekbar.setOnSeekBarChangeListener(this);

        tvRadiusLength = (TextView) findViewById(R.id.radius_length);
        tvConvertToTk = (TextView) findViewById(R.id.conver_to_tk);
        tvMin = (TextView) findViewById(R.id.text_min);
        tvMax = (TextView) findViewById(R.id.text_max);

        rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeseekbar);
        rangeSeekbar.setMinValue(10);
        rangeSeekbar.setMaxValue(100);
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
            }
        });

        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                //Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                minage = String.valueOf(minValue);
                maxage = String.valueOf(maxValue);
            }
        });

        mStrokeColorArgb = Color.argb(120, 0, 255, 0);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getUserData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textWatcher1 = new TextWatcher() {



        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                int tk = Integer.parseInt(etDollar.getText().toString()) * 90;
                tvConvertToTk.setText("" + tk);

            }catch (Exception e){
                //Toast.makeText(SocialboostActivity.this, "Invalid information!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher textWatcher = new TextWatcher() {



        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            try {
//                int tk = Integer.parseInt(etDollar.getText().toString()) * 90;
//                tvConvertToTk.setText("" + tk);
//
//            }catch (Exception e){
//                //Toast.makeText(SocialboostActivity.this, "Invalid information!", Toast.LENGTH_LONG).show();
//            }

            String stAmount = trxId.getText().toString().trim();
            String stNumber = buyerAccount1.getText().toString().trim();
            String stNumber2 = buyerAccount2.getText().toString().trim();

            confirm.setEnabled(!(stNumber.length()<11) && !stAmount.isEmpty() && !stNumber2.isEmpty() && stNumber.equalsIgnoreCase(stNumber2));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void buyingInstruction(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.payment_instructions, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        Button btnContinue = (Button) promptView.findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // btnAdd1 has been clicked
                alertD.cancel();
                buyBoost();
            }
        });

        alertD.setView(promptView);
        alertD.show();
    }

    void buyBoost(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.boost_buy, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        totalCost = (TextView) promptView.findViewById(R.id.total_cost);
        totalCost.setText("" + total);

        etPromoCode = (EditText) promptView.findViewById(R.id.et_promocode);
        Button btnVerifyCode = (Button) promptView.findViewById(R.id.btn_verifycode);

        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promoCodeCheckerPost();
            }
        });

        trxId = (EditText) promptView.findViewById(R.id.trx_id);
        buyerAccount1 = (EditText) promptView.findViewById(R.id.buyer_number1);
        buyerAccount2 = (EditText) promptView.findViewById(R.id.buyer_number2);
        serviceIdea = (EditText) promptView.findViewById(R.id.et_idea);

        trxId.addTextChangedListener(textWatcher);
        buyerAccount1.addTextChangedListener(textWatcher);
        buyerAccount2.addTextChangedListener(textWatcher);

        confirm = (Button) promptView.findViewById(R.id.btn_confirm);
        cancel = (Button) promptView.findViewById(R.id.btn_cancel);

        Spinner spinner = (Spinner)promptView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,pathsAccType);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        buyingaccounttype = "bkash";
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        buyingaccounttype = "rocket";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // btnAdd1 has been clicked

                if (!trxId.getText().toString().equals("") && !buyerAccount1.getText().toString().equals("") && !buyerAccount2.getText().toString().equals("") ) {

                    if(buyerAccount2.getText().toString().equals(buyerAccount1.getText().toString())) {
                        buyingaccountnumber = buyerAccount1.getText().toString();
                        buyingtrxid = trxId.getText().toString();

                        createBoost();
//                        Toast.makeText(FacebookadsActivity.this, "Confirmed!", Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(SocialboostActivity.this)
                                .setTitle("Thank you!")
                                .setMessage("You have successfully posted your ad.")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                    }
                                }).create().show();
                        alertD.cancel();
                    }else{
                        buyerAccount1.setError("Invalid number!");
                        buyerAccount1.requestFocus();
                    }
                } else {
                    trxId.setError("Insufficient Data!");
                    trxId.requestFocus();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertD.cancel();
                // btnAdd2 has been clicked
            }
        });
        alertD.setView(promptView);
        alertD.show();
    }

    void isPomoCodeUsed(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("promocodes");

        reference.child(etPromoCode.getText().toString()).child("adowners").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ownerinfo ownerinfos = snapshot.getValue(ownerinfo.class);
                    if (ownerinfos!=null) {
                        if(ownerinfos.getOwnerid().equals(mUser.getUid())) {
                            codeAvailable = false;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void promoCodeCheckerPost(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("promocodes");

        isPomoCodeUsed();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(codeAvailable){
                    try {
                        reference.child(etPromoCode.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    if (dataSnapshot.child("codeid").getValue(String.class).equals(etPromoCode.getText().toString())) {
                                        discountAmount = Double.parseDouble(dataSnapshot.child("discountamount").getValue(String.class));
                                        double priceOfAd = Double.parseDouble(totalCost.getText().toString());
                                        double newPrice = priceOfAd - discountAmount;
                                        totalCost.setText("" + newPrice);
                                        totalCost.setTextColor(Color.RED);

                                        reference.child(etPromoCode.getText().toString()).child("adowners").child(mUser.getUid()).setValue(owner);
                                        Toast.makeText(getApplicationContext(), "Congratulations! You got discount", Toast.LENGTH_SHORT).show();

                                        if(newPrice<=0.0){
                                            buyerAccount1.setText("00000000000");
                                            buyerAccount2.setText("00000000000");
                                            trxId.setText("00000000");

                                            buyerAccount1.setTextColor(Color.RED);
                                            buyerAccount2.setTextColor(Color.RED);
                                            trxId.setTextColor(Color.RED);

                                            buyerAccount1.setEnabled(false);
                                            buyerAccount2.setEnabled(false);
                                            trxId.setEnabled(false);
                                        }
                                    } else {
                                        totalCost.setText(totalCost.getText().toString());
                                        totalCost.setTextColor(Color.DKGRAY);

                                        buyerAccount1.setText("");
                                        buyerAccount2.setText("");
                                        trxId.setText("");

                                        buyerAccount1.setTextColor(Color.DKGRAY);
                                        buyerAccount2.setTextColor(Color.DKGRAY);
                                        trxId.setTextColor(Color.DKGRAY);

                                        buyerAccount1.setEnabled(true);
                                        buyerAccount2.setEnabled(true);
                                        trxId.setEnabled(true);

                                        Toast.makeText(getApplicationContext(), "Not a valid code!", Toast.LENGTH_SHORT).show();
                                    }
                                }catch(Exception e){
                                    totalCost.setText(totalCost.getText().toString());
                                    totalCost.setTextColor(Color.DKGRAY);

                                    buyerAccount1.setText("");
                                    buyerAccount2.setText("");
                                    trxId.setText("");

                                    buyerAccount1.setTextColor(Color.DKGRAY);
                                    buyerAccount2.setTextColor(Color.DKGRAY);
                                    trxId.setTextColor(Color.DKGRAY);

                                    buyerAccount1.setEnabled(true);
                                    buyerAccount2.setEnabled(true);
                                    trxId.setEnabled(true);

                                    Toast.makeText(getApplicationContext(),"Not a valid code!",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    catch (Exception e){
                        totalCost.setText(totalCost.getText().toString());
                        totalCost.setTextColor(Color.DKGRAY);

                        buyerAccount1.setText("");
                        buyerAccount2.setText("");
                        trxId.setText("");

                        buyerAccount1.setTextColor(Color.DKGRAY);
                        buyerAccount2.setTextColor(Color.DKGRAY);
                        trxId.setTextColor(Color.DKGRAY);

                        buyerAccount1.setEnabled(true);
                        buyerAccount2.setEnabled(true);
                        trxId.setEnabled(true);

                        Toast.makeText(getApplicationContext(), "Not a valid code!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    codeAvailable = true;
                    Toast.makeText(getApplicationContext(), "This code is already used!", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1000);


    }


    private void createBoost(){
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("socialboost");

        boostid = mUser.getUid()+ UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        booststatus = "pending";
        boostduration = "0";
        boostcategory = boosttype;
        boostownerid = mUser.getUid();
        boostownername = mUser.getDisplayName();
        boosttime = getTime();
        boostdate = getDate();
        durationreached = "0";
        boostcost = tvConvertToTk.getText().toString();
        locations = etLocations.getText().toString();
        boosturl = etAdUrl.getText().toString();
        radiuslength = String.valueOf(mRadiusMeters);

        socialboost serv = new socialboost(boostid, booststatus, boostduration, boostcategory, boostownerid, boostownername,
                boosttime, boostdate,
                buyingaccounttype, buyingaccountnumber, buyingtrxid, boostcost, durationreached, gendertype, minage, maxage, ownermobileno, owneremail, locations, boosturl, latlng, radiuslength, expertAccepted);

        mRef.child(boostid).setValue(serv);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mRadiusMeters  = seekBar.getProgress() * 1000;
        tvRadiusLength.setText(""+mRadiusMeters);
        onStyleChange();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    public void onStyleChange() {
        mCircle.setRadius(mRadiusMeters);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        // Override the default content description on the view, for accessibility mode.
        map.setContentDescription(getString(R.string.map_circle_description));

        mMap = map;
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);


        DraggableCircle circle = new DraggableCircle(SYDNEY, DEFAULT_RADIUS_METERS);
        mCircles.add(circle);

        // Move the map so that it is centered on the initial circle
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 4.0f));

        // Set up the click listener for the circle.
        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                // Flip the red, green and blue components of the circle's stroke color.
                circle.setStrokeColor(circle.getStrokeColor() ^ 0x00ffffff);
            }
        });



        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.8103, 90.4125), 12.0f));
    }


    @Override
    public void onMarkerDragStart(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        onMarkerMoved(marker);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        onMarkerMoved(marker);
    }

    private void onMarkerMoved(Marker marker) {
        for (DraggableCircle draggableCircle : mCircles) {
            if (draggableCircle.onMarkerMoved(marker)) {
                break;
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {
        // We know the center, let's place the outline at a point 3/4 along the view.
        View view = getSupportFragmentManager().findFragmentById(R.id.map).getView();
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    void getUserData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users user = dataSnapshot.getValue(users.class);
                if(user!=null){
                    ownerid = user.getUid();
                    ownername = user.getName();
                    owneremail = user.getEmail();
                    ownermobileno = user.getMobileno();

                    owner = new ownerinfo(ownerid, ownername, owneremail, ownermobileno);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

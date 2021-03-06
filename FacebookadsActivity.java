package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.gms.vision.text.Line;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FacebookadsActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MultiSelectModel> listLocation;
    List<String> listChecker;
    List<String> potentialReachCounter;
    int reachCounter;

    TextView postAmount, pageAmount;
    EditText etPromoCode;
    double discountAmount;
    ownerinfo owner;
    String ownerid, ownername, owneremail, ownermobileno;
    boolean codeAvailable;

    boolean adIsValid;
    String exStatus;


    private int locationCount = 1;
    TextView tvLocation;
    EditText etAdUrl;

    private TextView preac0, preac1, preac2, preac3, preac4, preac5;
    private TextView pshare0, pshare1, pshare2, pshare3, pshare4, pshare5;

    private TextView like0, like1, like2, like3, like4, like5;
    private TextView share0, share1, share2, share3, share4, share5;
    private TextView rev0, rev1, rev2, rev3, rev4, rev5;

    private TextView selectedPostReact, selectedPostShare, selectedLike, selectedShare, selectedReview;

    private int postReactionCount = 0, postShareCount = 0, pageLikeCount = 0, pageShareCount = 0, pageReviewCount = 0;
    private int postReactionCost = 2, postShareCost = 2, pageLikeCost = 3, pageShareCost = 2, pageReviewCost = 3;

    private TextView price, counter;

    private Button btnBuy;
    private  String buySelector;

    EditText trxId;
    EditText buyerAccount1;
    EditText buyerAccount2;

    Button confirm;
    Button cancel;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    private static final String[] paths = {"Bkash", "Rocket"};
    private static final String[] paths1 = {"any", "male", "female"};

    String  adid, adstatus, adcount, adcategory, adtype,
            buyingtype, adownerid, adownername, adlocationtarget,
            adtime, addate, buyingaccounttype = "bkash", buyingaccountnumber, buyingtrxid ,
            buyingamount, adcost, adurl, minage = "5", maxage = "100" , reached, gender;



    private int postReactionCountEx = 0, postShareCountEx = 0, pageLikeCountEx = 0, pageShareCountEx = 0, pageReviewCountEx = 0;
    boolean postReacOld, postShareOld, pageLikeOld, pageShareOld, pageRevOld;
    String adidOldPostReac, adidOldPostShare, adidOldPageLike, adidOldPageShare, adidOldPageReview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebookads);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        exStatus = "pending";
        codeAvailable = true;

        postReacOld = false;
        postShareOld = false;
        pageLikeOld = false;
        pageShareOld  = false;
        pageRevOld = false;

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnBuy = (Button) findViewById(R.id.btn_buy);
        buySelector = "nothing";
        price = (TextView) findViewById(R.id.price);
        counter = (TextView) findViewById(R.id.counter);

        preac0 = (TextView) findViewById(R.id.pr0);
        preac1 = (TextView) findViewById(R.id.pr1);
        preac2 = (TextView) findViewById(R.id.pr2);
        preac3 = (TextView) findViewById(R.id.pr3);
        preac4 = (TextView) findViewById(R.id.pr4);
        preac5 = (TextView) findViewById(R.id.pr5);

        preac0.setOnClickListener(this);
        preac1.setOnClickListener(this);
        preac2.setOnClickListener(this);
        preac3.setOnClickListener(this);
        preac4.setOnClickListener(this);
        preac5.setOnClickListener(this);

        pshare0 = (TextView) findViewById(R.id.ps0);
        pshare1 = (TextView) findViewById(R.id.ps1);
        pshare2 = (TextView) findViewById(R.id.ps2);
        pshare3 = (TextView) findViewById(R.id.ps3);
        pshare4 = (TextView) findViewById(R.id.ps4);
        pshare5 = (TextView) findViewById(R.id.ps5);

        pshare0.setOnClickListener(this);
        pshare1.setOnClickListener(this);
        pshare2.setOnClickListener(this);
        pshare3.setOnClickListener(this);
        pshare4.setOnClickListener(this);
        pshare5.setOnClickListener(this);


        like0 = (TextView) findViewById(R.id.l0);
        like1 = (TextView) findViewById(R.id.l1);
        like2 = (TextView) findViewById(R.id.l2);
        like3 = (TextView) findViewById(R.id.l3);
        like4 = (TextView) findViewById(R.id.l4);
        like5 = (TextView) findViewById(R.id.l5);

        like0.setOnClickListener(this);
        like1.setOnClickListener(this);
        like2.setOnClickListener(this);
        like3.setOnClickListener(this);
        like4.setOnClickListener(this);
        like5.setOnClickListener(this);


        share0 = (TextView) findViewById(R.id.s0);
        share1 = (TextView) findViewById(R.id.s1);
        share2 = (TextView) findViewById(R.id.s2);
        share3 = (TextView) findViewById(R.id.s3);
        share4 = (TextView) findViewById(R.id.s4);
        share5 = (TextView) findViewById(R.id.s5);

        share0.setOnClickListener(this);
        share1.setOnClickListener(this);
        share2.setOnClickListener(this);
        share3.setOnClickListener(this);
        share4.setOnClickListener(this);
        share5.setOnClickListener(this);

        rev0 = (TextView) findViewById(R.id.rw0);
        rev1 = (TextView) findViewById(R.id.rw1);
        rev2 = (TextView) findViewById(R.id.rw2);
        rev3 = (TextView) findViewById(R.id.rw3);
        rev4 = (TextView) findViewById(R.id.rw4);
        rev5 = (TextView) findViewById(R.id.rw5);

        rev0.setOnClickListener(this);
        rev1.setOnClickListener(this);
        rev2.setOnClickListener(this);
        rev3.setOnClickListener(this);
        rev4.setOnClickListener(this);
        rev5.setOnClickListener(this);

        selectedPostReact = preac0;
        selectedPostShare = pshare0;
        selectedLike = like0;
        selectedShare = share0;
        selectedReview = rev0;

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(postReactionCount>0 || postShareCount>0 || pageLikeCount>0 || pageShareCount>0 || pageReviewCount>0){
                    buy();
                }else{
                    Toast.makeText(FacebookadsActivity.this, "Please select something to continue.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadTargerLocation();
        targetLocationUserCount();

        getUserData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pr0:
                selectorPostReact(preac0, 0);
                break;
            case R.id.pr1:
                selectorPostReact(preac1, 50);
                break;
            case R.id.pr2:
                selectorPostReact(preac2, 100);
                break;
            case R.id.pr3:
                selectorPostReact(preac3, 250);
                break;
            case R.id.pr4:
                selectorPostReact(preac4, 500);
                break;
            case R.id.pr5:
                selectorPostReact(preac5, 1000);
                break;


            case R.id.ps0:
                selectorPostShare(pshare0, 0);
                break;
            case R.id.ps1:
                selectorPostShare(pshare1, 50);
                break;
            case R.id.ps2:
                selectorPostShare(pshare2, 100);
                break;
            case R.id.ps3:
                selectorPostShare(pshare3, 250);
                break;
            case R.id.ps4:
                selectorPostShare(pshare4, 500);
                break;
            case R.id.ps5:
                selectorPostShare(pshare5, 1000);
                break;

            case R.id.l0:
                selectorLike(like0, 0);
                break;
            case R.id.l1:
                selectorLike(like1, 50);
                break;
            case R.id.l2:
                selectorLike(like2, 100);
                break;
            case R.id.l3:
                selectorLike(like3, 250);
                break;
            case R.id.l4:
                selectorLike(like4, 500);
                break;
            case R.id.l5:
                selectorLike(like5, 1000);
                break;

            case R.id.s0:
                selectorShare(share0, 0);
                break;
            case R.id.s1:
                selectorShare(share1, 50);
                break;
            case R.id.s2:
                selectorShare(share2, 100);
                break;
            case R.id.s3:
                selectorShare(share3, 250);
                break;
            case R.id.s4:
                selectorShare(share4, 500);
                break;
            case R.id.s5:
                selectorShare(share5, 1000);
                break;

            case R.id.rw0:
                selectorReview(rev0, 0);
                break;
            case R.id.rw1:
                selectorReview(rev1, 50);
                break;
            case R.id.rw2:
                selectorReview(rev2, 100);
                break;
            case R.id.rw3:
                selectorReview(rev3, 250);
                break;
            case R.id.rw4:
                selectorReview(rev4, 500);
                break;
            case R.id.rw5:
                selectorReview(rev5,1000);
                break;
        }
    }

    private void selectorPostReact(TextView t, int count){

        preac0.setBackgroundResource(R.drawable.circle_shape);
        preac1.setBackgroundResource(R.drawable.circle_shape);
        preac2.setBackgroundResource(R.drawable.circle_shape);
        preac3.setBackgroundResource(R.drawable.circle_shape);
        preac4.setBackgroundResource(R.drawable.circle_shape);
        preac5.setBackgroundResource(R.drawable.circle_shape);

        pshare0.setBackgroundResource(R.drawable.circle_shape);
        pshare1.setBackgroundResource(R.drawable.circle_shape);
        pshare2.setBackgroundResource(R.drawable.circle_shape);
        pshare3.setBackgroundResource(R.drawable.circle_shape);
        pshare4.setBackgroundResource(R.drawable.circle_shape);
        pshare5.setBackgroundResource(R.drawable.circle_shape);

        like0.setBackgroundResource(R.drawable.circle_disabled);
        like1.setBackgroundResource(R.drawable.circle_disabled);
        like2.setBackgroundResource(R.drawable.circle_disabled);
        like3.setBackgroundResource(R.drawable.circle_disabled);
        like4.setBackgroundResource(R.drawable.circle_disabled);
        like5.setBackgroundResource(R.drawable.circle_disabled);

        share0.setBackgroundResource(R.drawable.circle_disabled);
        share1.setBackgroundResource(R.drawable.circle_disabled);
        share2.setBackgroundResource(R.drawable.circle_disabled);
        share3.setBackgroundResource(R.drawable.circle_disabled);
        share4.setBackgroundResource(R.drawable.circle_disabled);
        share5.setBackgroundResource(R.drawable.circle_disabled);

        rev0.setBackgroundResource(R.drawable.circle_disabled);
        rev1.setBackgroundResource(R.drawable.circle_disabled);
        rev2.setBackgroundResource(R.drawable.circle_disabled);
        rev3.setBackgroundResource(R.drawable.circle_disabled);
        rev4.setBackgroundResource(R.drawable.circle_disabled);
        rev5.setBackgroundResource(R.drawable.circle_disabled);


        selectedPostReact = t;
        postReactionCount = count;
        selectedPostReact.setBackgroundResource(R.drawable.circle_clicked);
        selectedPostShare.setBackgroundResource(R.drawable.circle_clicked);

        selectedLike = like0;
        selectedShare = share0;
        selectedReview = rev0;

        pageLikeCount = 0;
        pageShareCount = 0;
        pageReviewCount = 0;

        postCounter();
    }

    private void selectorPostShare(TextView t, int count){

        preac0.setBackgroundResource(R.drawable.circle_shape);
        preac1.setBackgroundResource(R.drawable.circle_shape);
        preac2.setBackgroundResource(R.drawable.circle_shape);
        preac3.setBackgroundResource(R.drawable.circle_shape);
        preac4.setBackgroundResource(R.drawable.circle_shape);
        preac5.setBackgroundResource(R.drawable.circle_shape);

        pshare0.setBackgroundResource(R.drawable.circle_shape);
        pshare1.setBackgroundResource(R.drawable.circle_shape);
        pshare2.setBackgroundResource(R.drawable.circle_shape);
        pshare3.setBackgroundResource(R.drawable.circle_shape);
        pshare4.setBackgroundResource(R.drawable.circle_shape);
        pshare5.setBackgroundResource(R.drawable.circle_shape);

        like0.setBackgroundResource(R.drawable.circle_disabled);
        like1.setBackgroundResource(R.drawable.circle_disabled);
        like2.setBackgroundResource(R.drawable.circle_disabled);
        like3.setBackgroundResource(R.drawable.circle_disabled);
        like4.setBackgroundResource(R.drawable.circle_disabled);
        like5.setBackgroundResource(R.drawable.circle_disabled);

        share0.setBackgroundResource(R.drawable.circle_disabled);
        share1.setBackgroundResource(R.drawable.circle_disabled);
        share2.setBackgroundResource(R.drawable.circle_disabled);
        share3.setBackgroundResource(R.drawable.circle_disabled);
        share4.setBackgroundResource(R.drawable.circle_disabled);
        share5.setBackgroundResource(R.drawable.circle_disabled);

        rev0.setBackgroundResource(R.drawable.circle_disabled);
        rev1.setBackgroundResource(R.drawable.circle_disabled);
        rev2.setBackgroundResource(R.drawable.circle_disabled);
        rev3.setBackgroundResource(R.drawable.circle_disabled);
        rev4.setBackgroundResource(R.drawable.circle_disabled);
        rev5.setBackgroundResource(R.drawable.circle_disabled);


        selectedPostShare = t;
        postShareCount = count;
        selectedPostReact.setBackgroundResource(R.drawable.circle_clicked);
        selectedPostShare.setBackgroundResource(R.drawable.circle_clicked);

        selectedLike = like0;
        selectedShare = share0;
        selectedReview = rev0;

        pageLikeCount = 0;
        pageShareCount = 0;
        pageReviewCount = 0;

        postCounter();
    }

    private void selectorLike(TextView t, int count){

        preac0.setBackgroundResource(R.drawable.circle_disabled);
        preac1.setBackgroundResource(R.drawable.circle_disabled);
        preac2.setBackgroundResource(R.drawable.circle_disabled);
        preac3.setBackgroundResource(R.drawable.circle_disabled);
        preac4.setBackgroundResource(R.drawable.circle_disabled);
        preac5.setBackgroundResource(R.drawable.circle_disabled);

        pshare0.setBackgroundResource(R.drawable.circle_disabled);
        pshare1.setBackgroundResource(R.drawable.circle_disabled);
        pshare2.setBackgroundResource(R.drawable.circle_disabled);
        pshare3.setBackgroundResource(R.drawable.circle_disabled);
        pshare4.setBackgroundResource(R.drawable.circle_disabled);
        pshare5.setBackgroundResource(R.drawable.circle_disabled);

        like0.setBackgroundResource(R.drawable.circle_shape);
        like1.setBackgroundResource(R.drawable.circle_shape);
        like2.setBackgroundResource(R.drawable.circle_shape);
        like3.setBackgroundResource(R.drawable.circle_shape);
        like4.setBackgroundResource(R.drawable.circle_shape);
        like5.setBackgroundResource(R.drawable.circle_shape);

        share0.setBackgroundResource(R.drawable.circle_shape);
        share1.setBackgroundResource(R.drawable.circle_shape);
        share2.setBackgroundResource(R.drawable.circle_shape);
        share3.setBackgroundResource(R.drawable.circle_shape);
        share4.setBackgroundResource(R.drawable.circle_shape);
        share5.setBackgroundResource(R.drawable.circle_shape);

        rev0.setBackgroundResource(R.drawable.circle_shape);
        rev1.setBackgroundResource(R.drawable.circle_shape);
        rev2.setBackgroundResource(R.drawable.circle_shape);
        rev3.setBackgroundResource(R.drawable.circle_shape);
        rev4.setBackgroundResource(R.drawable.circle_shape);
        rev5.setBackgroundResource(R.drawable.circle_shape);


        selectedLike = t;
        pageLikeCount = count;
        selectedLike.setBackgroundResource(R.drawable.circle_clicked);
        selectedShare.setBackgroundResource(R.drawable.circle_clicked);
        selectedReview.setBackgroundResource(R.drawable.circle_clicked);

        selectedPostReact = preac0;
        selectedPostShare = pshare0;

        postReactionCount = 0;
        postShareCount = 0;

        pageCounter();

    }

    private void selectorShare(TextView t, int count){

        preac0.setBackgroundResource(R.drawable.circle_disabled);
        preac1.setBackgroundResource(R.drawable.circle_disabled);
        preac2.setBackgroundResource(R.drawable.circle_disabled);
        preac3.setBackgroundResource(R.drawable.circle_disabled);
        preac4.setBackgroundResource(R.drawable.circle_disabled);
        preac5.setBackgroundResource(R.drawable.circle_disabled);

        pshare0.setBackgroundResource(R.drawable.circle_disabled);
        pshare1.setBackgroundResource(R.drawable.circle_disabled);
        pshare2.setBackgroundResource(R.drawable.circle_disabled);
        pshare3.setBackgroundResource(R.drawable.circle_disabled);
        pshare4.setBackgroundResource(R.drawable.circle_disabled);
        pshare5.setBackgroundResource(R.drawable.circle_disabled);

        like0.setBackgroundResource(R.drawable.circle_shape);
        like1.setBackgroundResource(R.drawable.circle_shape);
        like2.setBackgroundResource(R.drawable.circle_shape);
        like3.setBackgroundResource(R.drawable.circle_shape);
        like4.setBackgroundResource(R.drawable.circle_shape);
        like5.setBackgroundResource(R.drawable.circle_shape);

        share0.setBackgroundResource(R.drawable.circle_shape);
        share1.setBackgroundResource(R.drawable.circle_shape);
        share2.setBackgroundResource(R.drawable.circle_shape);
        share3.setBackgroundResource(R.drawable.circle_shape);
        share4.setBackgroundResource(R.drawable.circle_shape);
        share5.setBackgroundResource(R.drawable.circle_shape);

        rev0.setBackgroundResource(R.drawable.circle_shape);
        rev1.setBackgroundResource(R.drawable.circle_shape);
        rev2.setBackgroundResource(R.drawable.circle_shape);
        rev3.setBackgroundResource(R.drawable.circle_shape);
        rev4.setBackgroundResource(R.drawable.circle_shape);
        rev5.setBackgroundResource(R.drawable.circle_shape);


        selectedShare = t;
        pageShareCount = count;
        selectedLike.setBackgroundResource(R.drawable.circle_clicked);
        selectedShare.setBackgroundResource(R.drawable.circle_clicked);
        selectedReview.setBackgroundResource(R.drawable.circle_clicked);

        selectedPostReact = preac0;
        selectedPostShare = pshare0;

        postReactionCount = 0;
        postShareCount = 0;

        pageCounter();

    }

    private void selectorReview(TextView t, int count){

        preac0.setBackgroundResource(R.drawable.circle_disabled);
        preac1.setBackgroundResource(R.drawable.circle_disabled);
        preac2.setBackgroundResource(R.drawable.circle_disabled);
        preac3.setBackgroundResource(R.drawable.circle_disabled);
        preac4.setBackgroundResource(R.drawable.circle_disabled);
        preac5.setBackgroundResource(R.drawable.circle_disabled);

        pshare0.setBackgroundResource(R.drawable.circle_disabled);
        pshare1.setBackgroundResource(R.drawable.circle_disabled);
        pshare2.setBackgroundResource(R.drawable.circle_disabled);
        pshare3.setBackgroundResource(R.drawable.circle_disabled);
        pshare4.setBackgroundResource(R.drawable.circle_disabled);
        pshare5.setBackgroundResource(R.drawable.circle_disabled);

        like0.setBackgroundResource(R.drawable.circle_shape);
        like1.setBackgroundResource(R.drawable.circle_shape);
        like2.setBackgroundResource(R.drawable.circle_shape);
        like3.setBackgroundResource(R.drawable.circle_shape);
        like4.setBackgroundResource(R.drawable.circle_shape);
        like5.setBackgroundResource(R.drawable.circle_shape);

        share0.setBackgroundResource(R.drawable.circle_shape);
        share1.setBackgroundResource(R.drawable.circle_shape);
        share2.setBackgroundResource(R.drawable.circle_shape);
        share3.setBackgroundResource(R.drawable.circle_shape);
        share4.setBackgroundResource(R.drawable.circle_shape);
        share5.setBackgroundResource(R.drawable.circle_shape);

        rev0.setBackgroundResource(R.drawable.circle_shape);
        rev1.setBackgroundResource(R.drawable.circle_shape);
        rev2.setBackgroundResource(R.drawable.circle_shape);
        rev3.setBackgroundResource(R.drawable.circle_shape);
        rev4.setBackgroundResource(R.drawable.circle_shape);
        rev5.setBackgroundResource(R.drawable.circle_shape);


        selectedReview = t;
        pageReviewCount = count;
        selectedLike.setBackgroundResource(R.drawable.circle_clicked);
        selectedShare.setBackgroundResource(R.drawable.circle_clicked);
        selectedReview.setBackgroundResource(R.drawable.circle_clicked);

        selectedPostReact = preac0;
        selectedPostShare = pshare0;

        postReactionCount = 0;
        postShareCount = 0;

        pageCounter();
    }

    private void postCounter(){
        counter.setText(postReactionCount+" Reaction(s) | "+postShareCount+" Shares");
        double totalprice = postReactionCount * postReactionCost + postShareCount * postShareCost;
        price.setText(""+totalprice);
        buySelector = "post";
    }

    private void pageCounter(){
        counter.setText(pageLikeCount+" Like(s) | "+pageShareCount+" Share(s) | "+pageReviewCount+" review(s)");
        double totalprice = pageLikeCount * pageLikeCost + pageShareCount * pageShareCost + pageReviewCount * pageReviewCost;
        price.setText(""+totalprice);
        buySelector = "page";
    }


    public void buy(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.ad_details, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        confirm = (Button) promptView.findViewById(R.id.btn_continue);

        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) promptView.findViewById(R.id.rangeseekbar);

        etAdUrl = (EditText) promptView.findViewById(R.id.ad_url);


        tvLocation = (TextView) promptView.findViewById(R.id.tv_location);
        final TextView tvMin = (TextView) promptView.findViewById(R.id.text_min);
        final TextView tvMax = (TextView) promptView.findViewById(R.id.text_max);

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

        //spinner
        //final Spinner spinner = (Spinner)promptView.findViewById(R.id.spinner);

        Spinner spinner = (Spinner)promptView.findViewById(R.id.spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths1);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        gender = "any";
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        gender = "male";
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        gender = "female";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button btnArea = (Button) promptView.findViewById(R.id.btn_location);
        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTargetArea();
            }
        });

        adIsValid = false;

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // btnAdd1 has been clicked
//                if(!trxId.getText().toString().equals("")||!buyerAccount1.getText().toString().equals("")||!buyerAccount2.getText().toString().equals("")){
//
//                    buyingaccountnumber = buyerAccount1.getText().toString();
//                    buyingtrxid = trxId.getText().toString();
//
//                    createAd();
//                    Toast.makeText(getContext(), "Confirmed!", Toast.LENGTH_SHORT).show();
//                    alertD.cancel();
//                }else{
//                    trxId.setError("Insufficient Data!");
//                    trxId.requestFocus();
//                }

                if(!etAdUrl.getText().toString().equals("") && !tvLocation.getText().toString().equals("")){
                    adlocationtarget = tvLocation.getText().toString();

                    if(postReactionCount>0){
                        adurl = etAdUrl.getText().toString();
                        if((adurl.contains("facebook.com")) && (adurl.contains("posts") || adurl.contains("story.php"))){
                            checkIfOldAddPostLike();
                            adIsValid = true;
                            alertD.cancel();
                        }else{
                            etAdUrl.requestFocus();
                            Toast.makeText(FacebookadsActivity.this, "Not a valid facebook post! Try again.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    if(postShareCount>0){
                        adurl = etAdUrl.getText().toString();
                        if((adurl.contains("facebook.com")) && (adurl.contains("posts") || adurl.contains("story.php"))){
                            checkIfOldAddPostShare();
                            adIsValid = true;
                            alertD.cancel();
                        }else{
                            etAdUrl.requestFocus();
                            Toast.makeText(FacebookadsActivity.this, "Not a valid facebook post! Try again.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    if(pageLikeCount>0){
                        adurl = etAdUrl.getText().toString();

                        if((adurl.contains("facebook.com")) && !(adurl.contains("posts") || adurl.contains("story.php"))){
                            checkIfOldAddPageLike();
                            adIsValid = true;
                            alertD.cancel();
                        }else{
                            etAdUrl.requestFocus();
                            Toast.makeText(FacebookadsActivity.this, "Not a valid facebook post! Try again.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    if(pageShareCount>0){
                        adurl = etAdUrl.getText().toString();

                        if((adurl.contains("facebook.com")) && !(adurl.contains("posts") || adurl.contains("story.php"))){
                            checkIfOldAddPageShare();
                            adIsValid = true;
                            alertD.cancel();
                        }else{
                            etAdUrl.requestFocus();
                            Toast.makeText(FacebookadsActivity.this, "Not a valid facebook post! Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(pageReviewCount>0){
                        adurl = etAdUrl.getText().toString();

                        if((adurl.contains("facebook.com")) && !(adurl.contains("posts") || adurl.contains("story.php"))){
                            checkIfOldAddPageReview();
                            adIsValid = true;
                            alertD.cancel();
                        }else{
                            etAdUrl.requestFocus();
                            Toast.makeText(FacebookadsActivity.this, "Not a valid facebook post! Try again.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    if(adIsValid) {
                        buyingInstruction();
                    }

                }else{
                    Toast.makeText(FacebookadsActivity.this, "Please insert your ad url!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertD.setView(promptView);
        alertD.show();
    }



    private void buyingInstruction(){
        adIsValid = false;

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.payment_instructions, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        Button btnContinue = (Button) promptView.findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // btnAdd1 has been clicked
                alertD.cancel();
                continueBuying();
            }
        });

        alertD.setView(promptView);
        alertD.show();
    }

    private void continueBuying(){
        if(buySelector.equals("post")){
            postBuy();
        }else if(buySelector.equals("page")){
            pageBuy();
        }else{
            Toast.makeText(FacebookadsActivity.this, "Please select something to continue.", Toast.LENGTH_SHORT).show();
        }
    }

    private void postBuy(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.facebook_post, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        TextView postReaction = (TextView) promptView.findViewById(R.id.post_reaction);
        postReaction.setText(""+postReactionCount);
        TextView postShare = (TextView) promptView.findViewById(R.id.post_share);
        postShare.setText(""+postShareCount);
        postAmount = (TextView) promptView.findViewById(R.id.post_amount);
        postAmount.setText(price.getText().toString());

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


        trxId.addTextChangedListener(textWatcher);
        buyerAccount1.addTextChangedListener(textWatcher);
        buyerAccount2.addTextChangedListener(textWatcher);

        confirm = (Button) promptView.findViewById(R.id.btn_confirm);
        cancel = (Button) promptView.findViewById(R.id.btn_cancel);

        Spinner spinner = (Spinner)promptView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

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

                    if (!trxId.getText().toString().equals("") || !buyerAccount1.getText().toString().equals("") || !buyerAccount2.getText().toString().equals("")) {

                        if(buyerAccount2.getText().toString().equals(buyerAccount1.getText().toString())) {
                            buyingaccountnumber = buyerAccount1.getText().toString();
                            buyingtrxid = trxId.getText().toString();

                            createAd();
//                        Toast.makeText(FacebookadsActivity.this, "Confirmed!", Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(FacebookadsActivity.this)
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

    private void pageBuy(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.facebook_page, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        TextView pageLike = (TextView) promptView.findViewById(R.id.page_like);
        pageLike.setText(""+pageLikeCount);
        TextView pageShare = (TextView) promptView.findViewById(R.id.page_share);
        pageShare.setText(""+pageShareCount);
        TextView pageReview = (TextView) promptView.findViewById(R.id.page_review);
        pageReview.setText(""+pageReviewCount);
        pageAmount = (TextView) promptView.findViewById(R.id.page_amount);
        pageAmount.setText(price.getText().toString());

        etPromoCode = (EditText) promptView.findViewById(R.id.et_promocode);
        Button btnVerifyCode = (Button) promptView.findViewById(R.id.btn_verifycode);

        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promoCodeCheckerPage();

            }
        });

        trxId = (EditText) promptView.findViewById(R.id.trx_id);
        buyerAccount1 = (EditText) promptView.findViewById(R.id.buyer_number1);
        buyerAccount2 = (EditText) promptView.findViewById(R.id.buyer_number2);

        trxId.addTextChangedListener(textWatcher);
        buyerAccount1.addTextChangedListener(textWatcher);
        buyerAccount2.addTextChangedListener(textWatcher);

        confirm = (Button) promptView.findViewById(R.id.btn_confirm);
        cancel = (Button) promptView.findViewById(R.id.btn_cancel);

        Spinner spinner = (Spinner)promptView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

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



                    if (!trxId.getText().toString().equals("") || !buyerAccount1.getText().toString().equals("") || !buyerAccount2.getText().toString().equals("")) {

                        buyingaccountnumber = buyerAccount1.getText().toString();
                        buyingtrxid = trxId.getText().toString();

                        createAd();
//                        Toast.makeText(FacebookadsActivity.this, "Confirmed!", Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(FacebookadsActivity.this)
                                .setTitle("Thank you!")
                                .setMessage("You have successfully posted your ad.")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                    }
                                }).create().show();
                        alertD.cancel();
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
                                        double priceOfAd = Double.parseDouble(price.getText().toString());
                                        double newPrice = priceOfAd - discountAmount;
                                        postAmount.setText("" + newPrice);
                                        postAmount.setTextColor(Color.RED);

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
                                        postAmount.setText(price.getText().toString());
                                        postAmount.setTextColor(Color.DKGRAY);

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
                                    postAmount.setText(price.getText().toString());
                                    postAmount.setTextColor(Color.DKGRAY);

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
                        postAmount.setText(price.getText().toString());
                        postAmount.setTextColor(Color.DKGRAY);

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

    void promoCodeCheckerPage(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("promocodes");

        isPomoCodeUsed();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (codeAvailable) {
                    try {
                        reference.child(etPromoCode.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    if (dataSnapshot.child("codeid").getValue(String.class).equals(etPromoCode.getText().toString())) {
                                        discountAmount = Double.parseDouble(dataSnapshot.child("discountamount").getValue(String.class));
                                        double priceOfAd = Double.parseDouble(price.getText().toString());
                                        double newPrice = priceOfAd - discountAmount;
                                        pageAmount.setText("" + newPrice);
                                        pageAmount.setTextColor(Color.RED);

                                        reference.child(etPromoCode.getText().toString()).child("adowners").child(mUser.getUid()).setValue(owner);
                                        Toast.makeText(getApplicationContext(), "Congratulations! You got discount", Toast.LENGTH_SHORT).show();

                                        if (newPrice <= 0.0) {
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
                                        pageAmount.setText(price.getText().toString());
                                        pageAmount.setTextColor(Color.DKGRAY);

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
                                } catch (Exception e) {
                                    pageAmount.setText(price.getText().toString());
                                    pageAmount.setTextColor(Color.DKGRAY);

                                    buyerAccount1.setText("");
                                    buyerAccount2.setText("");
                                    trxId.setText("");

                                    buyerAccount1.setTextColor(Color.DKGRAY);
                                    buyerAccount2.setTextColor(Color.DKGRAY);
                                    trxId.setTextColor(Color.DKGRAY);

                                    buyerAccount1.setEnabled(true);
                                    buyerAccount2.setEnabled(true);
                                    trxId.setEnabled(true);

                                    Toast.makeText(getApplicationContext(), "Not a valid code!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } catch (Exception e) {
                        pageAmount.setText(price.getText().toString());
                        pageAmount.setTextColor(Color.DKGRAY);

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
            },1000);


    }

    private void createAd(){

        try{

            mDatabase = FirebaseDatabase.getInstance();
            mRef = mDatabase.getReference("ads");

            if(postReactionCount>0){
                adid = mUser.getUid()+ UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                adstatus = "pending";
                adcount = String.valueOf(postReactionCount);
                adcategory = "facebook";
                adtype = "post";
                buyingtype = "fbpostreaction";
                adownerid = mUser.getUid();
                adownername = mUser.getDisplayName();
                adcost = "1";
                reached = "0";
                adtime = getTime();
                addate = getDate();
                buyingamount = String.valueOf((postReactionCount * postReactionCost) - discountAmount);

                if(postReacOld){
                    int sum = postReactionCountEx+postReactionCount;
                    mRef.child(adidOldPostReac).child("adstatus").setValue(exStatus);
                    mRef.child(adidOldPostReac).child("adcount").setValue(""+sum);
                    mRef.child(adidOldPostReac).child("adlocationtarget").setValue(adlocationtarget);
                    mRef.child(adidOldPostReac).child("minage").setValue(minage);
                    mRef.child(adidOldPostReac).child("maxage").setValue(maxage);
                    mRef.child(adidOldPostReac).child("gender").setValue(gender);
                    mRef.child(adidOldPostReac).child("addate").setValue(addate);
                    mRef.child(adidOldPostReac).child("adtime").setValue(adtime);
                    mRef.child(adidOldPostReac).child("buyingamount").setValue(buyingamount);
                    mRef.child(adidOldPostReac).child("buyingtrxid").setValue(buyingtrxid);
                    mRef.child(adidOldPostReac).child("buyingaccounttype").setValue(buyingaccounttype);
                    mRef.child(adidOldPostReac).child("buyingaccountnumber").setValue(buyingaccountnumber);

                    postReacOld = false;
                }else{
                    ads ads = new ads(adid, adstatus, adcount, adcategory, adtype,
                            buyingtype, adownerid, adownername, adlocationtarget,
                            adtime, addate,
                            buyingaccounttype, buyingaccountnumber, buyingtrxid, buyingamount, adcost, adurl, minage, maxage, reached, gender);

                    mRef.child(adid).setValue(ads);
                }
            }

            if(postShareCount>0){
                adid = mUser.getUid()+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                adstatus = "pending";
                adcount = String.valueOf(postShareCount);
                adcategory = "facebook";
                adtype = "post";
                buyingtype = "fbpostshare";
                adownerid = mUser.getUid();
                adownername = mUser.getDisplayName();
                adcost = "1";
                reached = "0";
                adtime = getTime();
                addate = getDate();
                buyingamount = String.valueOf((postShareCount * postShareCost) - discountAmount);

//            checkIfOldAddPostShare();

                if(postShareOld){
                    int sum = postShareCountEx+postShareCount;
                    mRef.child(adidOldPostShare).child("adstatus").setValue(exStatus);
                    mRef.child(adidOldPostShare).child("adcount").setValue(""+sum);
                    mRef.child(adidOldPostShare).child("adlocationtarget").setValue(adlocationtarget);
                    mRef.child(adidOldPostShare).child("minage").setValue(minage);
                    mRef.child(adidOldPostShare).child("maxage").setValue(maxage);
                    mRef.child(adidOldPostShare).child("gender").setValue(gender);
                    mRef.child(adidOldPostShare).child("addate").setValue(addate);
                    mRef.child(adidOldPostShare).child("adtime").setValue(adtime);
                    mRef.child(adidOldPostShare).child("buyingamount").setValue(buyingamount);
                    mRef.child(adidOldPostShare).child("buyingtrxid").setValue(buyingtrxid);
                    mRef.child(adidOldPostShare).child("buyingaccounttype").setValue(buyingaccounttype);
                    mRef.child(adidOldPostShare).child("buyingaccountnumber").setValue(buyingaccountnumber);

                    postShareOld = false;
                }else{
                    ads ads = new ads(adid, adstatus, adcount, adcategory, adtype,
                            buyingtype, adownerid, adownername, adlocationtarget,
                            adtime, addate,
                            buyingaccounttype, buyingaccountnumber, buyingtrxid, buyingamount, adcost, adurl, minage, maxage, reached, gender);

                    mRef.child(adid).setValue(ads);
                }

            }

            if(pageLikeCount>0){
                adid = mUser.getUid()+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                adstatus = "pending";
                adcount = String.valueOf(pageLikeCount);
                adcategory = "facebook";
                adtype = "page";
                buyingtype = "fbpagelike";
                adownerid = mUser.getUid();
                adownername = mUser.getDisplayName();
                adcost = "2";
                reached = "0";
                adtime = getTime();
                addate = getDate();
                buyingamount = String.valueOf((pageLikeCount * pageLikeCost) - discountAmount);

                if(pageLikeOld){
                    int sum = pageLikeCountEx+pageLikeCount;
                    mRef.child(adidOldPageLike).child("adstatus").setValue(exStatus);
                    mRef.child(adidOldPageLike).child("adcount").setValue(""+sum);
                    mRef.child(adidOldPageLike).child("adlocationtarget").setValue(adlocationtarget);
                    mRef.child(adidOldPageLike).child("minage").setValue(minage);
                    mRef.child(adidOldPageLike).child("maxage").setValue(maxage);
                    mRef.child(adidOldPageLike).child("gender").setValue(gender);
                    mRef.child(adidOldPageLike).child("addate").setValue(addate);
                    mRef.child(adidOldPageLike).child("adtime").setValue(adtime);
                    mRef.child(adidOldPageLike).child("buyingamount").setValue(buyingamount);
                    mRef.child(adidOldPageLike).child("buyingtrxid").setValue(buyingtrxid);
                    mRef.child(adidOldPageLike).child("buyingaccounttype").setValue(buyingaccounttype);
                    mRef.child(adidOldPageLike).child("buyingaccountnumber").setValue(buyingaccountnumber);

                    pageLikeOld = false;
                }else{
                    ads ads = new ads(adid, adstatus, adcount, adcategory, adtype,
                            buyingtype, adownerid, adownername, adlocationtarget,
                            adtime, addate,
                            buyingaccounttype, buyingaccountnumber, buyingtrxid, buyingamount, adcost, adurl, minage, maxage, reached, gender);

                    mRef.child(adid).setValue(ads);
                }

            }

            if(pageShareCount>0){
                adid = mUser.getUid()+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                adstatus = "pending";
                adcount = String.valueOf(pageShareCount);
                adcategory = "facebook";
                adtype = "page";
                buyingtype = "fbpageshare";
                adownerid = mUser.getUid();
                adownername = mUser.getDisplayName();
                adcost = "1";
                reached = "0";
                adtime = getTime();
                addate = getDate();
                buyingamount = String.valueOf((pageShareCount * pageShareCost) - discountAmount);

                if(pageShareOld){
                    int sum = pageShareCountEx+pageShareCount;
                    mRef.child(adidOldPageShare).child("adstatus").setValue(exStatus);
                    mRef.child(adidOldPageShare).child("adcount").setValue(""+sum);
                    mRef.child(adidOldPageShare).child("adlocationtarget").setValue(adlocationtarget);
                    mRef.child(adidOldPageShare).child("minage").setValue(minage);
                    mRef.child(adidOldPageShare).child("maxage").setValue(maxage);
                    mRef.child(adidOldPageShare).child("gender").setValue(gender);
                    mRef.child(adidOldPageShare).child("addate").setValue(addate);
                    mRef.child(adidOldPageShare).child("adtime").setValue(adtime);
                    mRef.child(adidOldPageShare).child("buyingamount").setValue(buyingamount);
                    mRef.child(adidOldPageShare).child("buyingtrxid").setValue(buyingtrxid);
                    mRef.child(adidOldPageShare).child("buyingaccounttype").setValue(buyingaccounttype);
                    mRef.child(adidOldPageShare).child("buyingaccountnumber").setValue(buyingaccountnumber);

                    pageShareOld = false;
                }else{
                    ads ads = new ads(adid, adstatus, adcount, adcategory, adtype,
                            buyingtype, adownerid, adownername, adlocationtarget,
                            adtime, addate,
                            buyingaccounttype, buyingaccountnumber, buyingtrxid, buyingamount, adcost, adurl, minage, maxage, reached, gender);

                    mRef.child(adid).setValue(ads);
                }


            }

            if(pageReviewCount>0){
                adid = mUser.getUid()+UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                adstatus = "pending";
                adcount = String.valueOf(pageReviewCount);
                adcategory = "facebook";
                adtype = "page";
                buyingtype = "fbpagereview";
                adownerid = mUser.getUid();
                adownername = mUser.getDisplayName();
                adcost = "2";
                reached = "0";
                adtime = getTime();
                addate = getDate();
                buyingamount = String.valueOf((pageReviewCount * pageReviewCost) - discountAmount);


                if(pageRevOld){
                    int sum = pageReviewCountEx+pageReviewCount;
                    mRef.child(adidOldPageReview).child("adstatus").setValue(exStatus);
                    mRef.child(adidOldPageReview).child("adcount").setValue(""+sum);
                    mRef.child(adidOldPageReview).child("adlocationtarget").setValue(adlocationtarget);
                    mRef.child(adidOldPageReview).child("minage").setValue(minage);
                    mRef.child(adidOldPageReview).child("maxage").setValue(maxage);
                    mRef.child(adidOldPageReview).child("gender").setValue(gender);
                    mRef.child(adidOldPageReview).child("addate").setValue(addate);
                    mRef.child(adidOldPageReview).child("adtime").setValue(adtime);
                    mRef.child(adidOldPageReview).child("buyingamount").setValue(buyingamount);
                    mRef.child(adidOldPageReview).child("buyingtrxid").setValue(buyingtrxid);
                    mRef.child(adidOldPageReview).child("buyingaccounttype").setValue(buyingaccounttype);
                    mRef.child(adidOldPageReview).child("buyingaccountnumber").setValue(buyingaccountnumber);

                    pageRevOld = false;
                }else{
                    ads ads = new ads(adid, adstatus, adcount, adcategory, adtype,
                            buyingtype, adownerid, adownername, adlocationtarget,
                            adtime, addate,
                            buyingaccounttype, buyingaccountnumber, buyingtrxid, buyingamount, adcost, adurl, minage, maxage, reached, gender);

                    mRef.child(adid).setValue(ads);
                }

            }
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }





    }

    void checkIfOldAddPostLike(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("ads");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot adSnap : dataSnapshot.getChildren()) {
                        ads oldAd = adSnap.getValue(ads.class);
                        if(oldAd!=null) {
                            if(oldAd.getAdurl().equals(adurl)){
                                if(oldAd.getBuyingtype().equals("fbpostreaction")) {
                                    postReacOld = true;
                                    exStatus = "expending";
                                    postReactionCountEx = Integer.parseInt(oldAd.getAdcount());
                                    adidOldPostReac = oldAd.getAdid();
                                    Toast.makeText(FacebookadsActivity.this, "Old Ad", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    void checkIfOldAddPostShare(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("ads");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot adSnap : dataSnapshot.getChildren()) {
                        ads oldAd = adSnap.getValue(ads.class);
                        if(oldAd!=null) {
                            if(oldAd.getAdurl().equals(adurl)){
                                if(oldAd.getBuyingtype().equals("fbpostshare")) {
                                    postShareOld = true;
                                    exStatus = "expending";
                                    postShareCountEx = Integer.parseInt(oldAd.getAdcount());
                                    adidOldPostShare = oldAd.getAdid();
                                    Toast.makeText(FacebookadsActivity.this, "Old Ad", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    void checkIfOldAddPageLike(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("ads");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot adSnap : dataSnapshot.getChildren()) {
                        ads oldAd = adSnap.getValue(ads.class);
                        if(oldAd!=null) {
                            if(oldAd.getAdurl().equals(adurl)){
                                if(oldAd.getBuyingtype().equals("fbpagelike")) {
                                    pageLikeOld = true;
                                    exStatus = "expending";
                                    pageLikeCountEx = Integer.parseInt(oldAd.getAdcount());
                                    adidOldPageLike = oldAd.getAdid();
                                    Toast.makeText(FacebookadsActivity.this, "Old Ad", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    void checkIfOldAddPageShare(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("ads");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot adSnap : dataSnapshot.getChildren()) {
                        ads oldAd = adSnap.getValue(ads.class);
                        if(oldAd!=null) {
                            if(oldAd.getAdurl().equals(adurl)){
                                if(oldAd.getBuyingtype().equals("fbpageshare")) {
                                    pageShareOld = true;
                                    exStatus = "expending";
                                    pageShareCountEx = Integer.parseInt(oldAd.getAdcount());
                                    adidOldPageShare = oldAd.getAdid();
                                    Toast.makeText(FacebookadsActivity.this, "Old Ad", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    void checkIfOldAddPageReview(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("ads");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot adSnap : dataSnapshot.getChildren()) {
                        ads oldAd = adSnap.getValue(ads.class);
                        if(oldAd!=null) {
                            if(oldAd.getAdurl().equals(adurl)){
                                if(oldAd.getBuyingtype().equals("fbpagereview")) {
                                    pageRevOld = true;
                                    exStatus = "expending";
                                    pageReviewCountEx = Integer.parseInt(oldAd.getAdcount());
                                    adidOldPageReview = oldAd.getAdid();
                                    Toast.makeText(FacebookadsActivity.this, "Old Ad", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String stAmount = trxId.getText().toString().trim();
            String stNumber = buyerAccount1.getText().toString().trim();
            String stNumber2 = buyerAccount2.getText().toString().trim();

            confirm.setEnabled(!(stNumber.length()<11) && !stAmount.isEmpty() && !stNumber2.isEmpty() && stNumber.equalsIgnoreCase(stNumber2));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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

    private void loadTargerLocation(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference areaRef = database.getReference("users");

            areaRef.orderByChild("location").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listLocation = new ArrayList<MultiSelectModel>();
                    listChecker = new ArrayList<String>();
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        users user = areaSnapshot.getValue(users.class);
                        if(user!=null && !listChecker.contains(user.getLocation())) {
                            //listLocation.add(user.getLocation());
                            listChecker.add(user.getLocation());
                            listLocation.add(new MultiSelectModel(locationCount,user.getLocation()));
                            ++locationCount;
                        }
                    }

//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listLocation);
//                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(arrayAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectTargetArea(){
        try{
            MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                    .title(getResources().getString(R.string.multi_select_dialog_title)) //setting title for dialog
                    .titleSize(25)
                    .positiveText("Done")
                    .negativeText("Cancel")
                    .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                    .setMaxSelectionLimit(listLocation.size()) //you can set maximum checkbox selection limit (Optional)
                    .multiSelectList(listLocation) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            for (int i = 0; i < selectedIds.size(); i++) {

                                tvLocation.setText(dataString);

                                reachCounter = 0;

                                for (int j=0; j<potentialReachCounter.size(); j++) {
                                    if(dataString.contains(potentialReachCounter.get(j))){
                                        ++reachCounter;
                                    }
                                }
                            }

                            if(reachCounter<postReactionCount || reachCounter<postShareCount || reachCounter<pageLikeCount || reachCounter<pageShareCount || reachCounter<pageReviewCount){
                                tvLocation.setText("any, "+dataString);
                            }

                            Toast.makeText(FacebookadsActivity.this, "Potential Reach: "+reachCounter, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {
                            //Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }


                    });

            multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }


    void targetLocationUserCount(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference areaRef = database.getReference("users");

            areaRef.orderByChild("location").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    potentialReachCounter = new ArrayList<String>();
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        users user = areaSnapshot.getValue(users.class);
                        if(user!=null) {
                            //listLocation.add(user.getLocation());
                            potentialReachCounter.add(user.getLocation());
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    void getUserData(){
        try{
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
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }
}

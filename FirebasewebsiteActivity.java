package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FirebasewebsiteActivity extends AppCompatActivity {

    FirebaseUser mUser;
    String serviceid, servicestatus, servicemilestone, servicecategory, serviceownerid, serviceownername,
            servicetime, servicedate,
            buyingaccounttype, buyingaccountnumber, buyingtrxid, servicecost, serviceidea, milestonereached;

    TextView totalCost;
    EditText etPromoCode;
    ownerinfo owner;
    String ownerid, ownername, owneremail, ownermobileno;
    boolean codeAvailable;
    double discountAmount;
    EditText trxId;
    EditText buyerAccount1;
    EditText buyerAccount2;
    private static final String[] paths = {"Bkash", "Rocket"};
    Button confirm;
    Button cancel;
    EditText serviceIdea;
    int total;
    TextView starterDiscount;
    TextView discountLess, withoutDiscount;

    TextView hostingAmount, hostingPrice;
    ImageView hostingPlus;
    ImageView hostingMinus;
    int hostingPr = 5000;
    int hostingCount = 0;
    int hostingSum = 0;

    TextView installationPrice;
    int installationPr = 3000;

    Spinner spinnerDomain;
    TextView domainPrice;
    int domainPr = 0;
    private static final String[] pathsDomain = {"yes", "no"};

    Spinner spinnerEmail;
    TextView emailPrice;
    int emailPr = 3000;

    TextView pageAmount, pagePrice;
    ImageView pagePlus;
    ImageView pageMinus;
    int pagePr = 2000;
    int pageCount = 0;
    int pageSum = 0;

    TextView dynamicPageAmount, dynamicPagePrice;
    ImageView dynamicPagePlus;
    ImageView dynamicPageMinus;
    int dynamicPagePr = 4000;
    int dynamicPageCount = 0;
    int dynamicPageSum = 0;

    TextView featureAmount, featurePrice;
    ImageView featurePlus;
    ImageView featureMinus;
    int featurePr = 5000;
    int featureCount = 0;
    int featureSum = 0;

    TextView moderateFeatureAmount, moderateFeaturePrice;
    ImageView modeateFeaturePlus;
    ImageView modeateFeatureMinus;
    int moderateFeaturePr = 10000;
    int moderateFeatureCount = 0;
    int moderateFeatureSum = 0;

    TextView premiumFeatureAmount, premiumFeaturePrice;
    ImageView premiumFeaturePlus;
    ImageView premiumFeatureMinus;
    int premiumFeaturePr = 20000;
    int premiumFeatureCount = 0;
    int premiumFeatureSum = 0;

    TextView apiAmount, apiPrice;
    ImageView apiPlus;
    ImageView apiMinus;
    int apiPr = 3000;
    int apiCount = 0;
    int apiSum = 0;

    TextView heavyApiAmount, heavyApiPrice;
    ImageView heavyApiPlus;
    ImageView heavyApiMinus;
    int heavyApiPr = 5000;
    int heavyApiCount = 0;
    int heavyApiSum = 0;

    TextView totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasewebsite);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        codeAvailable = true;

        starterDiscount = (TextView) findViewById(R.id.starter_discount);
        discountLess = (TextView) findViewById(R.id.discount_less);
        withoutDiscount = (TextView) findViewById(R.id.without_discount);

        hostingAmount = (TextView) findViewById(R.id.hosting_amount);
        hostingPrice = (TextView) findViewById(R.id.hosting_price);

        hostingPlus = (ImageView) findViewById(R.id.hosting_plus);
        hostingPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostingCount = Integer.parseInt(hostingAmount.getText().toString());
                hostingCount++;
                if(hostingCount==1) {
                    hostingCount = 1;
                    hostingSum = 0;
                    hostingAmount.setText(""+hostingCount);
                    hostingPrice.setText("" + hostingSum);
                    totalSum();
                }else{
                    hostingSum = hostingPr * hostingCount;
                    hostingAmount.setText(""+hostingCount);
                    hostingPrice.setText("" + hostingSum);
                    totalSum();
                }
            }
        });

        hostingMinus = (ImageView) findViewById(R.id.hosting_minus);
        hostingMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostingCount = Integer.parseInt(hostingAmount.getText().toString());
                if(hostingCount>1) {
                    hostingCount--;
                    hostingSum = hostingPr * hostingCount;
                    hostingAmount.setText(""+hostingCount);
                    hostingPrice.setText(""+hostingSum);
                    totalSum();
                }else if(hostingCount==1){
                    hostingCount = 1;
                    hostingSum = 0;
                    hostingAmount.setText(""+hostingCount);
                    hostingPrice.setText("" + hostingSum);
                    totalSum();
                }
            }
        });




        installationPrice = (TextView) findViewById(R.id.installation_price);

        spinnerDomain = (Spinner) findViewById(R.id.sp_domain);
        domainPrice = (TextView) findViewById(R.id.domain_price);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,pathsDomain);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDomain.setAdapter(adapter);
        spinnerDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        domainPr = 1000;
                        domainPrice.setText(""+domainPr);
                        totalSum();
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        domainPr = 0;
                        domainPrice.setText(""+domainPr);
                        totalSum();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEmail = (Spinner) findViewById(R.id.sp_email);
        emailPrice = (TextView) findViewById(R.id.email_price);

        final ArrayAdapter<String> adapterEmail = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,pathsDomain);
        adapterEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmail.setAdapter(adapterEmail);
        spinnerEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        emailPr = 3000;
                        emailPrice.setText(""+emailPr);
                        totalSum();
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        emailPr = 0;
                        emailPrice.setText(""+emailPr);
                        totalSum();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pageAmount = (TextView) findViewById(R.id.page_amount);
        pagePrice = (TextView) findViewById(R.id.page_price);
        pagePlus = (ImageView) findViewById(R.id.page_plus);

        pagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount = Integer.parseInt(pageAmount.getText().toString());
                pageCount++;
                pageSum = pageCount * pagePr;
                pageAmount.setText("" + pageCount);
                pagePrice.setText("" + pageSum);
                totalSum();

            }
        });

        pageMinus = (ImageView) findViewById(R.id.page_minus);
        pageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount = Integer.parseInt(pageAmount.getText().toString());

                if(pageCount>0) {
                    pageCount--;
                    pageSum = pageCount * pagePr;
                    pageAmount.setText("" + pageCount);
                    pagePrice.setText("" + pageSum);
                    totalSum();
                }

            }
        });

        apiAmount = (TextView) findViewById(R.id.api_amount);
        apiPrice = (TextView) findViewById(R.id.api_price);
        apiPlus = (ImageView) findViewById(R.id.api_plus);
        apiPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCount = Integer.parseInt(apiAmount.getText().toString());
                apiCount++;
                apiSum = apiCount * apiPr;
                apiAmount.setText("" + apiCount);
                apiPrice.setText("" + apiSum);
                totalSum();

            }
        });

        apiMinus = (ImageView) findViewById(R.id.api_minus);
        apiMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCount = Integer.parseInt(apiAmount.getText().toString());

                if(apiCount>0){
                    apiCount--;
                    apiSum = apiCount * apiPr;
                    apiAmount.setText("" + apiCount);
                    apiPrice.setText("" + apiSum);
                    totalSum();
                }

            }
        });

        heavyApiAmount = (TextView) findViewById(R.id.heavyapi_amount);
        heavyApiPrice = (TextView) findViewById(R.id.heavyapi_price);
        heavyApiPlus = (ImageView) findViewById(R.id.heavyapi_plus);
        heavyApiPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heavyApiCount = Integer.parseInt(heavyApiAmount.getText().toString());
                heavyApiCount++;
                heavyApiSum = heavyApiCount * heavyApiPr;
                heavyApiAmount.setText("" + heavyApiCount);
                heavyApiPrice.setText("" + heavyApiSum);
                totalSum();
            }
        });
        heavyApiMinus = (ImageView) findViewById(R.id.heavyapi_minus);
        heavyApiMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heavyApiCount = Integer.parseInt(heavyApiAmount.getText().toString());

                if(heavyApiCount>0){
                    heavyApiCount--;
                    heavyApiSum = heavyApiCount * heavyApiPr;
                    heavyApiAmount.setText("" + heavyApiCount);
                    heavyApiPrice.setText("" + heavyApiSum);
                    totalSum();
                }
            }
        });

        dynamicPageAmount = (TextView) findViewById(R.id.dynamicpage_amount);
        dynamicPagePrice = (TextView) findViewById(R.id.dynamicpage_price);
        dynamicPagePlus = (ImageView) findViewById(R.id.dydynamicpage_plus);
        dynamicPagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicPageCount = Integer.parseInt(dynamicPageAmount.getText().toString());
                dynamicPageCount++;
                dynamicPageSum = dynamicPageCount * dynamicPagePr;
                dynamicPageAmount.setText("" + dynamicPageCount);
                dynamicPagePrice.setText("" + dynamicPageSum);
                totalSum();
            }
        });
        dynamicPageMinus = (ImageView) findViewById(R.id.dynamicpage_minus);
        dynamicPageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicPageCount = Integer.parseInt(dynamicPageAmount.getText().toString());

                if(dynamicPageCount>0){
                    dynamicPageCount--;
                    dynamicPageSum = dynamicPageCount * dynamicPagePr;
                    dynamicPageAmount.setText("" + dynamicPageCount);
                    dynamicPagePrice.setText("" + dynamicPageSum);
                    totalSum();
                }
            }
        });


        featureAmount = (TextView) findViewById(R.id.feature_amount);
        featurePrice = (TextView) findViewById(R.id.feature_price);
        featurePlus = (ImageView) findViewById(R.id.feature_plus);
        featurePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featureCount = Integer.parseInt(featureAmount.getText().toString());
                featureCount++;
                featureSum = featureCount * featurePr;
                featureAmount.setText("" + featureCount);
                featurePrice.setText("" + featureSum);
                totalSum();
            }
        });
        featureMinus = (ImageView) findViewById(R.id.feature_minus);
        featureMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featureCount = Integer.parseInt(featureAmount.getText().toString());
                if(featureCount>0){
                    featureCount--;
                    featureSum = featureCount * featurePr;
                    featureAmount.setText("" + featureCount);
                    featurePrice.setText("" + featureSum);
                    totalSum();
                }
            }
        });


        moderateFeatureAmount = (TextView) findViewById(R.id.moderatefeature_amount);
        moderateFeaturePrice = (TextView) findViewById(R.id.moderatefeature_price);
        modeateFeaturePlus = (ImageView) findViewById(R.id.moderatefeature_plus);
        modeateFeaturePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moderateFeatureCount = Integer.parseInt(moderateFeatureAmount.getText().toString());
                moderateFeatureCount++;
                moderateFeatureSum = moderateFeatureCount * moderateFeaturePr;
                moderateFeatureAmount.setText("" + moderateFeatureCount);
                moderateFeaturePrice.setText("" + moderateFeatureSum);
                totalSum();
            }
        });
        modeateFeatureMinus = (ImageView) findViewById(R.id.moderatefeature_minus);
        modeateFeatureMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moderateFeatureCount = Integer.parseInt(moderateFeatureAmount.getText().toString());

                if(moderateFeatureCount>0){
                    moderateFeatureCount--;
                    moderateFeatureSum = moderateFeatureCount * moderateFeaturePr;
                    moderateFeatureAmount.setText("" + moderateFeatureCount);
                    moderateFeaturePrice.setText("" + moderateFeatureSum);
                    totalSum();
                }
            }
        });

        premiumFeatureAmount = (TextView) findViewById(R.id.premiumfeature_amount);
        premiumFeaturePrice = (TextView) findViewById(R.id.premiumfeature_price);
        premiumFeaturePlus = (ImageView) findViewById(R.id.premiumfeature_plus);
        premiumFeaturePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premiumFeatureCount = Integer.parseInt(premiumFeatureAmount.getText().toString());
                premiumFeatureCount++;
                premiumFeatureSum = premiumFeatureCount * premiumFeaturePr;
                premiumFeatureAmount.setText("" + premiumFeatureCount);
                premiumFeaturePrice.setText("" + premiumFeatureSum);
                totalSum();
            }
        });
        premiumFeatureMinus = (ImageView) findViewById(R.id.premiumfeature_minus);
        premiumFeatureMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premiumFeatureCount = Integer.parseInt(premiumFeatureAmount.getText().toString());

                if(premiumFeatureCount>0){
                    premiumFeatureCount--;
                    premiumFeatureSum = premiumFeatureCount * premiumFeaturePr;
                    premiumFeatureAmount.setText("" + premiumFeatureCount);
                    premiumFeaturePrice.setText("" + premiumFeatureSum);
                    totalSum();
                }
            }
        });

        CardView btnBuild = (CardView) findViewById(R.id.btn_build);
        btnBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyingInstruction();
            }
        });

        totalPrice = (TextView) findViewById(R.id.total_price);

        getUserData();

    }

    private void buyingInstruction(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.payment_instructions, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        Button btnContinue = (Button) promptView.findViewById(R.id.btn_continue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // btnAdd1 has been clicked
                alertD.cancel();
                buyService();
            }
        });

        alertD.setView(promptView);
        alertD.show();
    }

    void totalSum(){
    total = hostingSum + domainPr + installationPr + emailPr + pageSum + dynamicPageSum + featureSum + moderateFeatureSum
            + premiumFeatureSum + apiSum + heavyApiSum ;

    withoutDiscount.setText(""+total);

    double less = total*(.20);
    total = total - (int) less;

    discountLess.setText(""+(int) less);
    totalPrice.setText(""+ total);
    }

    private void buyService(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.service_buy, null);

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
                        serviceidea = serviceIdea.getText().toString();

                        createService();
//                        Toast.makeText(FacebookadsActivity.this, "Confirmed!", Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(FirebasewebsiteActivity.this)
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


    private void createService(){
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("services");

        serviceid = mUser.getUid()+ UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        servicestatus = "pending";
        servicemilestone = "0";
        servicecategory = "firebasewebsite";
        serviceownerid = mUser.getUid();
        serviceownername = mUser.getDisplayName();
        servicetime = getTime();
        servicedate = getDate();
        milestonereached = "0";
        servicecost = totalPrice.getText().toString();

        service serv = new service(serviceid, servicestatus, servicemilestone, servicecategory, serviceownerid, serviceownername,
                servicetime, servicedate,
                buyingaccounttype, buyingaccountnumber, buyingtrxid, servicecost, serviceidea, milestonereached);

        mRef.child(serviceid).setValue(serv);

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

            confirm.setEnabled(!(stNumber.length()<11) && !stAmount.isEmpty() && !stNumber.isEmpty() && !stNumber2.isEmpty() && stNumber.equalsIgnoreCase(stNumber2));
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

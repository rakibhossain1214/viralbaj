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

public class WebsiteplanActivity extends AppCompatActivity {

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
    int hostingPr = 1000;
    int hostingCount = 0;
    int hostingSum = 0;

    Spinner spinnerDomain;
    TextView domainPrice;
    int domainPr = 1000;

    TextView pageAmount, pagePrice;
    ImageView pagePlus;
    ImageView pageMinus;
    int pagePr = 500;
    int pageCount = 0;
    int pageSum = 0;


    TextView moderatePageAmount, moderatePagePrice;
    ImageView moderatePagePlus;
    ImageView moderatePageMinus;
    int moderatePagePr = 1000;
    int moderatePageCount = 0;
    int moderatePageSum = 0;

    TextView heavyPageAmount, heavyPagePrice;
    ImageView heavyPagePlus;
    ImageView heavyPageMinus;
    int heavyPagePr = 3000;
    int heavyPageCount = 0;
    int heavyPageSum = 0;

    TextView apiAmount, apiPrice;
    ImageView apiPlus;
    ImageView apiMinus;
    int apiPr = 1000;
    int apiCount = 0;
    int apiSum = 0;

    TextView heavyApiAmount, heavyApiPrice;
    ImageView heavyApiPlus;
    ImageView heavyApiMinus;
    int heavyApiPr = 3000;
    int heavyApiCount = 0;
    int heavyApiSum = 0;

    TextView pluginAmount, pluginPrice;
    ImageView pluginPlus;
    ImageView pluginMinus;
    int pluginPr = 2000;
    int pluginCount = 0;
    int pluginSum = 0;


    TextView themeAmount, themePrice;
    ImageView themePlus;
    ImageView themeMinus;
    int themePr = 3000;
    int themeCount = 0;
    int themeSum = 0;

    TextView uniquePluginAmount, uniquePluginPrice;
    ImageView uniquePluginPlus;
    ImageView uniquePluginMinus;
    int uniquePluginPr = 15000;
    int uniquePluginCount = 0;
    int uniquePluginSum = 0;

    TextView uniqueThemeAmount, uniqueThemePrice;
    ImageView uniqueThemePlus;
    ImageView uniqueThemeMinus;
    int uniqueThemePr = 20000;
    int uniqueThemeCount = 0;
    int uniqueThemeSum = 0;

    TextView totalPrice;

    private static final String[] pathsDomain = {"yes", "no"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websiteplan);

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
                hostingSum = hostingCount * hostingPr;
                hostingAmount.setText(""+hostingCount);
                hostingPrice.setText("" + hostingSum);
                totalSum();
            }
        });
        hostingMinus = (ImageView) findViewById(R.id.hosting_minus);
        hostingMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostingCount = Integer.parseInt(hostingAmount.getText().toString());

                if(hostingCount>0) {
                    hostingCount--;
                    hostingSum = hostingCount * hostingPr;
                    hostingAmount.setText("" + hostingCount);
                    hostingPrice.setText("" + hostingSum);
                    totalSum();
                }
            }
        });

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
        moderatePageAmount = (TextView) findViewById(R.id.moderatepage_amount);
        moderatePagePrice = (TextView) findViewById(R.id.moderatepage_price);
        moderatePagePlus = (ImageView) findViewById(R.id.moderatepage_plus);
        moderatePagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moderatePageCount = Integer.parseInt(moderatePageAmount.getText().toString());
                moderatePageCount++;
                moderatePageSum = moderatePageCount * moderatePagePr;
                moderatePageAmount.setText("" + moderatePageCount);
                moderatePagePrice.setText("" + moderatePageSum);
                totalSum();

            }
        });
        moderatePageMinus = (ImageView) findViewById(R.id.moderatepage_minus);
        moderatePageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moderatePageCount = Integer.parseInt(moderatePageAmount.getText().toString());

                if(moderatePageCount>0) {
                    moderatePageCount--;
                    moderatePageSum = moderatePageCount * moderatePagePr;
                    moderatePageAmount.setText("" + moderatePageCount);
                    moderatePagePrice.setText("" + moderatePageSum);
                    totalSum();
                }

            }
        });

        heavyPageAmount = (TextView) findViewById(R.id.heavypage_amount);
        heavyPagePrice = (TextView) findViewById(R.id.heavypage_price);
        heavyPagePlus = (ImageView) findViewById(R.id.heavypage_plus);
        heavyPagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heavyPageCount = Integer.parseInt(heavyPageAmount.getText().toString());
                heavyPageCount++;
                heavyPageSum = heavyPageCount * heavyPagePr;
                heavyPageAmount.setText("" + heavyPageCount);
                heavyPagePrice.setText("" + heavyPageSum);
                totalSum();

            }
        });
        heavyPageMinus = (ImageView) findViewById(R.id.heavypage_minus);
        heavyPageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heavyPageCount = Integer.parseInt(heavyPageAmount.getText().toString());

                if(heavyPageCount>0) {
                    heavyPageCount--;
                    heavyPageSum = heavyPageCount * heavyPagePr;
                    heavyPageAmount.setText("" + heavyPageCount);
                    heavyPagePrice.setText("" + heavyPageSum);
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
                if(apiCount>0) {
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
                if(heavyApiCount>0) {
                    heavyApiCount--;
                    heavyApiSum = heavyApiCount * heavyApiPr;
                    heavyApiAmount.setText("" + heavyApiCount);
                    heavyApiPrice.setText("" + heavyApiSum);
                    totalSum();
                }

            }
        });

        pluginAmount = (TextView) findViewById(R.id.plugin_amount);
        pluginPrice = (TextView) findViewById(R.id.plugin_price);
        pluginPlus = (ImageView) findViewById(R.id.plugin_plus);
        pluginPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pluginCount = Integer.parseInt(pluginAmount.getText().toString());
                pluginCount++;
                pluginSum = pluginCount * pluginPr;
                pluginAmount.setText("" + pluginCount);
                pluginPrice.setText("" + pluginSum);
                totalSum();
            }
        });
        pluginMinus = (ImageView) findViewById(R.id.plugin_minus);
        pluginMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pluginCount = Integer.parseInt(pluginAmount.getText().toString());
                if(pluginCount>0) {
                    pluginCount--;
                    pluginSum = pluginCount * pluginPr;
                    pluginAmount.setText("" + pluginCount);
                    pluginPrice.setText("" + pluginSum);
                    totalSum();
                }

            }
        });

        themeAmount = (TextView) findViewById(R.id.theme_amount);
        themePrice = (TextView) findViewById(R.id.theme_price);
        themePlus = (ImageView) findViewById(R.id.theme_plus);
        themePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeCount = Integer.parseInt(themeAmount.getText().toString());
                themeCount++;
                themeSum = themeCount * themePr;
                themeAmount.setText("" + themeCount);
                themePrice.setText("" + themeSum);
                totalSum();
            }
        });
        themeMinus = (ImageView) findViewById(R.id.theme_minus);
        themeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeCount = Integer.parseInt(themeAmount.getText().toString());
                if(themeCount>0) {
                    themeCount--;
                    themeSum = themeCount * themePr;
                    themeAmount.setText("" + themeCount);
                    themePrice.setText("" + themeSum);
                    totalSum();
                }

            }
        });

        uniquePluginAmount = (TextView) findViewById(R.id.uniqueplugin_amount);
        uniquePluginPrice = (TextView) findViewById(R.id.uniqueplugin_price);
        uniquePluginPlus = (ImageView) findViewById(R.id.uniqueplugin_plus);
        uniquePluginPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uniquePluginCount = Integer.parseInt(uniquePluginAmount.getText().toString());
                uniquePluginCount++;
                uniquePluginSum = uniquePluginCount * uniquePluginPr;
                uniquePluginAmount.setText("" + uniquePluginCount);
                uniquePluginPrice.setText("" + uniquePluginSum);
                totalSum();
            }
        });
        uniquePluginMinus = (ImageView) findViewById(R.id.uniqueplugin_minus);
        uniquePluginMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uniquePluginCount = Integer.parseInt(uniquePluginAmount.getText().toString());
                if(uniquePluginCount>0) {
                    uniquePluginCount--;
                    uniquePluginSum = uniquePluginCount * uniquePluginPr;
                    uniquePluginAmount.setText("" + uniquePluginCount);
                    uniquePluginPrice.setText("" + uniquePluginSum);
                    totalSum();
                }

            }
        });

        uniqueThemeAmount = (TextView) findViewById(R.id.uniquetheme_amount);
        uniqueThemePrice = (TextView) findViewById(R.id.uniquetheme_price);
        uniqueThemePlus = (ImageView) findViewById(R.id.uniquetheme_plus);
        uniqueThemePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uniqueThemeCount = Integer.parseInt(uniqueThemeAmount.getText().toString());
                uniqueThemeCount++;
                uniqueThemeSum = uniqueThemeCount * uniqueThemePr;
                uniqueThemeAmount.setText("" + uniqueThemeCount);
                uniqueThemePrice.setText("" + uniqueThemeSum);
                totalSum();
            }
        });
        uniqueThemeMinus = (ImageView) findViewById(R.id.uniquetheme_minus);
        uniqueThemeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uniqueThemeCount = Integer.parseInt(uniqueThemeAmount.getText().toString());
                if(uniqueThemeCount>0) {
                    uniqueThemeCount--;
                    uniqueThemeSum = uniqueThemeCount * uniqueThemePr;
                    uniqueThemeAmount.setText("" + uniqueThemeCount);
                    uniqueThemePrice.setText("" + uniqueThemeSum);
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

    void totalSum(){
        total = hostingSum + domainPr + pageSum + moderatePageSum + heavyPageSum + apiSum + heavyApiSum + uniqueThemeSum + uniquePluginSum + themeSum + pluginSum;

        withoutDiscount.setText(""+total);

        double less = total*(.20);
        total = total - (int) less;

        discountLess.setText(""+(int) less);

        totalPrice.setText(""+ total);
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
                        new AlertDialog.Builder(WebsiteplanActivity.this)
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
        servicecategory = "regularwebsite";
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

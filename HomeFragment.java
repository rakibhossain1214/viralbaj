package com.example.viralbaj;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FirebaseRecyclerOptions<ads> options;
    private FirebaseRecyclerAdapter<ads, ActiveAdsHolder> adapter;
    FirebaseUser mUser;

    TextView tvUserCoin;
    TextView tvUserBalance;

    String tvBalance;

    ArrayList<String> allLocations, allAgeRange;
    List<PieEntry> pieEntriesLocation, pieEntriesGender, pieEntriesAgeRange;

    int counter = 0;

    String adId;

    String coinBalance = "0.0";

    String uEmail = "";

    String uMobile = "";

    String amount, accountNumber;

    int maleCounter = 0, femaleCounter = 0;


    EditText amountBal;
    EditText numberBal, number2Bal;
    Button confirmBal;
    Button cancelBal;
    //

    Spinner spinner;

    private static final String[] paths = {"bkash", "rocket"};
    String accountType = "bkash";

    public HomeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        adId = "";

        ProgressBar progressAd = (ProgressBar) v.findViewById(R.id.progress_ads);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ads");
        options = new FirebaseRecyclerOptions.Builder<ads>().setQuery(databaseReference,ads.class).build();

        try{
            adapter = new FirebaseRecyclerAdapter<ads, ActiveAdsHolder>(options) {

                @NonNull
                @Override
                public ActiveAdsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_adsprogress, viewGroup, false);
                    return new ActiveAdsHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull final ActiveAdsHolder holder, int position, @NonNull final ads model) {

                    if(model.getAdstatus().equals("active")) {
                        if(model.getAdownerid().equals(mUser.getUid())){
                            if (model.getAdcategory().equals("facebook")) {
                                holder.imageViewCategory.setImageResource(R.drawable.facebook);
                                holder.progressBar.setMax(Integer.parseInt(model.getAdcount()));
                                holder.progressBar.setProgress(Integer.parseInt(model.getReached()));
                                holder.earningType.setText(model.getBuyingtype());
                                holder.textViewProgress.setText("progress : "+Integer.parseInt(model.getReached())+"/"+Integer.parseInt(model.getAdcount()));

                                holder.btnLocationChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadLocationChart();
                                    }
                                });

                                holder.btnGenderChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadGenderChart();
                                    }
                                });

                                holder.btnAgeChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadAgeRangeChart();
                                    }
                                });

                                holder.btnList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadWorkerList();
                                    }
                                });


                            } else if (model.getAdcategory().equals("instagram")) {
                                holder.imageViewCategory.setImageResource(R.drawable.instagram);
                                holder.progressBar.setMax(Integer.parseInt(model.getAdcount()));
                                holder.progressBar.setProgress(Integer.parseInt(model.getReached()));
                                holder.earningType.setText(model.getBuyingtype());
                                holder.textViewProgress.setText("progress : "+Integer.parseInt(model.getReached())+"/"+Integer.parseInt(model.getAdcount()));

                                holder.btnLocationChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadLocationChart();
                                    }
                                });

                                holder.btnGenderChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadGenderChart();
                                    }
                                });

                                holder.btnAgeChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadAgeRangeChart();
                                    }
                                });

                                holder.btnList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadWorkerList();
                                    }
                                });

                            } else if (model.getAdcategory().equals("youtube")) {
                                holder.imageViewCategory.setImageResource(R.drawable.youtube);
                                holder.progressBar.setMax(Integer.parseInt(model.getAdcount()));
                                holder.progressBar.setProgress(Integer.parseInt(model.getReached()));
                                holder.earningType.setText(model.getBuyingtype());
                                holder.textViewProgress.setText("progress : "+Integer.parseInt(model.getReached())+"/"+Integer.parseInt(model.getAdcount()));

                                holder.btnLocationChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadLocationChart();
                                    }
                                });

                                holder.btnGenderChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadGenderChart();
                                    }
                                });

                                holder.btnAgeChart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadAgeRangeChart();
                                    }
                                });

                                holder.btnList.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        adId = model.getAdid();
                                        loadWorkerList();
                                    }
                                });

                            }
                        }else{
                            holder.adCard.setVisibility(View.GONE);
                        }
                    }else{
                        holder.adCard.setVisibility(View.GONE);
                    }

                }
            };
            progressAd.setVisibility(View.GONE);
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        CardView btnBalanceTransfer = (CardView) v.findViewById(R.id.btn_balancetransfer);
        btnBalanceTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balanceTransfer();
            }
        });


        tvUserCoin = (TextView) v.findViewById(R.id.textView_user_coins);
        tvUserBalance = (TextView) v.findViewById(R.id.textView_user_balance);

        tvBalance = "0";

        CardView btnAdsReport = (CardView) v.findViewById(R.id.ad_report);
        btnAdsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AdsreportActivity.class);
                startActivity(intent);
            }
        });

        CardView btnServiceReport = (CardView) v.findViewById(R.id.service_report);
        btnServiceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ServicereportActivity.class);
                startActivity(intent);
            }
        });

        loadUserData();

        return v;
    }

    private void loadUserData(){

        try{
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();

            ref.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    users user = dataSnapshot.getValue(users.class);

                    if(user!=null){
                        coinBalance = user.getBalance();
                        uEmail = user.getEmail();
                        uMobile = user.getMobileno();
                        double coin = Double.parseDouble(coinBalance);
                        double bal = coin/2;
                        tvUserCoin.setText(String.format("%.2f", coin));
                        tvUserBalance.setText(String.format("%.2f", bal));
                        tvBalance = tvUserBalance.getText().toString();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    //balance transfer

    private void balanceTransfer(){
        LayoutInflater layoutInflaterBal = LayoutInflater.from(getContext());
        View promptViewBal = layoutInflaterBal.inflate(R.layout.balance_transfer, null);

        final AlertDialog alertDBal = new AlertDialog.Builder(getContext()).create();

        amountBal = (EditText) promptViewBal.findViewById(R.id.balance_transfer_amount);
        numberBal = (EditText) promptViewBal.findViewById(R.id.balance_transfer_number);
        number2Bal = (EditText) promptViewBal.findViewById(R.id.bal_trans_val);


        amountBal.addTextChangedListener(textWatcherBal);
        numberBal.addTextChangedListener(textWatcherBal);
        number2Bal.addTextChangedListener(textWatcherBal);

        confirmBal = (Button) promptViewBal.findViewById(R.id.btn_transfer_confirm);
        cancelBal = (Button) promptViewBal.findViewById(R.id.btn_transfer_cancel);


        spinner = (Spinner)promptViewBal.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        accountType = "bkash";
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        accountType = "rocket";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmBal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    double withdrawAmount = Double.parseDouble(amountBal.getText().toString());
                    double currentBalance = Double.parseDouble(tvBalance);

                    if(withdrawAmount>=100 && withdrawAmount <= currentBalance){

                        if (numberBal.getText().toString().equals(number2Bal.getText().toString())){

                            accountNumber = numberBal.getText().toString();
                            amount = String.valueOf(withdrawAmount);
                            balanceTransferRequest();

                            double left = currentBalance - withdrawAmount;
                            updateCoins(left);

                            alertDBal.cancel();
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Congratulations!")
                                    .setMessage("Your request to transfer bdt. "+withdrawAmount+" has been received! Please wait for 24 hours for confirmation.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                           onStart();
                                        }
                                    }).create().show();
                        }else{
                            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Invalid amount!", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(), "Invalid data!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelBal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDBal.cancel();
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                // btnAdd2 has been clicked
            }
        });
        alertDBal.setView(promptViewBal);
        alertDBal.show();
    }

    private void balanceTransferRequest(){

        try{
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference setRequestRefAmount = mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/amount/");
            setRequestRefAmount.setValue(amount);

            DatabaseReference setRequestRefAccountNumber= mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/accountnumber/");
            setRequestRefAccountNumber.setValue(accountNumber);

            DatabaseReference setRequestUserId = mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/uid/");
            setRequestUserId.setValue(mUser.getUid());

            DatabaseReference setRequestUserName = mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/name/");
            setRequestUserName.setValue(mUser.getDisplayName());

            DatabaseReference setRequestUserEmail = mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/email/");
            setRequestUserEmail.setValue(uEmail);

            DatabaseReference setRequestUserMobile = mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/mobileno/");
            setRequestUserMobile.setValue(uMobile);

            DatabaseReference setRequestAcountType = mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/accounttype/");
            setRequestAcountType.setValue(accountType);

            DatabaseReference setRequestStatus = mDatabase.getReference("balancetransfer/"+mUser.getUid()+"/status/");
            setRequestStatus.setValue("pending");
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


//        "balancetransfer/"+mUser.getUid()+"/balance/"
    }

    private void updateCoins(final double left){
        try{
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference updateCoinsRef = mDatabase.getReference("users/"+mUser.getUid()+"/balance/");
            updateCoinsRef.setValue(""+left*2);
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    private TextWatcher textWatcherBal = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String stAmount = amountBal.getText().toString().trim();
            String stNumber = numberBal.getText().toString().trim();
            String stNumber2 = number2Bal.getText().toString().trim();

            confirmBal.setEnabled(!(stNumber.length()<11) && !stAmount.isEmpty() && !stNumber2.isEmpty() && stNumber.equalsIgnoreCase(stNumber2));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //worker

    private void loadWorkerList(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.chart_list,null);

        final AlertDialog alertD = new AlertDialog.Builder(getContext()).create();

        TextView tvGoBack = (TextView) promptView.findViewById(R.id.go_back);
        tvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.cancel();
            }
        });

        final ProgressBar progressBar = (ProgressBar) promptView.findViewById(R.id.chart_progress);


        final RecyclerView recyclerView = (RecyclerView) promptView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        try {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ads").child(adId).child("adworkers");
            FirebaseRecyclerOptions<useradinfo> options = new FirebaseRecyclerOptions.Builder<useradinfo>().setQuery(databaseReference, useradinfo.class).build();

            FirebaseRecyclerAdapter<useradinfo, AdsWorkersHolder> adapter = new FirebaseRecyclerAdapter<useradinfo, AdsWorkersHolder>(options) {

                @NonNull
                @Override
                public AdsWorkersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_workerslist, viewGroup, false);
                    return new AdsWorkersHolder(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull final AdsWorkersHolder holder, int position, @NonNull final useradinfo model) {

                    holder.userName.setText(model.getName());
                    holder.userLocation.setText(model.getLocation());
                    holder.userGender.setText(model.getGender());
                    holder.userAge.setText(model.getAgerange());

                    try {
                        Glide.with(getContext())
                            .load(model.getProfileImage())
                            .into(holder.profileImage);

                        progressBar.setVisibility(View.GONE);
                    }catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            };

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        alertD.setView(promptView);
        alertD.show();
    }

    //age

    private void loadAgeRangeChart(){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.chart_age,  null);

        final AlertDialog alertD = new AlertDialog.Builder(getContext()).create();

        pieEntriesAgeRange = new ArrayList<PieEntry>();
        getAllAgeRange();

        final ProgressBar progressBar = (ProgressBar) promptView.findViewById(R.id.chart_progress);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAgeRangeCount();
            }},1000);



        //setupPieChart();
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                TextView tvGoBack = (TextView) promptView.findViewById(R.id.go_back);
                tvGoBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertD.cancel();
                    }
                });

                progressBar.setVisibility(View.GONE);

                PieDataSet dataSet = new PieDataSet(pieEntriesAgeRange,"Age Range Wise Distribution");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueTextSize(20f);

                PieChart chart = (PieChart) promptView.findViewById(R.id.chart);
                chart.setData(data);
                chart.animateY(1000);
                chart.invalidate();

            }},1500);

        alertD.setView(promptView);
        alertD.show();
    }

    private void getAgeRangeCount(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference areaRef = database.getReference("ads");

            areaRef.child(adId).child("adworkers").orderByChild("agerange").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(int i=0; i<allAgeRange.size(); i++){
                        counter = 0;
                        for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                            users user = areaSnapshot.getValue(users.class);
                            if(user!=null && allAgeRange.get(i).contains(user.getAgerange())) {
                                //listLocation.add(user.getLocation());
                                counter++;
                            }
                        }
                        float count = (float) counter;
                        pieEntriesAgeRange.add(new PieEntry(count,allAgeRange.get(i)));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getAllAgeRange(){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference areaRef = database.getReference("ads");

            areaRef.child(adId).child("adworkers").orderByChild("agerange").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    allAgeRange = new ArrayList<String>();
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        users user = areaSnapshot.getValue(users.class);
                        if(user!=null && !allAgeRange.contains(user.getAgerange())) {
                            allAgeRange.add(user.getAgerange());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    //gender

    private void loadGenderChart(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.chart_gender,  null);

        final AlertDialog alertD = new AlertDialog.Builder(getContext()).create();

        pieEntriesGender = new ArrayList<PieEntry>();

        final ProgressBar progressBar = (ProgressBar) promptView.findViewById(R.id.chart_progress);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getGenderCount();
            }},1000);


        //setupPieChart();
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                TextView tvGoBack = (TextView) promptView.findViewById(R.id.go_back);
                tvGoBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertD.cancel();
                    }
                });
                progressBar.setVisibility(View.GONE);
                PieDataSet dataSet = new PieDataSet(pieEntriesGender,"Gender Wise Distribution");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueTextSize(20f);

                PieChart chart = (PieChart) promptView.findViewById(R.id.chart);
                chart.setData(data);
                chart.animateY(1000);
                chart.invalidate();

            }},1500);

        alertD.setView(promptView);
        alertD.show();
    }

    private void getGenderCount(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference areaRef = database.getReference("ads");

            areaRef.child(adId).child("adworkers").orderByChild("gender").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    maleCounter = 0;
                    femaleCounter = 0;
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        users user = areaSnapshot.getValue(users.class);
                        if(user!=null) {
                            //listLocation.add(user.getLocation());
                            if(user.getGender().equals("male")){
                                maleCounter++;
                            }else{
                                femaleCounter++;
                            }
                        }
                    }
                    float countMale = (float) maleCounter;
                    float countfemale = (float) femaleCounter;
                    pieEntriesGender.add(new PieEntry(countMale,"male"));
                    pieEntriesGender.add(new PieEntry(countfemale,"female"));
                    //}
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    //location
    private void loadLocationChart(){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View promptView = layoutInflater.inflate(R.layout.chart_location,  null);

        final AlertDialog alertD = new AlertDialog.Builder(getContext()).create();

        pieEntriesLocation = new ArrayList<PieEntry>();
        getAllLocations();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocationCount();
            }},1000);

        final ProgressBar progressBar = (ProgressBar) promptView.findViewById(R.id.chart_progress);
        //setupPieChart();
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                TextView tvGoBack = (TextView) promptView.findViewById(R.id.go_back);
                tvGoBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertD.cancel();
                    }
                });
                progressBar.setVisibility(View.GONE);
                PieDataSet dataSet = new PieDataSet(pieEntriesLocation,"location Wise Distribution");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueTextSize(20f);

                PieChart chart = (PieChart) promptView.findViewById(R.id.chart);
                chart.setData(data);
                chart.animateY(1000);
                chart.invalidate();

            }},1500);

        alertD.setView(promptView);
        alertD.show();
    }

    private void getLocationCount(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference areaRef = database.getReference("ads");

            areaRef.child(adId).child("adworkers").orderByChild("location").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(int i=0; i<allLocations.size(); i++){
                        counter = 0;
                        for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                            users user = areaSnapshot.getValue(users.class);
                            if(user!=null && allLocations.get(i).contains(user.getLocation())) {
                                //listLocation.add(user.getLocation());
                                counter++;
                            }
                        }
                        float count = (float) counter;
                        pieEntriesLocation.add(new PieEntry(count,allLocations.get(i)));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getAllLocations(){
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference areaRef = database.getReference("ads");

            areaRef.child(adId).child("adworkers").orderByChild("location").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    allLocations = new ArrayList<String>();
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        users user = areaSnapshot.getValue(users.class);
                        if(user!=null && !allLocations.contains(user.getLocation())) {
                            allLocations.add(user.getLocation());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        loadUserData();
    }
}

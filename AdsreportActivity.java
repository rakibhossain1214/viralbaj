package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdsreportActivity extends AppCompatActivity {
    private FirebaseRecyclerOptions<ads> options;
    private FirebaseRecyclerAdapter<ads, AdsHistoryHolder> adapter;
    FirebaseUser mUser;

    int maleCounter = 0, femaleCounter = 0;

    ArrayList<String> allLocations, allAgeRange;
    List<PieEntry> pieEntriesLocation, pieEntriesGender, pieEntriesAgeRange;

    int counter = 0;

    String adId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsreport);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        adId = "";

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ads");
        Query query = databaseReference.orderByChild("addate");
        options = new FirebaseRecyclerOptions.Builder<ads>().setQuery(query,ads.class).build();

        adapter = new FirebaseRecyclerAdapter<ads, AdsHistoryHolder>(options) {

            @NonNull
            @Override
            public AdsHistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_adshistory, viewGroup, false);
                return new AdsHistoryHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final AdsHistoryHolder holder, int position, @NonNull final ads model) {

                    if(model.getAdownerid().equals(mUser.getUid())){

                        holder.adDate.setText("Date: "+model.getAddate());

                        if (model.getAdcategory().equals("facebook")) {
                            holder.imageViewCategory.setImageResource(R.drawable.facebook);
                            holder.earningType.setText(model.getBuyingtype());

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
                            holder.earningType.setText(model.getBuyingtype());

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
                            holder.earningType.setText(model.getBuyingtype());

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


            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //worker

    void loadWorkerList(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.chart_list,null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        final RecyclerView recyclerView = (RecyclerView) promptView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        TextView tvGoBack = (TextView) promptView.findViewById(R.id.go_back);
        tvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.cancel();
            }
        });

        final ProgressBar progressBar = (ProgressBar) promptView.findViewById(R.id.chart_progress);

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
                        Glide.with(getApplicationContext())
                                .load(model.getProfileImage())
                                .into(holder.profileImage);
                        progressBar.setVisibility(View.GONE);
                    }catch (Exception e){
                        Toast.makeText(AdsreportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            };

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(AdsreportActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        alertD.setView(promptView);
        alertD.show();
    }

    //age

    void loadAgeRangeChart(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.chart_age,  null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        pieEntriesAgeRange = new ArrayList<PieEntry>();
        getAllAgeRange();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAgeRangeCount();
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

    void getAgeRangeCount(){
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
    }

    void getAllAgeRange(){
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
    }

    //gender

    void loadGenderChart(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.chart_gender,  null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        pieEntriesGender = new ArrayList<PieEntry>();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getGenderCount();
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

    void getGenderCount(){
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
    }

    //location
    void loadLocationChart(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View promptView = layoutInflater.inflate(R.layout.chart_location,  null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

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

    void getLocationCount(){
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
    }

    void getAllLocations(){
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
    }
}

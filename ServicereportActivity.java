package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ServicereportActivity extends AppCompatActivity {

    private FirebaseRecyclerOptions<ads> options;
    private FirebaseRecyclerAdapter<ads, ServiceProgressHolder> adapter;
    FirebaseUser mUser;

    String adId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicereport);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        adId = "";

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ads");
        options = new FirebaseRecyclerOptions.Builder<ads>().setQuery(databaseReference,ads.class).build();

        adapter = new FirebaseRecyclerAdapter<ads, ServiceProgressHolder>(options) {

            @NonNull
            @Override
            public ServiceProgressHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_serviceprogress, viewGroup, false);
                return new ServiceProgressHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ServiceProgressHolder holder, int position, @NonNull final ads model) {

                if(model.getAdownerid().equals(mUser.getUid())){
                    if (model.getAdcategory().equals("facebook")) {
                        holder.imageViewCategory.setImageResource(R.drawable.facebook);
                        holder.progressBar.setMax(Integer.parseInt(model.getAdcount()));
                        holder.progressBar.setProgress(Integer.parseInt(model.getReached()));
                        holder.earningType.setText(model.getBuyingtype());
                        holder.textViewProgress.setText("progress : "+Integer.parseInt(model.getReached())+"/"+Integer.parseInt(model.getAdcount()));


                    } else if (model.getAdcategory().equals("instagram")) {
                        holder.imageViewCategory.setImageResource(R.drawable.instagram);
                        holder.progressBar.setMax(Integer.parseInt(model.getAdcount()));
                        holder.progressBar.setProgress(Integer.parseInt(model.getReached()));
                        holder.earningType.setText(model.getBuyingtype());
                        holder.textViewProgress.setText("progress : "+Integer.parseInt(model.getReached())+"/"+Integer.parseInt(model.getAdcount()));


                    } else if (model.getAdcategory().equals("youtube")) {
                        holder.imageViewCategory.setImageResource(R.drawable.youtube);
                        holder.progressBar.setMax(Integer.parseInt(model.getAdcount()));
                        holder.progressBar.setProgress(Integer.parseInt(model.getReached()));
                        holder.earningType.setText(model.getBuyingtype());
                        holder.textViewProgress.setText("progress : "+Integer.parseInt(model.getReached())+"/"+Integer.parseInt(model.getAdcount()));

                    }
                }else{
                    holder.adCard.setVisibility(View.GONE);
                }


            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

}

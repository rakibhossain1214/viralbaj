package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecycleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<ads> options;
    FirebaseRecyclerAdapter<ads, AdViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ads");
        options = new FirebaseRecyclerOptions.Builder<ads>().setQuery(databaseReference,ads.class).build();

        adapter = new FirebaseRecyclerAdapter<ads, AdViewHolder>(options) {

            @NonNull
            @Override
            public AdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ad_layout, viewGroup, false);
                return new AdViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AdViewHolder holder, int position, @NonNull ads model) {

                if(model.adcategory.equals("facebook")) {
                   holder.imageViewCategory.setImageResource(R.drawable.facebook);
                }else if(model.adcategory.equals("instagram")){
                    holder.imageViewCategory.setImageResource(R.drawable.instagram);
                }else if(model.adcategory.equals("youtube")){
                    holder.imageViewCategory.setImageResource(R.drawable.youtube);
                }else if(model.adcategory.equals("web")){
                    holder.imageViewCategory.setImageResource(R.drawable.browser);
                }else if(model.adcategory.equals("playstore")){
                    holder.imageViewCategory.setImageResource(R.drawable.playstore);
                }


                holder.earningAmount.setText(model.getAdcost());
                holder.totalCount.setText(model.getAdcount());
                holder.countLeft.setText("0");
                holder.adCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(RecycleActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.startListening();
        }
    }
}

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdsadminActivity extends AppCompatActivity {
    private FirebaseRecyclerOptions<ads> options;
    private FirebaseRecyclerAdapter<ads, AdViewHolderAdmin> adapter;


    FirebaseDatabase database;
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsadmin);

        database = FirebaseDatabase.getInstance();
        mref = database.getReference();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ads");
        options = new FirebaseRecyclerOptions.Builder<ads>().setQuery(databaseReference,ads.class).build();

        adapter = new FirebaseRecyclerAdapter<ads, AdViewHolderAdmin>(options) {

            @NonNull
            @Override
            public AdViewHolderAdmin onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ad_accepter, viewGroup, false);
                return new AdViewHolderAdmin(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final AdViewHolderAdmin holder, int position, @NonNull final ads model) {

                if (model.getAdstatus().equals("pending") || model.getAdstatus().equals("expending")) {
                    holder.tvAdownerId.setText(model.getAdownerid());
                    holder.tvAdownerName.setText(model.getAdownername());
                    holder.tvAdurl.setText(model.getAdurl());
                    holder.tvBuyingType.setText(model.getBuyingtype());
                    holder.tvAdCount.setText(model.getAdcount());
                    holder.tvBuyingAmount.setText(model.getBuyingamount());
                    holder.tvAccountType.setText(model.getBuyingaccounttype());
                    holder.tvTrxId.setText(model.getBuyingtrxid());
                    holder.tvAccountNumber.setText(model.getBuyingaccountnumber());
                    holder.tvAddate.setText(model.getAddate());
                    holder.adStatus.setText(model.getAdstatus());

                    holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           mref.child("ads").child(model.getAdid()).child("adstatus").setValue("active");
                        }
                    });

                    holder.btnReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mref.child("ads").child(model.getAdid()).child("adstatus").setValue("rejected");
                        }
                    });
                }else{
                    holder.adCard.setVisibility(View.GONE);
                }
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null){
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.startListening();
        }
    }
}

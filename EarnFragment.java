package com.example.viralbaj;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EarnFragment extends Fragment implements IEarnListener{

    private IEarnListener mListener; // listener field

    //AdViewHolder holder;
    private FirebaseRecyclerOptions<ads> options;
    private FirebaseRecyclerAdapter<ads, AdViewHolder> adapter;
    private boolean adAvailable;
    private FirebaseUser mUser;

    private String useragerange, usergender, userlocation;

    public EarnFragment() {
        // Required empty public constructor
    }

    public void loadRecycleViewCallback()
    {
        getUserData();
        if (mListener != null) {
            mListener.onDataLoaded();
        }
    }

    ProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_earn, container, false);


        mListener = this;

        adAvailable = true;
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        //getUserData();

        progressBar = (ProgressBar) v.findViewById(R.id.earn_progress);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);


        loadRecycleViewCallback();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }},1500);

        return v;
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

    private void getUserData(){
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference mRef = database.getReference();
            mRef.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    users user = dataSnapshot.getValue(users.class);
                    if (user != null) {
                        userlocation = user.getLocation();
                        useragerange = user.getAgerange();
                        usergender = user.getGender();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDataLoaded() {
        recyclerView.setHasFixedSize(true);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ads");
        options = new FirebaseRecyclerOptions.Builder<ads>().setQuery(databaseReference,ads.class).build();

        adapter = new FirebaseRecyclerAdapter<ads, AdViewHolder>(options) {

            @NonNull
            @Override
            public AdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ad_layout, viewGroup, false);
                return new AdViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final AdViewHolder holder, int position, @NonNull final ads model) {

                if(model.adstatus.equals("active")){

                    if(model.adcategory.equals("facebook")) {
                        final String adId = model.getAdid();
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference ref = database.getReference("ads");

                        if(Integer.parseInt(model.getReached())>=Integer.parseInt(model.getAdcount())){
                            ref.child(adId).child("adstatus").setValue("expired");
                            holder.adCard.setVisibility(View.GONE);
                        }
                        else{
                            if(Integer.parseInt(model.getMinage())<=Integer.parseInt(useragerange) && Integer.parseInt(useragerange)<=Integer.parseInt(model.getMaxage())){
                                if(model.getGender().equals("any") || model.getGender().equals(usergender) ){
                                    if(model.getAdlocationtarget().contains(userlocation) || model.getAdlocationtarget().contains("any")){
                                        try {
                                            ref.child(adId).child("adworkers").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    adAvailable = true;
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        useradinfo adinfos = snapshot.getValue(useradinfo.class);
                                                        if (adinfos!=null) {
                                                            if(adinfos.getUid().equals(mUser.getUid())) {
                                                                adAvailable = false;
                                                                holder.adCard.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    }

                                                    if(adAvailable) {
                                                        holder.imageViewCategory.setImageResource(R.drawable.facebook);
                                                        holder.earningAmount.setText(model.getAdcost());
                                                        holder.totalCount.setText(model.getAdcount());
                                                        holder.countLeft.setText(model.getReached());
                                                        holder.earningType.setText(model.getBuyingtype());
                                                        holder.adCard.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                if(model.getBuyingtype().equals("fbpostreaction")){
                                                                    Intent intent = new Intent(getContext(), FacebookpostlikeActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }else if(model.getBuyingtype().equals("fbpostshare")){
                                                                    Intent intent = new Intent(getContext(), FacebookpostshareActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }else if(model.getBuyingtype().equals("fbpagelike")){
                                                                    Intent intent = new Intent(getContext(), FacebookpagelikeActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }else if(model.getBuyingtype().equals("fbpageshare")){
                                                                    Intent intent = new Intent(getContext(), FacebookpageshareActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }else if(model.getBuyingtype().equals("fbpagereview")){
                                                                    Intent intent = new Intent(getContext(), FacebookpagereviewActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }catch (Exception e){
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        holder.adCard.setVisibility(View.GONE);
                                    }
                                }else{
                                    holder.adCard.setVisibility(View.GONE);
                                }
                            }
                            else{
                                holder.adCard.setVisibility(View.GONE);
                            }
                        }

                    }
                    else if(model.adcategory.equals("instagram")){
                        final String adId = model.getAdid();
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference ref = database.getReference("ads");

                        if(Integer.parseInt(model.getReached())>=Integer.parseInt(model.getAdcount())){
                            ref.child(adId).child("adstatus").setValue("expired");
                            holder.adCard.setVisibility(View.GONE);
                        }else{
                            if(Integer.parseInt(model.getMinage())<=Integer.parseInt(useragerange) && Integer.parseInt(useragerange)<=Integer.parseInt(model.getMaxage())){
                                if(model.getGender().contains("any") || model.getGender().contains(usergender) ){
                                    if(model.getAdlocationtarget().contains(userlocation) || model.getAdlocationtarget().contains("any")){
                                        try {
                                            ref.child(adId).child("adworkers").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    adAvailable = true;
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        useradinfo adinfos = snapshot.getValue(useradinfo.class);
                                                        if (adinfos!=null) {
                                                            if(adinfos.getUid().equals(mUser.getUid())) {
                                                                adAvailable = false;
                                                                holder.adCard.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    }

                                                    if(adAvailable) {
                                                        holder.imageViewCategory.setImageResource(R.drawable.instagram);
                                                        holder.earningAmount.setText(model.getAdcost());
                                                        holder.totalCount.setText(model.getAdcount());
                                                        holder.countLeft.setText(model.getReached());
                                                        holder.earningType.setText(model.getBuyingtype());
                                                        holder.adCard.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                if(model.getBuyingtype().equals("instagramlikes")){
                                                                    Intent intent = new Intent(getContext(), InstagrampostlikeActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }else if(model.getBuyingtype().equals("instagramfollowers")){
                                                                    Intent intent = new Intent(getContext(), InstagramfollowerActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }catch (Exception e){
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        holder.adCard.setVisibility(View.GONE);
                                    }
                                }else{
                                    holder.adCard.setVisibility(View.GONE);
                                }
                            }
                            else{
                                holder.adCard.setVisibility(View.GONE);
                            }
                        }
                    }else if(model.adcategory.equals("youtube")){
                        final String adId = model.getAdid();
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference ref = database.getReference("ads");

                        if(Integer.parseInt(model.getReached())>=Integer.parseInt(model.getAdcount())){
                            ref.child(adId).child("adstatus").setValue("expired");
                            holder.adCard.setVisibility(View.GONE);
                        }else{
                            if(Integer.parseInt(model.getMinage())<=Integer.parseInt(useragerange) && Integer.parseInt(useragerange)<=Integer.parseInt(model.getMaxage())){
                                if(model.getGender().equals("any") || model.getGender().equals(usergender) ){
                                    if(model.getAdlocationtarget().contains(userlocation) || model.getAdlocationtarget().equals("any")){
                                        try {
                                            ref.child(adId).child("adworkers").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    adAvailable = true;
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        useradinfo adinfos = snapshot.getValue(useradinfo.class);
                                                        if (adinfos!=null) {
                                                            if(adinfos.getUid().equals(mUser.getUid())) {
                                                                adAvailable = false;
                                                                holder.adCard.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    }

                                                    if(adAvailable) {
                                                        holder.imageViewCategory.setImageResource(R.drawable.youtube);
                                                        holder.earningAmount.setText(model.getAdcost());
                                                        holder.totalCount.setText(model.getAdcount());
                                                        holder.countLeft.setText(model.getReached());
                                                        holder.earningType.setText(model.getBuyingtype());
                                                        holder.adCard.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                if(model.getBuyingtype().equals("youtubelikes")){
                                                                    Intent intent = new Intent(getContext(), YoutubepostlikeActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }else if(model.getBuyingtype().equals("youtubeviews")){
                                                                    Intent intent = new Intent(getContext(), YoutubepostviewActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }else if(model.getBuyingtype().equals("youtubesubscribers")){
                                                                    Intent intent = new Intent(getContext(), YoutubesubscriberActivity.class);
                                                                    intent.putExtra("AD_ID", model.getAdid());
                                                                    intent.putExtra("AD_COST", model.getAdcost());
                                                                    intent.putExtra("AD_TYPE", model.getBuyingtype());
                                                                    intent.putExtra("AD_URL", model.getAdurl());
                                                                    intent.putExtra("AD_COUNT", model.getAdcount());
                                                                    intent.putExtra("AD_REACH", model.getReached());
                                                                    startActivity(intent);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }catch (Exception e){
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        holder.adCard.setVisibility(View.GONE);
                                    }
                                }else{
                                    holder.adCard.setVisibility(View.GONE);
                                }
                            }
                            else{
                                holder.adCard.setVisibility(View.GONE);
                            }
                        }
                    }

                }else{
                    holder.adCard.setVisibility(View.GONE);
                }
            }

        };

        progressBar.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
package com.example.viralbaj;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;



public class GoviralFragment extends Fragment {
    //CardView promoAds, facebookAds, instagramAds, youtubeAds, playstoreAds, webAds;

    public GoviralFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goviral, container, false);

        CardView btnFacebook = (CardView) v.findViewById(R.id.facebook_boost);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FacebookadsActivity.class);
                startActivity(intent);
            }
        });

        CardView btnInstagram = (CardView) v.findViewById(R.id.instagram_boost);
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InstagramadsActivity.class);
                startActivity(intent);
            }
        });

        CardView btnYoutube = (CardView) v.findViewById(R.id.youtube_boost);
        btnYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), YoutubeadsActivity.class);
                startActivity(intent);
            }
        });

        CardView btnWebsite = (CardView) v.findViewById(R.id.btn_website);
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebsiteplanActivity.class);
                startActivity(intent);
            }
        });


        CardView btnWebsiteFirebase = (CardView) v.findViewById(R.id.btn_website_firebase);
        btnWebsiteFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FirebasewebsiteActivity.class);
                startActivity(intent);
            }
        });

        CardView btnAndroidApp = (CardView) v.findViewById(R.id.android_app);
        btnAndroidApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AndroidappActivity.class);
                startActivity(intent);
            }
        });

        CardView btnSocialBoost = (CardView) v.findViewById(R.id.social_boost);
        btnSocialBoost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SocialboostActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }

}

package com.example.viralbaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button adAcceptReject = (Button) findViewById(R.id.btn_ad);
        adAcceptReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AdsadminActivity.class);
                startActivity(intent);
            }
        });

        Button createPromoCode = (Button) findViewById(R.id.btn_promocode);
        createPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, CreatepromocodeActivity.class);
                startActivity(intent);
            }
        });


        Button createweb = (Button) findViewById(R.id.btn_service);
        createweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, SocialLogin.class);
                startActivity(intent);
            }
        });




    }
}

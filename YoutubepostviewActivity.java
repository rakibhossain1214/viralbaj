package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderLayout;

public class YoutubepostviewActivity extends AppCompatActivity {
    String adId;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    TextView tvHint, tvCountDownTimer;
    Button btnSocialLogin;

    String adType, adCost, adUrl, adCount, adReach;

    float startX, startY;

    WebView webView;

    String userBalance, name, profileImage, agerange, gender, location;

    CountDownTimer countDownTimer;

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtubepostview);

        adId = getIntent().getStringExtra("AD_ID");
        adCost = getIntent().getStringExtra("AD_COST");
        adType = getIntent().getStringExtra("AD_TYPE");
        adUrl = getIntent().getStringExtra("AD_URL");
        adCount = getIntent().getStringExtra("AD_COUNT");
        adReach = getIntent().getStringExtra("AD_REACH");

        //Toast.makeText(this, "Count: "+adCount + " Reach: "+adReach, Toast.LENGTH_SHORT).show();

        tvHint = (TextView) findViewById(R.id.tv_hint);
        Animation shake = AnimationUtils.loadAnimation(YoutubepostviewActivity.this, R.anim.shake);
        tvHint.startAnimation(shake);
        btnSocialLogin = (Button) findViewById(R.id.btn_sociallogin);

        tvCountDownTimer = (TextView) findViewById(R.id.tv_timer);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setUserAgentString("Android");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(adUrl);

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();
                        if (isAClick(startX, endX, startY, endY)) {
                            countDownTimer.cancel();
                            tvCountDownTimer.setText("20s");
                            webView.setVisibility(View.GONE);
                            retryAlert();
                            Toast.makeText(YoutubepostviewActivity.this, "You have touched the screen! Try again.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false;
            }
        });

        getUserData();

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCountDownTimer.setText("Left: "+millisUntilFinished / 1000+"s");
            }

            public void onFinish() {
                tvCountDownTimer.setText("done!");
                getAdData();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Write whatever to want to do after delay specified (1 sec)
                        likeSuccessActivity();
                    }
                }, 2000);
            }
        }.start();

        btnSocialLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socialLogin();
            }
        });

    }

    private boolean isAClick(float startX, float endX, float startY, float endY) {

        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);

        int CLICK_ACTION_THRESHOLD = 200;
        return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
    }

    void retryAlert(){
        new AlertDialog.Builder(this)
                .setTitle("Please try again!")
                .setMessage("Do you want to try again?")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl(adUrl);
                        countDownTimer.start();
                    }
                }).create().show();
    }

    private void likeSuccessActivity() {
        double bal = Double.parseDouble(userBalance)+Double.parseDouble(adCost);

        int count = Integer.parseInt(adReach)+1;

        if(Integer.parseInt(adReach)>=Integer.parseInt(adCount)){
            mRef.child("ads").child(adId).child("adstatus").setValue("expired");
            Toast.makeText(this, "Already ad count reached to target!", Toast.LENGTH_SHORT).show();
        }else{
            mRef.child("users").child(mUser.getUid()).child("balance").setValue(""+bal);
            mRef.child("ads").child(adId).child("reached").setValue(""+count);
        }

        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You have received "+adCost+" coin(s)!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }

    void getAdData(){

        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("ads");

            reference.child(adId).child("reached").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adReach = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    void getUserData(){
        mRef.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users user = dataSnapshot.getValue(users.class);
                if(user!=null){
                    userBalance = user.getBalance();
                }else{
                    userBalance = "0";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    void socialLogin(){
        Intent intent = new Intent(YoutubepostviewActivity.this,SocialLogin.class);
        intent.putExtra("url","https://www.youtube.com");
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        countDownTimer.start();
    }

}

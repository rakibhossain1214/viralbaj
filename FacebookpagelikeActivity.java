package com.example.viralbaj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookpagelikeActivity extends AppCompatActivity {
    String adId;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    SliderLayout sliderLayout;
    TextView tvHint, tvClickCount;
    Button btnSocialLogin, btnGetHelp;

    String adType, adCost, adUrl, adCount, adReach;

    int vibrant;
    int vibrantLight;
    int vibrantDark;
    int muted;
    int mutedLight;
    int mutedDark;

//    Button btnRetry;
    WebView webView;
    ImageView imageView;
    Bitmap bitmap;
    private float startX;
    private float startY;
    boolean matched;

    TextView text;

    String userBalance, name, profileImage, agerange, gender, location;

    useradinfo useradinfos;


    ProgressBar progressBar;

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebookpagelike);

        adId = getIntent().getStringExtra("AD_ID");
        adCost = getIntent().getStringExtra("AD_COST");
        adType = getIntent().getStringExtra("AD_TYPE");
        adUrl = getIntent().getStringExtra("AD_URL");
        adCount = getIntent().getStringExtra("AD_COUNT");
        adReach = getIntent().getStringExtra("AD_REACH");

        //Toast.makeText(this, "Count: "+adCount + " Reach: "+adReach, Toast.LENGTH_SHORT).show();

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        tvClickCount = (TextView) findViewById(R.id.tv_clickcount);
        tvHint = (TextView) findViewById(R.id.tv_hint);
        Animation shake = AnimationUtils.loadAnimation(FacebookpagelikeActivity.this, R.anim.shake);
        tvHint.startAnimation(shake);
        btnSocialLogin = (Button) findViewById(R.id.btn_sociallogin);
        btnGetHelp = (Button) findViewById(R.id.btn_gethelp);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        getUserData();

//        btnRetry = (Button) findViewById(R.id.btn_retry);
        webView = (WebView) findViewById(R.id.webView);
        imageView = (ImageView) findViewById(R.id.imageView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Android");
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

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

                            progressBar.setVisibility(View.VISIBLE);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Write whatever to want to do after delay specified (1 sec)
                                    webView.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                    bitmap = returnRoot();
                                    imageView.setImageBitmap(bitmap);

                                    Animation shake = AnimationUtils.loadAnimation(FacebookpagelikeActivity.this, R.anim.shake);
                                    tvHint.startAnimation(shake);
                                    tvClickCount.setText("Click: 1/1");
                                    tvHint.setText("Done");

//                                    int exY = 0;
//                                    if(startY-47 + 165 > bitmap.getHeight()){
//                                        exY = (int) (startY + 47) - (int) bitmap.getHeight();
//                                    }
//
//                                    if(startX==1040){
//                                        try{
//                                            Bitmap croppedBmp = Bitmap.createBitmap(bitmap, (int) startX - 180, (int) (startY-exY) - 47, 200, 165);
//                                            imageView.setImageBitmap(croppedBmp);
//                                            createPaletteAsync(croppedBmp);
//                                            //crBitmap = croppedBmp;
//                                        }catch (Exception e){
//                                            retryAlert();
//                                            Toast.makeText(FacebookpagelikeActivity.this, e.getMessage()+ "Please try again!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }else if(startX>1040){
//                                        int extra = (int) startX - 1040;
//                                        try{
//                                            Bitmap croppedBmp = Bitmap.createBitmap(bitmap, (int) startX - (180+extra), (int) (startY-exY)  - 47, 200, 165);
//                                            imageView.setImageBitmap(croppedBmp);
//                                            createPaletteAsync(croppedBmp);
//                                            //crBitmap = croppedBmp;
//                                        }catch (Exception e){
//                                            retryAlert();
//                                            Toast.makeText(FacebookpagelikeActivity.this, e.getMessage()+ "Please try again!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }else if(startX<1040){
//                                        int extra = 1040 - (int) startX;
//                                        int sum = (int) (startX + extra) - 180 + 200 - (int) bitmap.getWidth();
//                                        try{
//                                            Bitmap croppedBmp = Bitmap.createBitmap(bitmap, (int) (startX + extra) - (sum+180), (int) (startY-exY)  - 47, 200, 165);
//                                            imageView.setImageBitmap(croppedBmp);
//                                            createPaletteAsync(croppedBmp);
//                                            //crBitmap = croppedBmp;
//                                        }catch (Exception e){
//                                            retryAlert();
////                                            int sum1 = (int) (startX + extra) - 180 + 200 - (int) bitmap.getWidth();
//                                            Toast.makeText(FacebookpagelikeActivity.this, e.getMessage()+"Please try again!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
                                    if(startY+100>bitmap.getHeight()){
                                        startY= startY - ((startY+100)-bitmap.getHeight());

                                        if(startX+100>bitmap.getWidth()){
                                            startX = startX - ((startX+100)-bitmap.getWidth());
                                        }else if(startX-100<0){
                                            startX = startX - (startX-100);
                                        }

                                    }else if(startY-100<0){
                                        startY= startY - (startY-100);

                                        if(startX+100>bitmap.getWidth()){
                                            startX = startX - ((startX+100)-bitmap.getWidth());
                                        }else if(startX-100<0){
                                            startX = startX - (startX-100);
                                        }

                                    }else if(startX+100>bitmap.getWidth()){
                                        startX = startX - ((startX+100)-bitmap.getWidth());
                                    }else if(startX-100<0){
                                        startX = startX - (startX-100);
                                    }

                                    try{
                                        Bitmap croppedBmp = Bitmap.createBitmap(bitmap, (int) startX - 100, (int) startY - 90, 200, 200);
                                        imageView.setImageBitmap(croppedBmp);
                                        createPaletteAsync(croppedBmp);
                                            //crBitmap = croppedBmp;
                                    }catch (Exception e){
                                        retryAlert();
                                        Toast.makeText(FacebookpagelikeActivity.this, e.getMessage()+ "Please try again!", Toast.LENGTH_SHORT).show();
                                    }

                                progressBar.setVisibility(View.GONE);
                                }
                            }, 5000);

                            Toast.makeText(FacebookpagelikeActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false; //specific to my project
            }
        });


        btnGetHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpSlider();
            }
        });

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

    Bitmap returnRoot(){
        View rootView = findViewById(R.id.webView);
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    private void createPaletteAsync(Bitmap bitmap) {

        text = (TextView) findViewById(R.id.text);
        text.setVisibility(View.VISIBLE);

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        Task<FirebaseVisionText> result =
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                // Task completed successfully
                                // ...
                                textRecognize(firebaseVisionText);

//                                Toast.makeText(FacebookpagelikeActivity.this, "success", Toast.LENGTH_SHORT).show();

                                if(-15175440==vibrant || -15175440==vibrantLight || -15175440==vibrantDark || -15175440==muted || -15175440==mutedLight || -15175440==mutedDark){
                                    if(matched) {
                                        Toast.makeText(FacebookpagelikeActivity.this, "Liked Successfully!", Toast.LENGTH_SHORT).show();
                                        //checkAdCount();
                                        getAdData();
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Write whatever to want to do after delay specified (1 sec)
                                                likeSuccessActivity();
                                            }
                                        }, 2500);

                                    }else{
                                        Toast.makeText(FacebookpagelikeActivity.this, "Try Again! Like button is not pressed!", Toast.LENGTH_SHORT).show();
                                        webView.setVisibility(View.GONE);
                                        retryAlert();
                                    }
                                }else{
                                    Toast.makeText(FacebookpagelikeActivity.this, "Not Liked! Try Again!", Toast.LENGTH_SHORT).show();
                                    webView.setVisibility(View.GONE);
                                    retryAlert();
                                }

                                //imageView.setVisibility(View.GONE);
                                //text.setVisibility(View.GONE);

                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                        Toast.makeText(FacebookpagelikeActivity.this, "failed: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Use generated instance
                //work with the palette here

                int defaultValue = 0x000000;
                vibrant = palette.getVibrantColor(defaultValue);
                vibrantLight = palette.getLightVibrantColor(defaultValue);
                vibrantDark = palette.getDarkVibrantColor(defaultValue);
                muted = palette.getMutedColor(defaultValue);
                mutedLight = palette.getLightMutedColor(defaultValue);
                mutedDark = palette.getDarkMutedColor(defaultValue);

                //vibrantLight = palette.getLightVibrantColor(defaultValue);

            }
        });
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
            mRef.child("ads").child(adId).child("adworkers").child(mUser.getUid()).setValue(useradinfos);
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

    private void textRecognize(FirebaseVisionText firebaseVisionText) {
        String resultText = firebaseVisionText.getText();
        for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
            String blockText = block.getText();
            Float blockConfidence = block.getConfidence();
            List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (FirebaseVisionText.Line line: block.getLines()) {
                String lineText = line.getText();
                Float lineConfidence = line.getConfidence();
                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (FirebaseVisionText.Element element: line.getElements()) {
                    String elementText = element.getText();
                    Float elementConfidence = element.getConfidence();
                    List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();

                    text = (TextView) findViewById(R.id.text);
                    text.setText("RESULT TEXT: "+ resultText);
                }
            }
        }

        matched = false;
        if(text.getText().toString().contains("Liked")){
            matched=true;
        }
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

    void checkMatch() {
        TextView textView = (TextView) findViewById(R.id.text);

        String s = textView.getText().toString();

        matched = false;

        Scanner scanner = new Scanner(s);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            textView.setText(line);

            Matcher m1 = Pattern.compile("^(?=.*?\\bLike\\b).*$").matcher(line);

            if (m1.find()) {
                matched = true;
            }
        }
    }

    void getUserData(){
        mRef.child("users").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users user = dataSnapshot.getValue(users.class);
                if(user!=null){
                    userBalance = user.getBalance();
                    name = user.getName();
                    profileImage = user.getImageurl();
                    location = user.getLocation();
                    agerange = user.getAgerange();
                    gender = user.getGender();

                    useradinfos = new useradinfo(name, profileImage, location, agerange, gender, mUser.getUid());

                }else{
                    userBalance = "0";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                        imageView.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        Animation shake = AnimationUtils.loadAnimation(FacebookpagelikeActivity.this, R.anim.shake);
                        tvHint.startAnimation(shake);
                        tvHint.setText("Hint: Just click on the 'Like' button to earn coins!");
                        tvClickCount.setText("Click: 0/1");
                        webView.loadUrl(adUrl);
                    }
                }).create().show();
    }

    private void helpSlider(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.help_slider, null);
        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        sliderLayout = promptView.findViewById(R.id.imageSlider);
        setSliderViews();
        alertD.setView(promptView);
        alertD.show();
    }

    private void setSliderViews() {
        DefaultSliderView sliderView = new DefaultSliderView(this);
        sliderView.setImageDrawable(R.drawable.ic_launcher_background);
        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        sliderView.setDescription("Just hit the like button. Don't click anywhere else to avoid unnecessary hussle.");
        sliderLayout.addSliderView(sliderView);
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    void socialLogin(){
        Intent intent = new Intent(FacebookpagelikeActivity.this,SocialLogin.class);
        intent.putExtra("url","https://www.facebook.com");
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        webView.loadUrl(adUrl);
    }

}
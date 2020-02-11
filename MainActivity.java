package com.example.viralbaj;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Uri mInvitationUrl;
    String referrerUid;

    private String imageUrl;
    private BottomNavigationView navView;
    private FrameLayout frame;

    private HomeFragment homeFragment;
    private EarnFragment earnFragment;
    private GoviralFragment goviralFragment;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    users users;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    setFragment(homeFragment);
                    return true;
                case R.id.navigation_earn:
                    setFragment(earnFragment);
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_monetize:
                    setFragment(goviralFragment);
                    // mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referral checker
        SharedPreferences prefs = getSharedPreferences("signinorup", MODE_PRIVATE);
        boolean oldUser = prefs.getBoolean("olduser", false);//"No name defined" is the default value.

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                        //
                        // If the user isn't signed in and the pending Dynamic Link is
                        // an invitation, sign in the user anonymously, and record the
                        // referrer's UID.
                        //
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null
                                && deepLink != null
                                && deepLink.getBooleanQueryParameter("invitedby", false)) {
                            referrerUid = deepLink.getQueryParameter("invitedby");
                            DatabaseReference userRecord =
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("referredusers")
                                            .child(user.getUid());
                            userRecord.child("referred_by").setValue(referrerUid);
                            //createAnonymousAccountWithReferrerInfo(referrerUid);
                        }
                    }
                });
    //refferal checker

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser==null && !oldUser){
            finish();
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        }else if(mUser==null && oldUser){
            finish();
            Intent intent = new Intent(MainActivity.this, SigninActivity.class);
            startActivity(intent);
        } else{
            homeFragment = new HomeFragment();
            earnFragment = new EarnFragment();
            goviralFragment = new GoviralFragment();

            navView = (BottomNavigationView) findViewById(R.id.nav);
            frame = (FrameLayout) findViewById(R.id.frame);
            navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            View headerView = navigationView.getHeaderView(0);

            //load user Profile data in navigation header
            final TextView name = (TextView) headerView.findViewById(R.id.tv_username);
            final TextView email = (TextView) headerView.findViewById(R.id.tv_email);
//            final TextView location = (TextView) headerView.findViewById(R.id.tv_location);
//            final TextView bday = (TextView) headerView.findViewById(R.id.tv_bday);
//            final TextView gender = (TextView) headerView.findViewById(R.id.tv_gender);

            final CircleImageView imageView = (CircleImageView) headerView.findViewById(R.id.imageView);
            mDatabase = FirebaseDatabase.getInstance();
            mRef = mDatabase.getReference("users");
            mRef.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    users = dataSnapshot.getValue(users.class);

                    if(users!=null) {
                        name.setText(users.getName());
                        email.setText(users.getEmail());
//                        location.setText(users.getLocation());
//                        bday.setText(users.getBirthday());
//                        gender.setText(users.getGender());
                        imageUrl = users.getImageurl();

                        Glide.with(getApplicationContext())
                                .load(imageUrl)
                                .into(imageView);
                    }else{
                        finish();
                        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            setFragment(goviralFragment);
        }
    }
//    private void keyGenerator(){
//        try
//        {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.viralbaj",
//                    PackageManager.GET_SIGNATURES);
//
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//        } catch (NoSuchAlgorithmException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_support) {
            return true;
        }else if(id == R.id.action_admin) {
           Intent intent = new Intent(MainActivity.this, AdminActivity.class);
           startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_update){
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("fbid",users.getFbid());
            intent.putExtra("mobileno",users.getMobileno());
            intent.putExtra("email",users.getEmail());
            intent.putExtra("gender",users.getGender());
            intent.putExtra("birthday",users.getBirthday());
            intent.putExtra("location",users.getLocation());
            intent.putExtra("agerange",users.getAgerange());
            intent.putExtra("imageurl",users.getImageurl());
            intent.putExtra("balance",users.getBalance());
            intent.putExtra("uid",users.getUid());
            intent.putExtra("name",users.getName());
            intent.putExtra("latittude",users.getLatittude());
            intent.putExtra("longitude",users.getLongitude());
            startActivity(intent);
        }
        else if (id == R.id.nav_refer) {
            rewardReferral();
        } else if (id == R.id.nav_logout) {
            if(mUser!=null) {
                signOutDialogue();
            }
        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_terms) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void signOutDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Are you sure you want to logged out?")
                .setTitle("Logging out");

        builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // users clicked OK button
                Toast.makeText(MainActivity.this, "You are logged out!", Toast.LENGTH_SHORT).show();
//                mAuth.signOut();


                mAuth.signOut();
                finish();
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // users cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void rewardReferral(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String link = "https://www.google.com/?invitedby=" + uid;
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link))
                .setDomainUriPrefix("https://viralbaj.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.example.viralbaj")
                                .setMinimumVersion(125)
                                .build())
                .buildShortDynamicLink()
                .addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                    @Override
                    public void onSuccess(ShortDynamicLink shortDynamicLink) {
                        mInvitationUrl = shortDynamicLink.getShortLink();

                        Toast.makeText(MainActivity.this, mInvitationUrl.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("InvitationUrl: ", mInvitationUrl.toString());

                        sendReferral();
                        // ...
                    }
                });
    }

    void sendReferral(){
        String referrerName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String subject = String.format("%s wants you to play MyExampleGame!", referrerName);
        String invitationLink = mInvitationUrl.toString();
        String msg = "Let's play MyExampleGame together! Use my referrer link: "
                + invitationLink;
        String msgHtml = String.format("<p>Let's play MyExampleGame together! Use my "
                + "<a href=\"%s\">referrer link</a>!</p>", invitationLink);

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        intent.putExtra(Intent.EXTRA_TEXT, msg);
//        intent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
        sendIntent.setType("text/plain");

// Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

}

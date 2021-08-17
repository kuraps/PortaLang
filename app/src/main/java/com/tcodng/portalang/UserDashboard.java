package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BlurView blurView;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    TextView names,email;
    ImageView ava;
    DatabaseReference reference;

    private BottomNavigationView bottomNavigationView;
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;;
                names = (TextView) findViewById(R.id.names);
                email =(TextView)  findViewById(R.id.email);
                ava = (ImageView) findViewById(R.id.ava);
                blurView=(BlurView)findViewById(R.id.blurView);
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
                Sprite wave = new Wave();
                progressBar.setIndeterminateDrawable(wave);
                getUsernameLocal();
                switch (item.getItemId()) {
                    case R.id.nav_menu:
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.openDrawer(GravityCompat.START);
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(username_key_new);
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                                    Picasso.with(getApplicationContext())
                                            .load(dataSnapshot.child("avatar").getValue().toString())
                                            .centerCrop()
                                            .fit()
                                            .placeholder(R.drawable.icon_nopic).error(R.drawable.icon_nopic)
                                            .into(ava);
                                    names.setText(dataSnapshot.child("name").getValue().toString());
                                    email.setText(dataSnapshot.child("email").getValue().toString());
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case R.id.nav_portal:
                        fragment = new FragmentPortal();
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.nav_home:
                        fragment = new FragmentHome();
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                        blurBackground();
                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Blur and Spinner
                                blurView.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 2000);
                        break;
                    case R.id.nav_help:
                        fragment = new FragmentHelp();
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.nav_profile:
                        fragment = new FragmentProfile();
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
                return loadFragment(fragment);
            }

        };
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_dashboard);
            loadFragment(new FragmentHome());
            if (!isNetworkAvailable(this)) {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
                Intent gogetStarted = new Intent(UserDashboard.this, NoInternet.class);
                startActivity(gogetStarted);
                finish();
            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            bottomNavigationView = findViewById(R.id.navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
            layoutParams.setBehavior(new BottomNavigationBehavior());
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
    public void blurBackground(){
        float radius = 22f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }

    boolean twice;
        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if(twice) {
                    finish();
                }
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getResources().getString(R.string.prompt_exit_back_pressed),
                        Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        twice = false;
                    }
                },3000);
                twice = true;
            }
        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();
            Fragment fragment = null;;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (id == R.id.nav_profile) {
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);
                drawer.closeDrawer(GravityCompat.START);
            }else if (id == R.id.nav_share){
                Intent shareIntent = new Intent();
                String url_ps = ("");
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getApplicationContext().getString(R.string.share_tv)+url_ps);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                drawer.closeDrawer(GravityCompat.START);
            }else if (id == R.id.nav_settings){
                Intent settings = new Intent(UserDashboard.this, Settings.class);
                startActivity(settings);
                drawer.closeDrawer(GravityCompat.START);
            }else if (id == R.id.logout){
                new SweetAlertDialog(UserDashboard.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.sweet_exit_dialog_title)+" "+username_key_new)
                        .setContentText(getResources().getString(R.string.sweet_exit_dialog_body))
                        .setConfirmText(getResources().getString(R.string.sweet_exit_dialog_confirm))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                SharedPreferences sharedPreferences = UserDashboard.this.getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, null);
                                editor.apply();
                                Intent gotosignin = new Intent(UserDashboard.this, GetStarted.class);
                                gotosignin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                gotosignin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(gotosignin);
                                finish();
                            }

                        })
                        .setCancelButton((getResources().getString(R.string.sweet_exit_dialog_cancel)), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;
        }
    }
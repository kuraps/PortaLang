package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.colindodd.gradientlayout.GradientRelativeLayout;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class UserDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    BlurView blurView;
    private BottomNavigationView bottomNavigationView;
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;;
                blurView=(BlurView)findViewById(R.id.blurView);
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
                Sprite wave = new Wave();
                progressBar.setIndeterminateDrawable(wave);
                switch (item.getItemId()) {
                    case R.id.nav_menu:
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.openDrawer(GravityCompat.START);
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
            //loading the default fragment
            if (!isNetworkAvailable(this)) {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
                // pindah activty lain
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

    public void blurBackground(){
        float radius = 22f;

        View decorView = getWindow().getDecorView();
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
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

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_profile) {
                Intent dashboard = new Intent(UserDashboard.this, UserPortal.class);
                startActivity(dashboard);
            } else if (id == R.id.nav_share) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hei saya baru saja kehilangan orang hilang!, Tolong bantu saya. Yuk download  aplikasi PORTALANG di Playsotre");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            } else if (id == R.id.nav_about) {
                Intent dashboard = new Intent(UserDashboard.this, UserPortal.class);
                startActivity(dashboard);
            }else if (id == R.id.nav_settings) {
                Intent dashboard = new Intent(UserDashboard.this, Settings.class);
                startActivity(dashboard);
            }  else if (id == R.id.nav_dark_mode) {
                DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
                darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        //create a seperate class file, if required in multiple activities
        public void setDarkMode(Window window) {
            if (new DarkModePrefManager(this).isNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                changeStatusBar(0, window);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                changeStatusBar(1, window);
            }
        }

        public void changeStatusBar(int mode, Window window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(R.color.white));
                //white mode
                if (mode == 1) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        }

        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;
        }
    }
package com.tcodng.portalang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class SplashAct extends AppCompatActivity {

    Animation fade_in,floating;
    TextView porta,lang,app_desc,app_version;
    ImageView logo;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        floating = AnimationUtils.loadAnimation(this, R.anim.floating);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        porta = findViewById(R.id.porta);
        lang = findViewById(R.id.lang);
        app_desc = findViewById(R.id.app_desc);
        app_version = findViewById(R.id.app_version);
        logo = findViewById(R.id.logo);
        porta.startAnimation(fade_in);
        lang.startAnimation(fade_in);
        app_desc.startAnimation(fade_in);
        app_version.startAnimation(fade_in);
        logo.startAnimation(floating);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent GetStarted = new Intent(SplashAct.this, GetStarted.class);
                startActivity(GetStarted);
                finish();
            }
        },2000);
        if (cekTema()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        getUsernameLocal();
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            Intent gogetStarted = new Intent(SplashAct.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        } else {
            if (username_key_new.isEmpty()) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent gogetStarted = new Intent(SplashAct.this, GetStarted.class);
                        startActivity(gogetStarted);
                        finish();
                    }
                }, 2000);

            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent gogethome = new Intent(SplashAct.this, UserDashboard.class);
                        gogethome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        gogethome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gogethome);

                        finish();
                    }
                }, 2000);
            }
        }
    }

    public void setAppLocale(String localCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(localCode.toLowerCase());
        res.updateConfiguration(conf, dm);
    }
    private boolean cekBahasa() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean cekBahasaIndo = pref.getBoolean("bahasa",false);
        return  cekBahasaIndo;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
    private boolean cekTema() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean cekTema = pref.getBoolean("tema",false);
        return  cekTema;
    }
}
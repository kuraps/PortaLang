package com.tcodng.portalang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class SplashAct extends AppCompatActivity {

    Animation fade_in;
    TextView porta,lang,app_desc,app_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        porta = findViewById(R.id.porta);
        lang = findViewById(R.id.lang);
        app_desc = findViewById(R.id.app_desc);
        app_version = findViewById(R.id.app_version);

        // START ANIMASI
        porta.startAnimation(fade_in);
        lang.startAnimation(fade_in);
        app_desc.startAnimation(fade_in);
        app_version.startAnimation(fade_in);

        // HANDLER UNTUK KASIH TIMER PINDAH ACTIVITY

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
    private boolean cekTema() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean cekTema = pref.getBoolean("tema",false);
        return  cekTema;
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
}
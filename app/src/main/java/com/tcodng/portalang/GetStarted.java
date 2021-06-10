package com.tcodng.portalang;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GetStarted extends AppCompatActivity {

    Animation top_to_bottom,bottom_to_top,fade_in;
    TextView tv1,tv2,app_version,porta,lang;
    Button btn_login,btn_register;
    ImageView icon_illu_search;
    View bg_overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(GetStarted.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }

        top_to_bottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        icon_illu_search = findViewById(R.id.icon_illu_search);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        porta = findViewById(R.id.porta);
        lang = findViewById(R.id.lang);
        app_version = findViewById(R.id.app_version);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        bg_overlay = findViewById(R.id.get_started_overlay);

        // ANIMASI
        bg_overlay.startAnimation(fade_in);
        porta.startAnimation(top_to_bottom);
        lang.startAnimation(top_to_bottom);
        tv1.startAnimation(top_to_bottom);
        tv2.startAnimation(top_to_bottom);
        icon_illu_search.startAnimation(top_to_bottom);
        btn_login.startAnimation(bottom_to_top);
        btn_register.startAnimation(bottom_to_top);
        app_version.startAnimation(bottom_to_top);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(GetStarted.this, UserLogin.class);
                startActivity(login);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(GetStarted.this, UserRegisterA.class);
                startActivity(login);
            }
        });
    }

    boolean twice;
    @Override
    public void onBackPressed() {
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

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
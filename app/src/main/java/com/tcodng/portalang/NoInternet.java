package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoInternet extends AppCompatActivity {
    Animation fade_in,top_to_bottom,floating,bottom_to_top;
    TextView tv1,tv2,tv3;
    ImageView img1;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        top_to_bottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        floating = AnimationUtils.loadAnimation(this, R.anim.floating);

        tv1 = findViewById(R.id.txt1);
        tv2 = findViewById(R.id.txt2);
        img1 = findViewById(R.id.icon_illu);
        btn1 = findViewById(R.id.btn1);
        tv3= findViewById(R.id.btn2);

        tv2.startAnimation(top_to_bottom);
        img1.startAnimation(floating);
        btn1.startAnimation(bottom_to_top);
        tv3.startAnimation(bottom_to_top);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(NoInternet.this, SplashAct.class);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(launchIntent);
            }
        });
    }
}
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

public class UserRegisterSuccess extends AppCompatActivity {
    Animation fade_in,top_to_bottom,floating,bottom_to_top;
    TextView tv1,tv2,tv3;
    ImageView img1;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_success);

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(UserRegisterSuccess.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        top_to_bottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        floating = AnimationUtils.loadAnimation(this, R.anim.floating);

        tv1 = findViewById(R.id.txt1);
        tv2 = findViewById(R.id.txt2);
        img1 = findViewById(R.id.icon_illu);
        btn1 = findViewById(R.id.btn1);
        tv3= findViewById(R.id.btn2);

        tv1.startAnimation(top_to_bottom);
        tv2.startAnimation(top_to_bottom);
        img1.startAnimation(floating);
        btn1.startAnimation(bottom_to_top);
        tv3.startAnimation(bottom_to_top);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(UserRegisterSuccess.this, UserDashboard.class);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(launchIntent);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hei iam using PortaLang Apps for searching missing people. lets be part of us! download on Play Store");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
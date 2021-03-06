package com.tcodng.portalang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;
import java.util.Locale;

public class Welcome extends AppCompatActivity {
    LinearLayout l1,l2;
    TextView tv1;
    Button btn1,btn2;
    ImageView img1,bg;
    TextView[] tv_dots1;
    WelcomeViewPager vp1;
    WelcomeAdapter intro_slideadapter1;
    int cp_1 = 0;
    Animation an1,an2,an3,move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tv1=(TextView)findViewById(R.id.intro_skip);
        vp1=(WelcomeViewPager) findViewById(R.id.intro_viewpager);
        l1=(LinearLayout)findViewById(R.id.intro_dots);
        l2=(LinearLayout)findViewById(R.id.intro_l2);
        img1=(ImageView)findViewById(R.id.intro_btn_start);
        btn2=(Button)findViewById(R.id.intro_btn_next);
        bg = findViewById(R.id.bg);
        if (restorePrefData()) {
            Intent intent = new Intent(Welcome.this,SplashAct.class);
            startActivity(intent);
            finish();
        }
        if (cekBahasa()) {
            setAppLocale("in");
        }else{
            setAppLocale("en");
        }
        if (cekTema()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        an1 = AnimationUtils.loadAnimation(this, R.anim.invisible_to_visible);
        an2 = AnimationUtils.loadAnimation(this, R.anim.button_animation);
        an3 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        move = AnimationUtils.loadAnimation(this, R.anim.move);
        bg.startAnimation(an1);
        loadmove();
        intro_slideadapter1=new WelcomeAdapter(this);
        vp1.setAdapter(intro_slideadapter1);
        adddots(0);
        vp1.setOnPageChangeListener(viewlistener);
        vp1.setPageTransformer(true, new ParallaxViewTransformer(R.id.intro_slideimg));
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp1.setCurrentItem(cp_1 + 6);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,SplashAct.class);
                startActivity(intent);
                finish();
                savePrefsData();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp1.setCurrentItem(cp_1 + 1);
            }
        });
    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            adddots(position);
            cp_1=position;
            if (position == tv_dots1.length-1) {
                vp1.disableScroll(true);
                vp1.invalidate();
                loaddLastScreen();
            }else {
            }
        }

        @Override
        public void onPageScrolled(int position, float psotionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public void adddots(int i) {
        tv_dots1=new TextView[4];
        l1.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 0, 5, 0);
        for(int x=0;x<tv_dots1.length;x++) {
            tv_dots1[x]=new TextView(this);
            tv_dots1[x].setText(Html.fromHtml("&#8226"));
            tv_dots1[x].setTextSize(30);
            tv_dots1[x].setLayoutParams(params);
            tv_dots1[x].setTextColor(getResources().getColor(R.color.blackPrimary));
            l1.addView(tv_dots1[x]);
        }
        if(tv_dots1.length>0) {
            tv_dots1[i].setTextSize(28);
            tv_dots1[i].setText(Html.fromHtml("&#215"));
            tv_dots1[i].setTextColor(getResources().getColor(R.color.bluePrimary));
        }
    }
    public void loaddLastScreen() {
        btn2.setVisibility(View.INVISIBLE);
        l2.setVisibility(View.VISIBLE);
        img1.setVisibility(View.VISIBLE);
        tv1.setVisibility(View.INVISIBLE);
        l1.setVisibility(View.INVISIBLE);
        l2.startAnimation(an2);
        img1.startAnimation(an1);
        loadnim();
    }

    public void loadnim() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                an3.setRepeatCount(Animation.INFINITE);
                l2.startAnimation(an3);
            }
        },1300);
    }
    public void loadmove() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                move.setRepeatCount(Animation.INFINITE);
                bg.startAnimation(move);
            }
        },1500);
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
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

    public void setAppLocale(String localCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = new Locale(localCode.toLowerCase());
        res.updateConfiguration(conf, dm);
    }
    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }
}

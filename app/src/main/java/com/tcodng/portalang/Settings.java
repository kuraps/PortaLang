package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchAnimListener;
import com.mahfa.dnswitch.DayNightSwitchListener;
import java.util.Calendar;
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;

public class Settings extends AppCompatActivity {
    ThemedToggleButtonGroup btngrup;
    ThemedButton indo,eng;
    TextView mode_tema,hello_title,hello_desc;
    LinearLayout btn2;
    Button btn1;
    public static final String TAG = "Settings";
    public static final String KEY_DAY_NIGHT_SWITCH_STATE = "day_night_switch_state";
    private DayNightSwitch day_night_switch;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getUsernameLocal();
        btngrup = findViewById(R.id.cards);
        indo = findViewById(R.id.indo);
        eng = findViewById(R.id.eng);
        mode_tema = findViewById(R.id.mode_tema);
        btn2 = findViewById(R.id.btn2);
        hello_desc = findViewById(R.id.hello_desc);
        hello_title = findViewById(R.id.hello_title);
        btn1 = findViewById(R.id.btn1);
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            Intent gogetStarted = new Intent(Settings.this, SplashAct.class);
            startActivity(gogetStarted);
            finish();
        }
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            hello_desc.setText(getResources().getString(R.string.goodmorning));
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            hello_desc.setText(getResources().getString(R.string.goodafternoon));
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            hello_desc.setText(getResources().getString(R.string.goodevening));
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            hello_desc.setText(getResources().getString(R.string.goodnight));
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        hello_title.setText(getResources().getString(R.string.hello) + " " + username_key_new);
        day_night_switch = (DayNightSwitch) findViewById(R.id.day_night_switch);
        day_night_switch.setDuration(450);
        if (cekTema()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mode_tema.setText(getResources().getString(R.string.settings_darkmode_desc));
            day_night_switch.isNight();
            day_night_switch.setIsNight(true, true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            mode_tema.setText(getResources().getString(R.string.settings_lightmode));
        }
        day_night_switch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                Log.d(TAG, "onSwitch() called with: is_night = [" + is_night + "]");
                if (is_night) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("tema", true);
                    editor.apply();
                    refreshAct();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("tema", false);
                    editor.apply();
                    mode_tema.setText(getResources().getString(R.string.settings_lightmode));
                    refreshAct();
                }
            }
        });
        day_night_switch.setAnimListener(new DayNightSwitchAnimListener() {
            @Override
            public void onAnimStart() {
                Log.d(TAG, "onAnimStart() called");
            }

            @Override
            public void onAnimEnd() {
                Log.d(TAG, "onAnimEnd() called");
            }

            @Override
            public void onAnimValueChanged(float value) {
                Log.d(TAG, "onAnimValueChanged() called with: value = [" + value + "]");
            }
        });

        if (savedInstanceState != null
                && savedInstanceState.containsKey(KEY_DAY_NIGHT_SWITCH_STATE))
            day_night_switch.setIsNight(savedInstanceState.getBoolean(KEY_DAY_NIGHT_SWITCH_STATE), true);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
        });
        indo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("bahasa", true);
                editor.apply();
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.restartapp), Toast.LENGTH_SHORT).show();
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("bahasa", false);
                editor.apply();
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.restartapp), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean cekTema() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean cekTema = pref.getBoolean("tema", false);
        return cekTema;
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_DAY_NIGHT_SWITCH_STATE, day_night_switch.isNight());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, SplashAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    public void refreshAct() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private boolean cekBahasa() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean cekBahasaIndo = pref.getBoolean("bahasa", false);
        return cekBahasaIndo;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
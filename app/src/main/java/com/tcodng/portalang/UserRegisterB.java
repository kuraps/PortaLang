package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UserRegisterB extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText name,passion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_b);

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(UserRegisterB.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
        btn_back = findViewById(R.id.btn_back);
        name = findViewById(R.id.name);
        passion = findViewById(R.id.passion);
        btn_continue = findViewById(R.id.btn_continue);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_continue.setEnabled(false);
                btn_continue.setText(getResources().getString(R.string.loading_btn));
                final String xname = name.getText().toString();
                final String xpassion = passion.getText().toString();
                if(name.length() == 0 && passion.length()==0){
                    btn_continue.setEnabled(true);
                    btn_continue.setText(getResources().getString(R.string.continue_btn));
                    name.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    passion.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.error_ed),Toast.LENGTH_SHORT).show();
                }else{

                    if (xname.isEmpty()) {
                        name.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                        btn_continue.setEnabled(true);
                        btn_continue.setText(getText(R.string.continue_btn));
                        Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_name),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(xpassion.isEmpty()){
                            passion.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                            btn_continue.setEnabled(true);
                            btn_continue.setText(getText(R.string.continue_btn));
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_bio),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // KODING DATABASE NANTI DISINI
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.success_menu_launch),Toast.LENGTH_SHORT).show();
                            name.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            passion.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            btn_continue.setEnabled(false);
                            btn_continue.setText(getResources().getString(R.string.loading_btn));
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // berpindah activity
                                    Intent gogetStarted = new Intent(UserRegisterB.this, UserRegisterSuccess.class);
                                    startActivity(gogetStarted);
                                }
                            }, 2000);
                        }
                    }
                }
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
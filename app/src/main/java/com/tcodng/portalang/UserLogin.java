package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class UserLogin extends AppCompatActivity {
    Animation bounce;
    TextView forgot_acc,register;
    EditText username,password;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        forgot_acc = findViewById(R.id.forgot_acc);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userRegister = new Intent(UserLogin.this, UserRegisterA.class);
                startActivity(userRegister);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_btn.setEnabled(false);
                login_btn.setText(getResources().getString(R.string.loading_btn));
                final String xusername = username.getText().toString();
                final String xpassword = password.getText().toString();
                if(password.length() == 0 && username.length()==0){
                    forgot_acc.startAnimation(bounce);
                    forgot_acc.setTextColor(getResources().getColor(R.color.redPrimary));
                    login_btn.setEnabled(true);
                    login_btn.setText(getResources().getString(R.string.login_btn));
                    username.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    password.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.error_ed),Toast.LENGTH_SHORT).show();
                }else{

                    if (xusername.isEmpty()) {
                        forgot_acc.startAnimation(bounce);
                        forgot_acc.setTextColor(getResources().getColor(R.color.redPrimary));
                        username.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                        password.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                        login_btn.setEnabled(true);
                        login_btn.setText(getText(R.string.login_btn));
                        Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_username),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(xpassword.isEmpty()){
                            forgot_acc.startAnimation(bounce);
                            forgot_acc.setTextColor(getResources().getColor(R.color.redPrimary));
                            username.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            password.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                            login_btn.setEnabled(true);
                            login_btn.setText(getText(R.string.login_btn));
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_password),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // KODING DATABASE NANTI DISINI
                            forgot_acc.setTextColor(getResources().getColor(R.color.blackPrimary));
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.success_menu_launch),Toast.LENGTH_SHORT).show();
                            username.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            password.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            login_btn.setEnabled(false);
                            login_btn.setText(getResources().getString(R.string.loading_btn));
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // berpindah activity
                                    Intent gogetStarted = new Intent(UserLogin.this, UserDashboard.class);
                                    startActivity(gogetStarted);
                                }
                            }, 2000);
                        }
                    }
                }
            }
        });

        forgot_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotAcc = new Intent(UserLogin.this, UserForgotPassword.class);
                startActivity(forgotAcc);
            }
        });
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(UserLogin.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
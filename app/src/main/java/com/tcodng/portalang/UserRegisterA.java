package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserRegisterA extends AppCompatActivity {
    LinearLayout btn_back;
    Button btn_continue;
    EditText username,password,repassword,email;
    ImageView togglepw,togglepwre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(UserRegisterA.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
        btn_back = findViewById(R.id.btn_back);
        btn_continue = findViewById(R.id.btn_continue);;
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

        togglepw = findViewById(R.id.togglepw);
        togglepwre = findViewById(R.id.togglepwre);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.length() != 0 && password.length() != 0 && email.length() != 0) {
                    String rePassword = password.getText().toString();
                    if (password.getText().toString().equals(rePassword) && repassword.getText().toString().equals(rePassword)) {
                        // ubah state menjadi loading
                        btn_continue.setEnabled(false);
                        btn_continue.setText(getResources().getString(R.string.loading_btn));
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // berpindah activity
                                Intent gogetStarted = new Intent(UserRegisterA.this, UserRegisterB.class);
                                startActivity(gogetStarted);
                            }
                        }, 2000);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(R.string.error_ed),
                            Toast.LENGTH_SHORT).show();
                    username.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    email.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    password.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    repassword.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.togglepw){

            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                togglepw.setImageResource(R.drawable.ic_baseline_remove_blue_eye_24);

                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                togglepw.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
    public void ShowHideRePass(View view){

        if(view.getId()==R.id.togglepwre){

            if(repassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                togglepwre.setImageResource(R.drawable.ic_baseline_remove_blue_eye_24);

                //Show Password
                repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                togglepwre.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Hide Password
                repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

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
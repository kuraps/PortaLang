package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserForgotPassword extends AppCompatActivity {

    TextView customer_service;
    EditText username,email;
    Button complete_btn;
    ImageView btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);
        btn_back = findViewById(R.id.btn_back);
        customer_service = findViewById(R.id.customer_service);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        complete_btn = findViewById(R.id.complete_btn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        customer_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.success_menu_launch),Toast.LENGTH_SHORT).show();
            }
        });
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete_btn.setEnabled(false);
                complete_btn.setText(getResources().getString(R.string.loading_btn));
                final String xusername = username.getText().toString();
                final String xemail = email.getText().toString();
                if(email.length() == 0 && username.length()==0){
                    complete_btn.setEnabled(true);
                    complete_btn.setText(getResources().getString(R.string.forgot_pass_title));
                    username.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    email.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.error_ed),Toast.LENGTH_SHORT).show();
                }else{

                    if (xusername.isEmpty()) {
                        username.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                        complete_btn.setEnabled(true);
                        complete_btn.setText(getText(R.string.login_btn));
                        Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_username),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(xemail.isEmpty()){
                            email.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                            complete_btn.setEnabled(true);
                            complete_btn.setText(getText(R.string.login_btn));
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_email),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // KODING DATABASE NANTI DISINI
                            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.success_menu_launch),Toast.LENGTH_SHORT).show();
                            username.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            email.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            complete_btn.setEnabled(true);
                            complete_btn.setText(getText(R.string.login_btn));
                        }
                    }
                }
            }
        });
    }
}
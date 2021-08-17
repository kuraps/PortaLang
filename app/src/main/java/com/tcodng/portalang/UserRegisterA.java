package com.tcodng.portalang;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Pattern;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class    UserRegisterA extends AppCompatActivity {
    LinearLayout btn_back;
    Button btn_continue;
    EditText username,password,repassword,email;
    ImageView togglepw,togglepwre;
    DatabaseReference reference_username;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
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
            public void onClick(View view) {
                if (username.length() != 0 && password.length() != 0 && email.length() != 0) {
                    btn_continue.setEnabled(false);
                    btn_continue.setText(getResources().getString(R.string.loading_btn));
                    reference_username = FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(username.getText().toString());
                    reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.username_already), Toast.LENGTH_SHORT).show();
                                btn_continue.setEnabled(true);
                                btn_continue.setText(getResources().getString(R.string.continue_btn));
                                username.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                email.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                                password.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                                repassword.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                            }else{
                                if(isValidEmailId(email.getText().toString().trim())) {
                                    String rePassword = password.getText().toString();
                                    if (password.getText().toString().equals(rePassword) && repassword.getText().toString().equals(rePassword)) {
                                        int blue = ContextCompat.getColor(UserRegisterA.this, R.color.bluePrimary);
                                        SweetAlertDialog pDialog = new SweetAlertDialog(UserRegisterA.this, SweetAlertDialog.PROGRESS_TYPE);
                                        pDialog.getProgressHelper().setBarColor(blue);
                                        pDialog.setTitleText(getResources().getString(R.string.sweet_loading));
                                        pDialog.setCancelable(true);
                                        pDialog.show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                String value_username = username.getText().toString();
                                                String value_password = password.getText().toString();
                                                String value_email = email.getText().toString();
                                                Intent a = new Intent(UserRegisterA.this, UserRegisterB.class);
                                                a.putExtra("value_username", value_username);
                                                a.putExtra("value_password", value_password);
                                                a.putExtra("value_email", value_email);
                                                startActivity(a);
                                            }
                                        }, 2000);
                                    }else{
                                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.wrong_pw), Toast.LENGTH_SHORT).show();
                                        btn_continue.setEnabled(true);
                                        username.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                                        password.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                        repassword.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                        email.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                                        btn_continue.setText(getResources().getString(R.string.continue_btn));
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            getApplicationContext().getResources().getString(R.string.error_em),
                                            Toast.LENGTH_SHORT).show();
                                    username.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                                    email.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                    password.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                                    repassword.setBackground(getResources().getDrawable(R.drawable.bg_ed_selector));
                                    btn_continue.setEnabled(true);
                                    btn_continue.setText(getResources().getString(R.string.continue_btn));
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }else {
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
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                togglepw.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    public void ShowHideRePass(View view){
        if(view.getId()==R.id.togglepwre){
            if(repassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                togglepwre.setImageResource(R.drawable.ic_baseline_remove_blue_eye_24);
                repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                togglepwre.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
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
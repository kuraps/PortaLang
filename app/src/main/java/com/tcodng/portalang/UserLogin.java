package com.tcodng.portalang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Pattern;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserLogin extends AppCompatActivity {
    Animation bounce;
    TextView forgot_acc,register;
    EditText username_ed,password_ed;
    Button login_btn;
    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        forgot_acc = findViewById(R.id.forgot_acc);
        username_ed = findViewById(R.id.username);
        password_ed = findViewById(R.id.password);
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
            public void onClick(View view) {
                login_btn.setEnabled(false);
                login_btn.setText(getResources().getString(R.string.login_btn));
                final String username = username_ed.getText().toString();
                final String password = password_ed.getText().toString();
                if (username.isEmpty()) {
                    login_btn.setEnabled(true);
                    login_btn.setText(getText(R.string.login_btn));
                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_username),Toast.LENGTH_SHORT).show();

                } else {
                    if (password.isEmpty()) {
                        login_btn.setEnabled(true);
                        login_btn.setText(getText(R.string.login_btn));
                        Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_password),Toast.LENGTH_SHORT).show();
                    } else {
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String usernameFromFirebase = dataSnapshot.child("username").getValue().toString();
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();
                                    if (username.equals(usernameFromFirebase) && password.equals(passwordFromFirebase)) {
                                        int blue = ContextCompat.getColor(UserLogin.this, R.color.bluePrimary);
                                        SweetAlertDialog pDialog = new SweetAlertDialog(UserLogin.this, SweetAlertDialog.PROGRESS_TYPE);
                                        pDialog.getProgressHelper().setBarColor(blue);
                                        pDialog.setTitleText(getResources().getString(R.string.sweet_signin));
                                        pDialog.setCancelable(true);
                                        pDialog.show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString(username_key, username_ed.getText().toString());
                                                editor.apply();
                                                Intent launchIntent = new Intent(UserLogin.this, UserDashboard.class);
                                                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                finish();
                                                startActivity(launchIntent);
                                                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.login_ok),Toast.LENGTH_SHORT).show();
                                            }
                                        }, 2000);

                                    } else {
                                        login_btn.setEnabled(true);
                                        login_btn.setText(getText(R.string.login_btn));
                                        Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.invalid_data),Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    login_btn.setEnabled(true);
                                    login_btn.setText(getText(R.string.login_btn));
                                    Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.invalid_data),Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_db),Toast.LENGTH_SHORT).show();
                            }
                        });
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
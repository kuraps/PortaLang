package com.tcodng.portalang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Pattern;

public class UserForgotPassword extends AppCompatActivity {

    EditText username_ed,email_ed,pass;
    Button complete_btn,backs;
    DatabaseReference reference;
    ImageView btn_back;
    LinearLayout pw,l2,l3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);
        pw=findViewById(R.id.pw);
        pass=findViewById(R.id.pass);
        btn_back = findViewById(R.id.btn_back);
        username_ed = findViewById(R.id.username);
        email_ed = findViewById(R.id.email);
        backs = findViewById(R.id.backs);
        complete_btn = findViewById(R.id.complete_btn);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);

        LinearLayout cs_wa = findViewById(R.id.cs_wa);
        LinearLayout cs_fb = findViewById(R.id.cs_fb);
        LinearLayout cs_em = findViewById(R.id.cs_email);
        LinearLayout cs_tw = findViewById(R.id.cs_tw);

        cs_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=6285156400335");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                startActivity(intent);
            }
        });
        cs_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fb = "https://facebook.com/yudhiez.chitilieaz";
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, fb);
                startActivity(intent);
            }
        });
        cs_em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                String emailuser = "yudhist3@gmail.com";
                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailuser});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "PortaLang");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hei i need to help");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });
        cs_tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tw = "https://twitter.com/kurapss";
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, tw);
                startActivity(intent);
            }
        });

        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete_btn.setEnabled(false);
                complete_btn.setText(getResources().getString(R.string.loading_reg));
                final String username = username_ed.getText().toString();
                final String email = email_ed.getText().toString();
                if (username.length() != 0 && email.length() != 0) {
                    if(isValidEmailId(email_ed.getText().toString().trim())) {
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String emailFromFirebase = dataSnapshot.child("email").getValue().toString();
                                    String usernameFromFirebase = dataSnapshot.child("username").getValue().toString();
                                    if (email.equals(emailFromFirebase) && username.equals(usernameFromFirebase)) {
                                        String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();
                                        pw.setVisibility(View.VISIBLE);
                                        pass.setText(passwordFromFirebase);
                                        complete_btn.setEnabled(false);
                                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.cekpw), Toast.LENGTH_SHORT).show();
                                        l2.setVisibility(View.GONE);
                                        l3.setVisibility(View.GONE);
                                        complete_btn.setVisibility(View.GONE);
                                        backs.setVisibility(View.VISIBLE);
                                    } else {
                                        complete_btn.setEnabled(true);
                                        complete_btn.setText(getText(R.string.continue_btn));
                                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
                                        username_ed.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                        email_ed.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                    }
                                } else {
                                    complete_btn.setEnabled(true);
                                    complete_btn.setText(getText(R.string.continue_btn));
                                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
                                    username_ed.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                    email_ed.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.no_db), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),
                                getApplicationContext().getResources().getString(R.string.invalid_data),
                                Toast.LENGTH_SHORT).show();
                        complete_btn.setEnabled(true);
                        email_ed.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                        complete_btn.setText(getResources().getString(R.string.continue_btn));
                    }
                }else {
                    username_ed.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    email_ed.setBackground(getResources().getDrawable(R.drawable.bg_ed_pressed_red));
                    complete_btn.setEnabled(true);
                    complete_btn.setText(getResources().getString(R.string.continue_btn));
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(R.string.error_ed),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
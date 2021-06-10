package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserReportMissingB extends AppCompatActivity {

    LinearLayout btn_date,btn_maps;
    EditText date;
    ImageView maps_dummy;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_missing_b);

        btn_date = (LinearLayout)findViewById(R.id.date_btn);
        btn_maps = (LinearLayout)findViewById(R.id.maps_btn);
        date = (EditText) findViewById(R.id.date);
        btn1=(Button)findViewById(R.id.btn1);
        maps_dummy = (ImageView) findViewById(R.id.maps_preview);
        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(UserReportMissingB.this, SplashAct.class);
            startActivity(gogetStarted);
            finish();
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(UserReportMissingB.this, UserReportMissingC.class);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(launchIntent);
            }
        });
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(UserReportMissingB.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        date.setText(dateFormatter.format(newDate.getTime()));
                    }


                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        maps_dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    }
                }, 1000);
            }
        });
        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    }
                }, 1000);
            }
        });
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
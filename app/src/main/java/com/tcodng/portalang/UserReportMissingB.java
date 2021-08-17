package com.tcodng.portalang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserReportMissingB extends AppCompatActivity {

    ImageView maps_dummy,missing_ppl_photos;
    Button btn1, upload_ava;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Spinner urcity,urtype,gender;
    DatabaseReference reference,reference2;
    Integer photo_max = 1;
    StorageReference storage;
    Uri photo_location;
    EditText missname,yourname, missnik,age, phonenumber, email, characteristic, chronology, mapss,missdate,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_missing_b);
        getUsernameLocal();
        mapss = (EditText)findViewById(R.id.maps);
        missdate = (EditText)findViewById(R.id.missdate);
        age = (EditText) findViewById(R.id.age);
        missname = (EditText) findViewById(R.id.missname);
        yourname = (EditText) findViewById(R.id.yourname);
        missnik = (EditText) findViewById(R.id.missnik);
        username = (EditText) findViewById(R.id.username);
        urcity = (Spinner) findViewById(R.id.urcity);
        urtype = (Spinner) findViewById(R.id.urtype);
        gender = (Spinner) findViewById(R.id.gender);
        age = (EditText) findViewById(R.id.age);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        email = (EditText) findViewById(R.id.email);
        characteristic = (EditText) findViewById(R.id.characteristic);
        chronology = (EditText) findViewById(R.id.chronology);
        btn1=(Button)findViewById(R.id.btn1);
        upload_ava=(Button)findViewById(R.id.upload_ava);
        maps_dummy = (ImageView) findViewById(R.id.maps_preview);
        missing_ppl_photos = (ImageView) findViewById(R.id.missing_ppl_photos);
        reference2 = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    yourname.setText(dataSnapshot.child("name").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    username.setText(dataSnapshot.child("username").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        String[] urcity_pick = {"Jakarta","Bogor","Depok","Tanggerang","Bekasi"};
        urcity.setAdapter(new SpinnerAdapterCity(this, R.layout.spinner_background_layout, urcity_pick));
        String[] urtype_pick = {"Missing People","Found People"};
        urtype.setAdapter(new SpinnerAdapterCity(this, R.layout.spinner_background_layout, urtype_pick));
        String[] gender_pick = {"Laki-laki","Perempuan"};
        gender.setAdapter(new SpinnerAdapterCity(this, R.layout.spinner_background_layout, gender_pick));
        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(UserReportMissingB.this, SplashAct.class);
            startActivity(gogetStarted);
            finish();
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photo_location != null) {
                if (missname.length() != 0 && yourname.length() != 0 && missnik.length() != 0 && phonenumber.length() != 0 && age.length() != 0
                        && mapss.length() != 0 && missdate.length() != 0 && phonenumber.length() != 0 && email.length() != 0 && characteristic.length() != 0 && chronology.length() != 0) {
                    int blue = ContextCompat.getColor(UserReportMissingB.this, R.color.bluePrimary);
                    SweetAlertDialog pDialog = new SweetAlertDialog(UserReportMissingB.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(blue);
                    pDialog.setTitleText(getResources().getString(R.string.sweet_uploading));
                    pDialog.setCancelable(true);
                    pDialog.show();
                    btn1.setEnabled(false);
                    btn1.setText(getResources().getString(R.string.loading_reg));
                    String nik_miss = missnik.getText().toString();
                        reference = FirebaseDatabase.getInstance().getReference().child("TemporaryMissingPeople").child(nik_miss);
                        storage = FirebaseStorage.getInstance().getReference().child("MissingPeople").child(nik_miss);
                        final StorageReference storageReference1 =
                                storage.child("NIK_"+nik_miss+"_ID_"+System.currentTimeMillis() + "." +
                                        getFileExtension(photo_location));
                        storageReference1.putFile(photo_location)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = uri.toString();
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        String nik = missnik.getText().toString();
                                                        String urcity_txt = urcity.getSelectedItem().toString();
                                                        String urtype_txt = urtype.getSelectedItem().toString();
                                                        String gender_txt = gender.getSelectedItem().toString();
                                                        reference.getRef().child("missing_ppl_name").setValue(missname.getText().toString());
                                                        reference.getRef().child("missing_ppl_ur_username").setValue(username.getText().toString());
                                                        reference.getRef().child("missing_ppl_ur_name").setValue(yourname.getText().toString());
                                                        reference.getRef().child("missing_ppl_nik").setValue(missnik.getText().toString());
                                                        reference.getRef().child("missing_ppl_ur_number").setValue(phonenumber.getText().toString());
                                                        reference.getRef().child("missing_ppl_age").setValue(age.getText().toString());
                                                        reference.getRef().child("missing_ppl_city").setValue(urcity_txt);
                                                        reference.getRef().child("missing_ppl_type").setValue(urtype_txt);
                                                        reference.getRef().child("missing_ppl_gender").setValue(gender_txt);
                                                        reference.getRef().child("missing_ppl_address").setValue(mapss.getText().toString());
                                                        reference.getRef().child("missing_ppl_date").setValue(missdate.getText().toString());
                                                        reference.getRef().child("missing_ppl_ur_number").setValue(phonenumber.getText().toString());
                                                        reference.getRef().child("missing_ppl_ur_email").setValue(email.getText().toString());
                                                        reference.getRef().child("missing_ppl_characteristic").setValue(characteristic.getText().toString());
                                                        reference.getRef().child("missing_ppl_chronology").setValue(chronology.getText().toString());
                                                        reference.getRef().child("missing_ppl_photo").setValue(url);
                                                        reference.getRef().child("missing_ppl_police_report").setValue("https://firebasestorage.googleapis.com/v0/b/portalang-e909a.appspot.com/o/MissingPeople%2F12170209%2Fpolice_report.jpg?alt=media&token=1534f66c-dbfe-4684-b10e-b8b4bfbda569");
                                                        Log.d("url", url);
                                                        Intent a = new Intent(UserReportMissingB.this, UserReportMissingC.class);
                                                        a.putExtra("value_nik", nik);
                                                        startActivity(a);
                                                        finish();
                                                    }
                                                }, 2000);
                                            }
                                        });
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            }
                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            }
                        });
                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(R.string.error_ed),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        upload_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
        missdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserReportMissingB.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        missdate.setText(dateFormatter.format(newDate.getTime()));
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
        mapss.setOnClickListener(new View.OnClickListener() {
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

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==photo_max && resultCode==RESULT_OK && data != null && data.getData() != null){
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(missing_ppl_photos);
        }else{
            Toast.makeText(this, "No missing people image choosen!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
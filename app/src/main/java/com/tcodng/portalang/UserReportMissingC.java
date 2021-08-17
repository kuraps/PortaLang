package com.tcodng.portalang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserReportMissingC extends AppCompatActivity {

    Animation fade_in,top_to_bottom,floating,bottom_to_top;
    TextView tv1,tv2;
    ImageView img1;
    Button btn1,btn2,btn3;
    DatabaseReference reference;
    Integer photo_max = 1;
    StorageReference storage;
    Uri photo_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_missing_c);
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            Intent gogetStarted = new Intent(UserReportMissingC.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        top_to_bottom = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        floating = AnimationUtils.loadAnimation(this, R.anim.floating);
        tv1 = findViewById(R.id.txt1);
        tv2 = findViewById(R.id.txt2);
        img1 = findViewById(R.id.icon_illu);
        btn1 = findViewById(R.id.btn1);
        btn2= findViewById(R.id.btn2);
        btn3= findViewById(R.id.btn3);
        tv1.startAnimation(top_to_bottom);
        tv2.startAnimation(top_to_bottom);
        img1.startAnimation(floating);
        btn1.startAnimation(bottom_to_top);
        btn2.startAnimation(bottom_to_top);
        btn3.startAnimation(bottom_to_top);
        btn3.setEnabled(false);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(UserReportMissingC.this, UserReportMissingSuccess.class);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(launchIntent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int blue = ContextCompat.getColor(UserReportMissingC.this, R.color.bluePrimary);
                SweetAlertDialog pDialog = new SweetAlertDialog(UserReportMissingC.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(blue);
                pDialog.setTitleText(getResources().getString(R.string.sweet_uploading));
                pDialog.setCancelable(true);
                pDialog.show();
                btn1.setEnabled(false);
                btn1.setText(getResources().getString(R.string.upload_reg));
                btn1.setEnabled(false);
                btn3.setText(getResources().getString(R.string.pleasewait_reg));
                Bundle bundle3 = getIntent().getExtras();
                String value_nik = bundle3.getString("value_nik");
                reference = FirebaseDatabase.getInstance().getReference().child("TemporaryMissingPeople").child(value_nik);
                storage = FirebaseStorage.getInstance().getReference().child("MissingPeople").child(value_nik);
                if (photo_location != null) {
                    StorageReference storageReference1 =
                            storage.child("PoliceReport_NIK_"+value_nik+System.currentTimeMillis() + "." +
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
                                                    reference.child("missing_ppl_police_report").setValue(url);;
                                                    Log.d("url", url);
                                                    Intent a = new Intent(UserReportMissingC.this, UserRegisterSuccess.class);
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
            }
        });
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

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            btn3.animate().translationY(0).alpha(1).setDuration(350).start();
            btn3.setEnabled(true);
            btn2.setVisibility(View.GONE);
            tv1.setTextColor(getResources().getColor(R.color.greenmood));
            Picasso.with(this).load(photo_location).centerCrop().fit().into(img1);
        }else{
            Toast.makeText(getApplicationContext(),getApplicationContext().getResources().getString(R.string.no_photos),Toast.LENGTH_SHORT).show();
        }
    }

    String getFileExtension(Uri uri) {
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
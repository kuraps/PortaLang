package com.tcodng.portalang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class UserRegisterB extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText name,passion;
    ImageView avatar,btn_upload;
    Uri ava_location;
    Integer ava_max = 1;
    DatabaseReference reference;
    StorageReference storage;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_b);

        btn_upload = findViewById(R.id.btn_upload);
        btn_back = findViewById(R.id.btn_back);
        name = findViewById(R.id.name);
        passion = findViewById(R.id.passion);
        avatar = findViewById(R.id.avatar);
        btn_continue = findViewById(R.id.btn_continue);

        Bundle bundle1 = getIntent().getExtras();
        Bundle bundle2 = getIntent().getExtras();
        Bundle bundle3 = getIntent().getExtras();
        String value_username = bundle1.getString("value_username");
        String value_password = bundle2.getString("value_password");
        String value_email = bundle3.getString("value_email");

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            Intent gogetStarted = new Intent(UserRegisterB.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DO NOTHING
                findAva();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.length() != 0 && passion.length() != 0) {
                    btn_continue.setEnabled(false);
                    btn_continue.setText(getResources().getString(R.string.loading_btn));
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(value_username);
                storage = FirebaseStorage.getInstance().getReference().child("UsersAvatar").child(value_username);
                if (ava_location != null) {
                    final StorageReference storageReference1 =
                            storage.child(System.currentTimeMillis() + "." +
                                    getFileExtension(ava_location));
                    storageReference1.putFile(ava_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            int blue = ContextCompat.getColor(UserRegisterB.this, R.color.bluePrimary);
                                            SweetAlertDialog pDialog = new SweetAlertDialog(UserRegisterB.this, SweetAlertDialog.PROGRESS_TYPE);
                                            pDialog.getProgressHelper().setBarColor(blue);
                                            pDialog.setTitleText(getResources().getString(R.string.sweet_regist));
                                            pDialog.setCancelable(true);
                                            pDialog.show();
                                            String url = uri.toString();
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString(username_key, value_username);
                                                    editor.apply();
                                                    reference.getRef().child("username").setValue(value_username);
                                                    reference.getRef().child("password").setValue(value_password);
                                                    reference.getRef().child("email").setValue(value_email);
                                                    reference.getRef().child("avatar").setValue(url);
                                                    reference.getRef().child("name").setValue(name.getText().toString());
                                                    reference.getRef().child("bio").setValue(passion.getText().toString());
                                                    Log.d("url", url);
                                                    Intent a = new Intent(UserRegisterB.this, UserRegisterSuccess.class);
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
                } else {
                    int blue = ContextCompat.getColor(UserRegisterB.this, R.color.bluePrimary);
                    SweetAlertDialog pDialog = new SweetAlertDialog(UserRegisterB.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(blue);
                    pDialog.setTitleText(getResources().getString(R.string.sweet_regist));
                    pDialog.setCancelable(true);
                    pDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key, value_username);                                                     
                            editor.apply();
                            reference.getRef().child("username").setValue(value_username);
                            reference.getRef().child("password").setValue(value_password);
                            reference.getRef().child("email").setValue(value_email);
                            reference.getRef().child("avatar").setValue("https://firebasestorage.googleapis.com/v0/b/portalang-e909a.appspot.com/o/UsersAvatar%2Ficon_nopic.png?alt=media&token=c3c77f76-69d8-4b31-af60-4dcaedd97f1f");
                            reference.getRef().child("name").setValue(name.getText().toString());
                            reference.getRef().child("bio").setValue(passion.getText().toString());
                            Intent a = new Intent(UserRegisterB.this, UserRegisterSuccess.class);
                            startActivity(a);
                            finish();
                        }
                    }, 2000);
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getResources().getString(R.string.error_ed),
                        Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findAva (){
        Intent ava = new Intent();
        ava.setType("image/*");
        ava.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(ava, ava_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ava_max && resultCode==RESULT_OK && data != null && data.getData() != null){
            ava_location = data.getData();
            Picasso.with(this).load(ava_location).centerCrop().fit().into(avatar);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
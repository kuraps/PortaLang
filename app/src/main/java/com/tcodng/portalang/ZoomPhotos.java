package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class ZoomPhotos extends AppCompatActivity {
    Button btn_save;
    LinearLayout btn_back;
    public final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_photos);
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(ZoomPhotos.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        btn_back=findViewById(R.id.btn_backs);
        btn_save=findViewById(R.id.btn_save);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String zoomphotos = extras.getString("zoom_photos");
        Picasso.with(ZoomPhotos.this)
                .load(zoomphotos)
                .placeholder(R.drawable.icon_nopic).error(R.drawable.icon_nopic)
                .into(photoView);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUrl = zoomphotos;

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getUrl));
                String title = URLUtil.guessFileName(getUrl, null, null);
                request.setTitle(title);
                request.setDescription("Downloading Missing People Photos...");
                String cookie = CookieManager.getInstance().getCookie(getUrl);
                request.addRequestHeader("cookie", cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
                DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
                Toast.makeText(ZoomPhotos.this, "Downloading Started", Toast.LENGTH_SHORT).show();


            }
        });
    }

    public Boolean verifyPermissions() {
        int permissionExternalMemory = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSIONS, 1);
            return false;
        }
        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
}
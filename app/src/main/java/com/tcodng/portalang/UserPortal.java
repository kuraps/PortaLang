package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class UserPortal extends AppCompatActivity {

    ArrayList<FragmentPortalModelPeople> arrayList;
    RecyclerView recyclerView;
    int ppl_image[] = {R.drawable.icon_nopic,R.drawable.irwan_bulat,R.drawable.langse};
    String ppl_name[] = {"Kuraps NoTail", "Irwansah Abare bare", "Lord Gilang"};
    String ppl_desc[] = {"Tidak diketahui ...", "Keribo, kulit merah merona", "Hitam khas NTB, badan besar"};
    String ppl_since[] = {"NEW!", "69 Days", "1000 Days"};
    String ppl_city[] = {"Depok", "Citayam", "Sawangan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);

        recyclerView = (RecyclerView) findViewById(R.id.portal_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        arrayList = new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        for (int i = 0; i < ppl_image.length; i++) {
            FragmentPortalModelPeople portalModel = new FragmentPortalModelPeople();
            portalModel.setImage(ppl_image[i]);
            portalModel.setName(ppl_name[i]);
            portalModel.setDesc(ppl_desc[i]);
            portalModel.setSince(ppl_since[i]);
            portalModel.setCity(ppl_city[i]);

            //add in array list
            arrayList.add(portalModel);
        }
        FragmentPortalAdapterPeople adapter = new FragmentPortalAdapterPeople(getApplicationContext(), arrayList);
        recyclerView.setAdapter(adapter);
        // BARIS BARU
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            // pindah activty lain
            Intent gogetStarted = new Intent(UserPortal.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
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
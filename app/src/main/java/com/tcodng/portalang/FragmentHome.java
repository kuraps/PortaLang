package com.tcodng.portalang;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import net.colindodd.gradientlayout.GradientRelativeLayout;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class FragmentHome extends Fragment{

    DatabaseReference reference,ref_bogor,ref_depok,ref_jakarta,ref_tanggerang,ref_bekasi;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    ImageView avatar;
    TextView settings_app,jakarta_tv,bogor_tv,depok_tv,tanggerang_tv,bekasi_tv;

    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View bottom_sheet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        jakarta_tv = (TextView) rootView.findViewById(R.id.tv_jakarta);
        bogor_tv = (TextView) rootView.findViewById(R.id.tv_bogor);
        depok_tv = (TextView) rootView.findViewById(R.id.tv_depok);
        tanggerang_tv = (TextView) rootView.findViewById(R.id.tv_tanggerang);
        bekasi_tv = (TextView) rootView.findViewById(R.id.tv_bekasi);
        avatar = (ImageView) rootView.findViewById(R.id.avatar);
        ref_jakarta = FirebaseDatabase.getInstance().getReference()
                .child("CityCases").child("Jakarta");
        ref_jakarta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    long count = dataSnapshot.getChildrenCount();
                    String strLong = Long.toString(count);
                    jakarta_tv.setText(strLong);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref_bogor = FirebaseDatabase.getInstance().getReference()
                .child("CityCases").child("Bogor");
        ref_bogor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    long count = dataSnapshot.getChildrenCount();
                    String strLong = Long.toString(count);
                    bogor_tv.setText(strLong);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref_depok = FirebaseDatabase.getInstance().getReference()
                .child("CityCases").child("Depok");
        ref_depok.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    long count = dataSnapshot.getChildrenCount();
                    String strLong = Long.toString(count);
                    depok_tv.setText(strLong);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref_tanggerang = FirebaseDatabase.getInstance().getReference()
                .child("CityCases").child("Tanggerang");
        ref_tanggerang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    long count = dataSnapshot.getChildrenCount();
                    String strLong = Long.toString(count);
                    tanggerang_tv.setText(strLong);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref_bekasi = FirebaseDatabase.getInstance().getReference()
                .child("CityCases").child("Bekasi");
        ref_bekasi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    long count = dataSnapshot.getChildrenCount();
                    String strLong = Long.toString(count);
                    bekasi_tv.setText(strLong);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // BARIS BARU

        TextView greetings = (TextView) rootView.findViewById(R.id.greetings);
        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView bio = (TextView) rootView.findViewById(R.id.bio);
        bottom_sheet = rootView.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);


        TextView SettingsApp = (TextView) rootView.findViewById(R.id.settings_app);
        SettingsApp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingapp = new Intent(getActivity(), Settings.class);
                startActivity(settingapp);
            }
        });

        RelativeLayout CardView0 = (RelativeLayout) rootView.findViewById(R.id.CardView0);
        CardView0.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                Intent reportMissing = new Intent(getActivity(), UserReportMissingA.class);
                startActivity(reportMissing);
            }
        });
        RelativeLayout CardView1 = (RelativeLayout) rootView.findViewById(R.id.CardView1);
        CardView1.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                showBottomSheetDialog();
            }
        });

        getUsernameLocal();
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    bio.setText(dataSnapshot.child("bio").getValue().toString());
                    Picasso.with(getActivity())
                            .load(dataSnapshot.child("avatar").getValue().toString())
                            .centerCrop()
                            .fit()
                            .placeholder(R.drawable.icon_nopic).error(R.drawable.icon_nopic)
                            .into(avatar);
                    Calendar c = Calendar.getInstance();
                    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                        if (timeOfDay < 12) {
                            greetings.setText(rootView.getResources().getString(R.string.goodmorning));
                        }
                        else if (timeOfDay < 16) {
                            greetings.setText(rootView.getResources().getString(R.string.goodafternoon));
                        }
                        else if (timeOfDay < 21) {
                            greetings.setText(rootView.getResources().getString(R.string.goodevening));
                        }
                        else if(timeOfDay > 21){
                            greetings.setText(rootView.getResources().getString(R.string.goodnight));
                        }
                    }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    private void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.sheet_about, null);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        (view.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });

        sheetDialog = new BottomSheetDialog(getActivity());
        sheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sheetDialog.show();
        sheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sheetDialog = null;
            }
        });
    }

}
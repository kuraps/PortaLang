package com.tcodng.portalang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import cn.pedant.SweetAlert.SweetAlertDialog;
import static android.content.Context.MODE_PRIVATE;

public class FragmentProfile extends Fragment {
    DatabaseReference reference,reference2;
    DatabaseReference ref,ref2;
    ArrayList<FragmentPortalModelPeople> modelPeople;
    RecyclerView recyclerView;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView bio = (TextView) rootView.findViewById(R.id.bio);
        TextView username = (TextView) rootView.findViewById(R.id.username);
        TextView email = (TextView) rootView.findViewById(R.id.email);
        TextView report_total = (TextView) rootView.findViewById(R.id.report_total);
        ImageView btn_edit = (ImageView) rootView.findViewById(R.id.edit_btn);
        ImageView avatar = (ImageView) rootView.findViewById(R.id.avatar);
        LinearLayout logout = (LinearLayout) rootView.findViewById(R.id.logout);
        ref = FirebaseDatabase.getInstance().getReference("UsersReport").child(username_key_new).child("MissingReport");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.portal_adapter);
        getUsernameLocal();
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);
        reference.keepSynced(false);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    bio.setText(dataSnapshot.child("bio").getValue().toString());
                    username.setText(dataSnapshot.child("username").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                            Picasso.with(getActivity())
                            .load(dataSnapshot.child("avatar").getValue().toString())
                            .centerCrop()
                            .fit()
                            .placeholder(R.drawable.icon_nopic).error(R.drawable.icon_nopic)
                            .into(avatar);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        reference2 = FirebaseDatabase.getInstance().getReference()
                .child("UsersReport").child(username_key_new).child("MissingReport");
        reference2.keepSynced(false);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {

                    long count = dataSnapshot.getChildrenCount();
                    String strLong = Long.toString(count);
                    report_total.setText(strLong);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.sweet_exit_dialog_title)+" "+username_key_new)
                        .setContentText(getResources().getString(R.string.sweet_exit_dialog_body))
                        .setConfirmText(getResources().getString(R.string.sweet_exit_dialog_confirm))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, null);
                                editor.apply();
                                Intent gotosignin = new Intent(getActivity(), GetStarted.class);
                                gotosignin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                gotosignin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(gotosignin);
                                getActivity().finish();
                            }

                        })
                        .setCancelButton((getResources().getString(R.string.sweet_exit_dialog_cancel)), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), UserProfileEdit.class);
                startActivity(a);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ref != null){
            modelPeople = new ArrayList<FragmentPortalModelPeople>();

            ref2 = FirebaseDatabase.getInstance().getReference("UsersReport").child(username_key_new).child("MissingReport");
            ref2.keepSynced(false);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        FragmentPortalModelPeople p = dataSnapshot1.getValue(FragmentPortalModelPeople.class);
                        modelPeople.add(p);
                    }
                    FragmentProfileAdapterPeople portalAdapter = new FragmentProfileAdapterPeople(getContext(), modelPeople);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(portalAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
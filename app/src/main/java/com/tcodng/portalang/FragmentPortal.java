package com.tcodng.portalang;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class FragmentPortal extends Fragment {

    DatabaseReference ref,ref2;
    ArrayList<FragmentPortalModelPeople> modelPeople;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_portal, container, false);
        ref = FirebaseDatabase.getInstance().getReference().child("MissingPeople");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.portal_adapter);
        searchView = (SearchView) rootView.findViewById(R.id.searchView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ref != null){
            modelPeople = new ArrayList<FragmentPortalModelPeople>();
            ref2 = FirebaseDatabase.getInstance().getReference("MissingPeople");
            ref2.keepSynced(false);
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        FragmentPortalModelPeople p = dataSnapshot1.getValue(FragmentPortalModelPeople.class);
                        modelPeople.add(p);
                    }
                    FragmentPortalAdapterPeople portalAdapter = new FragmentPortalAdapterPeople(getContext(),modelPeople);
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
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

    }

    private void search(String str) {
        if (!str.equals("")){
            ArrayList<FragmentPortalModelPeople> myList = new ArrayList<>();
            for (FragmentPortalModelPeople object : modelPeople){
                if(object.getMissing_ppl_name().toLowerCase().contains(str.toLowerCase())){
                    myList.add(object);
                }
            }
            FragmentPortalAdapterPeople adapterClass = new FragmentPortalAdapterPeople(getContext(), myList);
            recyclerView.setAdapter(adapterClass);
        }

    }

}
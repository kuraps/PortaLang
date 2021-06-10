package com.tcodng.portalang;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.colindodd.gradientlayout.GradientRelativeLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class FragmentPortal extends Fragment {
    View fragmentView;
    ArrayList<FragmentPortalModelPeople> arrayList;
    RecyclerView recyclerView;
    int ppl_image[] = {R.drawable.icon_nopic,R.drawable.irwan_bulat,R.drawable.langse};
    String ppl_name[] = {"Kuraps NoTail", "Irwansah Abare bare", "Lord Gilang"};
    String ppl_desc[] = {"Tidak diketahui ...", "Keribo, kulit merah merona", "Hitam khas NTB, badan besar"};
    String ppl_since[] = {"NEW!", "69 Days", "1000 Days"};
    String ppl_city[] = {"Depok", "Citayam", "Sawangan"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_portal, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.portal_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
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
        FragmentPortalAdapterPeople adapter = new FragmentPortalAdapterPeople(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
        // BARIS BARU
        return rootView;
    }
}
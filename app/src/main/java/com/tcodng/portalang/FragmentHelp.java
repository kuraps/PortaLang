package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class FragmentHelp extends Fragment {
    View fragmentView;
    ArrayList<FragmentHelpModel> arrayList;
    RecyclerView recyclerView;
    int icon[] = {R.drawable.ic_faq};
    String title[] = {"FAQs"};
    String desc[] = {" Find intelligent answers instantly"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_help, container, false);
        ConstraintLayout item_portal = (ConstraintLayout) rootView.findViewById(R.id.item_portal);
        item_portal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                Intent faqs = new Intent(getActivity(), FAQs.class);
                startActivity(faqs);
            }
        });
        // BARIS BARU
        return rootView;
    }
}
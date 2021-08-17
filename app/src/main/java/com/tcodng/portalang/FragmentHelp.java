package com.tcodng.portalang;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        LinearLayout cs_wa = (LinearLayout)rootView.findViewById(R.id.cs_wa);
        LinearLayout cs_fb = (LinearLayout)rootView.findViewById(R.id.cs_fb);
        LinearLayout cs_em = (LinearLayout)rootView.findViewById(R.id.cs_email);
        LinearLayout cs_tw = (LinearLayout)rootView.findViewById(R.id.cs_tw);

        cs_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=6285156400335");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                startActivity(intent);
            }
        });
        cs_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fb = "https://facebook.com/yudhiez.chitilieaz";
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, fb);
                startActivity(intent);
            }
        });
        cs_em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                String emailuser = "yudhist3@gmail.com";
                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailuser});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "PortaLang");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hei i need to help");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });
        cs_tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tw = "https://twitter.com/kurapss";
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, tw);
                startActivity(intent);
            }
        });

        // BARIS BARU
        return rootView;
    }
}
package com.tcodng.portalang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import net.colindodd.gradientlayout.GradientRelativeLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class FragmentHome extends Fragment{

    View fragmentView;
    ArrayList<FragmentHomeModelCity> arrayList;
    RecyclerView recyclerView;
    int city_icon[] = {R.drawable.ic_city_depok,R.drawable.ic_city_bogor};
    String city_name[] = {"Depok", "Bogor"};
    String city_cases[] = {"14 Cases", "10 Cases"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.city_adapter);
        arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        for (int i = 0; i < city_icon.length; i++) {
            FragmentHomeModelCity cityModel = new FragmentHomeModelCity();
            cityModel.setImage(city_icon[i]);
            cityModel.setName(city_name[i]);
            cityModel.setCases(city_cases[i]);

            //add in array list
            arrayList.add(cityModel);
        }

        FragmentHomeAdapterCity adapter = new FragmentHomeAdapterCity(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);

        // BARIS BARU

        TextView greetings = (TextView) rootView.findViewById(R.id.greetings);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetings.setText(getResources().getString(R.string.goodmorning));
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greetings.setText(getResources().getString(R.string.goodafternoon));
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greetings.setText(getResources().getString(R.string.goodevening));
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            greetings.setText(getResources().getString(R.string.goodnight));
        }

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
        GradientRelativeLayout CardView1 = (GradientRelativeLayout) rootView.findViewById(R.id.CardView1);
        CardView1.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                Intent reportMissing = new Intent(getActivity(), UserReportFoundingA.class);
                startActivity(reportMissing);
            }
        });
        GradientRelativeLayout CardView2 = (GradientRelativeLayout) rootView.findViewById(R.id.CardView2);
        CardView2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
               // Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
                Intent portalBerita = new Intent(getActivity(), UserPortal.class);
                startActivity(portalBerita);
            }
        });
        RelativeLayout CardView3 = (RelativeLayout) rootView.findViewById(R.id.CardView3);

        CardView3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(getActivity(), Settings.class);
                startActivity(settings);
            }
        });

        RelativeLayout CardView4 = (RelativeLayout) rootView.findViewById(R.id.CardView4);
        return rootView;
    }

}
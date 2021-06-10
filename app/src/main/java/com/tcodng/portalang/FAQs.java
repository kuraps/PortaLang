package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FAQs extends AppCompatActivity {
int Click = 0;
    int Click2 = 0;
TextView search_missing,found_missing;
LinearLayout push_report,push_found;
ImageView dropdown,dropdown2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        dropdown = findViewById(R.id.dropdown);
        dropdown2 = findViewById(R.id.dropdown2);
        search_missing = findViewById(R.id.search_missing);
        found_missing = findViewById(R.id.found_missing);
        push_report = findViewById(R.id.push_report);
        push_found = findViewById(R.id.push_found);
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(300);
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);
        push_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Click == 0) {
                    search_missing.setVisibility(View.VISIBLE);
                    dropdown.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    search_missing.startAnimation(in);
                    dropdown.startAnimation(in);
                    Click++;
                } else if (Click == 1) {
                    Click = 0;
                    dropdown.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    search_missing.setVisibility(View.GONE);
                }
            }
        });
        push_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Click2 == 0) {
                    found_missing.setVisibility(View.VISIBLE);
                    dropdown2.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    found_missing.startAnimation(in);
                    dropdown2.startAnimation(in);
                    Click2++;
                } else if (Click2 == 1) {
                    Click2 = 0;
                    dropdown2.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    found_missing.setVisibility(View.GONE);
                }
            }
        });
    }
}
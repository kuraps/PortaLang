package com.tcodng.portalang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class UserReportFoundingA extends AppCompatActivity {
    Button btn_sure;
    ImageView btn_back;
    TextView tv_agreements;
    CheckBox acc_aggreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_founding);

        btn_sure = findViewById(R.id.sure);
        btn_back = findViewById(R.id.btn_back);
        tv_agreements = findViewById(R.id.agreements_text);
        acc_aggreement = findViewById(R.id.acc_aggreement);

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fillData = new Intent(UserReportFoundingA.this , UserReportFoundingB.class);
                startActivity(fillData);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        acc_aggreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(acc_aggreement.isChecked()){
                    btn_sure.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_sure.setEnabled(true);
                }else if (!acc_aggreement.isChecked()){
                    btn_sure.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_sure.setEnabled(false);
                }
            }
        });
    }
}
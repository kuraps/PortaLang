package com.tcodng.portalang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class UserPortal extends AppCompatActivity {

    TextView zoom_text,misstype,missname,missnik,missage,misscity,misschar,misschrono,missdate,missby;
    TextView dummy_wa,dummy_email,dummy_maps;
    ImageView missava;
    LinearLayout btn_back;
    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    BlurView blurView;
    Button btn_zoom,btn_related;
    BottomSheetBehavior sheetBehavior;
    BottomSheetDialog sheetDialog;
    View bottom_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);
        blurView=(BlurView)findViewById(R.id.blurView);
        blurBackground();
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        Sprite wave = new WanderingCubes();
        progressBar.setIndeterminateDrawable(wave);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Blur and Spinner
                blurView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
        dummy_wa = findViewById(R.id.dummywa);
        dummy_email = findViewById(R.id.dummyemail);
        dummy_maps = findViewById(R.id.dummymaps);
        misstype = findViewById(R.id.misstype);
        missname = findViewById(R.id.missing_people_name);
        missnik = findViewById(R.id.missnik);
        missage = findViewById(R.id.missage);
        misscity = findViewById(R.id.misscity);
        misschar = findViewById(R.id.missing_people_characteristic);
        misschrono = findViewById(R.id.missing_people_chronology);
        missdate = findViewById(R.id.missing_people_since);
        missby = findViewById(R.id.reportby);
        missava = findViewById(R.id.missing_people_photo);
        btn_zoom=findViewById(R.id.button_zoom);
        btn_related=findViewById(R.id.button_related);
        zoom_text=findViewById(R.id.txt3);
        btn_back=findViewById(R.id.btn_back);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_related.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        btn_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getZoomPhotos = zoom_text.getText().toString();
                final String getNamePeople = missname.getText().toString();
                Intent moved = new Intent(UserPortal.this, ZoomPhotos.class);
                moved.putExtra("zoom_photos", getZoomPhotos);
                moved.putExtra("name_ppl", getNamePeople);
                startActivity(moved);
            }
        });

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
            Intent gogetStarted = new Intent(UserPortal.this, NoInternet.class);
            startActivity(gogetStarted);
            finish();
        }
        getUsernameLocal();
        Bundle bundle = getIntent().getExtras();
        final String nik = bundle.getString("missing_ppl_nik");
        reference = FirebaseDatabase.getInstance().getReference().child("UsersReport").child(username_key_new).child("MissingReport").child(nik);
        reference.keepSynced(false);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = getIntent();
                Bundle extrass = intent.getExtras();
                String foto = extrass.getString("missing_ppl_photo");
                Picasso.with(UserPortal.this)
                        .load(foto)
                        .centerCrop()
                        .fit()
                        .placeholder(R.drawable.icon_nopic).error(R.drawable.icon_nopic)
                        .into(missava);
                String xmisstype = extrass.getString("missing_ppl_type");
                String xmissname = extrass.getString("missing_ppl_name");
                String xmissnik = extrass.getString("missing_ppl_nik");
                String xmissage = extrass.getString("missing_ppl_age");
                String xmisscity = extrass.getString("missing_ppl_city");
                String xmisschar = extrass.getString("missing_ppl_characteristic");
                String xmisschrono = extrass.getString("missing_ppl_chronology");
                String zoom_photo = extrass.getString("missing_ppl_photo");
                String xmissdate = extrass.getString("missing_ppl_date");
                String xmissaddress = extrass.getString("missing_ppl_address");
                String xemail = extrass.getString("missing_ppl_ur_email");
                String xphone = extrass.getString("missing_ppl_ur_number");
                String xname = extrass.getString("missing_ppl_ur_name");
                if(xmisstype.equals("Missing People")){
                    misstype.setText(getResources().getString(R.string.miss)+" ");
                }else {
                    misstype.setText(getResources().getString(R.string.found)+" ");
                }
                missname.setText(xmissname);
                missnik.setText(xmissnik);
                missage.setText(xmissage);
                misscity.setText(xmisscity);
                misschar.setText(xmisschar);
                misschrono.setText(xmisschrono);
                missdate.setText(xmissdate);
                missby.setText(xname);
                zoom_text.setText(zoom_photo);
                dummy_wa.setText(xphone);
                dummy_email.setText(xemail);
                dummy_maps.setText(xmissaddress);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }
    public void blurBackground(){
        float radius = 22f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }
    private void showBottomSheetDialog() {
        View view = getLayoutInflater().inflate(R.layout.sheet_related, null);

        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        (view.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });

        (view.findViewById(R.id.whatsapp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone="+dummy_wa.getText().toString()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                sheetDialog.dismiss();
            }
        });
        (view.findViewById(R.id.email)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Create the Intent */
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                String emailuser = dummy_email.getText().toString();
                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailuser});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "PortaLang");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hei i need to contact you");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
                sheetDialog.dismiss();
            }
        });
        (view.findViewById(R.id.maps)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressuser = dummy_maps.getText().toString();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, addressuser);
                startActivity(intent);
                sheetDialog.dismiss();
            }
        });

        sheetDialog = new BottomSheetDialog(this);
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
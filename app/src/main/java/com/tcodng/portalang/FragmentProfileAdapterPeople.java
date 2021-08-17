package com.tcodng.portalang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentProfileAdapterPeople extends RecyclerView.Adapter<FragmentProfileAdapterPeople.MyViewHolder> {
    DatabaseReference reference3;
    Context context;
    ArrayList<FragmentPortalModelPeople> modelPeople;

    public FragmentProfileAdapterPeople(Context context, ArrayList<FragmentPortalModelPeople> modelPeople){
        this.context = context;
        this.modelPeople = modelPeople;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile_people, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String missing_ppl_nik = modelPeople.get(position).getMissing_ppl_nik();
        reference3 = FirebaseDatabase.getInstance().getReference("MissingPeople").child(missing_ppl_nik);
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Picasso.with(holder.itemView.getContext())
                        .load(snapshot.child("missing_ppl_photo").getValue().toString())
                        .centerCrop()
                        .fit()
                        .placeholder(R.drawable.icon_nopic).error(R.drawable.icon_nopic)
                        .into(holder.xmissing_people_photo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        holder.xmissing_people_name.setText(modelPeople.get(position).getMissing_ppl_name());
        holder.xmissing_people_characteristic.setText(modelPeople.get(position).getMissing_ppl_characteristic());
        holder.xmissing_people_address.setText(modelPeople.get(position).getMissing_ppl_city());
        final String missing_ppl_photo = modelPeople.get(position).getMissing_ppl_photo();
        final String missing_ppl_police_report = modelPeople.get(position).getMissing_ppl_police_report();
        final String missing_people_name = modelPeople.get(position).getMissing_ppl_name();
        final String missing_ppl_age = modelPeople.get(position).getMissing_ppl_age();
        final String missing_ppl_address = modelPeople.get(position).getMissing_ppl_address();
        final String missing_ppl_ur_number = modelPeople.get(position).getMissing_ppl_ur_number();
        final String missing_ppl_ur_email = modelPeople.get(position).getMissing_ppl_ur_email();
        final String missing_ppl_type = modelPeople.get(position).getMissing_ppl_type();
        final String missing_ppl_date = modelPeople.get(position).getMissing_ppl_date();
        final String missing_ppl_city = modelPeople.get(position).getMissing_ppl_city();
        final String missing_ppl_characteristic = modelPeople.get(position).getMissing_ppl_characteristic();
        final String missing_ppl_chronology = modelPeople.get(position).getMissing_ppl_chronology();
        final String missing_ppl_ur_name = modelPeople.get(position).getMissing_ppl_ur_name();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moved = new Intent(context, UserPortal.class);
                moved.putExtra("missing_ppl_nik", missing_ppl_nik);
                moved.putExtra("missing_ppl_name", missing_people_name);
                moved.putExtra("missing_ppl_age", missing_ppl_age);
                moved.putExtra("missing_ppl_address", missing_ppl_address);
                moved.putExtra("missing_ppl_ur_number", missing_ppl_ur_number);
                moved.putExtra("missing_ppl_ur_email", missing_ppl_ur_email);
                moved.putExtra("missing_ppl_characteristic", missing_ppl_characteristic);
                moved.putExtra("missing_ppl_chronology", missing_ppl_chronology);
                moved.putExtra("missing_ppl_photo", missing_ppl_photo);
                moved.putExtra("missing_ppl_police_report", missing_ppl_police_report);
                moved.putExtra("missing_ppl_type", missing_ppl_type);
                moved.putExtra("missing_ppl_date", missing_ppl_date);
                moved.putExtra("missing_ppl_city", missing_ppl_city);
                moved.putExtra("missing_ppl_ur_name", missing_ppl_ur_name);
                context.startActivity(moved);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelPeople == null ? 0 : modelPeople.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView xmissing_people_name, xmissing_people_characteristic, xmissing_people_address;
        ImageView xmissing_people_photo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            xmissing_people_photo = itemView.findViewById(R.id.missing_people_photo);
            xmissing_people_name = itemView.findViewById(R.id.missing_people_name);
            xmissing_people_characteristic = itemView.findViewById(R.id.missing_people_characteristic);
            xmissing_people_address = itemView.findViewById(R.id.missing_people_address);
        }
    }


}
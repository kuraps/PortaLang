package com.tcodng.portalang;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class FragmentHomeAdapterCity extends RecyclerView.Adapter<FragmentHomeAdapterCity.viewHolder> {

    Context context;
    ArrayList<FragmentHomeModelCity> arrayList;
    DatabaseReference reference;

    public FragmentHomeAdapterCity(Context context, ArrayList<FragmentHomeModelCity> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public  FragmentHomeAdapterCity.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_city, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public  void onBindViewHolder(FragmentHomeAdapterCity.viewHolder viewHolder,int position) {
        viewHolder.city_name.setText(arrayList.get(position).getName());
        viewHolder.city_cases.setText(arrayList.get(position).getCases());
        viewHolder.city_icon.setImageResource(arrayList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView city_icon;
        TextView city_name,city_cases;

        public viewHolder(View itemView) {
            super(itemView);
            city_icon = (ImageView) itemView.findViewById(R.id.city_icon);
            city_name = (TextView) itemView.findViewById(R.id.city_name);
            city_cases = (TextView) itemView.findViewById(R.id.city_cases);

        }
    }

}

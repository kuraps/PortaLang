package com.tcodng.portalang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentPortalAdapterPeople extends RecyclerView.Adapter<FragmentPortalAdapterPeople.viewHolder> {

    Context context;
    ArrayList<FragmentPortalModelPeople> arrayList;

    public FragmentPortalAdapterPeople(Context context, ArrayList<FragmentPortalModelPeople> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public  FragmentPortalAdapterPeople.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_portal_people, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public  void onBindViewHolder(FragmentPortalAdapterPeople.viewHolder viewHolder, int position) {
        viewHolder.ppl_name.setText(arrayList.get(position).getName());
        viewHolder.ppl_desc.setText(arrayList.get(position).getDesc());
        viewHolder.ppl_since.setText(arrayList.get(position).getSince());
        viewHolder.ppl_city.setText(arrayList.get(position).getCity());
        viewHolder.ppl_image.setImageResource(arrayList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView ppl_image;
        TextView ppl_name,ppl_desc,ppl_since,ppl_city;

        public viewHolder(View itemView) {
            super(itemView);
            ppl_image = (ImageView) itemView.findViewById(R.id.ppl_image);
            ppl_name = (TextView) itemView.findViewById(R.id.ppl_name);
            ppl_desc = (TextView) itemView.findViewById(R.id.ppl_desc);
            ppl_since = (TextView) itemView.findViewById(R.id.ppl_since);
            ppl_city = (TextView) itemView.findViewById(R.id.ppl_city);

        }
    }

}

package com.tcodng.portalang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentHelpAdapter extends RecyclerView.Adapter<FragmentHelpAdapter.viewHolder> {

    Context context;
    ArrayList<FragmentHelpModel> arrayList;

    public FragmentHelpAdapter(Context context, ArrayList<FragmentHelpModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public  FragmentHelpAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_help_menu, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public  void onBindViewHolder(FragmentHelpAdapter.viewHolder viewHolder, int position) {
        viewHolder.title.setText(arrayList.get(position).getTitle());
        viewHolder.desc.setText(arrayList.get(position).getDesc());
        viewHolder.icon.setImageResource(arrayList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title,desc;

        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);

        }
    }

}

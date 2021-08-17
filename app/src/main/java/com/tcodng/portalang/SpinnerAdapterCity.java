package com.tcodng.portalang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class SpinnerAdapterCity extends ArrayAdapter<String> {
    private final String[] objects;

    public SpinnerAdapterCity(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        this.objects=objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_background_layout, parent, false);
        final TextView label=(TextView)row.findViewById(R.id.tv_type);
        label.setText(objects[position]);
        return row;
    }
}
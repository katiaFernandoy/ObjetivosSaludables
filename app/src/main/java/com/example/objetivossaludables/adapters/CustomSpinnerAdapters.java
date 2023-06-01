package com.example.objetivossaludables.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.objetivossaludables.R;

import java.util.List;

public class CustomSpinnerAdapters extends ArrayAdapter<String> {

    public CustomSpinnerAdapters(@NonNull Context context, List<String> items) {
        super(context, R.layout.list_item_spinner_genero,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_spinner_genero, parent, false);
        }

        TextView textViewItem = view.findViewById(R.id.textViewItem);
        String item = getItem(position);
        if (item != null) {
            textViewItem.setText(item);
        }

        return view;
    }
}

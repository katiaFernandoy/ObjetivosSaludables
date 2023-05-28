package com.example.objetivossaludables.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
        View view = super.getDropDownView(position, convertView, parent);
        return view;
    }
}

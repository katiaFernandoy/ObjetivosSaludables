package com.example.objetivossaludables.fragmenthomepage;


import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.MY_PREFERENCES;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.fragmenthomepage.pagesLogin.ConfgPreferencias;
import com.example.objetivossaludables.pagesLogin.ConfgPersonal;
import com.example.objetivossaludables.pagesLogin.IniciarSesion;

public class SettingsFragemt extends Fragment {

    public static final String STATUS = "status";
    private Button bt_cerrar;
    private LinearLayout linearLayoutConfPersonal;
    private LinearLayout linearLayoutPreferencias;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings_fragemt, container, false);

        bt_cerrar = root.findViewById(R.id.bt_cerrarSesion);
        linearLayoutConfPersonal = root.findViewById(R.id.linearLayoutConfPersonal);
        linearLayoutPreferencias = root.findViewById(R.id.linearLayoutPreferencias);

        linearLayoutConfPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfgPersonal.class);
                startActivity(intent);
            }
        });

        linearLayoutPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfgPreferencias.class);
                startActivity(intent);
            }
        });



        bt_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        return root;
    }

    public void cerrarSesion() {

        sharedPreferences = getContext().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(STATUS, false);
        editor.putString(EMAIL, "");
        editor.apply();

        ((Activity) getContext()).finish();
        Intent intent = new Intent(getContext(), IniciarSesion.class);
        startActivity(intent);

    }

}
package com.example.objetivossaludables.pages.fragmenthomepage;


import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.MY_PREFERENCES;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.pages.configuracion.ConfgNotificaciones;
import com.example.objetivossaludables.pages.configuracion.ConfgPersonal;
import com.example.objetivossaludables.pages.configuracion.ConfgPreferencias;
import com.example.objetivossaludables.pages.configuracion.ConfgSeguridad;
import com.example.objetivossaludables.pages.inicioapp.IniciarSesion;

public class SettingsFragemt extends Fragment {

    public static final String STATUS = "status";
    private Button bt_cerrar;
    private LinearLayout linearLayoutConfPersonal;
    private LinearLayout linearLayoutPreferencias;
    private LinearLayout linearLayoutSeguridad;
    private LinearLayout linearLayoutNotificaciones;

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings_fragemt, container, false);

        bt_cerrar = root.findViewById(R.id.bt_cerrarSesion);
        linearLayoutConfPersonal = root.findViewById(R.id.linearLayoutConfPersonal);
        linearLayoutPreferencias = root.findViewById(R.id.linearLayoutPreferencias);
        linearLayoutNotificaciones = root.findViewById(R.id.linearLayoutNotificaciones);
        linearLayoutSeguridad = root.findViewById(R.id.linearLayoutSeguridad);

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

        linearLayoutNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfgNotificaciones.class);
                startActivity(intent);
            }
        });

        linearLayoutSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConfgSeguridad.class);
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
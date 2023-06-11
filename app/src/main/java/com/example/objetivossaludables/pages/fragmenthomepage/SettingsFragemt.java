package com.example.objetivossaludables.pages.fragmenthomepage;

import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.pages.configuracion.ConfgNotificaciones;
import com.example.objetivossaludables.pages.configuracion.ConfgPersonal;
import com.example.objetivossaludables.pages.configuracion.ConfgPreferencias;
import com.example.objetivossaludables.pages.configuracion.ConfgSeguridad;
import com.example.objetivossaludables.pages.inicioapp.Portada;

import java.util.Collections;

public class SettingsFragemt extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings_fragemt, container, false);

        setColorState(getContext(), Collections.singletonList(root.findViewById(R.id.bt_cerrarSesion)));

        Button bt_cerrar = root.findViewById(R.id.bt_cerrarSesion);
        LinearLayout linearLayoutConfPersonal = root.findViewById(R.id.linearLayoutConfPersonal);
        LinearLayout linearLayoutPreferencias = root.findViewById(R.id.linearLayoutPreferencias);
        LinearLayout linearLayoutNotificaciones = root.findViewById(R.id.linearLayoutNotificaciones);
        LinearLayout linearLayoutSeguridad = root.findViewById(R.id.linearLayoutSeguridad);

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
        new UserPreferences(requireContext()).clearPreferences();
        ((Activity) getContext()).finish();
        Intent intent = new Intent(getContext(), Portada.class);
        startActivity(intent);
    }
}
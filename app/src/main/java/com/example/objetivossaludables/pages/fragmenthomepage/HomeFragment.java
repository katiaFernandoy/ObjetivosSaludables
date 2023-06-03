package com.example.objetivossaludables.pages.fragmenthomepage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.pages.ConsultarEntrenamientos;
import com.example.objetivossaludables.pages.EstablecerObjetivos;
import com.example.objetivossaludables.pages.GrabarPasos;
import com.example.objetivossaludables.pages.GrabarSuenio;

public class HomeFragment extends Fragment {

    private LinearLayout tvObjetivos;
    private LinearLayout llEntrenamiento;
    private LinearLayout llPasos;
    private LinearLayout llSuenio;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_home_fragmento, container, false);

        tvObjetivos = root.findViewById(R.id.llHorasSuenio);
        llEntrenamiento = root.findViewById(R.id.llEntrenamiento);
        llPasos = root.findViewById(R.id.llPasos);
        llSuenio = root.findViewById(R.id.llSuenio);

        tvObjetivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarObjetivos();
            }
        });

        llEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarEntrenamientos();
            }
        });

        llPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarPasos();
            }
        });

        llSuenio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarSuenio();
            }
        });


        return root;
    }

    public void lanzarSuenio() {
        Intent intent = new Intent(getContext(), GrabarSuenio.class);
        startActivity(intent);
    }

    public void lanzarPasos() {
        Intent intent = new Intent(getContext(), GrabarPasos.class);
        startActivity(intent);
    }

    public void lanzarEntrenamientos() {
        Intent intent = new Intent(getContext(), ConsultarEntrenamientos.class);
        startActivity(intent);
    }

    public void lanzarObjetivos() {
            Intent intent = new Intent(getContext(), EstablecerObjetivos.class);
            startActivity(intent);

    }
}
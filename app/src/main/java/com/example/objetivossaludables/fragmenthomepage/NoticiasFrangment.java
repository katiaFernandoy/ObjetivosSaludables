package com.example.objetivossaludables.fragmenthomepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.AdapterNoticias;
import com.example.objetivossaludables.modelo.Noticias;

import java.util.ArrayList;

public class NoticiasFrangment extends Fragment {

    RecyclerView rvNoticias;
    ArrayList<Noticias> listaNoticias;
    AdapterNoticias adapterNoticias;
    String[] titulosNoticias, enlacesNoticias;
    int[] imagenesResourceId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void getData() {

        for (int i = 0; i < titulosNoticias.length; i++) {
            Noticias noticias = new Noticias(
                    titulosNoticias[i],
                    imagenesResourceId[i],
                    enlacesNoticias[i]);
            listaNoticias.add(noticias);
        }
        adapterNoticias.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_noticias_frangment, container, false);

        rvNoticias = root.findViewById(R.id.recyclerView);
        rvNoticias.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNoticias.setHasFixedSize(true);

        listaNoticias = new ArrayList<>();
        adapterNoticias = new AdapterNoticias(getContext(), listaNoticias);

        rvNoticias.setAdapter(adapterNoticias);

        imagenesResourceId = new int[]{
                R.drawable.run,
                R.drawable.calentamiento,
                R.drawable.ejercicios_casa,
                R.drawable.consejos_comidas
        };
        titulosNoticias = getResources().getStringArray(R.array.titulo_noticias);
        enlacesNoticias = getResources().getStringArray(R.array.enlace_noticias);

        getData();
        return root;
    }
}
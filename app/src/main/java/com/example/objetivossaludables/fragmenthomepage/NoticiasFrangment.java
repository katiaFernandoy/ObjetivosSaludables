package com.example.objetivossaludables.fragmenthomepage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.AdapterNoticias;
import com.example.objetivossaludables.modelo.Noticias;

import java.util.ArrayList;

public class NoticiasFrangment extends Fragment {

    RecyclerView rvNoticias;
    ArrayList<Noticias> listaNoticias;
    AdapterNoticias adapterNoticias ;
    String[] newsTitulos;
    int [] imagenesResourceId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void getData() {

        for(int i = 0; i< newsTitulos.length; i++){

            Noticias noticias = new Noticias(newsTitulos[i],imagenesResourceId[i]);
            listaNoticias.add(noticias);
        }
        adapterNoticias.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_noticias_frangment, container, false);

        rvNoticias= root.findViewById(R.id.recyclerView);
        rvNoticias.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNoticias.setHasFixedSize(true);

        listaNoticias = new ArrayList<>();
        adapterNoticias = new AdapterNoticias(getContext(), listaNoticias);
        rvNoticias.setAdapter(adapterNoticias);

        newsTitulos = new String[]{
                "El deporte, la medicina perfecta: «No basta con decirlo en la consulta, hay que facilitarlo»",
                "El deporte, la medicina perfecta: «No basta con decirlo en la consulta, hay que facilitarlo»",
                "El deporte, la medicina perfecta: «No basta con decirlo en la consulta, hay que facilitarlo»",
                "El deporte, la medicina perfecta: «No basta con decirlo en la consulta, hay que facilitarlo»",
                "El deporte, la medicina perfecta: «No basta con decirlo en la consulta, hay que facilitarlo»",
                "El deporte, la medicina perfecta: «No basta con decirlo en la consulta, hay que facilitarlo»"
        };
        imagenesResourceId = new int[]{
                R.drawable.run,
                R.drawable.run,
                R.drawable.run,
                R.drawable.run,
                R.drawable.run,
                R.drawable.run
        };
        getData();
        return root;
    }
}
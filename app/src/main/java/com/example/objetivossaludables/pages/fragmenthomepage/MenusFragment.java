package com.example.objetivossaludables.pages.fragmenthomepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import java.util.Arrays;
import java.util.List;

public class MenusFragment extends Fragment {

    private List<Integer> listaMenus;
    private UserPreferences preferences;
    private ImageView ivMenuSeleccionado;
    private int actualPosicion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_menus_fragment, container, false);

        preferences = new UserPreferences(getContext());

        if(preferences.getObjetivoPeso()){ // Cambiar por el objetivo de ejercicio
            listaMenus = getListMenusPerderPeso();
        }else {
            listaMenus = getListMenusGanarMusculo();
        }

        ivMenuSeleccionado = root.findViewById(R.id.ivMenuSeleccionado);
        actualPosicion = preferences.getMenuSeleccionado();
        setearImagen(listaMenus.get(actualPosicion));

        root.findViewById(R.id.backMenu).setOnClickListener((v)-> accionesAlCambiarImagen(-1));

        root.findViewById(R.id.forwardMenu).setOnClickListener((v)-> accionesAlCambiarImagen(1));

        ((ToggleButton)root.findViewById(R.id.tgMenuSeleccionado)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                listaMenus = getListMenusPerderPeso();
                resetVariables(true);
            } else {
                listaMenus = getListMenusGanarMusculo();
                resetVariables(false);
            }
        });
        return root;
    }

    private void accionesAlCambiarImagen(int accion){
        actualPosicion = sliderImagenes(accion);
        setearImagen(listaMenus.get(actualPosicion));
        preferences.setMenuSeleccionado(actualPosicion);
    }

    private void resetVariables(Boolean isChecked){
        actualPosicion = 0;
        setearImagen(listaMenus.get(actualPosicion));
        preferences.setMenuSeleccionado(actualPosicion);
        preferences.setObjetivoPeso(isChecked);
    }

    private void setearImagen(int imagen){
        ivMenuSeleccionado.setImageDrawable(getResources().getDrawable(imagen));
    }

    private int sliderImagenes(int slide){
        if(actualPosicion == 0 && slide == -1){
            return listaMenus.size()-1;
        }else if(actualPosicion == listaMenus.size()-1 && slide == 1){
            return 0;
        }

        return actualPosicion + slide;
    }

    private List<Integer> getListMenusPerderPeso(){
        return Arrays.asList(
                R.drawable.menu1,
                R.drawable.menu2,
                R.drawable.menu3,
                R.drawable.menu4,
                R.drawable.menu5);
    }

    private List<Integer> getListMenusGanarMusculo(){
        return Arrays.asList(
                R.drawable.fuerza1,
                R.drawable.fuerza2,
                R.drawable.fuerza3);
    }
}
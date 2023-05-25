package com.example.objetivossaludables.fragmenthomepage;

import static com.example.objetivossaludables.pagesLogin.IniciarSesion.EMAIL;
import static com.example.objetivossaludables.pagesLogin.IniciarSesion.MY_PREFERENCES;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.pagesLogin.IniciarSesion;
import com.example.objetivossaludables.pagesLogin.Menu;

public class SettingsFragemt extends Fragment {

    private Button bt_cerrar;
    private SharedPreferences sharedPreferences;
    public static final String STATUS = "status";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View root = inflater.inflate(R.layout.fragment_settings_fragemt, container, false);

      bt_cerrar = root.findViewById(R.id.bt_cerrarSesion);

      bt_cerrar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              cerrarSesion();
          }
      });

      return root;
    }

    public void cerrarSesion(){

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
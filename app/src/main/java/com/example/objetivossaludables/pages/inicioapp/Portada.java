package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.SettingPreferences;

import java.util.Arrays;
import java.util.Locale;

public class Portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cambiarIdioma(new SettingPreferences(this).getIdioma());
        setContentView(R.layout.activity_portada);

        setColorState(this,
                Arrays.asList(findViewById(R.id.bt_crearCuentaPortada),findViewById(R.id.bt_iniciarSesionPortada)));

        findViewById(R.id.bt_crearCuentaPortada).setOnClickListener(v -> {
            Intent intent = new Intent(Portada.this, CrearCuenta.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.bt_iniciarSesionPortada).setOnClickListener(v -> {
            Intent intent = new Intent(Portada.this, IniciarSesion.class);
            startActivity(intent);
            finish();
        });

    }

    public void cambiarIdioma(int idioma) {
        Locale locale;

        // Determina el idioma seleccionado
        switch (idioma) {
            case 0: //Español
                locale = new Locale("es");
                break;
            case 1: // Inglés
                locale = new Locale("en");
                break;
            default:
                locale = Locale.getDefault();
                break;
        }

        // Establece el idioma seleccionado como el idioma de la aplicación
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }
}
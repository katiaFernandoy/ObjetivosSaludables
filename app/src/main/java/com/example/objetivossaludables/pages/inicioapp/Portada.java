package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;

import java.util.Arrays;

public class Portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        setColorState(this,
                Arrays.asList(findViewById(R.id.bt_crearCuentaPortada),findViewById(R.id.bt_iniciarSesionPortada)));

        findViewById(R.id.bt_crearCuentaPortada).setOnClickListener(v -> {
            Intent intent = new Intent(Portada.this, CrearCuenta.class);
            startActivity(intent);
        });

        findViewById(R.id.bt_iniciarSesionPortada).setOnClickListener(v -> {
            Intent intent = new Intent(Portada.this, IniciarSesion.class);
            startActivity(intent);
        });

    }

}
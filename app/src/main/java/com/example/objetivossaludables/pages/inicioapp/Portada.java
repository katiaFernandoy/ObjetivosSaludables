package com.example.objetivossaludables.pages.inicioapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;

public class Portada extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_portada);

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
}
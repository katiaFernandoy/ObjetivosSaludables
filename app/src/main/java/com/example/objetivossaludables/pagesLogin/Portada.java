package com.example.objetivossaludables.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.objetivossaludables.R;

public class Portada extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_portada);

        findViewById(R.id.bt_crearCuentaPortada).setOnClickListener(v -> {
            Intent intent = new Intent(Portada.this,CrearCuenta.class);
            startActivity(intent);
        });

        findViewById(R.id.bt_iniciarSesionPortada).setOnClickListener(v -> {
            Intent intent = new Intent(Portada.this,IniciarSesion.class);
            startActivity(intent);
        });






    }
}
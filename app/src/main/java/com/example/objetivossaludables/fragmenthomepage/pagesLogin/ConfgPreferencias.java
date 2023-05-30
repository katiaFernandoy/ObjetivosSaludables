package com.example.objetivossaludables.fragmenthomepage.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.example.objetivossaludables.R;

import java.util.Locale;

public class ConfgPreferencias extends AppCompatActivity {

    private MediaPlayer sonido;
    private Button btSonidoActivar;
    private Button btSonidoDesactivar;

    private Spinner spinnerIdioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_preferencias);

        sonido = MediaPlayer.create(this, R.raw.sonido1);
        btSonidoActivar = findViewById(R.id.btSonidoActivar);
        btSonidoDesactivar = findViewById(R.id.btSonidoDesactivar);
        spinnerIdioma = findViewById(R.id.spinnerIdioma);




    }

}
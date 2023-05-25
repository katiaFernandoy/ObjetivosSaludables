package com.example.objetivossaludables.pagesLogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;


public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        new HiloEsperaCount().start();
    }

    class HiloEsperaCount extends Thread {

        @Override
        public void run() {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Inicio.this, Portada.class);
            startActivity(intent);
            finish();
        }
    }
}
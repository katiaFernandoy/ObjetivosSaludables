package com.example.objetivossaludables.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;

import java.util.Arrays;

public class ConfgPersonal extends AppCompatActivity {

    private EditText txt_nombreModif;
    private EditText txt_PesoModif;
    private EditText txt_fechaModif;
    private Spinner spn_generoModif;
    private EditText txt_alturaModif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_personal);
        iniciarSpinner();


    }

    private void iniciarSpinner() {

        spn_generoModif = findViewById(R.id.spinner);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.spinnerGenero)));

        spn_generoModif.setAdapter(adapters);

    }
}
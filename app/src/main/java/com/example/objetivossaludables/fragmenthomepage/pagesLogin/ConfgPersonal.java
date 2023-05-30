package com.example.objetivossaludables.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;

import java.util.Arrays;
import java.util.List;

import com.example.objetivossaludables.modelo.ApiInformacionPersonal;

public class ConfgPersonal extends AppCompatActivity {

    private Spinner spn_generoModif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_personal);
        iniciarSpinner();

        List<View> vistas = Arrays.asList(
                findViewById(R.id.txt_nombreModif),
                findViewById(R.id.txt_pesoModif),
                findViewById(R.id.txt_fechaModif),
                null,
                findViewById(R.id.txt_alturaModif)
        );

        ProgressDialog pdLoading = new ProgressDialog(ConfgPersonal.this);

        ApiInformacionPersonal apiInformacionPersonal = new ApiInformacionPersonal(vistas,ConfgPersonal.this,pdLoading);
        apiInformacionPersonal.execute();
    }

    private void iniciarSpinner() {
        spn_generoModif = findViewById(R.id.spinner);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.spinnerGenero)));

        spn_generoModif.setAdapter(adapters);
    }
}
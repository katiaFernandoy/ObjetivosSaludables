package com.example.objetivossaludables.pages.configuracion;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;
import com.example.objetivossaludables.manager.api.ApiGetInformacionPersonal;
import com.example.objetivossaludables.manager.api.ApiModificarInfoPersonal;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.modelo.InformacionPersonal;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ConfgPersonal extends AppCompatActivity {

    private static InformacionPersonal infoPersonal = null;
    protected static Spinner spn_generoModif;
    protected static TextInputEditText txt_nombreMod, txt_pesoMod, txt_fechaMod, txt_alturaMod;
    @SuppressLint("SimpleDateFormat")
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    protected static UserPreferences preferences;
    protected static ProgressDialog pdLoading;

    public static Context context;

    public static InformacionPersonal getInfoPersonal() {
        return infoPersonal;
    }

    public static void setInfoPersonal(InformacionPersonal infoPersonal) {
        ConfgPersonal.infoPersonal = infoPersonal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_personal);
        context = getApplicationContext();
        preferences = new UserPreferences(this);
        pdLoading = new ProgressDialog(this);

        iniciarSpinner();

        txt_nombreMod = findViewById(R.id.txt_nombreModif);
        txt_pesoMod = findViewById(R.id.txt_pesoModif);
        txt_fechaMod = findViewById(R.id.txt_fechaModif);
        txt_alturaMod = findViewById(R.id.txt_alturaModif);
        Button btn_modificar = findViewById(R.id.bt_modificarInfoUsu);

        new ApiGetInformacionPersonal();

        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ApiModificarInfoPersonal();
            }
        });
    }

    private void iniciarSpinner() {
        spn_generoModif = findViewById(R.id.spinner);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.spinnerGenero)));

        spn_generoModif.setAdapter(adapters);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        infoPersonal = null;
    }
}
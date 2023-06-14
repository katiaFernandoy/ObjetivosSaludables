package com.example.objetivossaludables.pages.configuracion;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;
import com.example.objetivossaludables.manager.media.MediaManager;
import com.example.objetivossaludables.manager.sharedpreferences.SettingPreferences;

import java.util.Arrays;
import java.util.Locale;

public class ConfgPreferencias extends AppCompatActivity {

    private Spinner spinnerIdioma;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchSonido, switchVibracion;
    private SettingPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_preferencias);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        switchSonido = findViewById(R.id.switchSonido);
        switchVibracion = findViewById(R.id.switchVibracion);
        spinnerIdioma = findViewById(R.id.spinnerIdioma);
        RadioGroup rgColores = findViewById(R.id.rg_Colores);

        preferences = new SettingPreferences(getApplicationContext());

        iniciarComponentes();

        switchSonido.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                MediaManager.playSoundWithoutVerification(getApplicationContext());
            } else {
                MediaManager.stopSound();
            }

            preferences.setSound(isChecked);
        });

        switchVibracion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                MediaManager.vibrateWithoutVerification(getApplicationContext());
            } else {
                MediaManager.stopVibrate();
            }

            preferences.setVibrate(isChecked);
        });

        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("idioma","Idioma -> " + position);
                if(position == preferences.getIdioma()){
                    return;
                }

                Toast.makeText(getApplicationContext(), R.string.idiomaCambiado, Toast.LENGTH_SHORT).show();
                cambiarIdioma(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rgColores.setOnCheckedChangeListener((radioGroup, radioButtonId) -> {
            if (radioButtonId != -1 && getCheckBoxAtPosition() != preferences.getColor()) {
                preferences.setColor(getCheckBoxAtPosition());
            }
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
        preferences.setIdioma(idioma);
        recreate();
    }

    private void iniciarSpinner() {
        spinnerIdioma = findViewById(R.id.spinnerIdioma);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.idiomas)));

        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma.setAdapter(adapters);

        spinnerIdioma.setSelection(preferences.getIdioma());
    }

    private void iniciarComponentes(){
        switchSonido.setChecked(preferences.getSoundState());
        switchVibracion.setChecked(preferences.getVibrateState());
        setCheckBoxAtPosition(preferences.getColor());

        iniciarSpinner();
    }

    private int getCheckBoxAtPosition(){
            if(((RadioButton)findViewById(R.id.radioButtonRosa)).isChecked()){
                return 1;
            }else if(((RadioButton)findViewById(R.id.radioButtonAzul)).isChecked()){
                return 2;
            } else{
                return 0;
            }
    }

    private void setCheckBoxAtPosition(int position){
        switch (position) {
            case 1: //rosa
                ((RadioButton) findViewById(R.id.radioButtonRosa)).setChecked(true);
                break;
            case 2: // azul
                ((RadioButton) findViewById(R.id.radioButtonAzul)).setChecked(true);
                break;
            default: // verde
                ((RadioButton) findViewById(R.id.radioButtonVerde)).setChecked(true);
        }
    }
}


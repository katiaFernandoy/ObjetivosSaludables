package com.example.objetivossaludables.pages.configuracion;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
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

    private Switch switchSonido;
    private Switch switchVibracion;
    private Spinner spinnerIdioma;
    private SettingPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_preferencias);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        spinnerIdioma = findViewById(R.id.spinnerIdioma);
        switchSonido = findViewById(R.id.switchSonido);
        switchVibracion = findViewById(R.id.switchVibracion);

        preferences = new SettingPreferences(getApplicationContext());

        switchSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MediaManager.playSoundWithoutVerification(getApplicationContext());
                } else {
                    MediaManager.stopSound();
                }

                preferences.setSound(isChecked);
            }
        });

        switchVibracion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MediaManager.vibrateWithoutVerification(getApplicationContext());
                } else {
                    MediaManager.stopVibrate();
                }

                preferences.setVibrate(isChecked);
            }
        });

        iniciarSpinner();

        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idiomaSeleccionado = parent.getItemAtPosition(position).toString();
                // Aquí implementamos el metodo para poder cambiar de idioma

                if(idiomaSeleccionado.equals(preferences.getIdioma())){
                    return;
                }

                Toast.makeText(getApplicationContext(), R.string.idiomaCambiado, Toast.LENGTH_SHORT).show();
                cambiarIdioma(idiomaSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void cambiarIdioma(String idioma) {
        Locale locale;

        // Determina el idioma seleccionado
        switch (idioma) {
            case "Español":
                locale = new Locale("es");
                break;
            case "Ingles":
                locale = new Locale("en");
                break;
            case "Frances":
                locale = new Locale("fr");
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
    }

    private void iniciarSpinner() {

        spinnerIdioma = findViewById(R.id.spinnerIdioma);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.idiomas)));

        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma.setAdapter(adapters);

        spinnerIdioma.setSelection(getPosicionIdioma());
    }

    private int getPosicionIdioma() {
        switch (preferences.getIdioma()) {
            case "Ingles":
                return 1;
            case "Frances":
                return 2;
            default: // Español
                return 0;
        }
    }
}


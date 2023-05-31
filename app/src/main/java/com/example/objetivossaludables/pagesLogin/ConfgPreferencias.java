package com.example.objetivossaludables.pagesLogin;

import static android.os.VibrationEffect.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ConfgPreferencias extends AppCompatActivity {

    private MediaPlayer sonido;
    private Switch switchVibracionSonido;

    private Spinner spinnerIdioma;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_preferencias);

        sonido = MediaPlayer.create(this, R.raw.sonido1);
        spinnerIdioma = findViewById(R.id.spinnerIdioma);
        switchVibracionSonido = findViewById(R.id.switchVibracionSonido);
        spinner = findViewById(R.id.spinner);


        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        switchVibracionSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Activar vibración del botón
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        vibrator.vibrate(createOneShot(500, DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                } else {
                    // Desactivar vibración del botón
                    vibrator.cancel();
                }
            }
        });


        iniciarSpinner();

        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccionarIdioma = parent.getItemAtPosition(position).toString();
                // Aquí implementamos el metodo para poder cambiar de idioma

                Toast.makeText(getApplicationContext(), seleccionarIdioma, Toast.LENGTH_SHORT).show();

                cambiarIdioma(seleccionarIdioma);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción cuando no se selecciona ningún idioma




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

        getResources().

                updateConfiguration(configuration, getResources().

                        getDisplayMetrics());

        // Reinicia la actividad para aplicar el cambio de idioma
        //recreate();

    }

    private void iniciarSpinner() {

        spinnerIdioma = findViewById(R.id.spinnerIdioma);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.idiomas)));

        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma.setAdapter(adapters);
    }


    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public SpinnerActivity() { spinner.setOnItemSelectedListener(this);}

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            String seleccionarIdioma = parent.getItemAtPosition(pos).toString();
            // Aquí implementamos el metodo para poder cambiar de idioma

            cambiarIdioma(seleccionarIdioma);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}


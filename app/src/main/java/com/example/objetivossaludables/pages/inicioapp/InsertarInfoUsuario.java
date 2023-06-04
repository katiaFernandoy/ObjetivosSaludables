package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.DATE_FORMAT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;
import com.example.objetivossaludables.manager.api.ApiInsertarInformacionPersonal;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.modelo.InformacionPersonal;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Arrays;

public class InsertarInfoUsuario extends AppCompatActivity {

    private static InformacionPersonal newInfoPersonal = null;
    protected static TextInputEditText txt_insertPeso;
    protected static TextInputEditText txt_insertFechaNaci;
    protected static TextInputEditText txt_insertAltura;
    protected static Spinner spinnerInsertgenero;

    protected static UserPreferences preferences;
    protected static ProgressDialog pdLoading;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_info_usuario);
        context = InsertarInfoUsuario.this;
        preferences = new UserPreferences(this);
        pdLoading = new ProgressDialog(this);

        iniciarSpinner();

        txt_insertPeso = findViewById(R.id.txt_insertPeso);
        txt_insertFechaNaci = findViewById(R.id.txt_insertFechaNaci);
        txt_insertAltura = findViewById(R.id.txt_insertAltura);
        Button bt_insertInfo = findViewById(R.id.btn_insertInfo);

        bt_insertInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!verificarDatos()){
                   return;
               }

                try {
                    newInfoPersonal = new InformacionPersonal(
                            new UserPreferences(getApplicationContext()).getUserEmail(),
                            Double.parseDouble(txt_insertPeso.getText().toString()),
                            DATE_FORMAT.parse(txt_insertFechaNaci.getText().toString()),
                           String.valueOf( spinnerInsertgenero.getSelectedItemId()),
                            Integer.parseInt(txt_insertAltura.getText().toString()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                new ApiInsertarInformacionPersonal();
            }
        });
    }

    public boolean verificarDatos(){

        if(txt_insertPeso.getText() == null || txt_insertPeso.getText().equals("")){
            Toast.makeText(context.getApplicationContext(),getResources().getText(R.string.errorFaltaCampo),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_insertFechaNaci.getText() == null || txt_insertFechaNaci.getText().equals("")){
            Toast.makeText(context.getApplicationContext(),getResources().getText(R.string.campoFecha),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_insertAltura.getText() == null || txt_insertAltura.getText().equals("")){
            Toast.makeText(context.getApplicationContext(),getResources().getText(R.string.campoAltura),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void iniciarSpinner() {

        spinnerInsertgenero = findViewById(R.id.spinnerInsertGenero);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.spinnerGenero)));

        spinnerInsertgenero.setAdapter(adapters);
    }

    protected static InformacionPersonal getNewInfoPersonal() {
        return newInfoPersonal;
    }
}
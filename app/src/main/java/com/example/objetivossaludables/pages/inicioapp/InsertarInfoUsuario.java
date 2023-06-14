package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsInfoPersonal;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INSERT_INFO_PERSONAL;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class InsertarInfoUsuario extends AppCompatActivity implements ApiInterface {

    private TextInputEditText txt_insertPeso, txt_insertFechaNaci, txt_insertAltura;
    private Spinner spinnerInsertgenero;
    private PdLoading pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_info_usuario);
        setColorState(this, Collections.singletonList(findViewById(R.id.btn_insertInfo)));

        iniciarSpinner();
        txt_insertPeso = findViewById(R.id.txt_insertPeso);
        txt_insertFechaNaci = findViewById(R.id.txt_insertFechaNaci);
        txt_insertAltura = findViewById(R.id.txt_insertAltura);
        Button bt_insertInfo = findViewById(R.id.btn_insertInfo);

        bt_insertInfo.setOnClickListener(v -> {
            if(!verificarDatos()){
                return;
            }

            //email, peso, altura, fecha, genero
            HashMap <String,String> params = getParamsInfoPersonal(
                    new UserPreferences(this).getUserEmail(),
                    getTexto(txt_insertPeso), //quitar los espacios al principio y final
                    getTexto(txt_insertAltura),
                    getTexto(txt_insertFechaNaci),
                    String.valueOf(spinnerInsertgenero.getSelectedItem()));

            pdLoading = new PdLoading(this);
            new ApiHandler(this,URL_INSERT_INFO_PERSONAL,params).start(); // start que sino no funciona
        });
    }

    public boolean verificarDatos(){
        if(txt_insertPeso.getText() == null || getTexto(txt_insertPeso).equals("")){
            Toast.makeText(this,getResources().getText(R.string.errorFaltaCampo),Toast.LENGTH_SHORT).show();
            return false;
        }if(!isDouble(getTexto(txt_insertPeso))){
            Toast.makeText(this,getResources().getText(R.string.campoPesoIncorrecto),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_insertFechaNaci.getText() == null || getTexto(txt_insertFechaNaci).equals("")){
            Toast.makeText(this,getResources().getText(R.string.campoFecha),Toast.LENGTH_SHORT).show();
            return false;
        }if(!isDate(getTexto(txt_insertFechaNaci))){
            Toast.makeText(this,getResources().getText(R.string.campoFechaIncorrecto),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_insertAltura.getText() == null || getTexto(txt_insertAltura).equals("")){
            Toast.makeText(this,getResources().getText(R.string.campoAltura),Toast.LENGTH_SHORT).show();
            return false;
        }if(!isInteger( getTexto(txt_insertAltura))){
            Toast.makeText(this,getResources().getText(R.string.campoAlturaIncorrecto),Toast.LENGTH_SHORT).show();
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

    @Override
    public void returnResponse(JSONObject json) {
        pdLoading.dismiss(); // Se cierra el PdLoading

        try {
            if(json.getBoolean("error")){
                Toast.makeText(this,json.getString("message"),Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this,json.getString("message"),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, IniciarSesion.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
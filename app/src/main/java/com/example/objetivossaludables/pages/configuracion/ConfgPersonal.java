package com.example.objetivossaludables.pages.configuracion;

import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsInfoPersonal;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INFORMACION_PERSONAL;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_MODIFICAR_INFO_PERSONAL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.DATE_FORMAT;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.*;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.adapters.CustomSpinnerAdapters;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.modelo.InformacionPersonal;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;

public class ConfgPersonal extends AppCompatActivity implements ApiInterface {

    private InformacionPersonal oldInfo, newInfo;
    private boolean isObtenerInfo;
    private Spinner spn_generoModif;
    private TextInputEditText txt_nombreMod, txt_pesoMod, txt_fechaMod, txt_alturaMod;
    private UserPreferences preferences;
    private PdLoading pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_personal);

        preferences = new UserPreferences(this);
        iniciarSpinner();
        TextView txt_mailInfoUsu = findViewById(R.id.txt_emailInfoPersonal);
        txt_mailInfoUsu.setText(preferences.getUserEmail());
        txt_nombreMod = findViewById(R.id.txt_nombreModif);
        txt_pesoMod = findViewById(R.id.txt_pesoModif);
        txt_fechaMod = findViewById(R.id.txt_fechaModif);
        txt_alturaMod = findViewById(R.id.txt_alturaModif);
        Button btn_modificar = findViewById(R.id.bt_modificarInfoUsu);

        obtenerInformacionPersonal();

        btn_modificar.setOnClickListener(v -> {
            if(!verificarDatos()){
                return;
            }
            try {
                nuevaInformacionPersonal();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean verificarDatos(){
        if(txt_nombreMod.getText() == null || getTexto(txt_nombreMod).equals("")){
            Toast.makeText(this,getResources().getText(R.string.faltaCampoNombre),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_pesoMod.getText() == null || getTexto(txt_pesoMod).equals("")){
            Toast.makeText(this,getResources().getText(R.string.faltaCampoPeso),Toast.LENGTH_SHORT).show();
            return false;
        }if(!isDouble(getTexto(txt_pesoMod))){
            Toast.makeText(this,getResources().getText(R.string.campoPesoIncorrecto),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_fechaMod.getText() == null || getTexto(txt_fechaMod).equals("")){
            Toast.makeText(this,getResources().getText(R.string.campoFecha),Toast.LENGTH_SHORT).show();
            return false;
        }if(!isDate(getTexto(txt_fechaMod))){
            Toast.makeText(this,getResources().getText(R.string.campoFechaIncorrecto),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_alturaMod.getText() == null || getTexto(txt_alturaMod).equals("")){
            Toast.makeText(this,getResources().getText(R.string.campoAltura),Toast.LENGTH_SHORT).show();
            return false;
        }if(!isInteger( getTexto(txt_alturaMod))){
            Toast.makeText(this,getResources().getText(R.string.campoAlturaIncorrecto),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void iniciarSpinner() {
        spn_generoModif = findViewById(R.id.spinner);

        CustomSpinnerAdapters adapters = new CustomSpinnerAdapters(
                this, Arrays.asList(getResources().getStringArray(R.array.spinnerGenero)));

        spn_generoModif.setAdapter(adapters);
    }

    private void obtenerInformacionPersonal(){
        isObtenerInfo = true;
        pdLoading = new PdLoading(this);
        new ApiHandler(this,URL_INFORMACION_PERSONAL,
                getParamsInfoPersonal(preferences.getUserEmail())).start();
    }

    private void nuevaInformacionPersonal() throws ParseException {
        pdLoading = new PdLoading(this);
        new ApiHandler(this,URL_MODIFICAR_INFO_PERSONAL, settingInformacionPersonal()).start();
    }

    @Override
    public void returnResponse(JSONObject json) {
        pdLoading.dismiss(); // Se cierra el PdLoading

        try {
            if(isObtenerInfo){
                handlerResponseObtenerInfoPersonal(json);
                return;
            }

            if (json.getBoolean("error")) {
                Toast.makeText(this, getResources().getText(R.string.errorAlObtenerInfo), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
            oldInfo = newInfo;
            newInfo = null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handlerResponseObtenerInfoPersonal(JSONObject json) throws JSONException, ParseException {
        isObtenerInfo = false;

        if(json.getBoolean("error")){
            Toast.makeText(this, getResources().getText(R.string.errorAlObtenerInfo), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Toast.makeText(this,json.getString("message"),Toast.LENGTH_SHORT).show();
        oldInfo = new InformacionPersonal(
                preferences.getUserEmail(),
                json.getString("nombre"),
                json.getDouble("peso"),
                json.getInt("altura"),
                json.getString("genero"),
                DATE_FORMAT.parse(json.getString("fechaNacimiento")));

        runOnUiThread(() -> {
            txt_nombreMod.setText(oldInfo.getNombre());
            txt_pesoMod.setText(String.valueOf(oldInfo.getPeso()));
            txt_fechaMod.setText(DATE_FORMAT.format(oldInfo.getFechaNacimiento()));
            spn_generoModif.setSelection(getGenero(oldInfo.getGenero()));
            txt_alturaMod.setText(String.valueOf(oldInfo.getAltura()));
        });
    }

    private HashMap<String,String> settingInformacionPersonal() throws ParseException {
        newInfo = new InformacionPersonal(
                oldInfo.getEmail(),
                getTexto(txt_nombreMod),
                Double.parseDouble(getTexto(txt_pesoMod)),
                Integer.parseInt(getTexto(txt_alturaMod)),
                spn_generoModif.getSelectedItem().toString(),
                DATE_FORMAT.parse(getTexto(txt_fechaMod)));

        return getParamsInfoPersonal(
                newInfo.getEmail(),
                newInfo.getNombre().equals(oldInfo.getNombre()) ? "" : newInfo.getNombre(),
                newInfo.getPeso() == oldInfo.getPeso() ? "" : String.valueOf(newInfo.getPeso()),
                newInfo.getAltura() == oldInfo.getAltura() ? "" : String.valueOf(newInfo.getAltura()),
                DATE_FORMAT.format(oldInfo.getFechaNacimiento()).equals(getTexto(txt_fechaMod)) ? "" : getTexto(txt_fechaMod),
                newInfo.getGenero().equals(oldInfo.getGenero()) ? "" : newInfo.getGenero());
    }

    private int getGenero(String genero) {
        String[] generos = getResources().getStringArray(R.array.spinnerGenero);

        for (int i = 0; i < generos.length; i++) {
            if (generos[i].equals(genero)) {
                return i;
            }
        }

        return 0;
    }
}
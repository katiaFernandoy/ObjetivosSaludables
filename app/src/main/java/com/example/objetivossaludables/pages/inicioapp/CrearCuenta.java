package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsInfo;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_REGISTRO;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;


public class CrearCuenta extends AppCompatActivity implements ApiInterface {

    private UserPreferences preferences;
    private PdLoading pdLoading;
    private EditText txt_nombreLogin, txt_apellidoLogin, txt_mailLogin, txt_passLogin, txt_passLogin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        setColorState(this, Collections.singletonList(findViewById(R.id.bt_crearCuenta)));
        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        txt_nombreLogin = findViewById(R.id.txt_nombreLogin);
        txt_apellidoLogin = findViewById(R.id.txt_apellidoLogin);
        txt_mailLogin = findViewById(R.id.txt_mailLogin);
        txt_passLogin = findViewById(R.id.txt_passLogin);
        txt_passLogin2 = findViewById(R.id.txt_passLogin2);

        preferences = new UserPreferences(this);
    }


    public boolean verificarCampos() {
        if (txt_nombreLogin.getText() == null || txt_nombreLogin.getText().toString().equals("")) {
            resaltarError(R.id.lbNameError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.name));
            return false;
        } else if (txt_nombreLogin.getText().toString().length() > 28) {
            resaltarError(R.id.lbNameError, getResources().getString(R.string.errorMaxChars));
            return false;
        }
        if (txt_apellidoLogin.getText() == null || txt_apellidoLogin.getText().toString().equals("")) {
            resaltarError(R.id.lbApellidoError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.Appellido));
            return false;
        } else if (txt_apellidoLogin.getText().toString().length() > 28) {
            resaltarError(R.id.lbApellidoError, getResources().getString(R.string.errorMaxChars));
            return false;
        }
        if (txt_mailLogin.getText() == null || txt_mailLogin.getText().toString().equals("")) {
            resaltarError(R.id.lbEmailError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.email));
            return false;
        } else if (!verificarMail()) {
            resaltarError(R.id.lbEmailError, getResources().getString(R.string.errorBadEmail));
            return false;
        }
        if (!verificarPassword()) {
            ((TextView) findViewById(R.id.lbPasswordInfo)).setTextColor(Color.RED);
            return false;
        }
        if(!getTexto(txt_passLogin).equals(getTexto(txt_passLogin2))){
            Toast.makeText(this,getResources().getText(R.string.passNoCoinciden),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public void limpiarErrores(View view) {
        switch (view.getId()) {
            case R.id.txt_nombreLogin:
                findViewById(R.id.lbNameError).setVisibility(View.GONE);
                break;
            case R.id.txt_apellidoLogin:
                findViewById(R.id.lbApellidoError).setVisibility(View.GONE);
                break;
            case R.id.txt_mailLogin:
                findViewById(R.id.lbEmailError).setVisibility(View.GONE);
                break;
            case R.id.txt_passLogin:
                ((TextView) findViewById(R.id.lbPasswordInfo)).setTextColor(getResources().getColor(R.color.grayInfo,getTheme()));
                break;
        }

    }

    public void resaltarError(int txt, String texto) {
        TextView txtError = findViewById(txt);
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(texto);
    }

    public boolean verificarMail() {
        Pattern patron = Pattern.compile("([a-z\\d]+(\\.?[a-z\\d])*)+@(([a-z]+)\\.([a-z]+))+");
        return patron.matcher(txt_mailLogin.getText().toString()).find();
    }

    public boolean verificarPassword() {
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(txt_passLogin.getText().toString()).find();
    }


    public void registrarUsuario(View view) {
        if(!verificarCampos()){
            return;
        }

        pdLoading = new PdLoading(this);

        HashMap<String,String> params = getParamsInfo(
                getTexto(txt_mailLogin).toLowerCase(),
                getTexto(txt_nombreLogin),
                getTexto(txt_apellidoLogin),
                getTexto(txt_passLogin));

        new ApiHandler(this,URL_REGISTRO,params).start();
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
            preferences.setUserEmail(getTexto(txt_mailLogin).toLowerCase(),json.getInt("id"));
            preferences.setUserStatus(true);

            Intent intent = new Intent(this, InsertarInfoUsuario.class);
            startActivity(intent);

            finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}


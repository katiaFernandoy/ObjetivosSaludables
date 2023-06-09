package com.example.objetivossaludables.pages.configuracion;

import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsModifyPassword;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_MODIFICAR_PWD;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ConfgSeguridad extends AppCompatActivity implements ApiInterface {

    private EditText txt_passWordActual;
    private EditText txt_passWordNueva;
    private EditText txt_passWordNuevaRepe;
    PdLoading pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confg_seguridad);

        txt_passWordActual = findViewById(R.id.txt_passWordActual);
        txt_passWordNueva = findViewById(R.id.txt_passWordNueva);
        txt_passWordNuevaRepe = findViewById(R.id.txt_passWordNuevaRepe);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());
    }

    public boolean verificarDatos() {

        if (txt_passWordActual.getText() == null || txt_passWordActual.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getText(R.string.campoObligatorio), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txt_passWordNueva.getText() == null || txt_passWordNueva.getText().toString().equals("")) {
            Toast.makeText(this, getResources().getText(R.string.campoObligatorio), Toast.LENGTH_SHORT).show();
            return false;
        }if (!verificarPassword()) {
            ((TextView) findViewById(R.id.lbError)).setTextColor(Color.RED);
            return false;
        }
        if (txt_passWordActual.getText().equals(txt_passWordNueva.getText())) {
            Toast.makeText(this, getResources().getText(R.string.passViejaYNuevaSonIguales), Toast.LENGTH_LONG).show();
            return false;
        }
        if (!txt_passWordNueva.getText().toString().equals(txt_passWordNuevaRepe.getText().toString())) {
            Toast.makeText(this, getResources().getText(R.string.passNoCoinciden), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public boolean verificarPassword() {
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(txt_passWordNueva.getText().toString()).find();
    }

    public void cambiarPassword(View view) {
        if (!verificarDatos()) {
            return;
        }

        // Construimos lo parámetros para el PHP
        HashMap<String,String> params =
                getParamsModifyPassword(
                        new UserPreferences(this).getUserEmail(),
                        getTexto(txt_passWordActual),
                        getTexto(txt_passWordNueva));

        pdLoading = new PdLoading(this); // Implementada nueva clase
        new ApiHandler(this,URL_MODIFICAR_PWD, params).start(); // Ejecuta el hilo de ApiHandler
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

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }finally {
            //Importante, en este método dentro del hilo secundario,
            // si queremos modificar  alguna vista, hay que hacerlo con runOnUiThread
            runOnUiThread(() -> {
                txt_passWordActual.setText("");
                txt_passWordNueva.setText("");
                txt_passWordNuevaRepe.setText("");
                ((TextView) findViewById(R.id.lbError)).setTextColor(Color.WHITE);
            });
        }
    }
}
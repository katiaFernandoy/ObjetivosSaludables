package com.example.objetivossaludables.pages.HomePages;

import static com.example.objetivossaludables.pages.HomePages.GrabarSuenio.GetDay;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsEntrenamiento;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsObjetivos;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_SET_ENTRENAMIENTO;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_SET_OBJETIVOS;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

public class EstablecerObjetivos extends AppCompatActivity implements ApiInterface {

    private UserPreferences preferences;
    private EditText objHorasSuenio, objHorasEntrenamiento, objNumPasos;
    private RadioButton rbMusculo, rbPerderPeso;
    private PdLoading pdLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecer_objetivos);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        objHorasSuenio = findViewById(R.id.objHorasSuenio);
        objHorasEntrenamiento = findViewById(R.id.objHorasEntrenamiento);
        objNumPasos = findViewById(R.id.objNumPasos);
        rbMusculo = findViewById(R.id.rbMusculo);
        rbPerderPeso = findViewById(R.id.rbPerderPeso);

        preferences = new UserPreferences(this);

    }

    public void grabarObjetivos(View view) {

        final double entrenamiento = Double.parseDouble(getTexto(objHorasEntrenamiento));

        if (entrenamiento < 0 || entrenamiento >= 24) {
            Toast.makeText(this,getResources().getText(R.string.pasosError),Toast.LENGTH_SHORT).show();
            return;
        }

        final Double suenio = Double.parseDouble(getTexto(objHorasSuenio));

        if (suenio < 0 || suenio >= 24) {
            Toast.makeText(this,getResources().getText(R.string.pasosError),Toast.LENGTH_SHORT).show();
            return;
        }

        final int pasos = Integer.parseInt(getTexto(objNumPasos));

        if (pasos < 0 || pasos > 90000) {
            Toast.makeText(this,getResources().getText(R.string.pasosError),Toast.LENGTH_SHORT).show();
            return;
        }

        final int id_usu = preferences.getUserId();

        HashMap<String,String> params = getParamsObjetivos(
                String.valueOf(id_usu),
                String.valueOf(objHorasSuenio.getText()),
                String.valueOf(objHorasEntrenamiento.getText()),
                String.valueOf(objNumPasos.getText())
                );

        pdLoading = new PdLoading(this);
        new ApiHandler(this,URL_SET_OBJETIVOS,params).start();
    }

    @Override
    public void returnResponse(JSONObject json) {
        pdLoading.dismiss(); // Se cierra el PdLoading

        try {
            if(json.getBoolean("error")){
                Toast.makeText(this,getResources().getText(R.string.errorAlObtenerInfo),Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if(!json.getString("apicall").equals("getter")){
                Toast.makeText(this,json.getString("message"),Toast.LENGTH_SHORT).show();
            }

            String objEntrenamiento = json.getString("entrenamiento");
            String objSuenio = json.getString("suenio");
            String objPasos = json.getString("pasos");
            boolean objPeso = rbPerderPeso.isChecked();

            preferences.setObjectives(objEntrenamiento,objSuenio, objPasos, objPeso);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
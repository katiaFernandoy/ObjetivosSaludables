package com.example.objetivossaludables.pages.HomePages;

import static com.example.objetivossaludables.pages.HomePages.GrabarSuenio.GetDay;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsEntrenamiento;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsId;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsPasos;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_GET_ENTRENAMIENTO;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_GET_PASOS;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_SET_ENTRENAMIENTO;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_SET_PASOS;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getPuntuacion;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;

public class GrabarEntrenamiento extends AppCompatActivity implements ApiInterface {

    private EditText entrenamientoDia, horasMediaEntrenamiento;
    private TextView tvResultadoEntrenamiento, tvResultAnalisEntrenamiento;
    private ImageView ivResultAnalisEntrenamiento;
    private PdLoading pdLoading;
    private UserPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_entrenamiento);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        entrenamientoDia = findViewById(R.id.pasosDia);
        tvResultadoEntrenamiento = findViewById(R.id.tvResultadoEntrenamiento);
        ivResultAnalisEntrenamiento = findViewById(R.id.ivResultAnalisEntrenamiento);
        tvResultAnalisEntrenamiento = findViewById(R.id.tvResultAnalisEntrenamiento);
        horasMediaEntrenamiento = findViewById(R.id.horasMediaEntrenamiento);

        preferences = new UserPreferences(this);
        getEntrenamiento();
    }

    private void getEntrenamiento() {
        pdLoading = new PdLoading(this);
        String id = String.valueOf(preferences.getUserId());
        new ApiHandler(this,URL_GET_ENTRENAMIENTO,getParamsId(id)).start();
    }

    public void grabarEntrenamiento(View view) {

        final int entrenamiento = Integer.parseInt(getTexto(entrenamientoDia));

        if (entrenamiento < 0 || entrenamiento >= 24) {
            Toast.makeText(this,getResources().getText(R.string.pasosError),Toast.LENGTH_SHORT).show();
            return;
        }

        getAnimoEntrenamiento(entrenamiento);
        int porcentajeEntrenamiento = getPuntuacion(Double.parseDouble(String.valueOf(entrenamiento)),Double.parseDouble(preferences.getObjetivoEntrenar()));
        tvResultadoEntrenamiento.setText(String.valueOf(porcentajeEntrenamiento));

        if(porcentajeEntrenamiento < 50){
            ivResultAnalisEntrenamiento.setImageDrawable(getResources().getDrawable(R.drawable.triste));
        }if(porcentajeEntrenamiento > 90){
            ivResultAnalisEntrenamiento.setImageDrawable(getResources().getDrawable(R.drawable.feliz));
        }

        final int id_usu = preferences.getUserId();
        final String day = GetDay();

        HashMap<String,String> params = getParamsEntrenamiento(
                String.valueOf(id_usu),
                String.valueOf(entrenamiento),
                day);

        pdLoading = new PdLoading(this);
        new ApiHandler(this,URL_SET_ENTRENAMIENTO,params).start();
    }

    private void getAnimoEntrenamiento(int entrenamiento) {
        if(entrenamiento < 1) {
            tvResultAnalisEntrenamiento.setText(R.string.humorMal);
        }else{
            tvResultAnalisEntrenamiento.setText(R.string.humorBien);
        }
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

            final String media = getMediaEntrenamiento(json.getJSONObject("entrenamiento"));
            if(media.equals("")){
                Toast.makeText(this,getResources().getText(R.string.noEntrenamiento),Toast.LENGTH_SHORT).show();
            }
            runOnUiThread(() -> horasMediaEntrenamiento.setText(!media.equals("") ? media : "0.00" ));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private String getMediaEntrenamiento(JSONObject entrenamiento) throws JSONException {
        double horasSuma = 0.0;
        int contador = 0;

        Iterator<String> horasIter = entrenamiento.keys();
        while (horasIter.hasNext()) {
            String key = horasIter.next();

            if (!entrenamiento.getString(key).equals("")) {
                Log.e("sumaMedia","Hola --> " + entrenamiento.getString(key));
                horasSuma += Double.parseDouble(entrenamiento.getString(key));
                contador++;
            }
        }

        if(contador == 0) return "";

        return new DecimalFormat("#.00").format(horasSuma/contador);
    }
}
package com.example.objetivossaludables.pages.HomePages;

import static com.example.objetivossaludables.pages.HomePages.GrabarSuenio.GetDay;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsId;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsPasos;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_GET_PASOS;
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

public class GrabarPasos extends AppCompatActivity implements ApiInterface {

    private EditText pasosDia, horasMediaSemana;
    private TextView tvResultadoPasos, tvResultAnalisPasos;
    private ImageView ivResultadoPasos;
    private PdLoading pdLoading;
    private UserPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_pasos);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        pasosDia = findViewById(R.id.pasosDia);
        tvResultadoPasos = findViewById(R.id.tvResultadoPasos);
        ivResultadoPasos = findViewById(R.id.ivResultadoPasos);
        tvResultAnalisPasos = findViewById(R.id.tvResultAnalisPasos);
        horasMediaSemana = findViewById(R.id.horasMediaSemana);

        preferences = new UserPreferences(this);
        getPasos();
    }

    private void getPasos() {
        pdLoading = new PdLoading(this);
        String id = String.valueOf(preferences.getUserId());
        new ApiHandler(this,URL_GET_PASOS,getParamsId(id)).start();
    }

    public void grabarPasos(View view) {

        final int pasos = Integer.parseInt(getTexto(pasosDia));

        if (pasos < 0 || pasos >= 90000) {
            Toast.makeText(this,getResources().getText(R.string.pasosError),Toast.LENGTH_SHORT).show();
            return;
        }

        getAnimoPasos(pasos);
        int porcentajePasos = getPuntuacion(Double.parseDouble(String.valueOf(pasos)),Double.parseDouble(preferences.getObjetivoPasos()));
        tvResultadoPasos.setText(String.valueOf(porcentajePasos));

        if(porcentajePasos < 50){
            ivResultadoPasos.setImageDrawable(getResources().getDrawable(R.drawable.triste));
        }if(porcentajePasos > 90){
            ivResultadoPasos.setImageDrawable(getResources().getDrawable(R.drawable.feliz));
        }

        final int id_usu = preferences.getUserId();
        final String day = GetDay();

        HashMap<String,String> params = getParamsPasos(
                String.valueOf(id_usu),
                String.valueOf(pasos),
                day);

        pdLoading = new PdLoading(this);
        new ApiHandler(this,URL_SET_PASOS,params).start();
    }



    private void getAnimoPasos(double pasos) {
        if(pasos < 5000) {
            tvResultAnalisPasos.setText(R.string.humorMal);
        }else{
            tvResultAnalisPasos.setText(R.string.humorBien);
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

            final String media = getMediaPasos(json.getJSONObject("pasos"));
            if(media.equals("")){
                Toast.makeText(this,getResources().getText(R.string.noPasos),Toast.LENGTH_SHORT).show();
            }
            runOnUiThread(() -> horasMediaSemana.setText(!media.equals("") ? media : "0.00" ));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMediaPasos(JSONObject pasos) throws JSONException {
        double horasSuma = 0.0;
        int contador = 0;

        Iterator<String> horasIter = pasos.keys();
        while (horasIter.hasNext()) {
            String key = horasIter.next();

            if (!pasos.getString(key).equals("")) {
                Log.e("sumaMedia","Hola --> " + pasos.getString(key));
                horasSuma += Double.parseDouble(pasos.getString(key));
                contador++;
            }
        }

        if(contador == 0) return "";

        return new DecimalFormat("#.00").format(horasSuma/contador);
    }
}
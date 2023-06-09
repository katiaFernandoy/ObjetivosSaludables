package com.example.objetivossaludables.pages.HomePages;

import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsId;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsSuenio;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_OBTENERSUENIO;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_SUENIO;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;


public class GrabarSuenio extends AppCompatActivity implements ApiInterface {
    private EditText horasSuenio, mediaHoras;
    private TextView tvResultadoSuenio, tvResultAnalisis;
    private ImageView ivResultadoSuenio;
    private PdLoading pdLoading;
    private UserPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_suenio);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        horasSuenio = findViewById(R.id.horasSuenio);
        tvResultadoSuenio = findViewById(R.id.tvResultadoSuenio);
        ivResultadoSuenio = findViewById(R.id.ivResultadoSuenio);
        tvResultAnalisis = findViewById(R.id.tvResultAnalisis);
        mediaHoras = findViewById(R.id.horasMediaSemana);

        preferences = new UserPreferences(this);
        getHorasSuenioInit();
    }

    public String GetDay(){
        Calendar c = Calendar.getInstance();
        int dia =  c.get(Calendar.DAY_OF_WEEK);
       String d ="";
        if(dia==Calendar.SUNDAY){
            d= "Domingo";
        }if(dia==Calendar.MONDAY){
            d= "Lunes";
        }
        if(dia==Calendar.TUESDAY){
            d= "Martes";
        }
        if(dia==Calendar.WEDNESDAY){
            d= "Miercoles";
        }if(dia==Calendar.THURSDAY){
            d= "Jueves";
        }
        if(dia==Calendar.FRIDAY){
            d= "Viernes";
        }
        if(dia==Calendar.SATURDAY){
            d= "Sabado";
        }
        return d;
    }

    public void getAnimo(Double suenio){
        if(suenio < 6) {
            tvResultAnalisis.setText(getResources().getText(R.string.humorMal));
        }else{
            tvResultAnalisis.setText(getResources().getText(R.string.humorBien));
        }
    }

    public void getHorasSuenioInit(){
        pdLoading = new PdLoading(this);
        String id = String.valueOf(preferences.getUserId());
        new ApiHandler(this,URL_OBTENERSUENIO,getParamsId(id)).start();
    }

    public void grabarSuenio(View view) {

        final double suenio = Double.parseDouble(getTexto(horasSuenio));

        if (suenio < 0 || suenio >= 24) {
            Toast.makeText(this,getResources().getText(R.string.suenioError),Toast.LENGTH_SHORT).show();
            return;
        }

        getAnimo(suenio);

        final int id_usu = preferences.getUserId();
        final String day = GetDay();

        HashMap<String,String> params = getParamsSuenio(
                String.valueOf(id_usu),
                String.valueOf(suenio),
                day);

        pdLoading = new PdLoading(this);
        new ApiHandler(this,URL_SUENIO,params).start();
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

            final String media = getMediaHoras(json.getJSONObject("horas"));
            if(media.equals("")){
                Toast.makeText(this,getResources().getText(R.string.noHorasSuenio),Toast.LENGTH_SHORT).show();
            }
            runOnUiThread(() -> mediaHoras.setText(!media.equals("") ? media : "0.00" ));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMediaHoras(JSONObject horas) throws JSONException {
        double horasSuma = 0.0;
        int contador = 0;

        Iterator<String> horasIter = horas.keys();
        while (horasIter.hasNext()) {
            String key = horasIter.next();

            if (!horas.getString(key).equals("")) {
                Log.e("sumaMedia","Hola --> " + horas.getString(key));
                horasSuma += Double.parseDouble(horas.getString(key));
                contador++;
            }
        }

        if(contador == 0) return "";

        return new DecimalFormat("#.00").format(horasSuma/contador);
    }
}
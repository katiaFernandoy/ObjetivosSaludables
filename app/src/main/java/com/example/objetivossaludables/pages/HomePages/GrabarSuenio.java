package com.example.objetivossaludables.pages.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiGetHorasSuenio;
import com.example.objetivossaludables.manager.api.InsertClass;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import java.util.Calendar;


public class GrabarSuenio extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private EditText horasSuenio;
    private TextView tvResultadoSuenio;
    private ImageView ivResultadoSuenio;
    private TextView tvResultAnalisis;
    protected  static EditText mediaHoras;
    protected static ProgressDialog pdLoading;
    protected static Context context;


    @SuppressLint("MissingInflatedId")
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
        pdLoading = new ProgressDialog(GrabarSuenio.this);

        context=GrabarSuenio.this;

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

    public void grabarSuenio(View view) {

        final Integer Id_usu = new UserPreferences(this).getUserId();
        final Double suenio = Double.parseDouble(horasSuenio.getText().toString());
        final String day = GetDay();
        pdLoading = new ProgressDialog(GrabarSuenio.this);

        if (suenio >= 0 && suenio < 24) {
            InsertClass insertclass = new InsertClass(suenio, day, Id_usu, GrabarSuenio.this, pdLoading);
            insertclass.execute();
            getAnimo(suenio);
            new ApiGetHorasSuenio();
        }

    }


}
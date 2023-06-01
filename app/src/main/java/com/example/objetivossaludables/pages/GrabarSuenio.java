package com.example.objetivossaludables.pages;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.MY_PREFERENCES;

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
import com.example.objetivossaludables.modelo.InsertClass;

import java.util.Calendar;


public class GrabarSuenio extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private EditText horasSuenio;
    private TextView tvResultadoSuenio;
    private ImageView ivResultadoSuenio;
    private TextView tvResultAnalisis;
    private EditText mediaHoras;
    ProgressDialog pdLoading;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_suenio);

        horasSuenio = findViewById(R.id.horasSuenio);
        tvResultadoSuenio = findViewById(R.id.tvResultadoSuenio);
        ivResultadoSuenio = findViewById(R.id.ivResultadoSuenio);
        tvResultAnalisis = findViewById(R.id.tvResultAnalisis);
        mediaHoras = findViewById(R.id.horasMediaSemana);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

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

    public void grabarSuenio(View view) {
        final String suenio = horasSuenio.getText().toString();
        final String day = GetDay();
        pdLoading = new ProgressDialog(GrabarSuenio.this);

        if (!suenio.isEmpty()) {
            InsertClass insertclass = new InsertClass(suenio, day, GrabarSuenio.this, pdLoading);
            insertclass.execute();
        }
    }

}
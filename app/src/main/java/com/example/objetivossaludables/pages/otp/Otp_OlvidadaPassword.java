package com.example.objetivossaludables.pages.otp;

import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsOTP;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INSERTAR_OTP;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_VERIFICAR_OTP;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.isInteger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.api.RequestHandler;
import com.example.objetivossaludables.manager.mailManager.MailJob;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class Otp_OlvidadaPassword extends AppCompatActivity implements ApiInterface {

    private boolean isVerificacionOTP = false;
    private EditText txt_otp;
    private UserPreferences preferences;
    private PdLoading pdLoading;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_olvidada_password);

        txt_otp = findViewById(R.id.txt_otp);
        preferences = new UserPreferences(this);

        findViewById(R.id.txt_volverEnviarOTP).setOnClickListener(v -> {
            pdLoading = new PdLoading(this);
            otp = String.valueOf((int) (Math.random() * (9999 - 1000 + 1) + 1000));
            new ApiHandler(this,URL_INSERTAR_OTP,getParamsOTP(preferences.getUserId(),otp)).start();
        });

        findViewById(R.id.bt_otpContinuar).setOnClickListener(v -> {
            if(!verificar()){
                return;
            }

            pdLoading = new PdLoading(this);
            isVerificacionOTP = true;

            new ApiHandler(this, URL_VERIFICAR_OTP, getParamsOTP(preferences.getUserId(),getTexto(txt_otp))).start();
        });
    }

    public boolean verificar(){
        if(txt_otp.getText() == null || getTexto(txt_otp).equals("")){
            Toast.makeText(this,getResources().getText(R.string.faltaOTP),Toast.LENGTH_SHORT).show();
            return false;
        }if(!isInteger(getTexto(txt_otp))){
            Toast.makeText(this,getResources().getText(R.string.otpSoloNumerico),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void returnResponse(JSONObject json) {
        try {
            if (json.getBoolean("error")) {
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
                pdLoading.dismiss();
                return;
            }

            Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();

            if(isVerificacionOTP){//Darle a continuar y verificarOTP
                isVerificacionOTP = false;
                Intent intent = new Intent(this, ModificarPassword_OTP.class);
                startActivity(intent);
                finish();
            }else{
                //Mando el mail, email cliente debe ser el email que puso en el txt
                // y tiene que existir para que envíe el mail
                new MailJob("", Integer.parseInt(otp));
            }
            pdLoading.dismiss();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
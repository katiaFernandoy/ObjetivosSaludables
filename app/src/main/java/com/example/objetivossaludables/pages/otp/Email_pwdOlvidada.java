package com.example.objetivossaludables.pages.otp;

import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsInfoPersonal;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsOTP;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INSERTAR_OTP;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_VERIFICAR_EMAIL;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.api.RequestHandler;
import com.example.objetivossaludables.manager.mailManager.MailJob;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Email_pwdOlvidada extends AppCompatActivity implements ApiInterface {

    private EditText txt_mailPassworOlvidada;
    private PdLoading pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_pwd_olvidada);

        txt_mailPassworOlvidada = findViewById(R.id.txt_mailPassworOlvidada);
        Button bt_restablecerPassword = findViewById(R.id.bt_restablecerPassword);

        bt_restablecerPassword.setOnClickListener(v -> {
            limpiarError();

            if(!verificar()){
                return;
            }

            String email = getTexto(txt_mailPassworOlvidada);
            pdLoading = new PdLoading(Email_pwdOlvidada.this);

            new ApiHandler(Email_pwdOlvidada.this, URL_VERIFICAR_EMAIL, getParamsInfoPersonal(email)).start();
        });
    }

    private boolean verificar(){
        if (txt_mailPassworOlvidada.getText() == null || getTexto(txt_mailPassworOlvidada).equals("")) {
            resaltarError(getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.email));
            return false;
        } else if (!verificarMail()) {
            resaltarError(getResources().getString(R.string.errorBadEmail));
            return false;
        }
        return true;
    }

    private void resaltarError(String texto) {
        TextView txtError = findViewById(R.id.lbEmailError);
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(texto);
    }

    private boolean verificarMail() {
        Pattern patron = Pattern.compile("([a-z\\d]+(\\.?[a-z\\d])*)+@(([a-z]+)\\.([a-z]+))+");
        return patron.matcher(getTexto(txt_mailPassworOlvidada)).find();
    }

    private void limpiarError(){
        TextView txtError = findViewById(R.id.lbEmailError);
        txtError.setVisibility(View.GONE);
        txtError.setText("");
    }

    @Override
    public void returnResponse(JSONObject json) {
        pdLoading.dismiss();

        try {
            if (json.getBoolean("error")) {
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();

            new UserPreferences(this).setUserEmail(getTexto(txt_mailPassworOlvidada), json.getInt("id"));

            //Cosas que tienen que ver con el mail e insertar el OTP en la BBDD
            final int otp = generadorOTP();
            //Mando el mail, email cliente debe ser el email que pone en el txt
            // y tiene que existir para que envíe el mail
            new MailJob("", otp);
            //Inserto en la base de datos encriptando
            new RequestHandler().sendPostRequest(
                    URL_INSERTAR_OTP,getParamsOTP(json.getInt("id"),String.valueOf(otp)));

            Intent intent = new Intent(this, Otp_OlvidadaPassword.class);
            startActivity(intent);
            finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private int generadorOTP(){
        //Los criterios para el OTP son 4 dígitos aleatorios
        return (int) (Math.random() * (9999 - 1000 + 1) + 1000);
    }
}
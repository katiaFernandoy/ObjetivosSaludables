package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsInfoPersonal;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_EMAIL_PWD_OLVIDADA;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class Email_pwdOlvidada extends AppCompatActivity implements ApiInterface {

    private EditText txt_mailPassworOlvidada;
    private Button bt_restablecerPassword;
    private PdLoading pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_pwd_olvidada);

        txt_mailPassworOlvidada = findViewById(R.id.txt_mailPassworOlvidada);
        bt_restablecerPassword = findViewById(R.id.bt_restablecerPassword);

        bt_restablecerPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTexto(txt_mailPassworOlvidada);

                pdLoading = new PdLoading(Email_pwdOlvidada.this);

                new ApiHandler(Email_pwdOlvidada.this, URL_EMAIL_PWD_OLVIDADA, getParamsInfoPersonal(email)).start();


            }
        });
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
            //enviar email
            // guardar el id como el mail

            new UserPreferences(this).setUserEmail(getTexto(txt_mailPassworOlvidada), json.getInt("id"));
            Intent intent = new Intent(this, Otp_OlvidadaPassword.class);
            startActivity(intent);
            finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }
}
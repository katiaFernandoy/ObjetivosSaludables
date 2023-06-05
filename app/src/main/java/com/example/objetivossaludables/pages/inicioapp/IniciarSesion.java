package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.manager.media.MediaManager.playSound;
import static com.example.objetivossaludables.manager.media.MediaManager.vibrate;
import static com.example.objetivossaludables.valoresestaticos.ParametrosHashMap.getParamsLogin;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_LOGIN;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.ApiHandler;
import com.example.objetivossaludables.manager.api.ApiInterface;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.pages.HomePages.Menu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class IniciarSesion extends AppCompatActivity implements ApiInterface {

    private EditText txt_mail, txt_passLogin;
    private PdLoading pdLoading;
    private UserPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        txt_mail = findViewById(R.id.txt_mailLogin);
        txt_passLogin = findViewById(R.id.txt_passLogin);
        TextView txt_passWordOlvidada = findViewById(R.id.txt_passWordOlvidada);
        preferences = new UserPreferences(this);

        if (preferences.getUserStatus()) {
            finish();
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        }

        txt_passWordOlvidada.setOnClickListener(v -> {
            Intent intent = new Intent(this, Email_pwdOlvidada.class);
            startActivity(intent);
        });
    }

    public boolean verificarCampos() {
        if (txt_mail.getText() == null || txt_mail.getText().toString().equals("")) {
            resaltarError(R.id.emailError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.email));
            return false;
        } else if (!verificarMail()) {
            resaltarError(R.id.emailError, getResources().getString(R.string.errorBadEmail));
            return false;
        }

        if (!verificarPassword()) {
            ((TextView) findViewById(R.id.passwordInfo)).setTextColor(Color.RED);
            return false;
        }

        return true;
    }

    public void resaltarError(int txt, String texto) {
        TextView txtError = findViewById(txt);
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(texto);
    }

    @SuppressLint("NonConstantResourceId")
    public void limpiarErrores(View view) {
        switch (view.getId()) {
            case R.id.txt_mailLogin:
                findViewById(R.id.emailError).setVisibility(View.GONE);
                break;
            case R.id.txt_passLogin:
                ((TextView) findViewById(R.id.passwordInfo)).setTextColor(getResources().getColor(R.color.grayInfo));
                break;
        }
    }

    public boolean verificarMail() {
        Pattern patron = Pattern.compile("([a-z\\d]+(\\.?[a-z\\d])*)+@(([a-z]+)\\.([a-z]+))+");
        return patron.matcher(txt_mail.getText().toString()).find();
    }

    public boolean verificarPassword() {
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(txt_passLogin.getText().toString()).find();
    }

    public void login(View view) {
        playSound(this);
        vibrate(this);

        if(!verificarCampos()){
            return;
        }

        pdLoading = new PdLoading(this);

        String email = getTexto(txt_mail).toLowerCase();
        new ApiHandler(this,URL_LOGIN,getParamsLogin(email,getTexto(txt_passLogin))).start();
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
            preferences.setUserEmail(getTexto(txt_mail).toLowerCase(),json.getInt("id"));
            preferences.setUserStatus(true);

            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);

            finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
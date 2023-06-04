package com.example.objetivossaludables.pages.inicioapp;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.STATUS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.api.Login;
import com.example.objetivossaludables.pages.HomePages.Menu;

import java.util.Locale;
import java.util.regex.Pattern;

public class IniciarSesion extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private EditText txt_mail;
    private EditText txt_passLogin;
    private Button bt_iniciarLogin;
    ProgressDialog pdLoading;
    public static String usuLog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        txt_mail = findViewById(R.id.txt_mailLogin);
        txt_passLogin = findViewById(R.id.txt_passLogin);
        bt_iniciarLogin = findViewById(R.id.bt_iniciarSesion);

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        boolean status = sharedPreferences.getBoolean(STATUS, false);

        if (status) {
            finish();
            Intent intent = new Intent(IniciarSesion.this, Menu.class);
            startActivity(intent);
        }
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
        Pattern patron = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        return patron.matcher(txt_mail.getText().toString()).find();
    }

    public boolean verificarPassword() {
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(txt_passLogin.getText().toString()).find();
    }

    public void login(View view) {
        final String email = txt_mail.getText().toString().toLowerCase(Locale.ROOT).trim();
        final String password = txt_passLogin.getText().toString();
        pdLoading = new ProgressDialog(IniciarSesion.this);

        if (verificarCampos()) {
            Login login = new Login(email, password, IniciarSesion.this, pdLoading);
            login.execute();
        }
    }
}
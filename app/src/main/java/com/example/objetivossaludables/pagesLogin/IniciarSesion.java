package com.example.objetivossaludables.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.objetivossaludables.R;

import java.util.regex.Pattern;

public class IniciarSesion extends AppCompatActivity {

    private EditText txt_mail;
    private EditText txt_passLogin;
    private Button bt_iniciarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        txt_mail = findViewById(R.id.txt_mailLogin);
        txt_passLogin = findViewById(R.id.txt_passLogin);
        bt_iniciarLogin = findViewById(R.id.bt_iniciarSesion);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> {
            finish();
        });

        bt_iniciarLogin.setOnClickListener(v -> {

            if(!verificarCampos()){

                return;
            }
        });

    }

    public boolean verificarCampos(){

        if(txt_mail.getText() == null || txt_mail.getText().toString().equals("")){
            resaltarError(R.id.emailError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.email));
            return false;
        }
        else if(!verificarMail()){
            resaltarError(R.id.emailError, getResources().getString(R.string.errorBadEmail));
            return false;
        }

        if(!verificarPassword()){
            ((TextView) findViewById(R.id.passwordInfo)).setTextColor(Color.RED);
            return false;
        }

        return true;
    }

    public void resaltarError(int txt, String texto){
        TextView txtError = findViewById(txt);
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(texto);
    }


    @SuppressLint("NonConstantResourceId")
    public void limpiarErrores(View view){
        switch (view.getId()){
            case R.id.txt_mailLogin:
                findViewById(R.id.emailError).setVisibility(View.GONE);
                break;
            case R.id.txt_passLogin:
                ((TextView) findViewById(R.id.passwordInfo)).setTextColor(getResources().getColor(R.color.grayInfo));
                break;
        }

    }
    public boolean verificarMail(){
        Pattern patron = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        return patron.matcher(txt_mail.getText().toString()).find();
    }

    public boolean verificarPassword(){
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(txt_passLogin.getText().toString()).find();
    }
}
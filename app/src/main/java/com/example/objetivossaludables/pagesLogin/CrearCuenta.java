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


public class CrearCuenta extends AppCompatActivity {

    private EditText txt_nombreLogin;
    private EditText txt_apellidoLogin;
    private EditText txt_mailLogin;
    private EditText txt_passLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        txt_nombreLogin = findViewById(R.id.txt_nombreLogin);
        txt_apellidoLogin = findViewById(R.id.txt_apellidoLogin);
        txt_mailLogin = findViewById(R.id.txt_mailLogin);
        txt_passLogin = findViewById(R.id.txt_passLogin);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> {
            finish();
        });
    }

    public void clickCrearCuenta(View view){
        if(!verificarCampos()){
            return;
        }

    }

    public boolean verificarCampos(){
        if(txt_nombreLogin.getText() == null || txt_nombreLogin.getText().toString().equals("")){
            resaltarError(R.id.lbNameError,getResources().getString(R.string.errorFaltaCampo) +" "+ getResources().getString(R.string.name));
            return false;
        }
        else if(txt_nombreLogin.getText().toString().length() > 28){
            resaltarError(R.id.lbNameError, getResources().getString(R.string.errorMaxChars));
            return false;
        }
        if(txt_apellidoLogin.getText() == null || txt_apellidoLogin.getText().toString().equals("")){
            resaltarError(R.id.lbApellidoError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.Appellido));
            return false;
        }
        else if(txt_apellidoLogin.getText().toString().length() > 28){
            resaltarError(R.id.lbApellidoError, getResources().getString(R.string.errorMaxChars));
            return false;
        }
        if(txt_mailLogin.getText() == null || txt_mailLogin.getText().toString().equals("")){
            resaltarError(R.id.lbEmailError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.email));
            return false;
        }
       else if(!verificarMail()){
            resaltarError(R.id.lbEmailError, getResources().getString(R.string.errorBadEmail));
            return false;
        }
       if(!verificarPassword()){
           ((TextView) findViewById(R.id.lbPasswordInfo)).setTextColor(Color.RED);
           return false;
       }

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public void limpiarErrores(View view){
        switch (view.getId()){
            case R.id.txt_nombreLogin:
                findViewById(R.id.lbNameError).setVisibility(View.GONE);
                break;
            case R.id.txt_apellidoLogin:
                findViewById(R.id.lbApellidoError).setVisibility(View.GONE);
                break;
            case R.id.txt_mailLogin:
                findViewById(R.id.lbEmailError).setVisibility(View.GONE);
                break;
            case R.id.txt_passLogin:
                ((TextView) findViewById(R.id.lbPasswordInfo)).setTextColor(getResources().getColor(R.color.grayInfo));
                break;
        }

    }

    public void resaltarError(int txt, String texto){
        TextView txtError = findViewById(txt);
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(texto);
    }

    public boolean verificarMail(){
        Pattern patron = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        return patron.matcher(txt_mailLogin.getText().toString()).find();
    }

    public boolean verificarPassword(){
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(txt_passLogin.getText().toString()).find();
    }

}
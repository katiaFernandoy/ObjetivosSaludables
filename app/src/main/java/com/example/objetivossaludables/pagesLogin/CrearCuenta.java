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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CrearCuenta extends AppCompatActivity {

    private EditText ed_nombre;
    private EditText ed_apellido;
    private EditText ed_mail;
    private EditText ed_passWord;
    private Button btRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        ed_nombre = findViewById(R.id.ed_Nombre);
        ed_apellido = findViewById(R.id.ed_Appelido);
        ed_mail = findViewById(R.id.ed_email);
        ed_passWord = findViewById(R.id.ed_Password);
        btRegistrar = findViewById(R.id.btRegistrar);


    }

    public void clickCrearCuenta(View view){

        if(!verificarCampos()){

            return;
        }

    }

    public boolean verificarCampos(){
        if(ed_nombre.getText() == null || ed_nombre.getText().toString().equals("")){
            resaltarError(R.id.nameError,getResources().getString(R.string.errorFaltaCampo) +" "+ getResources().getString(R.string.name));
            return false;
        }
        else if(ed_nombre.getText().toString().length() > 28){
            resaltarError(R.id.nameError, getResources().getString(R.string.errorMaxChars));
            return false;
        }
        if(ed_apellido.getText() == null || ed_apellido.getText().toString().equals("")){
            resaltarError(R.id.apellidoError, getResources().getString(R.string.errorFaltaCampo) + " " + getResources().getString(R.string.Appellido));
            return false;
        }
        else if(ed_apellido.getText().toString().length() > 28){
            resaltarError(R.id.apellidoError, getResources().getString(R.string.errorMaxChars));
            return false;
        }
        if(ed_mail.getText() == null || ed_mail.getText().toString().equals("")){
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

    @SuppressLint("NonConstantResourceId")
    public void limpiarErrores(View view){
        switch (view.getId()){
            case R.id.ed_Nombre:
                findViewById(R.id.nameError).setVisibility(View.GONE);
                break;
            case R.id.ed_Appelido:
                findViewById(R.id.apellidoError).setVisibility(View.GONE);
                break;
            case R.id.ed_email:
                findViewById(R.id.emailError).setVisibility(View.GONE);
                break;
            case R.id.ed_Password:
                ((TextView) findViewById(R.id.passwordInfo)).setTextColor(getResources().getColor(R.color.grayInfo));
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
        return patron.matcher(ed_mail.getText().toString()).find();
    }

    public boolean verificarPassword(){
        Pattern patron = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");
        return patron.matcher(ed_passWord.getText().toString()).find();
    }


}
package com.example.objetivossaludables.pages.configuracion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.objetivossaludables.R;

public class ConfgSeguridad extends AppCompatActivity {

    private EditText txt_passWordActual;
    private EditText txt_passWordNueva;
    private EditText txt_passWordNuevaRepe;

    private Button bt_modificarPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confg_seguridad);

        txt_passWordActual = findViewById(R.id.txt_passWordActual);
        txt_passWordNueva = findViewById(R.id.txt_passWordNueva);
        txt_passWordNuevaRepe = findViewById(R.id.txt_passWordNuevaRepe);


       

    }


    public boolean verificarDatos(){

        if(txt_passWordActual.getText().toString().equals("") || txt_passWordActual.getText() == null){
            Toast.makeText(this,getResources().getText(R.string.campoObligatorio),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_passWordActual.getText().toString().equals("") || txt_passWordActual.getText() == null){
            Toast.makeText(this,getResources().getText(R.string.errorMaxChars),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_passWordNueva.getText().toString().equals("") || txt_passWordNueva.getText() == null){
            Toast.makeText(this,getResources().getText(R.string.campoObligatorio),Toast.LENGTH_SHORT).show();
            return false;
        }if(txt_passWordNueva.getText().toString().equals("") || txt_passWordNueva.getText() == null){
            Toast.makeText(this,getResources().getText(R.string.errorMaxChars),Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
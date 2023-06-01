package com.example.objetivossaludables.pages.configuracion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


        bt_modificarPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    public boolean verificarPassWord(){

        if(txt_passWordActual.getText().toString().equals("") || txt_passWordActual.getText() == null){

        }

        return true;
    }
}
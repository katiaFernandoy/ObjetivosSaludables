package com.example.objetivossaludables.pages.configuracion;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.objetivossaludables.R;
import com.google.android.material.textfield.TextInputEditText;

public class ConfgEnviar_Comentarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviarcomentarios);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());

        Button bt_enviarComentario = findViewById(R.id.bt_enviarComentario);
        TextInputEditText txt_escribirComentario = findViewById(R.id.txt_escribirComentario);


        bt_enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = txt_escribirComentario.getText().toString();

                if(!comentario.isEmpty()){
                    sendEmail(comentario);

                }
            }
        });

    }

    private void sendEmail(String comentario) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");
        intent.putExtra(Intent.EXTRA_TEXT, comentario);
        startActivity(intent);
    }


}
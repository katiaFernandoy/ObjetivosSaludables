package com.example.objetivossaludables.pages.configuracion;


import static com.example.objetivossaludables.manager.media.ColorManager.setColorState;
import static com.example.objetivossaludables.valoresestaticos.Verificaciones.getTexto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.mailManager.MailJob;
import com.example.objetivossaludables.manager.progressdialog.PdLoading;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Collections;

public class ConfgEnviar_Comentarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviarcomentarios);

        findViewById(R.id.backNavigationButton).setOnClickListener(v -> onBackPressed());
        setColorState(this, Collections.singletonList(findViewById(R.id.bt_enviarComentario)));
        UserPreferences preferences = new UserPreferences(this);

        Button bt_enviarComentario = findViewById(R.id.bt_enviarComentario);
        TextInputEditText txt_escribirComentario = findViewById(R.id.txt_escribirComentario);

        bt_enviarComentario.setOnClickListener(v -> {
            String comentario = getTexto(txt_escribirComentario);

            if(comentario.isEmpty()){
                Toast.makeText(this, "Por favor rellena el contenido", Toast.LENGTH_SHORT).show();
                return;
            }

            PdLoading pdLoading = new PdLoading(this);

            new Thread(() -> {
                Looper.prepare();
                new MailJob(preferences.getUserEmail(),comentario);
                pdLoading.dismiss();
                Toast.makeText(this,"Mensaje enviado correctamente",Toast.LENGTH_SHORT).show();
            }).start();
        });

    }
}
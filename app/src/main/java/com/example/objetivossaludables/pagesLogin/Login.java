package com.example.objetivossaludables.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.objetivossaludables.R;

public class Login extends AppCompatActivity {

    private Button bt_login;
    private TextView lb_crearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login = findViewById(R.id.bt_login);
        lb_crearCuenta = findViewById(R.id.lb_crearCuenta);

        lb_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,Registro.class);
                startActivity(intent);
            }
        });
    }
}
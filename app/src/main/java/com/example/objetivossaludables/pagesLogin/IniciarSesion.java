package com.example.objetivossaludables.pagesLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objetivossaludables.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class IniciarSesion extends AppCompatActivity {

    private EditText txt_mail;
    private EditText txt_passLogin;
    private Button bt_iniciarLogin;
    SharedPreferences sharedPreferences;

    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String EMAIL = "email";
    public static final String STATUS = "status";
    public static final String USERNAME = "username";
    public static final String ID = "id";
    public static String sId_usuario;
    public static String username;

    private boolean status;
    public static final String URL_LOGIN = "https://objetivossaludablesks.000webhostapp.com/login.php";

/*    @Override
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
            Intent intent = new Intent(IniciarSesion.this, Menu.class);
            startActivity(intent);}


        });*/

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_iniciar_sesion);

            txt_mail = findViewById(R.id.txt_mailLogin);
            txt_passLogin = findViewById(R.id.txt_passLogin);
            bt_iniciarLogin = findViewById(R.id.bt_iniciarSesion);

            sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

            status = sharedPreferences.getBoolean(STATUS, false);

            if (status & verificarCampos()){
                finish();
                Intent intent = new Intent(IniciarSesion.this, Menu.class);
                startActivity(intent);
            }
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

    public void login(View view){
        final String email = txt_mail.getText().toString();
        final String password = txt_passLogin.getText().toString();

        if(email.isEmpty()|| password.isEmpty()){
            Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show();
        }

        else {
            class Login extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(IniciarSesion.this);

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    //this method will be running on UI thread
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);

                    //returning the response
                    return requestHandler.sendPostRequest(URL_LOGIN, params);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    pdLoading.dismiss();

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(EMAIL, email);
                            editor.putBoolean(STATUS, true);
                            editor.apply();

                            finish();
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(IniciarSesion.this, Menu.class);
                            startActivity(intent);
                        }
                            else
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(IniciarSesion.this, "Exception: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }

            Login login = new Login();
            login.execute();
        }
    }
}
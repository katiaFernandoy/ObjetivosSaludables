package com.example.objetivossaludables.modelo;

import static com.example.objetivossaludables.pagesLogin.IniciarSesion.MY_PREFERENCES;
import static com.example.objetivossaludables.pagesLogin.IniciarSesion.STATUS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.objetivossaludables.pagesLogin.IniciarSesion;
import com.example.objetivossaludables.pagesLogin.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registro extends AsyncTask<Void, Void, String> {

    private String email;
    private String password;
    private String password2;
    private String nombre;
    private String apellido;
    ProgressDialog pdLoading;

    public static final String URL_LOGIN = "https://objetivossaludablesks.000webhostapp.com/registro.php";
    SharedPreferences sharedPreferences;
    Context context;

    public Registro(String email, String password, String password2, String nombre, String apellido, Context context, ProgressDialog pdLoading) {
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.nombre = nombre;
        this.apellido = apellido;
        this.context = context;
        this.pdLoading = pdLoading;
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }



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
        params.put("password2", password2);
        params.put("nombre", nombre);
        params.put("apellido", apellido);

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
                editor.putString(email, email);
                editor.putString(nombre, nombre);
                editor.putString(apellido, apellido);
                editor.putBoolean(STATUS, true);
                editor.apply();
                ((Activity) context).finish();
                Toast.makeText(context.getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, IniciarSesion.class);
                context.startActivity(intent);
            } else
                Toast.makeText(context.getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
        }
    }
}

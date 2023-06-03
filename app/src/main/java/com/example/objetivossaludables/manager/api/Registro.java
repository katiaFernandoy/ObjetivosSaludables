package com.example.objetivossaludables.manager.api;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.APELLIDO;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.NOMBRE;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.PASSWORD;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_REGISTRO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.pages.inicioapp.IniciarSesion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Registro extends AsyncTask<Void, Void, String> {

    private final String email;
    private final String password;
    private final String nombre;
    private final String apellido;
    private final Context context;
    ProgressDialog pdLoading;

    public Registro(String email, String password, String nombre, String apellido, Context context, ProgressDialog pdLoading) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.context = context;
        this.pdLoading = pdLoading;
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
        params.put(EMAIL, email);
        params.put(PASSWORD, password);
        params.put(NOMBRE, nombre);
        params.put(APELLIDO, apellido);

        //returning the response
        return requestHandler.sendPostRequest(URL_REGISTRO, params);
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
                Toast.makeText(context.getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
            }

            int sId_usuario = Integer.parseInt(obj.getString("id"));
            new UserPreferences(context).setUserEmail(email,sId_usuario);

            ((Activity) context).finish();

            Toast.makeText(context.getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, IniciarSesion.class);
            context.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
        }
    }
}

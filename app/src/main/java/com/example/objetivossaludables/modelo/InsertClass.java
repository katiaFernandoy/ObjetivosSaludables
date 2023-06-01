package com.example.objetivossaludables.modelo;

import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.STATUS;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_REGISTRO;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_SUENIO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.objetivossaludables.pagesLogin.GrabarSuenio;
import com.example.objetivossaludables.pagesLogin.IniciarSesion;
import com.example.objetivossaludables.pagesLogin.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class InsertClass extends AsyncTask<Void, Void, String> {

    private final String suenio;
    private final String day;
    public static final String ID = "id";

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    ProgressDialog pdLoading;
    SharedPreferences sharedPreferences;

    public InsertClass(String suenio, String day, Context context, ProgressDialog pdLoading) {
        this.suenio = suenio;
        this.day = day;
        this.context=context;
        this.pdLoading = pdLoading;
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        //creating request parameters
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", EMAIL);
        params.put("horasSuenio", suenio);
        params.put("diaSemana", day);


        //returning the response
        return requestHandler.sendPostRequest(URL_SUENIO, params);
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
                editor.putString(ID, EMAIL);
                editor.putString(suenio, suenio);
                editor.putString(day, day);
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

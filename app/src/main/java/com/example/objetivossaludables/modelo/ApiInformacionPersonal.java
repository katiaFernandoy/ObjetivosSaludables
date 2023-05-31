package com.example.objetivossaludables.modelo;

import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.STATUS;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INFORMACION_PERSONAL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.pagesLogin.RequestHandler;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class ApiInformacionPersonal extends AsyncTask <Void, Void, String> {


    private Context context;
    private ProgressDialog pdLoading;

    private List<View> vistas;

    private SharedPreferences sharedPreferences;

    public ApiInformacionPersonal(List<View> vistas, Context context, ProgressDialog pdLoading) {
        this.vistas = vistas;
        this.context = context;
        this.pdLoading = pdLoading;
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES,Context.MODE_PRIVATE);

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
        HashMap<String,String> params = new HashMap<>();
        params.put("userID","23");
        return new RequestHandler().sendPostRequest(URL_INFORMACION_PERSONAL,params);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pdLoading.dismiss();

        try {
            //converting response to json object
            JSONObject json= new JSONObject(s);
            //if error in response
            if (json.getBoolean("error")) {
                Toast.makeText(context.getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                return;
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            InformacionPersonal infoPersonal = new InformacionPersonal(
                    "katia",
                    json.getDouble("peso"),
                    dateFormat.parse(json.getString("f_nacimiento")),
                    json.getString("genero"),
                    json.getInt("altura")
            );


            ((TextInputEditText)vistas.get(0)).setText(infoPersonal.getNombre());
            ((TextInputEditText)vistas.get(1)).setText(String.valueOf(infoPersonal.getPeso()));
            ((TextInputEditText)vistas.get(2)).setText(dateFormat.format(infoPersonal.getFechaNacimiento()));
            // genero 3
            ((TextInputEditText)vistas.get(4)).setText(String.valueOf(infoPersonal.getAltura()));



        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
        }
    }
}

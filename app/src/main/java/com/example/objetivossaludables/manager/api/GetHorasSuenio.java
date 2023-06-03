package com.example.objetivossaludables.manager.api;

import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INFORMACION_PERSONAL;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_OBTENERSUENIO;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.ID_USU;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.dateFormat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.modelo.InformacionPersonal;
import com.example.objetivossaludables.pages.GrabarSuenio;
import com.example.objetivossaludables.pages.configuracion.ConfgPersonal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;

public class GetHorasSuenio extends GrabarSuenio {

    final Integer Id_usu = new UserPreferences(this).getUserId();
    protected static ProgressDialog pdLoading;
    public static double media;



    class AssyncTaskAPI extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(Id_usu));
                return new RequestHandler().sendPostRequest(URL_OBTENERSUENIO, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pdLoading.dismiss();

                try {
                    //converting response to json object
                    Double Horas = 0.0;
                    int contador = 0;
                    JSONObject json = new JSONObject(s);
                    String key = json.getString("key"); //obtenemos el valor de la clave 'key'
                    JSONArray horas = json.getJSONArray("array"); //obtenemos el valor del array 'array'


                    for (int i = 0; i < horas.length(); i++) {
                        if (horas.getString(i) != null){
                        Horas += Double.parseDouble(horas.getString(i));
                        contador++;}
                    }

                    media = Horas/contador;

                    //if error in response
                    if (json.getBoolean("error")) {
                        Log.e("API OBTENER SUEÑO", json.getString("message"));
                        Toast.makeText(context, json.getString("Error al obtener la información, pruebe más tarde"), Toast.LENGTH_LONG).show();
                        ((Activity) context).finish();
                        return;
                    }





                } catch (JSONException e) {
                    Log.e("Error al parseo", e.getMessage());
                    Log.e("Response", s);
                    Toast.makeText(context, "Error al obtener la información, pruebe más tarde", Toast.LENGTH_LONG).show();
                    ((Activity) context).finish();
                }
            }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread

                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
            }
        }
    }

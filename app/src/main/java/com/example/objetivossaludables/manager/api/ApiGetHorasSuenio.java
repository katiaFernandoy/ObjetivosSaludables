package com.example.objetivossaludables.manager.api;

import static com.example.objetivossaludables.valoresestaticos.URLs.URL_OBTENERSUENIO;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.objetivossaludables.manager.sharedpreferences.UserPreferences;
import com.example.objetivossaludables.pages.HomePages.GrabarSuenio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ApiGetHorasSuenio extends GrabarSuenio {

    public ApiGetHorasSuenio() {
        new AssyncTaskAPI().execute();
    }

    final Integer Id_usu = new UserPreferences(context).getUserId();
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

                    JSONObject horas= json.getJSONObject("horas");
                    Iterator x = horas.keys();

                    while (x.hasNext()){
                        String key = (String) x.next();

                            if (horas.getString(key) != null){
                                Horas += Double.parseDouble(horas.getString(key));
                                contador++;}

                    }



                    media = Horas/contador;
                    mediaHoras.setText("" +String.valueOf(media).substring(0,4));

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

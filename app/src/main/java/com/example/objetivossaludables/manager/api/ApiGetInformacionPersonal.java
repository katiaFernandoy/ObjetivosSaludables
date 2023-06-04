package com.example.objetivossaludables.manager.api;

import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INFORMACION_PERSONAL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.DATE_FORMAT;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.modelo.InformacionPersonal;
import com.example.objetivossaludables.pages.configuracion.ConfgPersonal;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;

public class ApiGetInformacionPersonal extends ConfgPersonal {

    public ApiGetInformacionPersonal() {
        new AssyncTaskAPI().execute();
    }

    class AssyncTaskAPI extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            HashMap<String, String> params = new HashMap<>();
            params.put(EMAIL, preferences.getUserEmail());
            return new RequestHandler().sendPostRequest(URL_INFORMACION_PERSONAL, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();

            try {
                //converting response to json object
                JSONObject json = new JSONObject(s);
                //if error in response
                if (json.getBoolean("error")) {
                    Log.e("API INFORMACIÓN PERSONAL", json.getString("message"));
                    Toast.makeText(context, json.getString("Error al obtener la información, pruebe más tarde"), Toast.LENGTH_LONG).show();
                    ((Activity) context).finish();
                    return;
                }

                ConfgPersonal.setInfoPersonal(new InformacionPersonal(
                        preferences.getUserEmail(),
                        json.getString("nombre"),
                        json.getDouble("peso"),
                        json.getInt("altura"),
                        json.getString("genero"),
                        DATE_FORMAT.parse(json.getString("fechaNacimiento"))
                ));

                txt_nombreMod.setText(ConfgPersonal.getInfoPersonal().getNombre());
                txt_pesoMod.setText(String.valueOf(ConfgPersonal.getInfoPersonal().getPeso()));
                txt_fechaMod.setText(DATE_FORMAT.format(ConfgPersonal.getInfoPersonal().getFechaNacimiento()));
                spn_generoModif.setSelection(getGenero(getInfoPersonal().getGenero()));
                txt_alturaMod.setText(String.valueOf(ConfgPersonal.getInfoPersonal().getAltura()));

            } catch (JSONException | ParseException e) {
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

        private int getGenero(String genero) {
            String[] generos = context.getResources().getStringArray(R.array.spinnerGenero);

            for (int i = 0; i < generos.length; i++) {
                if (generos[i].equals(genero)) {
                    return i;
                }
            }

            return 0;
        }
    }
}

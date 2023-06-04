package com.example.objetivossaludables.manager.api;

import static com.example.objetivossaludables.valoresestaticos.URLs.URL_INSERT_INFO_PERSONAL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;

import com.example.objetivossaludables.pages.inicioapp.IniciarSesion;
import com.example.objetivossaludables.pages.inicioapp.InsertarInfoUsuario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ApiInsertarInformacionPersonal extends InsertarInfoUsuario {


    public ApiInsertarInformacionPersonal() {
        new AssyncTaskAPI().execute();
    }

    class AssyncTaskAPI extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            HashMap<String,String> parametros = new HashMap<>();
            parametros.put(EMAIL, getNewInfoPersonal().getEmail());
            parametros.put("peso",String.valueOf(getNewInfoPersonal().getPeso()));

            return new RequestHandler().sendPostRequest(URL_INSERT_INFO_PERSONAL,parametros);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("error")) {
                    Toast.makeText(context, "Error al insertar la información", Toast.LENGTH_SHORT).show();
                    Log.e("error",s);
                    Log.e("error",jsonObject.getString("message"));
                    return;
                }

                Toast.makeText(context, "Infomación insertada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, IniciarSesion.class);
                startActivity(intent);
                ((Activity)context).finish();
            } catch (JSONException e) {
                throw new RuntimeException(e);
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

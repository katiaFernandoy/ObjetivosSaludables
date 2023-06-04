package com.example.objetivossaludables.manager.api;


import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ApiHandler extends Thread {

    private final ApiInterface object;
    private final String URL;
    private final HashMap<String,String> PARAMS;

    public ApiHandler(ApiInterface object, String URL, HashMap<String,String> PARAMS){
        this.object = object; // Clase que tiene implementada la interfaz
        this.URL = URL; // Endpoint - URL donde está el php
        this.PARAMS = PARAMS; // Parámetros que le vamos a mandar al PHP
    }

    @Override
    public void run() {
        Looper.prepare(); // Lopper necesario para los toast
        //Metodo que llamamos desde un hilo secundario para no colapsar el hilo principal
        object.returnResponse(handleResponse());
    }

    public JSONObject handleResponse(){
        // Respues del servidor
        String response = new RequestHandler().sendPostRequest(URL,PARAMS);

        try{
            JSONObject json = new JSONObject(response); // Se transforma a JSON
            //Logs de información
            Log.i("ObjS_mensaje",json.getString("message"));
            Log.i("ObjS_respuesta", response);

            return json;

        }catch (JSONException json){
            // Mensajes en el logcat de error
            Log.e("ObjS_JSON_error", response);
            Log.e("ObjS_JSON_error", json.getMessage());
            json.getStackTrace();
            try {
                // Reenviar un JSON con los códigos de error
                return new JSONObject()
                        .put("error", true)
                        .put("message",json.getMessage());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
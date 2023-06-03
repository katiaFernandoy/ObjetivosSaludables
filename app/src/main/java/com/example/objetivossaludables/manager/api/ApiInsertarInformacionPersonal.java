package com.example.objetivossaludables.manager.api;

import com.example.objetivossaludables.modelo.InformacionPersonal;
import com.example.objetivossaludables.pages.configuracion.ConfgPersonal;
import com.example.objetivossaludables.pages.inicioapp.InsertarInfoUsuario;

import android.os.AsyncTask;

public class ApiInsertarInformacionPersonal extends InsertarInfoUsuario {


    public ApiInsertarInformacionPersonal() {
        new AssyncTaskAPI().execute();
    }

    class AssyncTaskAPI extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            return null;
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

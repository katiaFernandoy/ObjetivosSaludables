package com.example.objetivossaludables.manager.api;

import static com.example.objetivossaludables.valoresestaticos.URLs.URL_MODIFICAR_INFO_PERSONAL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.objetivossaludables.modelo.InformacionPersonal;
import com.example.objetivossaludables.pages.configuracion.ConfgPersonal;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

public class ApiModificarInfoPersonal extends ConfgPersonal {
    private InformacionPersonal modInfoPersonal;

    public ApiModificarInfoPersonal() {
        new AssyncTaskAPI().execute();
    }

    class AssyncTaskAPI extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {
                modInfoPersonal = new InformacionPersonal(
                        getInfoPersonal().getEmail(),
                        txt_nombreMod.getText().toString(),
                        Double.parseDouble(txt_pesoMod.getText().toString()),
                        Integer.parseInt(txt_alturaMod.getText().toString()),
                        spn_generoModif.getSelectedItem().toString(),
                        dateFormat.parse(txt_fechaMod.getText().toString())
                );
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            HashMap<String, String> params = new HashMap<>();
            params.put(EMAIL, getInfoPersonal().getEmail());
            params.put("nombre", compararAntiguoValorConNuevo(getInfoPersonal().getNombre(), modInfoPersonal.getNombre()));
            params.put("peso", compararAntiguoValorConNuevo(getInfoPersonal().getPeso(), modInfoPersonal.getPeso()));
            params.put("altura", compararAntiguoValorConNuevo(getInfoPersonal().getAltura(), modInfoPersonal.getAltura()));
            params.put("genero", compararAntiguoValorConNuevo(getInfoPersonal().getGenero(), modInfoPersonal.getGenero()));
            params.put("fechaNacimiento", compararAntiguoValorConNuevo(getInfoPersonal().getFechaNacimiento(), modInfoPersonal.getFechaNacimiento()));

            return new RequestHandler().sendPostRequest(URL_MODIFICAR_INFO_PERSONAL, params);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("error")) {
                    Toast.makeText(context, "Error al modificara la información", Toast.LENGTH_SHORT).show();
                    System.out.println(jsonObject.getString("message"));
                    return;
                }

                Toast.makeText(context, "Infomación modificada", Toast.LENGTH_SHORT).show();
                setInfoPersonal(modInfoPersonal);

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

        private String compararAntiguoValorConNuevo(String antiguo, String nuevo) {
            return antiguo.equals(nuevo) ? "" : nuevo;
        }

        private String compararAntiguoValorConNuevo(Double antiguo, Double nuevo) {
            return antiguo.equals(nuevo) ? "" : String.valueOf(nuevo);
        }

        private String compararAntiguoValorConNuevo(Integer antiguo, Integer nuevo) {
            return antiguo.equals(nuevo) ? "" : String.valueOf(nuevo);
        }

        private String compararAntiguoValorConNuevo(Date antiguo, Date nuevo) {
            return antiguo.equals(nuevo) ? "" : dateFormat.format(nuevo);
        }
    }
}

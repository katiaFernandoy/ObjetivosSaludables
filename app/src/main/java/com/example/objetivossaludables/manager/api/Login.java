package com.example.objetivossaludables.manager.api;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.PASSWORD;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.STATUS;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_LOGIN;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.objetivossaludables.pages.Menu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.logging.Logger;

public class Login extends AsyncTask<Void, Void, String> {


    private final String email;
    private final String password;
    public static final String ID = "id";
    public static String sId_usuario;
    private final Context context;
    private final SharedPreferences sharedPreferences;
    ProgressDialog pdLoading;

    public Login(String email, String password, Context context, ProgressDialog pdLoading) {
        this.email = email;
        this.password = password;
        this.context = context;
        this.pdLoading = pdLoading;
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
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

            //returning the response
            return requestHandler.sendPostRequest(URL_LOGIN, params);
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
                    //sId_usuario = obj.getString("ID");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(EMAIL, email);
                    //editor.putString(ID, sId_usuario);
                    editor.putBoolean(STATUS, true);
                    editor.apply();
                    ((Activity) context).finish();
                    Toast.makeText(context.getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, Menu.class);
                    context.startActivity(intent);

                } else
                    Toast.makeText(context.getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON mal formado", e.getMessage());
                Log.e("JSON Response", s);
                Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
            }
        }

}

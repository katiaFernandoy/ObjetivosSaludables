package com.example.objetivossaludables.modelo;

import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.PASSWORD;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.STATUS;
import static com.example.objetivossaludables.valoresestaticos.URLs.URL_LOGIN;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.objetivossaludables.pagesLogin.IniciarSesion;
import com.example.objetivossaludables.pagesLogin.Menu;
import com.example.objetivossaludables.pagesLogin.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AsyncTask<Void, Void, String> {


    private final String email;
    private final String password;
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
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(EMAIL, email);
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
                Toast.makeText(context, "Exception: " + e, Toast.LENGTH_LONG).show();
            }
        }

}
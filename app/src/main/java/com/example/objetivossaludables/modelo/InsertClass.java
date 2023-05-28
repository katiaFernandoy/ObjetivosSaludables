package com.example.objetivossaludables.modelo;

import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.SharedPreferences.ID;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.objetivossaludables.pagesLogin.GrabarSuenio;

public class InsertClass extends AsyncTask<Void, Void, String> {

    private final String suenio;
    private final String day;
    public static final String ID = "id";
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    ProgressDialog pdLoading;
    SharedPreferences sharedPreferences;

    public InsertClass(String suenio, String day, Context context, ProgressDialog pdLoading) {
        this.suenio = suenio;
        this.day = day;
        this.context=context;
        this.pdLoading = pdLoading;
        sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}

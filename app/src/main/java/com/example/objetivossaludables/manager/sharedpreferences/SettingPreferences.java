package com.example.objetivossaludables.manager.sharedpreferences;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.COLOR;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.IDIOMA;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.SONIDO;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.VIBRACION;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreferences {

    private final SharedPreferences sp;

    public SettingPreferences(Context c){
        sp = c.getSharedPreferences(MY_PREFERENCES,Context.MODE_PRIVATE);
    }

    public boolean getSoundState(){
        return sp.getBoolean(SONIDO,false);
    }

    public void setSound(Boolean estado){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(SONIDO,estado);
        edit.apply();
    }

    public boolean getVibrateState(){
        return sp.getBoolean(VIBRACION,false);
    }

    public void setVibrate(Boolean estado){
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(VIBRACION,estado);
        edit.apply();
    }

    //0 - Español, 1 - Inglés
    public int getIdioma(){
        return sp.getInt(IDIOMA,0);
    }

    public void setIdioma(int idioma){
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(IDIOMA,idioma);
        edit.apply();
    }

    // 0 = VERDE, 1 = ROSA, 2 = AZUL
    public int getColor(){
        return sp.getInt(COLOR,0);
    }

    public void setColor(int color){
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(COLOR,color);
        edit.apply();
    }
}

package com.example.objetivossaludables.manager.sharedpreferences;

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

    public String getIdioma(){
        return sp.getString(IDIOMA,"Español");
    }

    public void setIdioma(String idioma){
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(IDIOMA,idioma);
        edit.apply();
    }
}

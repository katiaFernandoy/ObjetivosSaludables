package com.example.objetivossaludables.manager.sharedpreferences;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.ID;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.ID_USU;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.STATUS;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private final SharedPreferences preferences;

    public UserPreferences(Context c){
        preferences = c.getSharedPreferences(MY_PREFERENCES,Context.MODE_PRIVATE);
    }

    public String getUserEmail(){
        return preferences.getString(EMAIL,"");
    }

    public Integer getUserId(){
        return preferences.getInt(ID_USU,0);
    }

    public void setUserEmail(String email,int id_usu){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(EMAIL,email);
        edit.putInt(ID_USU,id_usu);
        edit.apply();
    }

    public boolean getUserStatus(){
        return preferences.getBoolean(STATUS,false);
    }

    public void setUserStatus(boolean status){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(STATUS,status);
        edit.apply();
    }

    public void clearPreferences(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
    }

    public void cerrarSesion(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(EMAIL,"");
        edit.putInt(ID,-1);
        edit.putBoolean(STATUS,false);
        edit.apply();

    }

    public int getMenuSeleccionado(){
        return preferences.getInt("menu",0);
    }

    public void setMenuSeleccionado(int menu){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt("menu",menu);
        edit.apply();
    }

    public String getObjetivoSuenio(){ return preferences.getString("suenio","8");
    }

    public String getObjetivoPasos(){
        return preferences.getString("pasos","10000");
    }

    public String getObjetivoEntrenar(){ return preferences.getString("entrenamiento","2");
    }

    public Boolean getObjetivoPeso(){ return preferences.getBoolean("peso",true);
    }

    public void setObjetivoPeso(boolean objPeso) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean("peso", objPeso);
        edit.apply();
    }

    public void setObjectives(String suenio, String entrenamiento, String pasos, boolean objPeso){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("suenio", suenio.equals("")?"8":suenio);
        edit.putString("entrenamiento", entrenamiento.equals("")?"2":entrenamiento);
        edit.putString("pasos", pasos.equals("")?"10000":pasos);
        edit.putBoolean("peso", objPeso);
        edit.apply();
    }
}

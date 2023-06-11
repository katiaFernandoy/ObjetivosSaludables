package com.example.objetivossaludables.manager.sharedpreferences;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.APELLIDO;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.ID_USU;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.MY_PREFERENCES;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.NOMBRE;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.STATUS;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.objetivossaludables.modelo.Usuario;

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

    public Usuario getUser(){
        return new Usuario(
                preferences.getString(EMAIL,""),
                preferences.getString(NOMBRE,""),
                preferences.getString(APELLIDO,""));
    }

    public void setUsuario(Usuario usu){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(EMAIL,usu.getEmail());
        edit.putString(NOMBRE,usu.getNombre());
        edit.putString(APELLIDO,usu.getApellido());
        edit.putBoolean(STATUS,true);
        edit.apply();
    }
}

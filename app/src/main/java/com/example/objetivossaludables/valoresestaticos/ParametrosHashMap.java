package com.example.objetivossaludables.valoresestaticos;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.PASSWORD;

import java.util.HashMap;

public class ParametrosHashMap {

    public static HashMap<String,String> getParamsLogin(String email,String password){
        return new HashMap<String,String>(){{
            put(EMAIL,email);
            put(PASSWORD,password);
        }};
    }

    public static HashMap<String,String> getParamsInfo(
            String email, String nombre, String apellido, String password){
        return new HashMap<String,String>(){{
            put(EMAIL,email);
            put("nombre",nombre);
            put("apellido",apellido);
            put("password",password);
        }};
    }

    public static HashMap<String,String> getParamsInfoPersonal(String email){
        return new HashMap<String,String>(){{
            put(EMAIL,email);
        }};
    }

    public static HashMap<String,String> getParamsInfoPersonal(
            String email, String peso, String altura, String fechaNacimiento, String genero){
        return new HashMap<String,String>(){{
            put(EMAIL,email);
            put("peso",peso);
            put("altura",altura);
            put("fechaNacimiento",fechaNacimiento);
            put("genero",genero);
        }};
    }

    public static HashMap<String,String> getParamsInfoPersonal(
            String email, String nombre, String peso, String altura, String fechaNacimiento, String genero){
        return new HashMap<String,String>(){{
            put(EMAIL,email);
            put("nombre",nombre);
            put("peso",peso);
            put("altura",altura);
            put("fechaNacimiento",fechaNacimiento);
            put("genero",genero);
        }};
    }
}

package com.example.objetivossaludables.valoresestaticos;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.EMAIL;
import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.ID;
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

    public static HashMap<String,String> getParamsModifyPassword(String email,String oldPwd,String newPwd){
        return new HashMap<String,String>(){{
            put(EMAIL, email);
            put("oldPassword", oldPwd);
            put("newPassword", newPwd);
        }};
    }

    public static HashMap<String,String> getParamsOTP(int id_usu, String otp){
        return new HashMap<String,String>(){{
            put(ID, String.valueOf(id_usu));
            put("otp", otp);
        }};
    }

    public static HashMap<String,String> getParamsOTP(String email, String newPassword){
        return new HashMap<String,String>(){{
            put(EMAIL, email);
            put("newPassword", newPassword);
            put("isOptionOTP", "true");
        }};
    }

    public static HashMap<String,String> getParamsId(String id_usu){
        return new HashMap<String,String>(){{
            put(ID, id_usu);
        }};
    }

    public static HashMap<String,String> getParamsSuenio(String id_usu, String horas, String dia){
        return new HashMap<String,String>(){{
            put(ID, id_usu);
            put("horasSuenio", horas);
            put("diaSemana", dia);
        }};
    }

    public static HashMap<String,String> getParamsPasos(String id_usu, String pasos, String dia){
        return new HashMap<String,String>(){{
            put(ID, id_usu);
            put("pasos", pasos);
            put("diaSemana", dia);
        }};
    }

    public static HashMap<String,String> getParamsEntrenamiento(String id_usu, String entrenamiento, String dia){
        return new HashMap<String,String>(){{
            put(ID, id_usu);
            put("entrenamiento", entrenamiento);
            put("diaSemana", dia);
        }};
    }

    public static HashMap<String,String> getParamsObjetivos(String id_usu, String entrenamiento, String suenio, String pasos){
        return new HashMap<String,String>(){{
            put(ID, id_usu);
            put("horasEntrenamiento", entrenamiento);
            put("suenio", suenio);
            put("pasos", pasos);
        }};
    }
}

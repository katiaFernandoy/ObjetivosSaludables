package com.example.objetivossaludables.valoresestaticos;

import static com.example.objetivossaludables.valoresestaticos.ValuesPreferences.DATE_FORMAT;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Objects;

public class Verificaciones {

    public static boolean isInteger(String num){
        try{
            Integer.parseInt(num);
            return true;
        }catch (NumberFormatException ps){
            return false;
        }
    }

    public static boolean isDouble(String num){
        try{
            Double.parseDouble(num);
            return true;
        }catch (NumberFormatException ps){
            return false;
        }
    }

    public static boolean isDate(String fecha){
        try{
            DATE_FORMAT.parse(fecha);
            return true;
        }catch (ParseException ps){
            return false;
        }
    }

    public static String getTexto(EditText txt){
        return txt.getText().toString().trim();
    }

    public static String getTexto(TextInputEditText txt){
        return Objects.requireNonNull(txt.getText()).toString().trim();
    }
}

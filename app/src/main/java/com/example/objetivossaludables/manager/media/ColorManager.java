package com.example.objetivossaludables.manager.media;

import android.content.Context;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.SettingPreferences;

public class ColorManager {

    public static void setColorState(Context c, View v){
        v.setBackground(AppCompatResources.getDrawable(c,getColor(c)));
    }

    private static int getColor(Context c){
        switch (new SettingPreferences(c).getColor()){
            case 0:
                return R.drawable.button_style_green;
            case 1:
                return R.drawable.button_style_pink;
            default:
                return R.drawable.button_style_blue;
        }
    }
}

package com.example.objetivossaludables.manager.media;

import android.content.Context;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.SettingPreferences;

import java.util.List;

public class ColorManager {

    public static void setColorState(Context c, List<View> listViews){
        for(View view : listViews){
            view.setBackground(AppCompatResources.getDrawable(c,getColor(c)));
        }
    }

    private static int getColor(Context c){
        switch (new SettingPreferences(c).getColor()){
            case 1:
                return R.drawable.button_style_pink;
            case 2:
                return R.drawable.button_style_blue;
            default:// 0 default
                return R.drawable.button_style_green;
        }
    }
}
